package com.bimromatic.component.moudle_main;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bimromatic.component.common.app.AppActivity;
import com.bimromatic.component.common.router.modules.main.RouterHub;
import com.bimromatic.component.lib_base.act.BaseActivity;
import com.bimromatic.component.lib_base.aop.SingleClick;
import com.bimromatic.component.lib_base.utils.NAV;
import com.bimromatic.component.lib_base.utils.net.NetWorkSpeedUtils;
import com.bimromatic.component.lib_base.utils.net.NetworkInformation;
import com.bimromatic.component.moudle_main.databinding.ActivityMainBinding;
import com.bimromatic.component.moudle_main.impl.MainView;
import com.hjq.toast.ToastUtils;
import com.kongqw.network.monitor.enums.NetworkState;
import com.kongqw.network.monitor.interfaces.NetworkMonitor;
import com.orhanobut.logger.Logger;

@SuppressLint("NonConstantResourceId")
@Route(path = RouterHub.MAIN_MAINACTIVITY)
public class MainActivity extends AppActivity<ActivityMainBinding, MainPresenter> implements MainView {


    @SuppressLint("HandlerLeak")
    private Handler mHnadler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    //Log.d("当前网速",""+msg.obj.toString());
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

        try {
            //Logger.d("Fps", "testFps thread name:${}"+Thread.currentThread().getName());
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @SingleClick
    @Override
    public void onClick(View view) {
//        if (view.getId() == R.id.intro_test_txt) {
////            showLoading();
////            postDelayed(
////                    () -> {
////                        showTimeOut();
////                    }, 1000);
//            NAV.INSTANCE.go(com.bimromatic.component.common.router.modules.reader.RouterHub.READER_HOMEACTIVITY);
//        }
    }

    @Override
    public void onRetryBtnClick() {
        Log.e("onRetryBtnClick","onRetryBtnClick");
        //showLoading();
        postDelayed(
                ()->{
                    showSuccess();
                    Log.e("Looper",""+(Looper.getMainLooper().getThread() == Thread.currentThread()));
                },1000);
    }


    @NetworkMonitor
    public void onNetWorkStateChange(NetworkState networkState) {
        switch (networkState){
            case NONE:
                ToastUtils.show("暂无网络");
                showSuccess();
                break;
            case WIFI:
                //Logger.i("wifi网络信息:",""+ NetworkInformation.sharedManager().fetchSSIDInfo());
                break;
            case CELLULAR:
                ToastUtils.show("蜂窝网络");
                break;
            default:
                break;
        }


        new NetWorkSpeedUtils(MainActivity.this,mHnadler).startShowNetSpeed();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }
}