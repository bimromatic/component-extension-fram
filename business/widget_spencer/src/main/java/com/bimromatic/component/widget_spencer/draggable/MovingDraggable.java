package com.bimromatic.component.widget_spencer.draggable;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/19/21
 * desc   : 移动拖拽处理实现类
 * version: 1.0
 */
public class MovingDraggable extends BaseDraggable {

    /** 手指按下的坐标 */
    private float mViewDownX;
    private float mViewDownY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录按下的位置（相对 View 的坐标）
                mViewDownX = event.getX();
                mViewDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 记录移动的位置（相对屏幕的坐标）
                float rawMoveX = event.getRawX() - getWindowInvisibleWidth();
                float rawMoveY = event.getRawY() - getWindowInvisibleHeight();

                // 更新移动的位置
                updateLocation(rawMoveX - mViewDownX, rawMoveY - mViewDownY);
                break;
            case MotionEvent.ACTION_UP:
                // 如果用户移动了手指，那么就拦截本次触摸事件，从而不让点击事件生效
                return isTouchMove(mViewDownX, event.getX(), mViewDownY, event.getY());
            default:
                break;
        }
        return false;
    }
}
