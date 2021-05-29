package com.bimromatic.component.lib_base.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewbinding.ViewBinding;

import com.bimromatic.component.lib_base.action.ActivityAction;
import com.bimromatic.component.lib_base.action.BundleAction;
import com.bimromatic.component.lib_base.action.ClickAction;
import com.bimromatic.component.lib_base.action.HandlerAction;
import com.bimromatic.component.lib_base.action.KeyboardAction;
import com.bimromatic.component.lib_base.action.ResourcesAction;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/12/21
 * desc   :
 * version: 1.0
 */
public abstract class BaseFragment<A extends BaseActivity,VB extends ViewBinding> extends Fragment implements ActivityAction, ResourcesAction, HandlerAction, ClickAction, BundleAction, KeyboardAction {

    /** Activity 对象 */
    private A mActivity;
    /** 根布局 */
    private View mRootView;
    /** 当前是否加载过 */
    private boolean mLoading;

    private VB mBaseBinding;

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 获得全局的 Activity
        mActivity = (A) requireActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //返回当前类的父类的type，也就是baseAct
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType){
            //如果支持泛型
            try {
                Class<VB> clazz = (Class<VB>)((ParameterizedType)type).getActualTypeArguments()[0];
                //反射inflate
                Method method = clazz.getMethod("inflate", LayoutInflater.class,ViewGroup.class,Boolean.class);
                //方法调用,获取ViewBinding 实例
                mBaseBinding = (VB)method.invoke(null,container,false);
            }catch (Exception e){
                e.printStackTrace();
            }
            //ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams((ViewGroup.MarginLayoutParams) mViewBinding.getRoot().getLayoutParams());
            //setContentView(mViewBinding.getRoot());
        }

        //if (getLayoutId() <= 0) {
        //  return null;
        //}
        mLoading = false;
        //mRootView = inflater.inflate(getLayoutId(), container, false);
        initView();
        return mBaseBinding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!mLoading) {
            mLoading = true;
            initData();
            onFragmentResume(true);
            return;
        }

        if (mActivity != null && mActivity.getLifecycle().getCurrentState() == Lifecycle.State.STARTED) {
            onActivityResume();
        } else {
            onFragmentResume(false);
        }
    }

    /**
     * Fragment 可见回调
     *
     * @param first 是否首次调用
     */
    protected void onFragmentResume(boolean first) {}

    /**
     * Activity 可见回调
     */
    protected void onActivityResume() {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoading = false;
        removeCallbacks();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    /**
     * 这个 Fragment 是否已经加载过了
     */
    public boolean isLoading() {
        return mLoading;
    }

    @NonNull
    @Override
    public View getView() {
        return mRootView;
    }

    /**
     * 获取绑定的 Activity，防止出现 getActivity 为空
     */
    public A getAttachActivity() {
        return mActivity;
    }

    /**
     * 获取布局 ID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 根据资源 id 获取一个 View 对象
     */
    @Override
    public <V extends View> V findViewById(@IdRes int id) {
        return mRootView.findViewById(id);
    }

    @Override
    public Bundle getBundle() {
        return getArguments();
    }

    /**
     * startActivityForResult 方法优化
     */

    public void startActivityForResult(Class<? extends Activity> clazz, BaseActivity.OnActivityCallback callback) {
        getAttachActivity().startActivityForResult(clazz, callback);
    }

    public void startActivityForResult(Intent intent, BaseActivity.OnActivityCallback callback) {
        getAttachActivity().startActivityForResult(intent, null, callback);
    }

    public void startActivityForResult(Intent intent, Bundle options, BaseActivity.OnActivityCallback callback) {
        getAttachActivity().startActivityForResult(intent, options, callback);
    }

    /**
     * 销毁当前 Fragment 所在的 Activity
     */
    public void finish() {
        if (mActivity == null || mActivity.isFinishing() || mActivity.isDestroyed()) {
            return;
        }
        mActivity.finish();
    }

    /**
     * Fragment 按键事件派发
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            // 这个子 Fragment 必须是 BaseFragment 的子类，并且处于可见状态
            if (!(fragment instanceof BaseFragment) ||
                    fragment.getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                continue;
            }
            // 将按键事件派发给子 Fragment 进行处理
            if (((BaseFragment<?,?>) fragment).dispatchKeyEvent(event)) {
                // 如果子 Fragment 拦截了这个事件，那么就不交给父 Fragment 处理
                return true;
            }
        }
        switch (event.getAction()) {
            case KeyEvent.ACTION_DOWN:
                return onKeyDown(event.getKeyCode(), event);
            case KeyEvent.ACTION_UP:
                return onKeyUp(event.getKeyCode(), event);
            default:
                return false;
        }
    }

    /**
     * 按键按下事件回调
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 默认不拦截按键事件
        return false;
    }

    /**
     * 按键抬起事件回调
     */
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // 默认不拦截按键事件
        return false;
    }

}
