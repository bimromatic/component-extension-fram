package com.bimromatic.component.lib_base.action;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/11/21
 * desc   : Activity related intent
 * version: 1.0
 */
public interface ActivityAction {

    /**
     *  Get context object
     */
    Context getContext();

    /**
     * Get activity object
     */
    default Activity getActivity() {
        Context context = getContext();
        do {
            if (context instanceof Activity) {
                return (Activity) context;
            } else if (context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            } else {
                return null;
            }
        } while (context != null);
        return null;
    }

    /**
     * Jump to activity simplified
     */
    default void startActivity(Class<? extends Activity> clazz) {
        startActivity(new Intent(getContext(), clazz));
    }

    /**
     *
     *
     * Jump to activity
     */
    default void startActivity(Intent intent) {
        if (!(getContext() instanceof Activity)) {
            // Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        getContext().startActivity(intent);
    }
}
