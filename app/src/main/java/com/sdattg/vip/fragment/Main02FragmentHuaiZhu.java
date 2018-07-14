package com.sdattg.vip.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.sdattg.vip.base.BaseFragment;
import com.sdattg.vip.base.BaseTablayoutFtagment3HZ;
import com.sdattg.vip.bean.CategoryBean;
import com.sdattg.vip.search.MyCategoryDBHelper;
import com.sdattg.vip.util.FileUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinqm on 2018/6/26.
 */
public class Main02FragmentHuaiZhu extends BaseTablayoutFtagment3HZ {
    public static int book_count_bibei = 0;
    public static int book_count_jiankang = 0;
    public static int book_count_jiaoyu = 0;
    public static int book_count_mianyan = 0;
    public static int book_count_gaojian = 0;
    public static int book_count_zhengyan = 0;
    public static int book_count_budao = 0;
    public static int book_count_lingxiu = 0;
    public static int book_count_3 = 0;

    public static List<String> list_item;
    public static List<CategoryBean> list_bibei;
    public static List<String> list_bibei_str;
    public static  List<CategoryBean> list_jiankang;
    public static  List<String> list_jiankang_str;
    public static  List<CategoryBean> list_jiaoyu;
    public static  List<String> list_jiaoyu_str;
    public static  List<CategoryBean> list_mianyan;
    public static  List<String> list_mianyan_str;
    public static  List<CategoryBean> list_gaojian;
    public static  List<String> list_gaojian_str;
    public static  List<CategoryBean> list_zhengyan;
    public static  List<String> list_zhengyan_str;
    public static  List<CategoryBean> list_budao;
    public static  List<String> list_budao_str;
    public static  List<CategoryBean> list_lingxiu;
    public static  List<String> list_lingxiu_str;

