package com.android.mvp.library.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mvp.library.application.BaseApplication;
import com.android.mvp.library.utils.CleanLeakUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * mvp framgent 基类
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class BaseFragment<I, P extends BasePresenter<I>> extends Fragment implements View.OnClickListener {
    private ViewGroup view;
    private P presenter;

    private View mStatusBarView;
    private boolean isViewInitFinished = false;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
//        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitFinished = true;
        init();
        setListener();
    }

    @Override
    public void onDestroyView() {
        CleanLeakUtils.fixInputMethodManagerLeak(BaseApplication.getApplication());
        CleanLeakUtils.fixHuaWeiMemoryLeak();
        super.onDestroyView();
    }

    // 模仿Activity的查找控件
    public View findViewById(int id) {
        return getView().findViewById(id);
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isViewInitFinished && isVisibleToUser) {
            getData();
            if (presenter != null)
                presenter.cancelTag(this);
        }
    }

    protected abstract int getLayoutId();

    protected abstract void init();

    protected abstract void setListener();

    protected void setPresenter(P presenter) {
        this.presenter = presenter;
        presenter.attachView((I) this);
    }

    public P getPresenter() {
        return presenter;
    }

    @Nullable
    @Override
    public ViewGroup getView() {
        return view;
    }

    public void getData() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @Override
    public void onClick(View v) {
    }
}
