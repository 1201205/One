package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.DefaultTransformer;
import com.hyc.zhihu.base.ExceptionAction;
import com.hyc.zhihu.beans.BaseBean;
import com.hyc.zhihu.beans.Comment;
import com.hyc.zhihu.beans.CommentWrapper;
import com.hyc.zhihu.beans.movie.MovieContent;
import com.hyc.zhihu.beans.movie.MovieStoryWrapper;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IMovieContentPresenter;
import com.hyc.zhihu.view.MovieContentView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ray on 16/5/30.
 */
public class MovieContentPresenter extends BasePresenter<MovieContentView>
    implements IMovieContentPresenter {
    public MovieContentPresenter(MovieContentView view) {
        super(view);
    }


    private String mID;
    private String mLastIndex;


    @Override
    public void getAndShowContent(String id) {
        mView.showLoading();
        mID = id;
        mCompositeSubscription.add(
            Requests.getApi()
                .getMovieContentByID(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean<MovieContent>>() {
                    @Override
                    public void call(BaseBean<MovieContent> movieContentBaseBean) {
                        mView.showContent(movieContentBaseBean.getData());
                    }
                }));
        mCompositeSubscription.add(
            Requests.getApi()
                .getMovieStoryByID(id, "1", "0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean<MovieStoryWrapper>>() {
                    @Override
                    public void call(BaseBean<MovieStoryWrapper> movieStoryWrapperBaseBean) {
                        mView.showStory(movieStoryWrapperBaseBean.getData());
                    }
                }));
        mCompositeSubscription.add(
            Requests.getApi()
                .getMovieCommentsByIndex(id, "0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<BaseBean<CommentWrapper>, List<Comment>[]>() {
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
                })
                .subscribe(new Action1<List<Comment>[]>() {
                    @Override
                    public void call(List<Comment>[] comments) {
                        mView.showHotComment(comments[0]);
                        mView.refreshComment(comments[1]);
                        mView.dismissLoading();
                    }
                }));
    }


    @Override
    public void refreshComments() {
        mCompositeSubscription.add(
            Requests.getApi()
                .getMovieCommentsByIndex(mID, mLastIndex)
                .compose(new DefaultTransformer<BaseBean<CommentWrapper>, CommentWrapper>())
                .subscribe(new Action1<CommentWrapper>() {
                    @Override
                    public void call(CommentWrapper comments) {
                        List<Comment> c = comments.getData();
                        if (c == null || c.size() == 0) {
                            mView.showNoComments();
                            return;
                        }
                        mLastIndex = c.get(c.size() - 1).getId();
                        mView.refreshComment(c);
                    }
                }, new ExceptionAction() {
                    @Override
                    public void onNothingGet() {
                        mView.showNoComments();
                    }
                }));
    }

}
