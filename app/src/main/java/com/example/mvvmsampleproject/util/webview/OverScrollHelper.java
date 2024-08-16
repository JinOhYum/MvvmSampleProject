package com.example.mvvmsampleproject.util.webview;

import android.view.MotionEvent;
import android.view.ViewGroup;

public class OverScrollHelper {

    public static final int STATE_SCROLL_STOPPED = 0;
    public static final int STATE_SCROLL = 1;
    public static final int STATE_SCROLL_OVER_START = 2;
    public static final int STATE_SCROLL_OVER = 3;
    public static final int STATE_SCROLL_OVER_END = 4;

    private OverScrollListener onOverScrollListener = null;
    private OverScrollMeasure onOverScrollMeasure = null;

    private final ViewGroup targetView;

    private int currentOverScrollY = 0, lastOverScrollY = 0;
    private int scrollState = STATE_SCROLL_STOPPED;
    private float startOverScrollY = 0;
    private boolean scrollKinetic = false;

    public OverScrollHelper(ViewGroup targetView) {
        this.targetView = targetView;
    }

    public boolean onTouchEvent(MotionEvent event) {

        // When nobody watch then do nothing
        if (onOverScrollListener == null)
            return false;

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {

            scrollKinetic = false;
            // Do nothing...

        } else if (action == MotionEvent.ACTION_MOVE) {

            if (getScrollY() > 0) {

                if (scrollState == STATE_SCROLL_OVER) {
                    scrollState = STATE_SCROLL_OVER_END;
                    startOverScrollY = 0;
                    postOverScrollCancel();
                } else {
                    scrollState = STATE_SCROLL;
                    postScroll(0, getScrollY());
                }

            } else {

                if (scrollState != STATE_SCROLL_OVER && scrollState != STATE_SCROLL_OVER_START) {
                    scrollState = STATE_SCROLL_OVER_START;
                    startOverScrollY = event.getY();
                    postOverScrollStart();
                } else {
                    scrollState = STATE_SCROLL_OVER;
                    currentOverScrollY = (int) (event.getY() - startOverScrollY);
                    if (currentOverScrollY > 0) {
                        if (currentOverScrollY != lastOverScrollY) {
                            lastOverScrollY = currentOverScrollY;
                            postOverScroll(0, currentOverScrollY);
                        }
                        return true;
                    }

                }

            }

        } else if (action == MotionEvent.ACTION_UP) {
            scrollKinetic = true;
            if (scrollState == STATE_SCROLL_OVER) {
                scrollState = STATE_SCROLL_STOPPED;
                startOverScrollY = 0;
                postOverScrollCancel();
            }
        }

        return false;
    }

    public void onScrollChanged() {
        // Report scroll after release screen
        int newScrollY = getScrollY();
        if (scrollKinetic && newScrollY >= 0)
            postScroll(0, newScrollY);
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        // startOverScrollY correction after change e.g. top... for pull-to-refresh
        if (scrollState == STATE_SCROLL_OVER) {
            if (h != oldh) {
                startOverScrollY += h - oldh;
            }
        }
    }

    public int getScrollY() {
        if (onOverScrollMeasure != null) {
            return onOverScrollMeasure.getScrollY();
        } else {
            return targetView.getScrollY();
        }
    }

    private void postScroll(int scrollX, int scrollY) {
        if (onOverScrollListener != null) {
            onOverScrollListener.onScroll(targetView, scrollX, scrollY);
        }
    }

    private void postOverScrollStart() {
        if (onOverScrollListener != null) {
            onOverScrollListener.onOverScrollStart(targetView);
        }
    }

    private void postOverScroll(int overscrollX, int overScrollY) {
        if (onOverScrollListener != null) {
            onOverScrollListener.onOverScroll(targetView, overscrollX, overScrollY);
        }
    }

    private void postOverScrollCancel() {
        if (onOverScrollListener != null) {
            onOverScrollListener.onOverScrollCancel(targetView);
        }
    }

    public int getCurrentOverScrollY() {
        return currentOverScrollY;
    }

    public int getScrollState() {
        return scrollState;
    }

    public ViewGroup getTargetView() {
        return targetView;
    }

    public void setOnOverScrollListener(OverScrollListener onOverScrollListener) {
        this.onOverScrollListener = onOverScrollListener;
    }

    public void setOnOverScrollMeasure(OverScrollMeasure onOverScrollMeasure) {
        this.onOverScrollMeasure = onOverScrollMeasure;
    }

}
