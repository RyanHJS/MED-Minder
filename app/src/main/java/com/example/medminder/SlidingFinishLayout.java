package com.example.medminder;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class SlidingFinishLayout extends RelativeLayout {
    /**
     * minimum slice distance
     */
    private int mTouchSlop;

    private Scroller mScroller;

    /**
     * parent view set up
     */
    private ViewGroup mParentView;


    private int downX;
    private int downY;

    private int tempX;
    private int viewWidth;
    /**
     * check is it sliding
     */
    private boolean isSliding;

    private OnSlidingFinishListener onSlidingFinishListener;
    private boolean isFinish;
    public SlidingFinishLayout(Context context) {
        super(context);
    }

    public SlidingFinishLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScroller = new Scroller(getContext());
    }

    public interface OnSlidingFinishListener {
        /**
         * delete ole page
         */
        void onSlidingFinish();
    }

    public void setOnSlidingFinishListener(OnSlidingFinishListener onSlidingFinishListener) {
        this.onSlidingFinishListener = onSlidingFinishListener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // get the SlidingFinishLayout's parent layout
            mParentView = (ViewGroup) this.getParent();
            viewWidth = this.getWidth();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = tempX = (int) event.getRawX();
                downY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int deltaX = tempX - moveX;
                tempX = moveX;
                if (Math.abs(moveX - downX) > mTouchSlop
                        && Math.abs((int) event.getRawY() - downY) < mTouchSlop) {
                    isSliding = true;

                }

                if (moveX - downX >= 0 && isSliding) {
                    mParentView.scrollBy(deltaX, 0);

                }
                break;
            case MotionEvent.ACTION_UP:
                isSliding = false;
                if (mParentView.getScrollX() <= -viewWidth / 4) {
                    isFinish = true;
                    scrollRight();
                } else {
                    scrollOrigin();
                    isFinish = false;
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void scrollRight() {
        final int delta = (viewWidth + mParentView.getScrollX());
        //Start scrolling by providing a starting point and the distance to travel.
        mScroller.startScroll(mParentView.getScrollX(), 0, -delta + 1, 0,
                Math.abs(delta));
        postInvalidate();
    }

    private void scrollOrigin() {
        int delta = mParentView.getScrollX();
        //roll to the original page
        mScroller.startScroll(mParentView.getScrollX(), 0, -delta, 0,
                Math.abs(delta));
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        // when call startScroll, scroller.computeScrollOffset() return true，
        if (mScroller.computeScrollOffset()) {
            mParentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();

            if (mScroller.isFinished()) {
                if (onSlidingFinishListener != null && isFinish) {
                    onSlidingFinishListener.onSlidingFinish();
                }
            }
        }
    }
}
