package com.android.mvp.test;
import com.android.mvp.library.base.BaseAppCompatActivity;
import com.android.mvp.library.okhttp.CallBackUtil;
import com.android.mvp.library.okhttp.HttpUtil;
import com.github.anzewei.parallaxbacklayout.ParallaxHelper;

public class MainActivity extends BaseAppCompatActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        //关闭滑动返回功能
        ParallaxHelper.getInstance().disableParallaxBack(this);
    }

    @Override
    protected void setListener() {

    }
}
