package com.sdattg.vip.base;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;


import com.sdattg.vip.R;
import com.sdattg.vip.adapter.TabViewpager;

import java.util.List;

/**
 * Created by yinqm on 2018/4/24.
 */

public abstract class BaseTablayoutFtagment2 extends BaseTitleBarFragment {
    protected abstract List<BaseFragment> getFragmentList();
    protected abstract List<String> getFragmentTitleList();
    private List<String> titles;
    private List<BaseFragment> fragmentList;
    private TabViewpager adapter;
    private ViewPager viewpager;
    public TabLayout tabLayout;
    @Override
    public int bindLayout() {
        return R.layout.lib_tablayout2;
    }


    @Override
    protected void initView(View view) {
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
    }

    @Override
    protected void initData() {
        titles = getFragmentTitleList();
        fragmentList = getFragmentList();
        adapter = new TabViewpager(getChildFragmentManager(), getActivity(), titles, fragmentList);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
    }


}
