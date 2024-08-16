package com.example.mvvmsampleproject.util.webview;

import android.view.ViewGroup;

public interface OverScrollListener {
    void onScroll(ViewGroup parent, int scrollX, int scrollY);
    void onOverScrollStart(ViewGroup parent);
    void onOverScroll(ViewGroup parent, int overscrollX, int overscrollY);
    void onOverScrollCancel(ViewGroup parent);
}
