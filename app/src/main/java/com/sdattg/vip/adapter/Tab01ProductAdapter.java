package com.sdattg.vip.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdattg.vip.R;
import com.sdattg.vip.tool.ZipTool;

import java.io.File;
import java.util.List;


/**
 * Created by yinqm on 2018/4/17.
 */

public class Tab01ProductAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public Tab01ProductAdapter(@LayoutRes int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {
        if (helper.getLayoutPosition() == 0) {
            helper.getView(R.id.ll_top).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.ll_top).setVisibility(View.GONE);
        }

        //((TextView)helper.getView(R.id.booklist_author)).setText("test" + helper.getLayoutPosition());

    }


}
