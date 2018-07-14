package com.sdattg.vip.fragment;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.sdattg.vip.base.BaseFragment;
import com.sdattg.vip.base.BaseTablayoutFtagment2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinqm on 2018/6/27.
 */
public class Main02Fragment extends BaseTablayoutFtagment2 {


    @Override
    protected List<BaseFragment> getFragmentList() {
        List<BaseFragment> list = new ArrayList<>();
        Main02FragmentBible main01FragmentBible= new Main02FragmentBible();
        Main02FragmentHuaiZhu main02FragmentHuaiZhu= new Main02FragmentHuaiZhu();
        list.add(main01FragmentBible);
        list.add(main02FragmentHuaiZhu);
        return list;
    }

    @Override
    protected List<String> getFragmentTitleList() {
        List<String> list = new ArrayList<>();
        list.add("圣经");
        list.add("怀著");
        return list;
    }

    @Override
    public String setTitle() {
        return null;
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

