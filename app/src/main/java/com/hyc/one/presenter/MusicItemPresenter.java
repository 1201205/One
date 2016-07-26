package com.hyc.one.presenter;

import com.hyc.one.base.BasePresenter;
import com.hyc.one.beans.BaseBean;
import com.hyc.one.beans.Comment;
import com.hyc.one.beans.CommentWrapper;
import com.hyc.one.beans.music.Music;
import com.hyc.one.beans.music.MusicRelate;
import com.hyc.one.net.Requests;
import com.hyc.one.presenter.base.IMusicItemPresenter;
import com.hyc.one.view.MusicItemView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ray on 16/5/26.
 */
public class MusicItemPresenter extends BasePresenter<MusicItemView> implements IMusicItemPresenter {
    private String mID;

    public MusicItemPresenter(MusicItemView view) {
        super(view);
    }

    @Override
    public void getAndShowContent(String id) {
        mView.showLoading();
        mID = id;
        mCompositeSubscription.add(
                Requests.getApi().getMusicContentByID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseBean<Music>>() {
                    @Override
                    public void call(BaseBean<Music> musicWrapper) {
                        mView.showContent(musicWrapper.getData());
                        getRelate();
                        getAndShowComments();
                        mView.dismissLoading();
                    }
                }));
    }

    @Override
    public void getRelate() {
        mCompositeSubscription.add(
                Requests.getApi().getMusicRelateByID(mID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseBean<List<MusicRelate>>>() {
                    @Override
                    public void call(BaseBean<List<MusicRelate>> musicRelateWrapper) {
                        mView.showRelate(musicRelateWrapper.getData());
                    }
                }));
    }

    @Override
    public void refreshComments() {
        mCompositeSubscription.add(
                Requests.getApi().getMusicCommentsByIndex(mID, mLastIndex).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseBean<CommentWrapper>>() {
                    @Override
                    public void call(BaseBean<CommentWrapper> comments) {
                        List<Comment> c = comments.getData().getData();
                        if (c != null && c.size() > 0) {
                            mLastIndex = c.get(c.size() - 1).getId();
                        }
                        mView.refreshComment(comments.getData().getData());
                    }
                }));
    }

    private String mLastIndex;

    private void getAndShowComments() {
        mCompositeSubscription.add(
                Requests.getApi().getMusicCommentsByIndex(mID, "0").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<BaseBean<CommentWrapper>, List<Comment>[]>() {
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
                        if (normal.size() > 0) {
                            mLastIndex = normal.get(normal.size() - 1).getId();
                        }
                        return types;
                    }
                }).subscribe(new Action1<List<Comment>[]>() {
                    @Override
                    public void call(List<Comment>[] comments) {
                        mView.showHotComment(comments[0]);
                        mView.refreshComment(comments[1]);
                    }
                }));
    }
}
