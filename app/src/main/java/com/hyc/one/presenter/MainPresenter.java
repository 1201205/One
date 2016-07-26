package com.hyc.one.presenter;

import com.hyc.one.base.BasePresenter;
import com.hyc.one.view.TestView;

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
