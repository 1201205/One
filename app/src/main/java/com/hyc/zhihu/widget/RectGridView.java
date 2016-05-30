package com.hyc.zhihu.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class RectGridView extends View {
    private Paint mPaint;
    public RectGridView(Context context) {
        this(context,null);
    }

    public RectGridView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(AppUtil.getColor(R.color.google_blue));


    }
    private int mHalf;
    private int mOneThrid;
    private int mFirstMiddleY;
    private int mSecondMiddleY;
    private int middleX1;
    private int middleX2;
    private int middleX3;
    private int middleX4;
    private int middleX5;
    private int[] middle=new int[5];
    private int mDefaultHeight=AppUtil.dip2px(108);
    private int mDefaultWidth=AppUtil.dip2px(540);
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=getSize(mDefaultWidth,widthMeasureSpec);
        mHeight=getSize(mDefaultHeight,heightMeasureSpec);
        setMeasuredDimension(mWidth,mHeight);
        initLines();
    }
    private void initLines() {
        mLines=new ArrayList<>();
        int h=2*mBigWidth;
        int halfHeight=mHeight/2;
        mHalf=mWidth/2;
        mOneThrid=mWidth/3;
        int s=mWidth-4*mBigWidth;
        int real=s/3;
        Point p1=new Point(real+h,h);
        Point p2=new Point(real+h,halfHeight);
        mLines.add(new Point[]{p1,p2});
        Point p3=new Point(2*real+h,h);
        Point p4=new Point(2*real+h,halfHeight);
        mLines.add(new Point[]{p3,p4});
        Point p5=new Point(mHalf,mHeight-h);
        Point p6=new Point(mHalf,halfHeight);
        mLines.add(new Point[]{p5,p6});
        Point p7=new Point(h,halfHeight);
        Point p8=new Point(mWidth-h,halfHeight);
        mLines.add(new Point[]{p7,p8});
        mOneThrid=(mWidth-4*mBigWidth)/3;
        middle[0]=h+mOneThrid/2;
        middle[1]=h+mOneThrid/2+mOneThrid;
        middle[2]=h+mOneThrid/2+2*mOneThrid;
        middle[3]=h+mHalf/2;
        middle[4]=h+mHalf/2+mHalf;
        mTags=new ArrayList<>();
        mTags.add("111111");
        mTags.add("111");
        mTags.add("1");
        mTags.add("1118");
        mTags.add("112218");
    }

    private int getSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }
    private int mBigWidth=AppUtil.dip2px(2);
    private int mThinWidth=AppUtil.dip2px(1);
    private int mWidth;
    private int mHeight;
    private List<Point[]> mLines;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(mBigWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0,0,mWidth,mHeight,mPaint);
        mPaint.setStrokeWidth(mThinWidth);
        canvas.drawRect(2*mBigWidth,2*mBigWidth,mWidth-2*mBigWidth,mHeight-2*mBigWidth,mPaint);
        mPaint.setStrokeWidth(1);
        for(Point[] p:mLines) {
            canvas.drawLine(p[0].x,p[0].y,p[1].x,p[1].y,mPaint);
        }
        drawText(canvas);

    }
    private ArrayList<String> mTags;
    private void drawText(Canvas canvas) {
        mPaint.setTextSize(AppUtil.sp2px(13));
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        for (int i=0;i<mTags.size();i++) {
            int baseline=0;
            if (i < 3) {
                baseline = (mLines.get(i)[0].y + mLines.get(i)[1].y - fontMetrics.bottom - fontMetrics.top) / 2;
            } else {
                baseline = (mLines.get(2)[0].y + mLines.get(2)[1].y - fontMetrics.bottom - fontMetrics.top) / 2;
            }
            canvas.drawText(mTags.get(i),middle[i]-mPaint.measureText(mTags.get(i))/2,baseline,mPaint);
        }
    }
}
