package com.android.mvp.test.Presenter;

import android.util.Log;

import com.android.mvp.library.base.BaseBean;
import com.android.mvp.library.base.BasePresenter;
import com.android.mvp.library.okhttp.CallBackUtil;
import com.android.mvp.library.okhttp.HttpUtil;

import com.android.mvp.test.IView.MainIView;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.Call;

public class MainPresenter extends BasePresenter<MainIView> {
    public void event(Map<String, String> map, String url) {
        String json = new Gson().toJson(map);
        Log.e("MainPresenter","json");
        HttpUtil.PostJson(getIView(), url, json, new CallBackUtil<BaseBean>() {

            @Override
            public void onFailure(Call call, Exception e) {
                Log.e("MainPresenter","fail");
            }

            @Override
            public void onResponse(BaseBean response) {
                Log.e("MainPresenter","success");
            }
        });
    }
}
