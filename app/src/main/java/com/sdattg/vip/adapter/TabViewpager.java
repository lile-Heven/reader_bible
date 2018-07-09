package com.sdattg.vip.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sdattg.vip.base.BaseFragment;

import java.util.List;

/**
 * Created by mcy on 2017/7/6.
 * 描述:
 */

public class TabViewpager extends FragmentPagerAdapter {


    private Context context;
    private List<String> titles;
    private List<BaseFragment> fragmentList;

    public TabViewpager(FragmentManager fm, Context context, List<String> titles, List<BaseFragment> fragmentList) {
        super(fm);
        this.context = context;
        this.titles = titles;
        this.fragmentList = fragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
