package com.sdattg.vip.pay;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * <p>Fragment的基类</p>
 *
 * @author 张华洋
 * @name PayBaseFragment
 */
@Keep
public abstract class PayBaseFragment extends Fragment {

    protected PayBaseActivity mActivity;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(),container,false);
        addViewLayout(view);
        initView(view);
        initData();
        iniLogic();
        httpRequest();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (PayBaseActivity) context;
    }


    /**
     * 获取宿主Activity
     *
     * @return PayBaseActivity
     */
    protected PayBaseActivity getHoldingActivity() {
        return mActivity;
    }


    /**
     * 添加fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void addFragment(PayBaseFragment fragment, @IdRes int frameId) {
        getHoldingActivity().addFragment(fragment, frameId);

    }


    /**
     * 替换fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void replaceFragment(PayBaseFragment fragment, @IdRes int frameId) {
        getHoldingActivity().replaceFragment(fragment, frameId);
    }


    /**
     * 隐藏fragment
     *
     * @param fragment
     */
    protected void hideFragment(PayBaseFragment fragment) {
        getHoldingActivity().hideFragment(fragment);
    }


    /**
     * 显示fragment
     *
     * @param fragment
     */
    protected void showFragment(PayBaseFragment fragment) {
        getHoldingActivity().showFragment(fragment);
    }


    /**
     * 移除Fragment
     *
     * @param fragment
     */
    protected void removeFragment(PayBaseFragment fragment) {
        getHoldingActivity().removeFragment(fragment);

    }


    /**
     * 弹出栈顶部的Fragment
     */
    protected void popFragment() {
        getHoldingActivity().popFragment();
    }


    protected abstract int getLayoutId();
    protected abstract void addViewLayout(View view);//添加子布局
    protected abstract void initView(View view);//
    protected abstract void initData();//初始化数据
    protected abstract void iniLogic();//逻辑
    protected abstract void httpRequest();//数据请求
}
