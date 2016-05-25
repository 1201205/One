package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.beans.Comments;
import com.hyc.zhihu.beans.music.MusicWrapper;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IMusicRelatePresenter;
import com.hyc.zhihu.view.MusicRelateView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ray on 16/5/25.
 */
public class MusicRelatePresenter extends BasePresenter<MusicRelateView> implements IMusicRelatePresenter {
    public MusicRelatePresenter(MusicRelateView view) {
        super(view);
    }
    private String mID;
    private String mLastIndex;
    @Override
    public void setContent(String id) {
        mID=id;
        Requests.getApi().getMusicContentByID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<MusicWrapper>() {
            @Override
            public void call(MusicWrapper musicWrapper) {
                mView.showContent(musicWrapper.getData());
                mLastIndex="0";
                showComment();

            }
        });
    }

    @Override
    public void showComment() {
        //http://v3.wufazhuce.com:8000/api/comment/praiseandtime/music/564/0?
        Requests.getApi().getMusicCommentsByIndex(mID,mLastIndex).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Comments>() {
            @Override
            public void call(Comments comments) {
                if (comments.getData()!=null&&comments.getData().getData().size()>0) {
                    mLastIndex=comments.getData().getData().get(comments.getData().getData().size()-1).getId();
                }
                mView.showList(comments.getData().getData());
            }
        });
    }
}
