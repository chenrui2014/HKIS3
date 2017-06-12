package com.huake.hkis.hkis.model;

import com.huake.hkis.hkis.HKISAPI;

/**
 * Created by chen on 2017/6/10.
 */

public class MyResponsBody<T> {
    private HKISMessage msg;
    private T data;

    public MyResponsBody(HKISMessage msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public HKISMessage getMsg() {
        return msg;
    }

    public void setMsg(HKISMessage msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
