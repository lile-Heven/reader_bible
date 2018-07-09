package com.sdattg.vip.pay;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.sdattg.vip.R;
import com.sdattg.vip.pay.PayTabViewpager;

import java.util.List;

/**
 * Created by yinqm on 2018/4/24.
 */

public  abstract class PayBaseTablayoutActivity extends PayBaseTitleBarActivity {
    protected abstract List<PayBaseFragment> getFragmentList();
    protected abstract List<String> getFragmentTitleList();
    private List<String> titles;
    private List<PayBaseFragment> fragmentList;
    private PayTabViewpager adapter;
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
        adapter = new PayTabViewpager(getSupportFragmentManager(), this, titles, fragmentList);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
    }



    @Override
    public int bindLayout() {
        return R.layout.lib_tablayout;
    }


}
