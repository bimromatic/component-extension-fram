package com.bimromatic.component.lib_net.entiy;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/23/21
 * desc   : 通常服务器端会返回统一的数据格式，这里我们写一个BaseEntity
 * version: 1.0
 */
public class BaseEntity<T> implements Serializable {

    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private T data;

    public boolean isSuccess() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
