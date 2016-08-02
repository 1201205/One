package com.hyc.one.presenter.base;

import com.hyc.one.beans.OnePictureData;

/**
 * Created by ray on 16/4/19.
 */
public interface IPicturePresenter {
    void getPictureIdsAndFirstItem();
    void getPictureById(String id);
    void checkAndGetPictureIds();
    void gotoPosition(int position);

}
