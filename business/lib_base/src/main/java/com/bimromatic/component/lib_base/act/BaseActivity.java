package com.bimromatic.component.lib_base.act;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewbinding.ViewBinding;

import com.bimromatic.component.lib_base.R;
import com.bimromatic.component.lib_base.action.ActivityAction;
import com.bimromatic.component.lib_base.action.BundleAction;
import com.bimromatic.component.lib_base.action.ClickAction;
import com.bimromatic.component.lib_base.action.HandlerAction;
import com.bimromatic.component.lib_base.action.KeyboardAction;
import com.bimromatic.component.lib_base.action.TitleBarAction;
import com.bimromatic.component.lib_base.databinding.ActivityBaseBinding;
import com.bimromatic.component.lib_base.helper.DoubleClickExitDetector;
import com.bimromatic.component.lib_base.impl.INetView;
import com.bimromatic.component.lib_base.provider.ContextProvider;
import com.bimromatic.component.lib_base.status.EmptyStatus;
import com.bimromatic.component.lib_base.status.LoadingStatus;
import com.bimromatic.component.lib_base.status.NoneNetStatus;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kongqw.network.monitor.util.NetworkStateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/6/21
 * desc   : 基类
 * version: 1.0
 */
public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity implements ActivityAction, ClickAction, HandlerAction,TitleBarAction, BundleAction, KeyboardAction,INetView {

    protected VB mViewBinding;
    //protected T mPresenter;
    /** Activity 回调集合 */
    private SparseArray<OnActivityCallback> mActivityCallbacks;
    /** 标题栏对象 */
    private TitleBar mTitleBar;
    /** 状态栏沉浸 */
    private ImmersionBar mImmersionBar;
    /** 状态页管理*/
    private LoadService loadService;

    public View mContentView;
    private ActivityBaseBinding mBaseBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

        }
        super.onCreate(savedInstanceState);

        //getLayoutInflater() 如果布局顶层使用"ViewStub、include、merge" 会出现：erro：错误: 无法将类 ActivityBaseBinding中的方法 inflate应用到给定类型;BaseBinding = ActivityBaseBinding.inflate(getLayoutInflater());^//  需要: LayoutInflater,ViewGroup找到: LayoutInflater原因: 实际参数列表和形式参数列表长度不同
        mBaseBinding = ActivityBaseBinding.inflate(getLayoutInflater());
        mContentView = getLayoutInflater().inflate(getLayoutId(),null);
        mBaseBinding.containerLayout.removeAllViews();
        mBaseBinding.containerLayout.addView(mContentView);
        setContentView(mBaseBinding.getRoot());

        Type superclass = getClass().getGenericSuperclass();
        Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("bind", View.class);
            mViewBinding = (VB) method.invoke(null, mContentView);

        } catch (NoSuchMethodException | IllegalAccessException| InvocationTargetException e) {
            e.printStackTrace();
        }

        mBaseBinding.titleBar.setVisibility(enableSimplebar()?View.VISIBLE:View.GONE);
        //loadService = LoadSir.getDefault().register(mContentView, (Callback.OnReloadListener) this::onNetReload);
        initActivity(savedInstanceState);

        //返回当前类的父类的type，也就是baseAct
        //        Type type = this.getClass().getGenericSuperclass();
        //        if (type instanceof ParameterizedType){
        //            //如果支持泛型
        //            try {
        //                Class<T> clazz = (Class<T>)((ParameterizedType)type).getActualTypeArguments()[0];
        //                //反射inflate
        //                Method method = clazz.getMethod("inflate", LayoutInflater.class);
        //                //方法调用,获取ViewBinding 实例
        //                mViewBinding = (T)method.invoke(null,getLayoutInflater());
        //            }catch (Exception e){
        //                e.printStackTrace();
        //            }
        //            //ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams((ViewGroup.MarginLayoutParams) mViewBinding.getRoot().getLayoutParams());
        //            setContentView(mViewBinding.getRoot());
        //        }


        Log.e("net",""+NetworkStateUtils.INSTANCE.hasNetworkCapability(ContextProvider.getInstance().getContext()));
    }

    protected void initActivity(Bundle savedInstanceState) {
        initLayout();
        if (regEvent()) {
            EventBus.getDefault().register(this);
        }
        /**
         * 获取泛型实例化Presenter
         * 绑定View
         */
//        private void initPresenter(){
//            mPresenter = TUtil.getT(this);
//            if (mPresenter!=null){
//                mPresenter.setContext(mContext);
//                mPresenter.onStart(this);
//            }
//        }

        initView();
        // 子类中需要获取Activity异常销毁时的值的时候使用
        initState(savedInstanceState);
        initData();
    }

    /**
     * 获取布局 ID
     */
    protected abstract @LayoutRes int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    // 当页面因为异常销毁需要保存数据时调用该方法
    protected void initState(Bundle savedInstanceState){}

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化布局
     */
    protected void initLayout() {

        initSoftKeyboard();

        if (getTitleBar() != null) {
            getTitleBar().setOnTitleBarListener(this);
        }

        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            getStatusBarConfig().init();

            // 设置标题栏沉浸
            if (getTitleBar() != null) {
                ImmersionBar.setTitleBar(this, getTitleBar());
            }
        }

        doubleClickExitDetector =
                new DoubleClickExitDetector(this, "再按一次退出", 2000);
    }

    /**
     * 初始化软键盘
     */
    protected void initSoftKeyboard() {
        // 点击外部隐藏软键盘，提升用户体验
        getContentView().setOnClickListener(v -> {
            // 隐藏软键，避免内存泄漏
            hideKeyboard(getCurrentFocus());
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeCallbacks();
        if (regEvent()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void finish() {
        // 隐藏软键，避免内存泄漏
        hideKeyboard(getCurrentFocus());
        super.finish();
        overridePendingTransition(R.anim.left_in_activity, R.anim.left_out_activity);
    }

    /**
     * 如果当前的 Activity（singleTop 启动模式） 被复用时会回调
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 设置为当前的 Intent，避免 Activity 被杀死后重启 Intent 还是最原先的那个
        setIntent(intent);
    }

    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    /**
     * 和 setContentView 对应的方法
     */
    public ViewGroup getContentView() {
        return findViewById(Window.ID_ANDROID_CONTENT);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            // 这个 Fragment 必须是 BaseFragment 的子类，并且处于可见状态
            if (!(fragment instanceof BaseFragment) ||
                    fragment.getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                continue;
            }
            // 将按键事件派发给 Fragment 进行处理
            if (((BaseFragment<?,?>) fragment).dispatchKeyEvent(event)) {
                // 如果 Fragment 拦截了这个事件，那么就不交给 Activity 处理
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        // 隐藏软键，避免内存泄漏
        hideKeyboard(getCurrentFocus());
        // 查看源码得知 startActivity 最终也会调用 startActivityForResult
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.right_in_activity, R.anim.right_out_activity);
    }

    /**
     * startActivityForResult 方法优化
     */

    public void startActivityForResult(Class<? extends Activity> clazz, OnActivityCallback callback) {
        startActivityForResult(new Intent(this, clazz), null, callback);
    }

    public void startActivityForResult(Intent intent, OnActivityCallback callback) {
        startActivityForResult(intent, null, callback);
    }

    public void startActivityForResult(Intent intent, @Nullable Bundle options, OnActivityCallback callback) {
        if (mActivityCallbacks == null) {
            mActivityCallbacks = new SparseArray<>(1);
        }
        // 请求码必须在 2 的 16 次方以内
        int requestCode = new Random().nextInt((int) Math.pow(2, 16));
        mActivityCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode, options);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        OnActivityCallback callback;
        if (mActivityCallbacks != null && (callback = mActivityCallbacks.get(requestCode)) != null) {
            callback.onActivityResult(resultCode, data);
            mActivityCallbacks.remove(requestCode);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public interface OnActivityCallback {

        /**
         * 结果回调
         *
         * @param resultCode        结果码
         * @param data              数据
         */
        void onActivityResult(int resultCode, @Nullable Intent data);
    }

    ////////////////////////////////////////////   title    ////////////////////////////////////////////////////

    /**
     * 是否开启通用标题栏,默认true
     * @return
     */
    protected boolean enableSimplebar() {
        return false;
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(@StringRes int id) {
        setTitle(getString(id));
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (getTitleBar() != null) {
            getTitleBar().setTitle(title);
        }
    }

    @Override
    @Nullable
    public TitleBar getTitleBar() {
        if (mTitleBar == null) {
            mTitleBar = obtainTitleBar(getContentView());
        }
        return mTitleBar;
    }

    @Override
    public void onLeftClick(View view) {
        onBackPressed();
    }


    ////////////////////////////////////////////   bar    ////////////////////////////////////////////////////

    /**
     * 是否使用沉浸式状态栏
     */
    protected boolean isStatusBarEnabled() {
        return true;
    }

    /**
     * 状态栏字体深色模式
     */
    protected boolean isStatusBarDarkFont() {
        return true;
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    @NonNull
    public ImmersionBar getStatusBarConfig() {
        if (mImmersionBar == null) {
            mImmersionBar = createStatusBarConfig();
        }
        return mImmersionBar;
    }

    /**
     * 初始化沉浸式状态栏
     */
    @NonNull
    protected ImmersionBar createStatusBarConfig() {
        return ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarDarkFont(isStatusBarDarkFont())
                // 指定导航栏背景颜色
                .navigationBarColor(android.R.color.white)
                // 状态栏字体和导航栏内容自动变色，必须指定状态栏颜色和导航栏颜色才可以自动变色
                .autoDarkModeEnable(true, 0.2f);
    }



    ////////////////////////////////////////////   onBackPressed    ////////////////////////////////////////////////////

    private DoubleClickExitDetector doubleClickExitDetector;

    protected boolean isDoubleClickExit() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (isDoubleClickExit()) {
            boolean isExit = doubleClickExitDetector.click();
            if (isExit) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }


    ////////////////////////////////////////////   status    ////////////////////////////////////////////////////

    @Override
    public void showLoading() {
        if (loadService == null){
            loadService = LoadSir.getDefault().register(mContentView, v -> onRetryBtnClick());
        }
        loadService.showCallback(LoadingStatus.class);
    }

    @Override
    public void showLoading(View view) {
        if (loadService == null){
            loadService = LoadSir.getDefault().register(mContentView, v -> onRetryBtnClick());
        }
        loadService.showCallback(LoadingStatus.class);
    }

    @Override
    public void showEmpty() {
        if (loadService == null){
            loadService = LoadSir.getDefault().register(mContentView, v -> onRetryBtnClick());
        }
        loadService.showCallback(EmptyStatus.class);
    }

    @Override
    public void showTimeOut() {
        if (loadService == null){
            loadService = LoadSir.getDefault().register(mContentView, (Callback.OnReloadListener) v ->{});
        }

        loadService.showCallback(NoneNetStatus.class);
    }

    @Override
    public void showSuccess() {
        if (loadService == null){
            loadService = LoadSir.getDefault().register(mContentView, v -> onRetryBtnClick());
        }
        loadService.showSuccess();
    }

    @Override
    public void onRetryBtnClick() {

    }

    ////////////////////////////////////////////   Orientation    ////////////////////////////////////////////////////
    protected int getScreenOrientation() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }


    ////////////////////////////////////////////   EventBus    ////////////////////////////////////////////////////
    /**
     * 子类接收事件 重写该方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Object event) {
    }

    /**
     * 需要接收事件 重写该方法 并返回true
     */
    protected boolean regEvent() {
        return false;
    }
}
