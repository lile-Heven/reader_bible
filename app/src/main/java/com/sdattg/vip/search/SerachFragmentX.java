package com.sdattg.vip.search;

import android.util.Log;
import android.view.View;

import com.sdattg.vip.R;
import com.sdattg.vip.base.BaseFragment;
import com.sdattg.vip.bean.CategoryBean;
import com.sdattg.vip.fragment.Main02FragmentBible;
import com.sdattg.vip.util.FileUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yinqm on 2018/7/5.
 */
public class SerachFragmentX extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.serach_fragment_x;
    }

    @Override
    protected void addViewLayout(View view) {

    }

    @Override
    protected void initView(View view) {
        switch (SerachActivity.category_selected){
            case 0:
                querybooks("01-圣经");
                break;
            case 1:
                querybooks("02-怀著");
                break;
            case 2:
                querybooks("其他");
                break;
            case 3:
                querybooks("英文");
                break;
        }
        String[] test = new String[]{"01_旧约test", "02_新约test"};

        view.findViewById(R.id.)
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


    public static HashMap<String, List<String>> results;
    public static  List<String> list_xinyue_str;
    private void querybooks(String dirName){
        MyCategoryDBHelper myCategoryDBHelper = new MyCategoryDBHelper(getContext());
        results = myCategoryDBHelper.getQueryBooks(FileUtil.replaceBy_(dirName), null);



        /*for (CategoryBean categoryBean:
                list_xinyue) {
            Main02FragmentBible.BookBeanOnShuKu bean = new Main02FragmentBible.BookBeanOnShuKu();
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
            list_item.add(bean.toString());
            Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
        }*/

    }
}
