package com.hyc.one.presenter;

import com.hyc.one.base.BasePresenter;
import com.hyc.one.beans.BaseBean;
import com.hyc.one.beans.movie.MovieStory;
import com.hyc.one.beans.movie.MovieStoryWrapper;
import com.hyc.one.net.Requests;
import com.hyc.one.presenter.base.IMovieStoryPresenter;
import com.hyc.one.view.MovieStoryView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ray on 16/6/6.
 */
public class MovieStoryPresenter extends BasePresenter<MovieStoryView> implements IMovieStoryPresenter {
    private String mID;
    private String mLastIndex;

    public MovieStoryPresenter(MovieStoryView view) {
        super(view);
    }

    @Override
    public void getAndShowList(String id) {
        mView.showLoading();
        mID = id;
        mCompositeSubscription.add(

                Requests.getApi().getMovieStoryByID(id, "0", "0").map(new Func1<BaseBean<MovieStoryWrapper>, List<MovieStory>>() {
                    @Override
                    public List<MovieStory> call(BaseBean<MovieStoryWrapper> movieStoryWrapperBaseBean) {
                        if (movieStoryWrapperBaseBean.getData() != null && movieStoryWrapperBaseBean.getData().getData() != null) {
                            List<MovieStory> stories = movieStoryWrapperBaseBean.getData().getData();
                            int c = stories.size();
                            if (c > 0) {
                                mLastIndex = stories.get(c - 1).getId();
                                return stories;
                            }
                        }
                        return null;
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<MovieStory>>() {
                    @Override
                    public void call(List<MovieStory> stories) {
                        if (stories != null) {
                            mView.showList(stories);
                        }
                        mView.dismissLoading();
                    }
                }));
    }

    @Override
    public void refreshList() {
        mCompositeSubscription.add(

                Requests.getApi().getMovieStoryByID(mID, "0", mLastIndex).map(new Func1<BaseBean<MovieStoryWrapper>, List<MovieStory>>() {
                    @Override
                    public List<MovieStory> call(BaseBean<MovieStoryWrapper> movieStoryWrapperBaseBean) {
                        if (movieStoryWrapperBaseBean.getData() != null && movieStoryWrapperBaseBean.getData().getData() != null) {
                            List<MovieStory> stories = movieStoryWrapperBaseBean.getData().getData();
                            int c = stories.size();
                            if (c > 0) {
                                mLastIndex = stories.get(c - 1).getId();
                                return stories;
                            }
                        }
                        return null;
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<MovieStory>>() {
                    @Override
                    public void call(List<MovieStory> movieStoryWrapperBaseBean) {
                        mView.refreshList(movieStoryWrapperBaseBean);

                    }
                }));
    }
}
