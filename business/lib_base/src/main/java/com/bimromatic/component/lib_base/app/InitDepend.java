package com.bimromatic.component.lib_base.app;

import java.util.List;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/8/21
 * desc   :
 * version: 1.0
 */
public class InitDepend {

    private List<String>mainThreadDepends;
    private List<String>workerThreadDepends;

    public InitDepend(List<String> mainThreadDepends, List<String> workerThreadDepends) {
        this.mainThreadDepends = mainThreadDepends;
        this.workerThreadDepends = workerThreadDepends;
    }

    public List<String> getMainThreadDepends() {
        return mainThreadDepends;
    }

    public void setMainThreadDepends(List<String> mainThreadDepends) {
        this.mainThreadDepends = mainThreadDepends;
    }

    public List<String> getWorkerThreadDepends() {
        return workerThreadDepends;
    }

    public void setWorkerThreadDepends(List<String> workerThreadDepends) {
        this.workerThreadDepends = workerThreadDepends;
    }

}
