package com.sdattg.vip.search;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdattg.vip.R;

import java.util.List;


/**
 * Created by yinqm on 2018/4/17.
 */

public class SerachAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements View.OnClickListener{
    private Context context;
    private int checked_now = 3;

    public SerachAdapter(@LayoutRes int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {

        /*if (helper.getLayoutPosition() == 0) {
            TextView textView = helper.getView(R.id.tv_tag);
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.serach_red));
        } else if (helper.getLayoutPosition() == 1) {
            helper.getView(R.id.tv_tag).setBackgroundColor(ContextCompat.getColor(context, R.color.serach_yellow));
        } else if (helper.getLayoutPosition() == 2) {
            helper.getView(R.id.tv_tag).setBackgroundColor(ContextCompat.getColor(context, R.color.serach_yellow2));
        } else {
            helper.getView(R.id.tv_tag).setBackgroundColor(ContextCompat.getColor(context, R.color.gray3));
        }*/

        //helper.setText(R.id.tv_tag,helper.getLayoutPosition()+"");
        //helper.setText(R.id.tv_title,s);
        helper.getView(R.id.cb).setTag(helper.getLayoutPosition()+"");
        helper.setText(R.id.cb,s);
        helper.getView(R.id.cb).setOnClickListener(this);
        ((CheckBox)helper.getView(R.id.cb)).setChecked(false);
        /*if (helper.getLayoutPosition() == 3) {
            ((CheckBox)helper.getView(R.id.cb)).setChecked(true);
        }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cb:
                String tag = (String)view.getTag();
                int position = Integer.getInteger(tag);
                checked_now = position;
                Log.d("SerachAdapter", "position:" + position);
                ((CheckBox)view).setChecked(true);
                break;
        }
    }
}
