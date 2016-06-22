package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.beans.Comment;
import com.hyc.zhihu.beans.Comments;
import com.hyc.zhihu.beans.music.MusicRelateWrapper;
import com.hyc.zhihu.beans.music.MusicWrapper;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IMusicItemPresenter;
import com.hyc.zhihu.view.MusicItemView;

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
        Requests.getApi().getMusicContentByID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<MusicWrapper>() {
            @Override
            public void call(MusicWrapper musicWrapper) {
                mView.showContent(musicWrapper.getData());
                getRelate();
                getAndShowComments();
                mView.dismissLoading();
            }
        });
    }

    @Override
    public void getRelate() {
        Requests.getApi().getMusicRelateByID(mID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<MusicRelateWrapper>() {
            @Override
            public void call(MusicRelateWrapper musicRelateWrapper) {
                mView.showRelate(musicRelateWrapper.getData());
            }
        });
    }

    @Override
    public void refreshComments() {
        Requests.getApi().getMusicCommentsByIndex(mID, mLastIndex).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Comments>() {
            @Override
            public void call(Comments comments) {
                List<Comment> c = comments.getData().getData();
                if (c != null && c.size() > 0) {
                    mLastIndex = c.get(c.size() - 1).getId();
                }
                mView.refreshComment(comments.getData().getData());
            }
        });
    }

    private String mLastIndex;

    private void getAndShowComments() {
        Requests.getApi().getMusicCommentsByIndex(mID, "0").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<Comments, List<Comment>[]>() {
            @Override
            public List<Comment>[] call(Comments comments) {
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
        });
    }
}
