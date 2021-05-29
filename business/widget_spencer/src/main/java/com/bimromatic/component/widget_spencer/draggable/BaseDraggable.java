package com.bimromatic.component.widget_spencer.draggable;

import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.bimromatic.component.widget_spencer.SpencerManagers;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/19/21
 * desc   : 拖拽抽象基类
 * version: 1.0
 */
public abstract class BaseDraggable implements View.OnTouchListener {

    private SpencerManagers<?> mSpencerManagers;
    private View mRootView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowParams;

    /**
     * Toast 显示后回调这个类
     */
    public void start(SpencerManagers<?> spencerManager) {
        mSpencerManagers = spencerManager;
        mRootView = spencerManager.getView();
        mWindowManager = spencerManager.getWindowManager();
        mWindowParams = spencerManager.getWindowParams();

        mRootView.setOnTouchListener(this);
    }

    protected SpencerManagers<?> getXToast() {
        return mSpencerManagers;
    }

    protected WindowManager getWindowManager() {
        return mWindowManager;
    }

    protected WindowManager.LayoutParams getWindowParams() {
        return mWindowParams;
    }

    protected View getRootView() {
        return mRootView;
    }

    /**
     * 获取窗口不可见的宽度，一般情况下为横屏状态下刘海的高度
     */
    protected int getWindowInvisibleWidth() {
        Rect rect = new Rect();
        mRootView.getWindowVisibleDisplayFrame(rect);
        return rect.left;
    }

    /**
     * 获取窗口不可见的高度，一般情况下为状态栏的高度
     */
    protected int getWindowInvisibleHeight() {
        Rect rect = new Rect();
        mRootView.getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    /**
     * 更新悬浮窗的位置
     *
     * @param x             x 坐标
     * @param y             y 坐标
     */
    protected void updateLocation(float x, float y) {
        updateLocation((int) x, (int) y);
    }

    /**
     * 更新 WindowManager 所在的位置
     */
    protected void updateLocation(int x, int y) {
        // 屏幕默认的重心
        int screenGravity = Gravity.TOP | Gravity.START;
        // 判断本次移动的位置是否跟当前的窗口位置是否一致
        if (mWindowParams.gravity == screenGravity && mWindowParams.x == x && mWindowParams.y == y) {
            return;
        }

        mWindowParams.x = x;
        mWindowParams.y = y;
        // 一定要先设置重心位置为左上角
        mWindowParams.gravity = screenGravity;
        try {
            mWindowManager.updateViewLayout(mRootView, mWindowParams);
        } catch (IllegalArgumentException ignored) {
            // 当 WindowManager 已经消失时调用会发生崩溃
            // IllegalArgumentException: View not attached to window manager
        }
    }

    /**
     * 判断用户是否移动了，判断标准以下：
     * 根据手指按下和抬起时的坐标进行判断，不能根据有没有 move 事件来判断
     * 因为在有些机型上面，就算用户没有手指没有移动也会产生 move 事件
     *
     * @param downX         手指按下时的 x 坐标
     * @param upX           手指抬起时的 x 坐标
     * @param downY         手指按下时的 y 坐标
     * @param upY           手指抬起时的 y 坐标
     */
    protected boolean isTouchMove(float downX, float upX, float downY, float upY) {
        return ((int) downX) != ((int) upX) || ((int) (downY)) != ((int) upY);
    }
}
