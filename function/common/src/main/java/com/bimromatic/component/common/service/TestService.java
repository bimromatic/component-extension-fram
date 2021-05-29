package com.bimromatic.component.common.service;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/12/21
 * desc   : ARouter所谓的服务就是直接或者是间接实现ARouter提供的IProvider接口的类 公共层接口暴露
 * version: 1.0
 */
public interface TestService extends IProvider {
    String sayTestService(String name);
}
