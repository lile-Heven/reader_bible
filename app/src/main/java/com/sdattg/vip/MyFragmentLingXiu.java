package com.sdattg.vip;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.sdattg.vip.base.BaseFragment;
import com.sdattg.vip.R;
import com.sdattg.vip.pay.PayActivity;

/**
 * Created by yinqm on 2018/6/27.
 */
public class MyFragmentLingXiu extends BaseFragment {
    private Button bt_needpay;
    @Override
    protected int getLayoutId() {
        return R.layout.main_02_fragment;
    }

    @Override
    protected void addViewLayout(View view) {

    }

    @Override
    protected void initView(View view) {
        bt_needpay = (Button)view.findViewById(R.id.bt_needpay);
        bt_needpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PayActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                getActivity().startActivity(i);
            }
        });
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

