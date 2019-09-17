package com.android.mvp.library.base;

/**
 * Created by asad on 2018/5/9.
 */

public class BaseBean {
    /**
     * msg :
     * code : 0
     * data :
     */

    private String msg;
    private int code;
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
