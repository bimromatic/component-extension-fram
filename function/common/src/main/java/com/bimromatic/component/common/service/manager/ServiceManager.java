package com.bimromatic.component.common.service.manager;

import com.bimromatic.component.common.service.user.IAccountService;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/21/21
 * desc   :
 * version: 1.0
 */
public class ServiceManager {

    private ServiceManager() {}
    private IAccountService accountService;

    private static class AppConfigurationHolder {
        private static final ServiceManager instance = new ServiceManager();
    }

    public static ServiceManager getInstance() {
        return AppConfigurationHolder.instance;
    }

    public void setAccountService(IAccountService as) {
        this.accountService = as;
    }

    public IAccountService getAccountService() {
        return this.accountService;
    }

}
