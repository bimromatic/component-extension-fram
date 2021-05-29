package com.bimromatic.component.lib_base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.bimromatic.component.lib_base.utils.net.NetworkInformation;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/11/21
 * desc   :
 * version: 1.0
 */
public class NetworkBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkInformation.sharedManager().setContext(context);
        }
    }
}
