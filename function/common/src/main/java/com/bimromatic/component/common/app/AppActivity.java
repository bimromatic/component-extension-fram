package com.bimromatic.component.common.app;

import androidx.viewbinding.ViewBinding;

import com.bimromatic.component.lib_base.act.BaseActivity;
import com.bimromatic.component.lib_net.impl.IBaseView;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/23/21
 * desc   :
 * version: 1.0
 */
public abstract class AppActivity<VB extends ViewBinding,P extends AppPresenter> extends BaseActivity<VB> implements IBaseView {

    protected P mPresenter;

    @Override
    protected void initLayout() {
        mPresenter = createPresenter();
        //绑定view
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.initLayout();
    }

    //创建控制器
    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        if (mPresenter != null) {
            mPresenter.detachView();
        }

    }
}
