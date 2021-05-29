package com.bimromatic.component.lib_base.imp;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/10/21
 * desc   : 公共的序列化服务
 *           // 在支持路由的页面上添加注解(必选)
 *           // 这里的路径需要注意的是至少需要有两级，/xx/xx
 * version: 1.0
 */
@Route(path = "base/service/gson")
public class GosnServiceImp implements SerializationService {

    private Gson gson;

    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return gson.fromJson(input, clazz);    }

    @Override
    public String object2Json(Object instance) {
        return gson.toJson(instance);    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return gson.fromJson(input, clazz);    }

    @Override
    public void init(Context context) {
        gson = new Gson();
    }
}
