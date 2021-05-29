package com.bimromatic.component.widget_spencer;

import android.view.View;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/19/21
 * desc   : {@link View.OnClickListener} 包装类
 * version: 1.0
 */
public class ViewClickWrapper implements View.OnClickListener {

    private final SpencerManagers<?> mSpencerManagers;
    private final SpencerManagers.OnClickListener mListener;

    ViewClickWrapper(SpencerManagers<?> spencerManagers, SpencerManagers.OnClickListener listener) {
        mSpencerManagers = spencerManagers;
        mListener = listener;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void onClick(View view) {
        mListener.onClick(mSpencerManagers, view);
    }
}
