package com.sdattg.vip.pay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdattg.vip.R;


/**
 * Created by yinqm on 2018/3/13.
 */

public abstract class PayBaseTitleBarActivity extends PayBaseActivity {
    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    public abstract String setTitle();
    TextView tvBack;
    public LinearLayout llLeft;
    TextView tvTitle;
    LinearLayout rlBar;
    LinearLayout topLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.pay_lib_toolbar;
    }


    @Override
    protected void addViewLayout() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        llLeft = (LinearLayout) findViewById(R.id.ll_left);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rlBar = (LinearLayout) findViewById(R.id.rl_bar);
        topLayout = (LinearLayout) findViewById(R.id.top_layout);


        View mContextView = LayoutInflater.from(this)
                .inflate(bindLayout(), null);
        topLayout.addView(mContextView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        if (setTitle() != null) {
            tvTitle.setText(setTitle());
        }else{
            rlBar.setVisibility(View.GONE);
        }
        llLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null) {
                    if (getCurrentFocus().getWindowToken() != null) {
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                finish();
            }
        });
    }


    /**
     * 设置是否显示返回键
     */
    public void setShowBack(boolean isShow) {
        if (isShow) {
            llLeft.setVisibility(View.VISIBLE);
        } else {
            llLeft.setVisibility(View.GONE);
        }
    }





}
