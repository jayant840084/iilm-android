/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package utils;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * Created by jayant on 13/11/17.
 */

public class ProgressBarUtil {

    private LinearLayout mProgressBackground;
    private ProgressBar mProgressBar;

    public ProgressBarUtil(LinearLayout progressBackground, android.widget.ProgressBar progressBar) {
        this.mProgressBackground = progressBackground;
        this.mProgressBar = progressBar;
        init();
    }

    private void init() {
        // prevent touch events from passing into background views
        mProgressBackground.setOnTouchListener((view, motionEvent) -> true);
    }

    public void showProgress() {
        mProgressBackground.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mProgressBackground.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

}
