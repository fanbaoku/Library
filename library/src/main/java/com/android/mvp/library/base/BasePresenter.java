package com.android.mvp.library.base;



import com.android.mvp.library.okhttp.RequestUtil;

import okhttp3.Call;

/**
 * mvp presenter 基类
 */
public class BasePresenter<I> {
    private I iView;

    public void attachView(I iView) {
        this.iView = iView;
    }

    public I getIView() {
        return iView;
    }

    public void detachView() {
        if (iView != null) {
            cancelTag(iView);
            iView = null;
        }
    }

    public boolean isViewAttached() {
        return iView != null;
    }

    /**
     * 根据Tag取消请求
     */
    public void cancelTag(Object tag) {
        if (tag == null || RequestUtil.getmOkHttpClient() == null) return;
        for (Call call : RequestUtil.getmOkHttpClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : RequestUtil.getmOkHttpClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}
