package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.view.TestView;

/**
 * Created by ray on 16/4/19.
 */
public class MainPresenter extends BasePresenter<TestView> {
    public MainPresenter(TestView mTestView){
        super(mTestView);
    }
    public void onLoadFinished(){
        mView.showToast();
    }
}
