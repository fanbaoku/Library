package com.android.mvp.test;

import android.os.Bundle;
import android.widget.TextView;

import com.android.mvp.library.base.BaseAppCompatActivity;
import com.android.mvp.library.okhttp.HttpUtil;
import com.android.mvp.test.IView.MainIView;
import com.android.mvp.test.Presenter.MainPresenter;
import com.github.anzewei.parallaxbacklayout.ParallaxHelper;

import java.util.HashMap;
import java.util.Map;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class MainActivity extends BaseAppCompatActivity<MainIView, MainPresenter> {

    TextView tv;
    public static final String LOAN_URL = "https://www.go-rupiah.com";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        setPresenter(new MainPresenter());
        //关闭滑动返回功能
        ParallaxHelper.getInstance().disableParallaxBack(this);
        tv=findViewById(R.id.tv);
        tv.setText("测试成功");
        String actionUrl = LOAN_URL + "/api/market/insertAppEventLog";
        Map<String, String> map1 = new HashMap<>();
        map1.put("base64_android", "eyJ2ZXJzaW9uTmFtZSI6IjEuMCIsImRldmljZSI6ImZmZmZmZmZmLTg0ODgtYzgxYy1mZmZmLWZm" +
                "ZmZhNGI3OGVlMCIsInZlcnNpb25Db2RlIjoiMSIsImFuaWQiOiIzOGU0ZjNkODhlMzBmZDMiLCJl" +
                "dmVudFBhcmFtcyI6IjIwMTktMDktMTcgMTc6NTE6MTciLCJ2ZXN0SWQiOiIyMjAiLCJuZXdPbGQi" +
                "OiJuZXciLCJlc2lkIjoiY2U0OWM3N2Y2OGMwNGU4MTg2ZGNiYmFmYmYxYzQxM2YiLCJldmVudE5h" +
                "bWUiOiJldmVudF93ZWxjb21lX2JlZ2luIiwiZ2FpZCI6IjA5N2IyZDA3LWYwZGMtNDllYS1hNmVi" +
                "LWVmZjU1ODRjN2I0OSJ9");
        map1.put("md5_android", "B8EA70EB250D38D9DB371724571D8C4A");
        getPresenter().event(map1, actionUrl);

    }

    @Override
    protected void setListener() {

    }
}
