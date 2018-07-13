package com.sdattg.vip.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdattg.vip.R;
import com.sdattg.vip.bean.CategoryBean;
import com.sdattg.vip.fragment.Main02FragmentList;
import com.sdattg.vip.search.MyCategoryDBHelper;
import com.sdattg.vip.tool.ZipTool;
import com.sdattg.vip.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by yinqm on 2018/4/17.
 */

public class Tab01ProductAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public Tab01ProductAdapter(@LayoutRes int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {

        List<String> list_item = getData();


        //((TextView)helper.getView(R.id.booklist_author)).setText("test" + helper.getLayoutPosition());

        if (helper.getLayoutPosition() == 0) {
            helper.getView(R.id.ll_top).setVisibility(View.VISIBLE);
            ((TextView)(helper.getView(R.id.tv_first_title))).setText("旧约");

            ((TextView)(helper.getView(R.id.tv_first_title_2))).setText("共(" + Main02FragmentList.book_count_jiuyue + ")本");
        }  else if(helper.getLayoutPosition() == Main02FragmentList.book_count_jiuyue){
            helper.getView(R.id.ll_top).setVisibility(View.VISIBLE);
            ((TextView)(helper.getView(R.id.tv_first_title))).setText("新约");
            ((TextView)(helper.getView(R.id.tv_first_title_2))).setText("共(" + Main02FragmentList.book_count_xinyue + ")本");
        } else {
            helper.getView(R.id.ll_top).setVisibility(View.GONE);
        }
        String[] strs = list_item.get(helper.getLayoutPosition()).split("#3#");
        ((TextView)(helper.getView(R.id.tv_book_title))).setText(strs[0]);
        ((TextView)(helper.getView(R.id.tv_book_author))).setText(strs[1]);
        ((TextView)(helper.getView(R.id.tv_book_jieshao))).setText(strs[2]);
    }




}
