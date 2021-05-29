package com.bimromatic.component.lib_base.action;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/12/21
 * desc   : 软键盘相关意图
 * version: 1.0
 */
public interface KeyboardAction {
    /**
     * 显示软键盘
     */
    default void showKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.showSoftInput(view, 0);
        }
    }

    /**
     * 隐藏软键盘
     */
    default void hideKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 切换软键盘
     */
    default void toggleSoftInput(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.toggleSoftInput(0, 0);
        }
    }
}
