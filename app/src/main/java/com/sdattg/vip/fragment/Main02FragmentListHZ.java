package com.sdattg.vip.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sdattg.vip.R;
import com.sdattg.vip.adapter.Tab01ProductAdapter;
import com.sdattg.vip.adapter.Tab01ProductAdapterHZ;
import com.sdattg.vip.base.BaseFragment;
import com.sdattg.vip.bean.CategoryBean;

import java.util.List;

/**
 * Created by yinqm on 2018/6/26.
 */
public class Main02FragmentListHZ extends BaseFragment implements View.OnClickListener{
    public RecyclerView rv;
    public Tab01ProductAdapterHZ adapter;
    public View view2;
    private List<String> list;

    private List<CategoryBean> books_jiuyue;
    private List<CategoryBean> books_xinyue;

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main02fragment_list;
    }

    @Override
    protected void addViewLayout(View view) {

    }

    @Override
    protected void initView(View view) {
        this.view2 = view;
        /*list = new ArrayList<BookBeanOnShuKu>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");*/
        //Log.d("Main02FragmentList", "list_item.size():" + Main02FragmentBible.list_item.size());

        Bundle bundle = getArguments();
        switch (bundle.getInt("title")){
            case 0:
                adapter = new Tab01ProductAdapterHZ(R.layout.recy_item, Main02FragmentHuaiZhu.list_item, getActivity());
                break;
            case 1:
                adapter = new Tab01ProductAdapterHZ(R.layout.recy_item, Main02FragmentHuaiZhu.list_bibei_str, getActivity());
                break;
            case 2:
                adapter = new Tab01ProductAdapterHZ(R.layout.recy_item, Main02FragmentHuaiZhu.list_jiankang_str, getActivity());
                break;
            case 3:
                adapter = new Tab01ProductAdapterHZ(R.layout.recy_item, Main02FragmentHuaiZhu.list_jiaoyu_str, getActivity());
                break;
            case 4:
                adapter = new Tab01ProductAdapterHZ(R.layout.recy_item, Main02FragmentHuaiZhu.list_mianyan_str, getActivity());
                break;
            case 5:
                adapter = new Tab01ProductAdapterHZ(R.layout.recy_item, Main02FragmentHuaiZhu.list_gaojian_str, getActivity());
                break;
            case 6:
                adapter = new Tab01ProductAdapterHZ(R.layout.recy_item, Main02FragmentHuaiZhu.list_zhengyan_str, getActivity());
                break;
            case 7:
                adapter = new Tab01ProductAdapterHZ(R.layout.recy_item, Main02FragmentHuaiZhu.list_budao_str, getActivity());
                break;
            case 8:
                adapter = new Tab01ProductAdapterHZ(R.layout.recy_item, Main02FragmentHuaiZhu.list_lingxiu_str, getActivity());
                break;
        }

        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
        //rv.setOnClickListener();
    }

    @Override
    public void onClick(View view) {

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
