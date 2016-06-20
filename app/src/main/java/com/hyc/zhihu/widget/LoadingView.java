package com.hyc.zhihu.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ray on 16/6/20.
 */
public class LoadingView extends View {
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public LoadingView(Context context) {
        this(context, null);
    }


    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
}
