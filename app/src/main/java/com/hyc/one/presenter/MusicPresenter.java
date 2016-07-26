package com.hyc.one.presenter;

import android.text.TextUtils;

import com.hyc.one.base.BasePresenter;
import com.hyc.one.beans.BaseBean;
import com.hyc.one.beans.Comment;
import com.hyc.one.beans.CommentWrapper;
import com.hyc.one.beans.IDList;
import com.hyc.one.beans.Song;
import com.hyc.one.beans.music.Music;
import com.hyc.one.beans.music.MusicRelate;
import com.hyc.one.beans.music.MusicRelateListBean;
import com.hyc.one.net.Requests;
import com.hyc.one.presenter.base.IMusicPresenter;
import com.hyc.one.view.MusicView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/23.
 */
public class MusicPresenter extends BasePresenter<MusicView> implements IMusicPresenter {
    private List<String> mIDs;
    private List<Music> mMusics;
    private List<MusicRelateListBean> mRelateBeans;

    public MusicPresenter(MusicView view) {
        super(view);
    }

    @Override
    public void getAndShowContent() {
        mView.showLoading();
        final List<Song> songs = new ArrayList<>();
        //// FIXME: 2016/7/26 用法略low
        mCompositeSubscription.add(
                Requests.getApi().getMusicIds("0").map(new Func1<IDList, Observable<BaseBean<Music>>>() {
                    @Override
                    public Observable<BaseBean<Music>> call(IDList idList) {
                        mIDs = idList.getData();
                        mMusics = new ArrayList<Music>();
                        mRelateBeans = new ArrayList<MusicRelateListBean>();
                        for (int i = 0; i < mIDs.size(); i++) {
                            mRelateBeans.add(new MusicRelateListBean(mIDs.get(i), null, null, null));
                        }
                        return Observable.from(mIDs).flatMap(new Func1<String, Observable<BaseBean<Music>>>() {
                            @Override
                            public Observable<BaseBean<Music>> call(String s) {
                                return Requests.getApi().getMusicContentByID(s);
                            }
                        });
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Observable<BaseBean<Music>>>() {
                    @Override
                    public void call(Observable<BaseBean<Music>> musicWrapperObservable) {
                        musicWrapperObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseBean<Music>>() {
                            @Override
                            public void call(BaseBean<Music> musicWrapper) {
                                mMusics.add(musicWrapper.getData());
                                songs.add(new Song(musicWrapper.getData().getTitle(), musicWrapper.getData().getMusic_id()));
                                if (mMusics.size() == mIDs.size()) {
                                    mMusics.add(null);
                                    mView.setAdapter(mMusics, mRelateBeans);
                                    showCurrentRelate(0);
                                    showCurrentComment(0);
                                    mView.setSongList(songs);
                                    mView.dismissLoading();
                                }
                            }
                        });
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                }));
    }

    @Override
    public void showCurrentRelate(final int page) {
        mCompositeSubscription.add(
                Requests.getApi().getMusicRelateByID(mIDs.get(page)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseBean<List<MusicRelate>>>() {
                    @Override
                    public void call(BaseBean<List<MusicRelate>> musicRelateWrapper) {
                        mView.setRelate(page, musicRelateWrapper.getData());
                    }
                }));
    }

    @Override
    public void showCurrentComment(final int page) {
        mCompositeSubscription.add(

                Requests.getApi().getMusicCommentsByIndex(mIDs.get(page), "0").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<BaseBean<CommentWrapper>, List<Comment>[]>() {
                    @Override
                    public List<Comment>[] call(BaseBean<CommentWrapper> comments) {
                        List<Comment> hot = new ArrayList<Comment>();
                        List<Comment> normal = new ArrayList<Comment>();
                        List<Comment> all = comments.getData().getData();
                        int count = all.size();
                        for (int i = 0; i < count; i++) {
                            Comment c = all.get(i);
                            if (all.get(i).getType() == 0) {
                                hot.add(c);
                            } else {
                                normal.add(c);
                            }
                        }
                        List<Comment>[] types = new List[2];
                        types[0] = hot;
                        types[1] = normal;

                        return types;
                    }
                }).subscribe(new Action1<List<Comment>[]>() {
                    @Override
                    public void call(List<Comment>[] comments) {
                        String last = null;
                        if (comments[1].size() > 0) {
                            last = comments[1].get(comments[1].size() - 1).getId();
                        } else {
                            last = comments[0].get(comments[0].size() - 1).getId();
                        }
                        mView.setComment(page, comments[0], comments[1]);
                    }
                }));
    }

    @Override
    public void showRefreshComment(final int page, final String index) {
        if (TextUtils.isEmpty(index)) {
            return;
        }
        mCompositeSubscription.add(
                Requests.getApi().getMusicCommentsByIndex(mIDs.get(page), index).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseBean<CommentWrapper>>() {
                    @Override
                    public void call(BaseBean<CommentWrapper> comments) {
                        mView.refreshCommentList(page, comments.getData().getData());
                    }
                }));
    }

    @Override
    public void showCurrentCommentAndRelate(int page) {
        showCurrentComment(page);
        showCurrentRelate(page);
    }
}
