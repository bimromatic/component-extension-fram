package com.bimromatic.component.lib_base.ems;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/11/21
 * desc   : Anti repeat time enumeration
 * version: 1.0
 */
public enum DateEnum {
    //Here we mainly do the minute level control and second level control
    seconds("yyyy-MM-dd HH:mm:ss"), minutes("yyyy-MM-dd HH:mm");
    private String value;

    DateEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
