package com.huake.hkis.hkis.model;

import android.support.annotation.NonNull;

/**
 * Created by chen on 2017/6/10.
 */

public class HKISMessage {
    @NonNull
    private String code;
    @NonNull
    private String msg;

    public HKISMessage(){

    }

    public HKISMessage(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    public void setCode(@NonNull String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
