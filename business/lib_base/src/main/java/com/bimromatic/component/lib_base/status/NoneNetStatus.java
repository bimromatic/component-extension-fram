package com.bimromatic.component.lib_base.status;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.bimromatic.component.lib_base.R;
import com.kingja.loadsir.callback.Callback;


/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/14/21
 * desc   :
 * vion: 1.0
 */
public class NoneNetStatus extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.base_status_none_net;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        Toast.makeText(context.getApplicationContext(),"Connecting to the network again!",Toast.LENGTH_SHORT).show();
        return false;
    }
}
