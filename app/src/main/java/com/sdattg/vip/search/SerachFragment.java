package com.sdattg.vip.search;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.sdattg.vip.R;
import com.sdattg.vip.base.BaseFragment;
import com.sdattg.vip.util.SharePreferencesUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by yinqm on 2018/7/5.
 */
public class SerachFragment extends BaseFragment {
    private RecyclerView rv;
    private ListView lv_shistory;
    private List<String> names;

    @Override
    protected int getLayoutId() {
        return R.layout.serach_fragment_01;
    }

    @Override
    protected void addViewLayout(View view) {

    }

    @Override
    protected void initView(View view) {
        /*list = new ArrayList<>();
        list.add("书籍搜索");
        list.add("标题搜索");
        list.add("目录搜索");
        list.add("内容搜索");
        *//*list.add("飞魔幻");
        list.add("逃之夭夭");
        list.add("飞言情");
        list.add("漫画派对");
        list.add("杂志");
        list.add("一千零一夜");*//*
        rv = view.findViewById(R.id.rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
        rv.setLayoutManager(gridLayoutManager);
        adapter = new SerachAdapter(R.layout.serach_item,list,getActivity());
        rv.setAdapter(adapter);*/


        lv_shistory = (ListView) view.findViewById(R.id.lv_shistory);
        names = new ArrayList<String>();
        SerachActivity.shistroys = (HashSet)SharePreferencesUtil.SHistory(getContext(), null);
        Iterator<String> it = SerachActivity.shistroys.iterator();
        while(it.hasNext()){
            names.add(it.next());
            System.out.println(it.next());
        }

        lv_shistory.setAdapter(new MySearchHistoryAdapter(getContext(), names));
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
