package com.hyc.zhihu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/5/17.
 */
public class PullToRefreshListView extends ListView {
    private Scroller mScroller;
    private int mLeft, mTop, mBottom;
    private float mRawX,mRawY=-1;
    private boolean isPulling;
    private View mLoadingView;

    public PullToRefreshListView(Context context) {
        this(context, null);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public void setLoadingView(View v){
        mLoadingView=v;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (canPull(ev)) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mRawX = ev.getRawX();
                    mRawY = ev.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int dy=(int) (ev.getRawY()-mRawY  );
                    Log.e("dy",dy+"");
                    if (getBottom()+dy>mBottom) {
                        dy=mBottom-getBottom();
                }
                    Log.e("dy",dy+"");
//                    mLoadingView. offsetTopAndBottom( dy);
//                    offsetTopAndBottom( dy);
                    mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), 0 ,-dy);
                    mRawX = ev.getRawX();
                    mRawY = ev.getRawY();
                    break;
            }
            mRawX = ev.getRawX();
            mRawY = ev.getRawY();
            return true;
        } else {
            mRawX = ev.getRawX();
            mRawY = ev.getRawY();
            return super.onTouchEvent(ev);
        }

    }

    private boolean canPull(MotionEvent ev) {
        if (mRawY==-1f) {
            mRawY=0;
        }
        int lastPosition = getAdapter().getCount() - 1;
        int last = getLastVisiblePosition();
         int d= (int) (ev.getRawY()-mRawY);
        Log.e("d",d+"");
        if (last == lastPosition) {
            int first=getFirstVisiblePosition();

            if (getBottom()<=mBottom&&getChildAt(last - first).getBottom()-getBottomPaddingOffset() >= mBottom) {
                Log.e("test1",getBottom()+"");
                if (getBottom()==mBottom&&ev.getRawY()-mRawY>0) {
                    return false;
                }else{
                    return true;
                }

            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mLeft = l;
        mTop = t;
        mBottom = b;

    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }
}
