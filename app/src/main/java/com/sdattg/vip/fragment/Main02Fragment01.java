package com.sdattg.vip.fragment;


import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.sdattg.vip.base.BaseFragment;
import com.sdattg.vip.base.BaseTablayoutFtagment3;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinqm on 2018/6/26.
 */
public class Main02Fragment01 extends BaseTablayoutFtagment3 {


    @Override
    protected List<BaseFragment> getFragmentList() {
        List<BaseFragment> list = new ArrayList<>();
        Main02FragmentList main01Fragment = new Main02FragmentList();
        Main02FragmentList main02Fragment = new Main02FragmentList();
        Main02FragmentList main03Fragment = new Main02FragmentList();
        list.add(main01Fragment);
        list.add(main02Fragment);
        list.add(main03Fragment);
        return list;
    }

    @Override
    protected List<String> getFragmentTitleList() {
        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("旧约");
        list.add("新约");
        return list;
    }

    @Override
    public String setTitle() {
        return null;
    }

    @Override
    protected void iniLogic() {
        setTabLine(tabLayout,20,20);
    }

    @Override
    protected void httpRequest() {

    }


    public void setTabLine(TabLayout tab,int left,int right){
        try {
            Class<?> tablayout = tab.getClass();
            Field tabStrip = tablayout.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            LinearLayout ll_tab= (LinearLayout) tabStrip.get(tabLayout);
            for (int i = 0; i < ll_tab.getChildCount(); i++) {
                View child = ll_tab.getChildAt(i);
                child.setPadding(0,0,0,0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1);
                //修改两个tab的间距
                params.setMarginStart(dip2px(getActivity(),left));
                params.setMarginEnd(dip2px(getActivity(),right));
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp==dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
