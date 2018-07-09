package com.sdattg.vip.pay;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PayActivity extends PayBaseTablayoutActivity {


    @Override
    protected List<PayBaseFragment> getFragmentList() {
        List<PayBaseFragment> list = new ArrayList<>();
        PayFragment payFragment = new PayFragment();
        PayFragment tab02Fragment = new PayFragment();
        list.add(payFragment);
        list.add(tab02Fragment);
        return list;
    }

    @Override
    protected List<String> getFragmentTitleList() {
        List<String> list = new ArrayList<>();
        list.add("在线开通");
        list.add("离线激活");
        return list;
    }

    @Override
    public String setTitle() {
        return "账户信息";
    }

    @Override
    protected void iniLogic() {
        setIndicator(tabLayout,40,40);
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
