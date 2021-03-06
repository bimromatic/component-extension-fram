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
 * desc   : ??????
 * version: 1.0
 */
public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity implements ActivityAction, ClickAction, HandlerAction,TitleBarAction, BundleAction, KeyboardAction,INetView {

    protected VB mViewBinding;
    //protected T mPresenter;
    /** Activity ???????????? */
    private SparseArray<OnActivityCallback> mActivityCallbacks;
    /** ??????????????? */
    private TitleBar mTitleBar;
    /** ??????????????? */
    private ImmersionBar mImmersionBar;
    /** ???????????????*/
    private LoadService loadService;

    public View mContentView;
    private ActivityBaseBinding mBaseBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

        }
        super.onCreate(savedInstanceState);

        //getLayoutInflater() ????????????????????????"ViewStub???include???merge" ????????????erro?????????: ???????????? ActivityBaseBinding???????????? inflate?????????????????????;BaseBinding = ActivityBaseBinding.inflate(getLayoutInflater());^//  ??????: LayoutInflater,ViewGroup??????: LayoutInflater??????: ???????????????????????????????????????????????????
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

        //???????????????????????????type????????????baseAct
        //        Type type = this.getClass().getGenericSuperclass();
        //        if (type instanceof ParameterizedType){
        //            //??????????????????
        //            try {
        //                Class<T> clazz = (Class<T>)((ParameterizedType)type).getActualTypeArguments()[0];
        //                //??????inflate
        //                Method method = clazz.getMethod("inflate", LayoutInflater.class);
        //                //????????????,??????ViewBinding ??????
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
         * ?????????????????????Presenter
         * ??????View
         */
//        private void initPresenter(){
//            mPresenter = TUtil.getT(this);
//            if (mPresenter!=null){
//                mPresenter.setContext(mContext);
//                mPresenter.onStart(this);
//            }
//        }

        initView();
        // ?????????????????????Activity????????????????????????????????????
        initState(savedInstanceState);
        initData();
    }

    /**
     * ???????????? ID
     */
    protected abstract @LayoutRes int getLayoutId();

    /**
     * ???????????????
     */
    protected abstract void initView();

    // ???????????????????????????????????????????????????????????????
    protected void initState(Bundle savedInstanceState){}

    /**
     * ???????????????
     */
    protected abstract void initData();

    /**
     * ???????????????
     */
    protected void initLayout() {

        initSoftKeyboard();

        if (getTitleBar() != null) {
            getTitleBar().setOnTitleBarListener(this);
        }

        // ???????????????????????????
        if (isStatusBarEnabled()) {
            getStatusBarConfig().init();

            // ?????????????????????
            if (getTitleBar() != null) {
                ImmersionBar.setTitleBar(this, getTitleBar());
            }
        }

        doubleClickExitDetector =
                new DoubleClickExitDetector(this, "??????????????????", 2000);
    }

    /**
     * ??????????????????
     */
    protected void initSoftKeyboard() {
        // ????????????????????????????????????????????????
        getContentView().setOnClickListener(v -> {
            // ?????????????????????????????????
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
        // ?????????????????????????????????
        hideKeyboard(getCurrentFocus());
        super.finish();
        overridePendingTransition(R.anim.left_in_activity, R.anim.left_out_activity);
    }

    /**
     * ??????????????? Activity???singleTop ??????????????? ?????????????????????
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // ?????????????????? Intent????????? Activity ?????????????????? Intent ????????????????????????
        setIntent(intent);
    }

    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    /**
     * ??? setContentView ???????????????
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
            // ?????? Fragment ????????? BaseFragment ????????????????????????????????????
            if (!(fragment instanceof BaseFragment) ||
                    fragment.getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                continue;
            }
            // ???????????????????????? Fragment ????????????
            if (((BaseFragment<?,?>) fragment).dispatchKeyEvent(event)) {
                // ?????? Fragment ?????????????????????????????????????????? Activity ??????
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        // ?????????????????????????????????
        hideKeyboard(getCurrentFocus());
        // ?????????????????? startActivity ?????????????????? startActivityForResult
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.right_in_activity, R.anim.right_out_activity);
    }

    /**
     * startActivityForResult ????????????
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
        // ?????????????????? 2 ??? 16 ????????????
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
         * ????????????
         *
         * @param resultCode        ?????????
         * @param data              ??????
         */
        void onActivityResult(int resultCode, @Nullable Intent data);
    }

    ////////////////////////////////////////////   title    ////////////////////////////////////////////////////

    /**
     * ???????????????????????????,??????true
     * @return
     */
    protected boolean enableSimplebar() {
        return false;
    }

    /**
     * ????????????????????????
     */
    @Override
    public void setTitle(@StringRes int id) {
        setTitle(getString(id));
    }

    /**
     * ????????????????????????
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
     * ??????????????????????????????
     */
    protected boolean isStatusBarEnabled() {
        return true;
    }

    /**
     * ???????????????????????????
     */
    protected boolean isStatusBarDarkFont() {
        return true;
    }

    /**
     * ????????????????????????????????????
     */
    @NonNull
    public ImmersionBar getStatusBarConfig() {
        if (mImmersionBar == null) {
            mImmersionBar = createStatusBarConfig();
        }
        return mImmersionBar;
    }

    /**
     * ???????????????????????????
     */
    @NonNull
    protected ImmersionBar createStatusBarConfig() {
        return ImmersionBar.with(this)
                // ????????????????????????????????????
                .statusBarDarkFont(isStatusBarDarkFont())
                // ???????????????????????????
                .navigationBarColor(android.R.color.white)
                // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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
     * ?????????????????? ???????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Object event) {
    }

    /**
     * ?????????????????? ??????????????? ?????????true
     */
    protected boolean regEvent() {
        return false;
    }
}