    Main02FragmentListHZ main02FragmentListHZ_0 = new Main02FragmentListHZ();
    Main02FragmentListHZ main02FragmentListHZ_1 = new Main02FragmentListHZ();
    Main02FragmentListHZ main02FragmentListHZ_2 = new Main02FragmentListHZ();
    Main02FragmentListHZ main02FragmentListHZ_3 = new Main02FragmentListHZ();
    Main02FragmentListHZ main02FragmentListHZ_4 = new Main02FragmentListHZ();
    Main02FragmentListHZ main02FragmentListHZ_5 = new Main02FragmentListHZ();
    Main02FragmentListHZ main02FragmentListHZ_6 = new Main02FragmentListHZ();
    Main02FragmentListHZ main02FragmentListHZ_7 = new Main02FragmentListHZ();
    Main02FragmentListHZ main02FragmentListHZ_8 = new Main02FragmentListHZ();
   /* Log.d("Main02FragmentList", "list_item.size():" + list_item.size());
    adapter = new Tab01ProductAdapter(R.layout.recy_item, list_item, getActivity());
    rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);*/
    @Override
    protected List<BaseFragment> getFragmentList() {
        List<BaseFragment> list = new ArrayList<>();
        Bundle bundle0 = new Bundle();
        bundle0.putInt("title", 0);
        main02FragmentListHZ_0.setArguments(bundle0);

        Bundle bundle1 = new Bundle();
        bundle1.putInt("title", 1);
        main02FragmentListHZ_1.setArguments(bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putInt("title", 2);
        main02FragmentListHZ_2.setArguments(bundle2);

        Bundle bundle3 = new Bundle();
        bundle3.putInt("title", 3);
        main02FragmentListHZ_3.setArguments(bundle3);

        Bundle bundle4 = new Bundle();
        bundle4.putInt("title", 4);
        main02FragmentListHZ_4.setArguments(bundle4);

        Bundle bundle5 = new Bundle();
        bundle5.putInt("title", 5);
        main02FragmentListHZ_5.setArguments(bundle5);

        Bundle bundle6 = new Bundle();
        bundle6.putInt("title", 6);
        main02FragmentListHZ_6.setArguments(bundle6);

        Bundle bundle7 = new Bundle();
        bundle7.putInt("title", 7);
        main02FragmentListHZ_7.setArguments(bundle7);

        Bundle bundle8 = new Bundle();
        bundle8.putInt("title", 8);
        main02FragmentListHZ_8.setArguments(bundle8);

        list.add(main02FragmentListHZ_0);
        list.add(main02FragmentListHZ_1);
        list.add(main02FragmentListHZ_2);
        list.add(main02FragmentListHZ_3);
        list.add(main02FragmentListHZ_4);
        list.add(main02FragmentListHZ_5);
        list.add(main02FragmentListHZ_6);
        list.add(main02FragmentListHZ_7);
        list.add(main02FragmentListHZ_8);
        return list;
    }

    @Override
    protected List<String> getFragmentTitleList() {
        initMyDatas();

        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("必备");
        list.add("健康");
        list.add("教育");
        list.add("勉言");
        list.add("稿件");
        list.add("证言");
        list.add("布道");
        list.add("灵修");

        return list;
    }

    private void initMyDatas(){

        list_item = new ArrayList<String>();
        list_bibei_str = new ArrayList<String>();
        list_jiankang_str = new ArrayList<String>();
        list_jiaoyu_str = new ArrayList<String>();
        list_mianyan_str = new ArrayList<String>();
        list_gaojian_str = new ArrayList<String>();
        list_zhengyan_str = new ArrayList<String>();
        list_budao_str = new ArrayList<String>();
        list_lingxiu_str = new ArrayList<String>();

        MyCategoryDBHelper myCategoryDBHelper = new MyCategoryDBHelper(getContext());

        list_bibei = myCategoryDBHelper.getCategory(FileUtil.replaceBy_("01-必备"), null);
        for (CategoryBean categoryBean:
                list_bibei) {
            BookBeanOnShuKu bean = new BookBeanOnShuKu();
            bean.author = "作者:怀爱伦";
            bean.title = categoryBean.categoryName.substring(categoryBean.categoryName.indexOf("_") + 1);
            List<CategoryBean> books_jiuyue = myCategoryDBHelper.getCategory(FileUtil.replaceBy_(categoryBean.categoryName), "jieshao");
            if(books_jiuyue.size() > 0){
                bean.jieshao = books_jiuyue.get(0).categoryJieshao;
                Log.d("Tab01Product", "bean.jieshao:" + bean.jieshao);
            }else{
                bean.jieshao = "暂没有介绍";
            }
            list_bibei_str.add(bean.toString());
            list_item.add(bean.toString());
            Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
        }
        book_count_bibei = list_bibei.size();


        list_jiankang = myCategoryDBHelper.getCategory(FileUtil.replaceBy_("02-健康"), null);
        for (CategoryBean categoryBean:
                list_jiankang) {
            BookBeanOnShuKu bean = new BookBeanOnShuKu();
            bean.title = "";
            bean.title = categoryBean.categoryName.substring(categoryBean.categoryName.indexOf("_") + 1);
            bean.author = "作者:怀爱伦";
            List<CategoryBean> books_xinyue = myCategoryDBHelper.getCategory(FileUtil.replaceBy_(categoryBean.categoryName), "jieshao");
            if(books_xinyue.size() > 0){
                bean.jieshao = books_xinyue.get(0).categoryJieshao;
                Log.d("Tab01Product", "bean.jieshao:" + bean.jieshao);
            }else{
                bean.jieshao = "暂没有介绍";
            }
            list_jiankang_str.add(bean.toString());
            list_item.add(bean.toString());
            Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
        }
        book_count_jiankang = list_jiankang.size();


        list_jiaoyu = myCategoryDBHelper.getCategory(FileUtil.replaceBy_("03-教育"), null);
        for (CategoryBean categoryBean:
                list_jiaoyu) {
            BookBeanOnShuKu bean = new BookBeanOnShuKu();
            bean.title = "";
            bean.title = categoryBean.categoryName.substring(categoryBean.categoryName.indexOf("_") + 1);
            bean.author = "作者:怀爱伦";
            List<CategoryBean> books_xinyue = myCategoryDBHelper.getCategory(FileUtil.replaceBy_(categoryBean.categoryName), "jieshao");
            if(books_xinyue.size() > 0){
                bean.jieshao = books_xinyue.get(0).categoryJieshao;
                Log.d("Tab01Product", "bean.jieshao:" + bean.jieshao);
            }else{
                bean.jieshao = "暂没有介绍";
            }
            list_jiaoyu_str.add(bean.toString());
            list_item.add(bean.toString());
            Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
        }
        book_count_jiaoyu = list_jiaoyu.size();

        book_count_3 = book_count_bibei + book_count_jiankang + book_count_jiaoyu;

        list_mianyan = myCategoryDBHelper.getCategory(FileUtil.replaceBy_("04-勉言"), null);
        for (CategoryBean categoryBean:
                list_mianyan) {
            BookBeanOnShuKu bean = new BookBeanOnShuKu();
            bean.title = "";
            bean.title = categoryBean.categoryName.substring(categoryBean.categoryName.indexOf("_") + 1);
            bean.author = "作者:怀爱伦";
            List<CategoryBean> books_xinyue = myCategoryDBHelper.getCategory(FileUtil.replaceBy_(categoryBean.categoryName), "jieshao");
            if(books_xinyue.size() > 0){
                bean.jieshao = books_xinyue.get(0).categoryJieshao;
                Log.d("Tab01Product", "bean.jieshao:" + bean.jieshao);
            }else{
                bean.jieshao = "暂没有介绍";
            }
            list_mianyan_str.add(bean.toString());
            list_item.add(bean.toString());
            Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
        }
        book_count_mianyan = list_mianyan.size();


        list_gaojian = myCategoryDBHelper.getCategory(FileUtil.replaceBy_("05-稿件"), null);
        for (CategoryBean categoryBean:
                list_gaojian) {
            BookBeanOnShuKu bean = new BookBeanOnShuKu();
            bean.title = "";
            bean.title = categoryBean.categoryName.substring(categoryBean.categoryName.indexOf("_") + 1);
            bean.author = "作者:怀爱伦";
            List<CategoryBean> books_xinyue = myCategoryDBHelper.getCategory(FileUtil.replaceBy_(categoryBean.categoryName), "jieshao");
            if(books_xinyue.size() > 0){
                bean.jieshao = books_xinyue.get(0).categoryJieshao;
                Log.d("Tab01Product", "bean.jieshao:" + bean.jieshao);
            }else{
                bean.jieshao = "暂没有介绍";
            }
            list_gaojian_str.add(bean.toString());
            list_item.add(bean.toString());
            Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
        }
        book_count_gaojian = list_gaojian.size();


        list_zhengyan = myCategoryDBHelper.getCategory(FileUtil.replaceBy_("06-证言"), null);
        for (CategoryBean categoryBean:
                list_zhengyan) {
            BookBeanOnShuKu bean = new BookBeanOnShuKu();
            bean.title = "";
            bean.title = categoryBean.categoryName.substring(categoryBean.categoryName.indexOf("_") + 1);
            bean.author = "作者:怀爱伦";
            List<CategoryBean> books_xinyue = myCategoryDBHelper.getCategory(FileUtil.replaceBy_(categoryBean.categoryName), "jieshao");
            if(books_xinyue.size() > 0){
                bean.jieshao = books_xinyue.get(0).categoryJieshao;
                Log.d("Tab01Product", "bean.jieshao:" + bean.jieshao);
            }else{
                bean.jieshao = "暂没有介绍";
            }
            list_zhengyan_str.add(bean.toString());
            list_item.add(bean.toString());
            Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
        }
        book_count_zhengyan = list_zhengyan.size();

        list_budao = myCategoryDBHelper.getCategory(FileUtil.replaceBy_("07-布道"), null);
        for (CategoryBean categoryBean:
                list_budao) {
            BookBeanOnShuKu bean = new BookBeanOnShuKu();
            bean.title = "";
            bean.title = categoryBean.categoryName.substring(categoryBean.categoryName.indexOf("_") + 1);
            bean.author = "作者:怀爱伦";
            List<CategoryBean> books_xinyue = myCategoryDBHelper.getCategory(FileUtil.replaceBy_(categoryBean.categoryName), "jieshao");
            if(books_xinyue.size() > 0){
                bean.jieshao = books_xinyue.get(0).categoryJieshao;
                Log.d("Tab01Product", "bean.jieshao:" + bean.jieshao);
            }else{
                bean.jieshao = "暂没有介绍";
            }
            list_budao_str.add(bean.toString());
            list_item.add(bean.toString());
            Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
        }
        book_count_budao = list_budao.size();

        list_lingxiu = myCategoryDBHelper.getCategory(FileUtil.replaceBy_("08-灵修"), null);
        for (CategoryBean categoryBean:
                list_lingxiu) {
            BookBeanOnShuKu bean = new BookBeanOnShuKu();
            bean.title = "";
            bean.title = categoryBean.categoryName.substring(categoryBean.categoryName.indexOf("_") + 1);
            bean.author = "作者:怀爱伦";
            List<CategoryBean> books_xinyue = myCategoryDBHelper.getCategory(FileUtil.replaceBy_(categoryBean.categoryName), "jieshao");
            if(books_xinyue.size() > 0){
                bean.jieshao = books_xinyue.get(0).categoryJieshao;
                Log.d("Tab01Product", "bean.jieshao:" + bean.jieshao);
            }else{
                bean.jieshao = "暂没有介绍";
            }
            list_lingxiu_str.add(bean.toString());
            list_item.add(bean.toString());
            Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
        }
        book_count_lingxiu = list_lingxiu.size();
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
