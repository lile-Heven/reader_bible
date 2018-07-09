package com.sdattg.vip.search;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;


import com.sdattg.vip.R;
import com.sdattg.vip.adapter.TabViewpager;
import com.sdattg.vip.base.BaseActivity;
import com.sdattg.vip.base.BaseFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinqm on 2018/7/5.
 */
public class SerachActivity2 extends BaseActivity {

    private List<String> titles;
    private List<BaseFragment> fragmentList;
    private TabViewpager adapter;
    private ViewPager viewpager;
    public TabLayout tabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.serach_activity2;
    }

    @Override
    protected void addViewLayout() {

    }

    @Override
    protected void initView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
    }

    @Override
    protected void initData() {
        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titles.add("书籍");
        titles.add("有声");
        SerachFragment2 serachFragment2 = new SerachFragment2();
        SerachFragment2 serachFragment3 = new SerachFragment2();
        fragmentList.add(serachFragment2);
        fragmentList.add(serachFragment3);
        adapter = new TabViewpager(getSupportFragmentManager(), this, titles, fragmentList);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    protected void iniLogic() {
        setIndicator(tabLayout,50,50);
    }

    @Override
    protected void httpRequest() {

    }
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


    }
}
