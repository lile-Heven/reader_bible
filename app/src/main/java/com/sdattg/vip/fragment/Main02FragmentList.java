package com.sdattg.vip.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.sdattg.vip.R;
import com.sdattg.vip.adapter.Tab01ProductAdapter;
import com.sdattg.vip.base.BaseFragment;
import com.sdattg.vip.bean.CategoryBean;
import com.sdattg.vip.search.MyCategoryDBHelper;
import com.sdattg.vip.tool.ZipTool;
import com.sdattg.vip.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinqm on 2018/6/26.
 */
public class Main02FragmentList extends BaseFragment implements View.OnClickListener{
    private RecyclerView rv;
    private Tab01ProductAdapter adapter;
    private List<String> list_item;

    public static int book_count_jiuyue = 0;
    public static int book_count_xinyue = 0;

    private List<CategoryBean> books_jiuyue;
    private List<CategoryBean> books_xinyue;
    @Override
    protected int getLayoutId() {
        return R.layout.main02fragment_list;
    }

    @Override
    protected void addViewLayout(View view) {

    }

    @Override
    protected void initView(View view) {
        initMyDatas();

        /*list = new ArrayList<BookBeanOnShuKu>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");*/
        Log.d("Main02FragmentList", "list_item.size():" + list_item.size());
        adapter = new Tab01ProductAdapter(R.layout.recy_item, list_item, getActivity());
        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
        //rv.setOnClickListener();
    }

    @Override
    public void onClick(View view) {

    }

    private void initMyDatas(){

        list_item = new ArrayList<String>();

        MyCategoryDBHelper myCategoryDBHelper = new MyCategoryDBHelper(getContext());
        List<CategoryBean> list_jiuyue = myCategoryDBHelper.getCategory(FileUtil.replaceBy_("01-旧约"), null);
        for (CategoryBean categoryBean:
                list_jiuyue) {
            BookBeanOnShuKu bean = new BookBeanOnShuKu();
            bean.author = "作者:摩西";
            bean.title = categoryBean.categoryName.substring(categoryBean.categoryName.indexOf("_") + 1);
            List<CategoryBean> books_jiuyue = myCategoryDBHelper.getCategory(FileUtil.replaceBy_(categoryBean.categoryName), "jieshao");
            if(books_jiuyue.size() > 0){
                bean.jieshao = books_jiuyue.get(0).categoryJieshao;
                Log.d("Tab01Product", "bean.jieshao:" + bean.jieshao);
            }else{
                bean.jieshao = "无介绍";
            }
            list_item.add(bean.toString());
            Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
        }

        book_count_jiuyue = list_jiuyue.size();

        List<CategoryBean> list_xinyue = myCategoryDBHelper.getCategory(FileUtil.replaceBy_("02-新约"), null);
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
            list_item.add(bean.toString());
            Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
        }

        book_count_xinyue = list_xinyue.size();
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
    protected void initData() {
    }

    @Override
    protected void iniLogic() {

    }

    @Override
    protected void httpRequest() {

    }
}
