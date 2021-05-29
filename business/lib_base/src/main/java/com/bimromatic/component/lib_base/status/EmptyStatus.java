package com.bimromatic.component.lib_base.status;

import com.bimromatic.component.lib_base.R;
import com.kingja.loadsir.callback.Callback;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/13/21
 * desc   :
 * version: 1.0
 */
public class EmptyStatus extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.base_status_empty;
    }
}
