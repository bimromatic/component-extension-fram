package com.bimromatic.component.common.service;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bimromatic.component.common.router.service.ServiceRouterPath;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/12/21
 * desc   :
 * version: 1.0
 */

@Route(path = ServiceRouterPath.SERVICE_TEST)
public class TestServiceImpl implements TestService{
    @Override
    public String sayTestService(String name) {
        return "TestServiceImpl";
    }

    @Override
    public void init(Context context) {
        Log.d("TestServiceImpl", "init");
    }
}
