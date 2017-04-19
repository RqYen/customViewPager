package com.example.rq.viewpage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class JikeViewPager extends ViewGroup {

    private GestureDetector gestureDetector;
    private Scroller scroller;
    private float downX;
    private int currIndex;
    private float mInterceptDownX;
    private float mInterceptDownY;

    public JikeViewPager(Context context) {
        this(context, null);
    }

    public JikeViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        scroller = new Scroller(context);
        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy((int) distanceX, 0);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (velocityX > 0) {
                    currIndex--;
                } else if (velocityX < 0) {
                    currIndex++;
                }
                setPageIndex(currIndex);
                return false;

            }
        });
    }

    public JikeViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(getWidth() * i, 0, getWidth() * i + getWidth(), getHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int tempIndex = currIndex;

                if (downX - event.getX() > getWidth() / 2) {
                    tempIndex++;
                } else if (event.getX() - downX >= getWidth() /2) {
                    tempIndex--;
                }
                setPageIndex(tempIndex);
                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInterceptDownX = ev.getX();
                mInterceptDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int distanceX = (int) Math.abs(mInterceptDownX - ev.getX());
                int distanceY = (int) Math.abs(mInterceptDownY - ev.getY());
                if (distanceX > distanceY && distanceX > 5) {
                    isIntercept = true;
                } else {
                    isIntercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return isIntercept;
    }

    public void setPageIndex(int index) {

        if (index < 0) {
            index = 0;
        }

        if (index > getChildCount() - 1) {
            index = getChildCount() - 1;
        }
        currIndex = index;

        int distance = currIndex * getWidth() - getScrollX();
        //scrollTo(distance,0);
        scroller.startScroll(getScrollX(), 0, distance, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }
}
