package com.bimromatic.component.widget_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/24/21
 * desc   :
 * version: 1.0
 */
public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 不拦截这个事件
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 不处理这个事件
        return false;
    }

    @Override
    public boolean executeKeyEvent(@NonNull KeyEvent event) {
        // 不响应按键事件
        return false;
    }

    @Override
    public void setCurrentItem(int item) {
        // 只有相邻页才会有动画
        super.setCurrentItem(item, Math.abs(getCurrentItem() - item) == 1);
    }
}
