package com.bimromatic.component.widget_spencer;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/19/21
 * desc   : {@link View.OnTouchListener} 包装类
 * version: 1.0
 */
public class ViewTouchWrapper implements View.OnTouchListener {

    private final SpencerManagers<?> mSpencerManagers;
    private final SpencerManagers.OnTouchListener mListener;

    ViewTouchWrapper(SpencerManagers<?> spencerManagers, SpencerManagers.OnTouchListener listener) {
        mSpencerManagers = spencerManagers;
        mListener = listener;
    }

    @SuppressLint("ClickableViewAccessibility")
    @SuppressWarnings("unchecked")
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        return mListener.onTouch(mSpencerManagers, view, event);
    }
}
