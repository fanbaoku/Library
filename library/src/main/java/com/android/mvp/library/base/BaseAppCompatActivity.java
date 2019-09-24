package com.android.mvp.library.base;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.android.mvp.library.utils.CleanLeakUtils;
import com.android.mvp.library.utils.UIUtils;
import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//import butterknife.ButterKnife;

import static com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout.EDGE_MODE_DEFAULT;


/**
 * mvp activity 基类
 */
@ParallaxBack(edge = ParallaxBack.Edge.LEFT, layout = ParallaxBack.Layout.PARALLAX)
public abstract class BaseAppCompatActivity<I, P extends BasePresenter<I>> extends
        AppCompatActivity {
    private P presenter;
    public ParallaxBackLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
//        ButterKnife.bind(this);
        init();
        setListener();
        layout = ParallaxHelper.getParallaxBackLayout(this, true);
        layout.setEdgeMode(EDGE_MODE_DEFAULT);//边缘滑动
    }

    @Override
    public Resources getResources() {
        //获取到resources对象
        Resources res = super.getResources();
        //修改configuration的fontScale属性
        res.getConfiguration().fontScale = 1;
        //将修改后的值更新到metrics.scaledDensity属性上
        res.updateConfiguration(null, null);
        return res;
    }

    //初始化布局
    protected abstract int getLayoutId();

    //初始化控件
    protected abstract void init();

    //初始化监听事件
    protected abstract void setListener();

    //初始化Presenter
    protected void setPresenter(P presenter) {
        this.presenter = presenter;
        presenter.attachView((I) this);
    }

    //获取Presenter
    public P getPresenter() {
        return presenter;
    }



    @Override
    protected void onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(UIUtils.getContext());
        CleanLeakUtils.fixHuaWeiMemoryLeak();
        super.onDestroy();
        //Presenter解除绑定
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus() && event.getAction() == MotionEvent.ACTION_UP) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}
