package com.sdattg.vip.search;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdattg.vip.R;
import com.sdattg.vip.base.BaseActivity;


/**
 * Created by yinqm on 2018/7/5.
 */
public class SerachActivity extends BaseActivity {
    private ImageView iv_back;
    private TextView tv_serach;
    @Override
    protected int getLayoutId() {
        return R.layout.serach_activity;
    }

    @Override
    protected void addViewLayout() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_serach = findViewById(R.id.tv_serach);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Serach2Fragment  serachFragment = new Serach2Fragment();
                replaceFragment2(serachFragment,R.id.fl);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void iniLogic() {
        SerachFragment  serachFragment = new SerachFragment();
        replaceFragment(serachFragment,R.id.fl);
    }

    @Override
    protected void httpRequest() {

    }
}
