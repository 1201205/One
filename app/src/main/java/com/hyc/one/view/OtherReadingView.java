package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.Essay;
import com.hyc.one.beans.Question;
import com.hyc.one.beans.Serial;

import java.util.List;

/**
 * Created by ray on 16/5/5.
 */
public interface OtherReadingView extends BaseView {
    void showEssays(List<Essay> essays);

    void showQuestions(List<Question> questions);

    void showSerials(List<Serial> serials);

    void noMore(int type);
}
