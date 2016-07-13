package com.hyc.zhihu.view;

import com.hyc.zhihu.base.BaseView;
import com.hyc.zhihu.beans.Essay;
import com.hyc.zhihu.beans.Question;
import com.hyc.zhihu.beans.Serial;

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
