package com.sdattg.vip.pay;

import android.view.View;
import android.widget.TextView;

import com.sdattg.vip.R;


/**
 * Created by yinqm on 2018/7/3.
 */
public class PayFragment extends PayBaseFragment implements View.OnClickListener {
    private TextView btn_0;
    private TextView btn_1;
    private TextView btn_2;
    private TextView btn_3;
    private TextView btn_4;

    private TextView tv_copy;

    @Override
    protected int getLayoutId() {
        return R.layout.pay_fragemt;
    }

    @Override
    protected void addViewLayout(View view) {

    }

    @Override
    protected void initView(View view) {
        btn_0 = view.findViewById(R.id.btn_0);
        btn_1 = view.findViewById(R.id.btn_1);
        btn_2 = view.findViewById(R.id.btn_2);
        btn_3 = view.findViewById(R.id.btn_3);
        btn_4 = view.findViewById(R.id.btn_4);

        tv_copy = view.findViewById(R.id.tv_copy);
    }

    @Override
    protected void initData() {
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);

        tv_copy.setOnClickListener(this);
    }

    @Override
    protected void iniLogic() {

    }

    @Override
    protected void httpRequest() {

    }

    @Override
    public void onClick(View view) {
        setDefault();
        switch (view.getId()) {
            case R.id.btn_0:
                btn_0.setBackground(getResources().getDrawable(R.drawable.tabswitcher_long));
                btn_0.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case R.id.btn_1:
                btn_1.setBackground(getResources().getDrawable(R.drawable.tabswitcher_long));
                btn_1.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case R.id.btn_2:
                btn_2.setBackground(getResources().getDrawable(R.drawable.tabswitcher_long));
                btn_2.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case R.id.btn_3:
                btn_3.setBackground(getResources().getDrawable(R.drawable.tabswitcher_long));
                btn_3.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case R.id.btn_4:
                btn_4.setBackground(getResources().getDrawable(R.drawable.tabswitcher_long));
                btn_4.setTextColor(getResources().getColor(R.color.yellow));
                break;

            case R.id.tv_copy:
                //btn_4.setBackground(getResources().getDrawable(R.drawable.tabswitcher_long));
                //btn_4.setTextColor(getResources().getColor(R.color.yellow));

                break;

        }
    }

    private void setDefault() {
        btn_0.setBackground(getResources().getDrawable(R.drawable.tabswitcher_short));
        btn_1.setBackground(getResources().getDrawable(R.drawable.tabswitcher_short));
        btn_2.setBackground(getResources().getDrawable(R.drawable.tabswitcher_short));
        btn_3.setBackground(getResources().getDrawable(R.drawable.tabswitcher_short));
        btn_4.setBackground(getResources().getDrawable(R.drawable.tabswitcher_short));

        btn_0.setTextColor(getResources().getColor(R.color.gray4));
        btn_1.setTextColor(getResources().getColor(R.color.gray4));
        btn_2.setTextColor(getResources().getColor(R.color.gray4));
        btn_3.setTextColor(getResources().getColor(R.color.gray4));
        btn_4.setTextColor(getResources().getColor(R.color.gray4));
    }
}
