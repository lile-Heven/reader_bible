package com.sdattg.vip.pay;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by mcy on 2017/7/6.
 * 描述:
 */

public class PayTabViewpager extends FragmentPagerAdapter {


    private Context context;
    private List<String> titles;
    private List<PayBaseFragment> fragmentList;

    public PayTabViewpager(FragmentManager fm, Context context, List<String> titles, List<PayBaseFragment> fragmentList) {
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
