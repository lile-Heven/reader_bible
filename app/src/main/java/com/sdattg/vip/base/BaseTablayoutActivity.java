package com.sdattg.vip.base;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;


import com.sdattg.vip.R;
import com.sdattg.vip.adapter.TabViewpager;

import java.util.List;

/**
 * Created by yinqm on 2018/4/24.
 */

public  abstract class BaseTablayoutActivity extends BaseTitleBarActivity {
    protected abstract List<BaseFragment> getFragmentList();
    protected abstract List<String> getFragmentTitleList();
    private List<String> titles;
    private List<BaseFragment> fragmentList;
    private TabViewpager adapter;
    private ViewPager viewpager;
    public TabLayout tabLayout;

    @Override
    protected void initView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
    }

    @Override
    protected void initData() {
        titles = getFragmentTitleList();
        fragmentList = getFragmentList();
        adapter = new TabViewpager(getSupportFragmentManager(), this, titles, fragmentList);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
    }



    @Override
    public int bindLayout() {
        return R.layout.lib_tablayout;
    }


}
