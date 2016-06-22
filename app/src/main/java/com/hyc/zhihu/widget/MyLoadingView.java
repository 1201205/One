package com.hyc.zhihu.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/22.
 */
public class MyLoadingView extends View {
    private Paint mBlue, mYellow, mRed, mGreen;
    private int mWidth, mHeight;
    private static final int MIN_WIDTH = 100;
    private static final float SPACE_RATIO = 0.3f;
    private ArrayList<Point> mCenters;
    private int mRadius;
    private float mFrag=0.3f;
    private float mArgument;
    private Matrix mMatrix;
    private Camera mCamera;

    public MyLoadingView(Context context) {
        this(context, null);
    }

    public MyLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
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
        mHeight = h;
        initCircles();

        mArgument = w/2-mRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.save();
        mMatrix.setRotate(2700*mFrag,mWidth/2,mWidth/2);
        canvas.concat(mMatrix);
        float f = mArgument * mFrag;
        setPaintAlpha();
        canvas.drawCircle(mCenters.get(0).x , mCenters.get(0).y+f , mRadius, mBlue);
        canvas.drawCircle(mCenters.get(1).x-f, mCenters.get(1).y , mRadius, mGreen);
        canvas.drawCircle(mCenters.get(2).x , mCenters.get(2).y-f , mRadius, mRed);
        canvas.drawCircle(mCenters.get(3).x+f , mCenters.get(3).y , mRadius, mYellow);

//        canvas.restore();

    }

    private void rotate(Canvas canvas) {
//        canvas.rotate((float) (mFrag*2),mWidth/2,mWidth/2);
        canvas.setMatrix(mMatrix);
        mMatrix.setTranslate(-mWidth/2,-mWidth/2);
//        mMatrix.setRotate((float) (mFrag*Math.PI),mWidth/2,mWidth/2);
    }
    private ValueAnimator mAnimator;
    public void startAnim(){
        mAnimator= ObjectAnimator.ofFloat(0,1);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
               mFrag= animation.getAnimatedFraction();
                invalidate();
            }
        });
        mAnimator.setDuration(3000);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }
    public void stopAnim(){
        mAnimator.end();
        mAnimator.removeAllListeners();
    }
    private void setPaintAlpha() {
        int a=(int) (255*0.1+255*0.9*(1-mFrag));
        mGreen.setAlpha(a);
        mBlue.setAlpha(a);
        mRed.setAlpha(a);
        mYellow.setAlpha(a);
    }

    private void init() {
        mBlue = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBlue.setColor(Color.BLUE);
        mYellow = new Paint(Paint.ANTI_ALIAS_FLAG);
        mYellow.setColor(Color.YELLOW);
        mRed = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRed.setColor(Color.RED);
        mGreen = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGreen.setColor(Color.GREEN);
        mMatrix=new Matrix();
        mCamera=new Camera();


    }
    public void setFrag(float f){
        mFrag=f;
        invalidate();
    }
    private void initCircles() {
        if (mCenters != null) {
            mCenters.clear();
        } else {
            mCenters = new ArrayList<>();
        }
        mRadius = (int) ((mWidth * (1 - SPACE_RATIO) / 4));
        mCenters.add(new Point(mWidth/2, mRadius));
        mCenters.add(new Point(mWidth - mRadius, mWidth/2));
        mCenters.add(new Point(mWidth/2, mWidth - mRadius));
        mCenters.add(new Point(mRadius, mWidth/2));
    }

    private int getPx(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp + 0.5f);
    }
}
