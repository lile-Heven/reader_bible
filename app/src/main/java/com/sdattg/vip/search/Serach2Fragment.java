package com.sdattg.vip.search;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.sdattg.vip.R;
import com.sdattg.vip.adapter.TabViewpager;
import com.sdattg.vip.base.BaseFragment;
import com.sdattg.vip.bean.CategoryBean;
import com.sdattg.vip.fragment.Main02FragmentBible;
import com.sdattg.vip.util.FileUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinqm on 2018/7/5.
 */
public class Serach2Fragment extends BaseFragment {
    private List<String> titles;
    private List<BaseFragment> fragmentList;
    private TabViewpager adapter;
    private ViewPager viewpager;
    public TabLayout tabLayout;
    public ListView lv_sresult;

    public static  List<CategoryBean> list_xinyue;
    public static  List<String> list_xinyue_str;

    @Override
    protected int getLayoutId() {
        return R.layout.serach_activity2;
    }

    @Override
    protected void addViewLayout(View view) {

    }

    @Override
    protected void initView(View view) {
        viewpager = (ViewPager)view. findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        lv_sresult = (ListView) view.findViewById(R.id.lv_sresult);
    }

    @Override
    protected void initData() {
        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titles.add("搜索页面");
        //titles.add("有声");
        SerachFragment2 serachFragment2 = new SerachFragment2();
        //SerachFragment2 serachFragment3 = new SerachFragment2();
        fragmentList.add(serachFragment2);
        //fragmentList.add(serachFragment3);
        adapter = new TabViewpager(getChildFragmentManager(), getActivity(), titles, fragmentList);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);

        switch (SerachActivity.SEARCH_KIND){
            case 0:
                querybookFromSQL(SerachActivity.SEARCH_CONTENT);
                //lv_sresult.setAdapter(new MySearchResultBookAdapter());
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }

    }

    private List<String> querybookFromSQL(String content){
        MyCategoryDBHelper myCategoryDBHelper = new MyCategoryDBHelper(getContext());
        list_xinyue = myCategoryDBHelper.getCategory(FileUtil.replaceBy_("02-新约"), null);
        for (CategoryBean categoryBean:
                list_xinyue) {
            BookBeanOnShuKu bean = new BookBeanOnShuKu();
            bean.title = "";
            bean.title = categoryBean.categoryName.substring(categoryBean.categoryName.indexOf("_") + 1);
            bean.author = "作者:摩西";
            List<CategoryBean> books_xinyue = myCategoryDBHelper.getCategory(FileUtil.replaceBy_(categoryBean.categoryName), "jieshao");
            if(books_xinyue.size() > 0){
                bean.jieshao = books_xinyue.get(0).categoryJieshao;
                Log.d("Tab01Product", "bean.jieshao:" + bean.jieshao);
            }else{
                bean.jieshao = "无介绍";
            }
            list_xinyue_str.add(bean.toString());
            Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
        }

        return null;
    }

    private class BookBeanOnShuKu{
        public String title;
        public String author;
        public String jieshao;

        @Override
        public String toString() {

            return title + "#3#" + author + "#3#" + jieshao;
        }
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
