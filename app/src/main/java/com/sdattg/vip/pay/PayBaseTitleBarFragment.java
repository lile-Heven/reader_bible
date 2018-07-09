package com.sdattg.vip.pay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdattg.vip.R;


/**
 * Created by yinqm on 2018/4/3.
 */

public abstract class PayBaseTitleBarFragment extends PayBaseFragment {
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
        return R.layout.lib_toolbar;
    }

    @Override
    protected void addViewLayout(View view) {
        tvBack = (TextView) view.findViewById(R.id.tv_back);
        llLeft = (LinearLayout) view.findViewById(R.id.ll_left);
        tvTitle = (TextView)view. findViewById(R.id.tv_title);
        rlBar = (LinearLayout)view. findViewById(R.id.rl_bar);
        topLayout = (LinearLayout)view. findViewById(R.id.top_layout);


        View mContextView = LayoutInflater.from(getActivity())
                .inflate(bindLayout(), null);
        topLayout.addView(mContextView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        if (setTitle() != null) {
            tvTitle.setText(setTitle());
        }else{
            rlBar.setVisibility(View.GONE);
        }
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getActivity().getCurrentFocus() != null) {
                    if (getActivity().getCurrentFocus().getWindowToken() != null) {
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                getActivity().finish();
            }
        });
    }


}
