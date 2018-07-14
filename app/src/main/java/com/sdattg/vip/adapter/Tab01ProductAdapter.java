package com.sdattg.vip.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdattg.vip.R;
import com.sdattg.vip.fragment.Main02FragmentBible;

import java.util.List;


/**
 * Created by yinqm on 2018/4/17.
 */

public class Tab01ProductAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements View.OnClickListener{
    private Context context;

    public Tab01ProductAdapter(@LayoutRes int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.getView(R.id.tv_more_introduce).setTag(holder.getLayoutPosition() + "");
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {

        List<String> list_item = getData();


        //((TextView)helper.getView(R.id.booklist_author)).setText("test" + helper.getLayoutPosition());
        helper.getView(R.id.tv_more_introduce).setOnClickListener(this);
        if (helper.getLayoutPosition() == 0 && getData().size() > Main02FragmentBible.book_count_jiuyue) {
            helper.getView(R.id.ll_top).setVisibility(View.VISIBLE);
            ((TextView)(helper.getView(R.id.tv_first_title))).setText("旧约");

            ((TextView)(helper.getView(R.id.tv_first_title_2))).setText("共(" + Main02FragmentBible.book_count_jiuyue + ")本");
        }  else if(helper.getLayoutPosition() == Main02FragmentBible.book_count_jiuyue && getData().size() > Main02FragmentBible.book_count_jiuyue){
            helper.getView(R.id.ll_top).setVisibility(View.VISIBLE);
            ((TextView)(helper.getView(R.id.tv_first_title))).setText("新约");
            ((TextView)(helper.getView(R.id.tv_first_title_2))).setText("共(" + Main02FragmentBible.book_count_xinyue + ")本");
        } else {
            helper.getView(R.id.ll_top).setVisibility(View.GONE);
        }
        String[] strs = list_item.get(helper.getLayoutPosition()).split("#3#");
        ((TextView)(helper.getView(R.id.tv_book_title))).setText(strs[0]);
        ((TextView)(helper.getView(R.id.tv_book_author))).setText(strs[1]);
        ((TextView)(helper.getView(R.id.tv_book_jieshao))).setText("\u3000\u3000" + strs[2].substring(strs[2].indexOf("。", 2) + 2).trim());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_more_introduce:
                String[] strs = getData().get(Integer.valueOf(view.getTag().toString())).split("#3#");
                //Toast.makeText(context, "test jieshao:" + strs[2] , Toast.LENGTH_SHORT).show();;
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setMessage(strs[2])
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("进入阅读", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /*Intent intent = new Intent(context, MyReadActivity.class);
                                context.startActivity(intent);*/
                            }
                        })
                        .create();
                dialog.show();
                //dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
                break;
        }
    }

    /*class Listhoder extends RecyclerView.ViewHolder {
        TextView textview;

        public Listhoder(View itemView) {
            super(itemView);
            textview = (TextView) itemView.findViewById(R.id.textView);
        }

        public void setData(int position) {
            textview.setText(a.get(position));
        }

    }*/
}
