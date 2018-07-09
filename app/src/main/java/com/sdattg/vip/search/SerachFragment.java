package com.sdattg.vip.search;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.sdattg.vip.R;
import com.sdattg.vip.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinqm on 2018/7/5.
 */
public class SerachFragment extends BaseFragment {
    private RecyclerView rv;
    private List<String> list;
    private SerachAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.serach_fragment_01;
    }

    @Override
    protected void addViewLayout(View view) {

    }

    @Override
    protected void initView(View view) {
        list = new ArrayList<>();
        list.add("故事会");
        list.add("花火");
        list.add("知音");
        list.add("读者");
        list.add("飞魔幻");
        list.add("逃之夭夭");
        list.add("飞言情");
        list.add("漫画派对");
        list.add("杂志");
        list.add("一千零一夜");
        rv = view.findViewById(R.id.rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rv.setLayoutManager(gridLayoutManager);
        adapter = new SerachAdapter(R.layout.serach_item,list,getActivity());
        rv.setAdapter(adapter);
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
