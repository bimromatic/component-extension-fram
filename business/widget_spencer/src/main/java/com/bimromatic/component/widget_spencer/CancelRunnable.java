package com.bimromatic.component.widget_spencer;

import java.lang.ref.SoftReference;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/19/21
 * desc   : SpencerManager 定时销毁任务
 * version: 1.0
 */
final class CancelRunnable extends SoftReference<SpencerManagers<?>> implements Runnable {

    CancelRunnable(SpencerManagers spencerManagers) {
        super(spencerManagers);
    }

    @Override
    public void run() {
        SpencerManagers<?> spencerManagers = get();
        if (spencerManagers == null || !spencerManagers.isShow()) {
            return;
        }
        spencerManagers.cancel();
    }
}
