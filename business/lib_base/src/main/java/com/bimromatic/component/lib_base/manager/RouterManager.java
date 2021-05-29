package com.bimromatic.component.lib_base.manager;

import android.app.Activity;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/21/21
 * desc   : 路由管理器
 * version: 1.0
 */
public final class RouterManager {

    private Map<String, String> map = new HashMap<>();

    private RouterManager instance;

    private RouterManager() {}

    public static RouterManager getInstance() {
        return InstanceHolder.instance;
    }

    public void put(String url, String className) {
        map.put(url, className);

    }

    public void startActivity(Activity activity, String url, Intent intentData) {
        try {
            Intent intent = new Intent(activity, Class.forName(map.get(url)));
            intent.putExtras(intentData);
            activity.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static class InstanceHolder {
        public static RouterManager instance = new RouterManager();
    }
}
