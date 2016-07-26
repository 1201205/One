package com.hyc.one.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.hyc.one.R;

/**
 * Created by hyc on 2016/6/30.
 */
public class PureColorCircleView extends View {
    private static final int MIN_WIDTH = 20;
    private Paint mPaint;
    private int mRadius;
    private int mWidth;
    private int mColor;

    public PureColorCircleView(Context context) {
        this(context, null);
    }

    public PureColorCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PureColorCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typeArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.PureColorCircleView);
        mColor = typeArray.getColor(R.styleable.PureColorCircleView_background_color, 0);
        mRadius = typeArray.getDimensionPixelSize(R.styleable.PureColorCircleView_radius, 0);
        typeArray.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
        mPaint.setColor(mColor);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int min = getPx(MIN_WIDTH);
        if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(width, min);
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(height, min);
        }
        int s = Math.min(height, width);
        setMeasuredDimension(s, s);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        if (mRadius == 0) {
            mRadius = w / 2;
        } else if (mRadius > w / 2) {
            mRadius = w / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mWidth / 2, mRadius, mPaint);
    }

    private int getPx(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp + 0.5f);
    }
}
