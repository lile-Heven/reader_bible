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
import com.sdattg.vip.fragment.Main02FragmentHuaiZhu;

import java.util.List;


/**
 * Created by yinqm on 2018/4/17.
 */

public class Tab01ProductAdapterHZ extends BaseQuickAdapter<String, BaseViewHolder> implements View.OnClickListener{
    private Context context;


    public Tab01ProductAdapterHZ(@LayoutRes int layoutResId, @Nullable List<String> data, Context context) {
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
        helper.getView(R.id.ll_top).setVisibility(View.GONE);

        //((TextView)helper.getView(R.id.booklist_author)).setText("test" + helper.getLayoutPosition());
        helper.getView(R.id.tv_more_introduce).setOnClickListener(this);
        if(getData().size() >= Main02FragmentHuaiZhu.book_count_3){
            if (helper.getLayoutPosition() == 0) {
                helper.getView(R.id.ll_top).setVisibility(View.VISIBLE);
                ((TextView)(helper.getView(R.id.tv_first_title))).setText("必备");

                ((TextView)(helper.getView(R.id.tv_first_title_2))).setText("共(" + Main02FragmentHuaiZhu.book_count_bibei + ")本");
            }  else if(helper.getLayoutPosition() == Main02FragmentHuaiZhu.book_count_bibei ){
                helper.getView(R.id.ll_top).setVisibility(View.VISIBLE);
                ((TextView)(helper.getView(R.id.tv_first_title))).setText("健康");
                ((TextView)(helper.getView(R.id.tv_first_title_2))).setText("共(" + Main02FragmentHuaiZhu.book_count_jiankang + ")本");
            }  else if(helper.getLayoutPosition() == Main02FragmentHuaiZhu.book_count_bibei + Main02FragmentHuaiZhu.book_count_jiankang ){
                helper.getView(R.id.ll_top).setVisibility(View.VISIBLE);
                ((TextView)(helper.getView(R.id.tv_first_title))).setText("教育");
                ((TextView)(helper.getView(R.id.tv_first_title_2))).setText("共(" + Main02FragmentHuaiZhu.book_count_jiaoyu + ")本");
            }  else if(helper.getLayoutPosition() == Main02FragmentHuaiZhu.book_count_bibei + Main02FragmentHuaiZhu.book_count_jiankang + Main02FragmentHuaiZhu.book_count_jiaoyu){
                helper.getView(R.id.ll_top).setVisibility(View.VISIBLE);
                ((TextView)(helper.getView(R.id.tv_first_title))).setText("勉言");
                ((TextView)(helper.getView(R.id.tv_first_title_2))).setText("共(" + Main02FragmentHuaiZhu.book_count_mianyan + ")本");
            }  else if(helper.getLayoutPosition() == Main02FragmentHuaiZhu.book_count_bibei + Main02FragmentHuaiZhu.book_count_jiankang + Main02FragmentHuaiZhu.book_count_jiaoyu + Main02FragmentHuaiZhu.book_count_mianyan){
                helper.getView(R.id.ll_top).setVisibility(View.VISIBLE);
                ((TextView)(helper.getView(R.id.tv_first_title))).setText("稿件");
                ((TextView)(helper.getView(R.id.tv_first_title_2))).setText("共(" + Main02FragmentHuaiZhu.book_count_gaojian + ")本");
            }  else if(helper.getLayoutPosition() == Main02FragmentHuaiZhu.book_count_bibei + Main02FragmentHuaiZhu.book_count_jiankang + Main02FragmentHuaiZhu.book_count_jiaoyu + Main02FragmentHuaiZhu.book_count_mianyan
                    + Main02FragmentHuaiZhu.book_count_gaojian){
                helper.getView(R.id.ll_top).setVisibility(View.VISIBLE);
                ((TextView)(helper.getView(R.id.tv_first_title))).setText("证言");
                ((TextView)(helper.getView(R.id.tv_first_title_2))).setText("共(" + Main02FragmentHuaiZhu.book_count_zhengyan + ")本");
            }  else if(helper.getLayoutPosition() == Main02FragmentHuaiZhu.book_count_bibei + Main02FragmentHuaiZhu.book_count_jiankang + Main02FragmentHuaiZhu.book_count_jiaoyu + Main02FragmentHuaiZhu.book_count_mianyan
                    + Main02FragmentHuaiZhu.book_count_gaojian + Main02FragmentHuaiZhu.book_count_zhengyan){
                helper.getView(R.id.ll_top).setVisibility(View.VISIBLE);
                ((TextView)(helper.getView(R.id.tv_first_title))).setText("布道");
                ((TextView)(helper.getView(R.id.tv_first_title_2))).setText("共(" + Main02FragmentHuaiZhu.book_count_budao + ")本");
            }  else if(helper.getLayoutPosition() == Main02FragmentHuaiZhu.book_count_bibei + Main02FragmentHuaiZhu.book_count_jiankang + Main02FragmentHuaiZhu.book_count_jiaoyu + Main02FragmentHuaiZhu.book_count_mianyan
                    + Main02FragmentHuaiZhu.book_count_gaojian + Main02FragmentHuaiZhu.book_count_zhengyan + Main02FragmentHuaiZhu.book_count_budao){
                helper.getView(R.id.ll_top).setVisibility(View.VISIBLE);
                ((TextView)(helper.getView(R.id.tv_first_title))).setText("灵修");
                ((TextView)(helper.getView(R.id.tv_first_title_2))).setText("共(" + Main02FragmentHuaiZhu.book_count_lingxiu + ")本");
            }
        }


        String[] strs = list_item.get(helper.getLayoutPosition()).split("#3#");
        ((TextView)(helper.getView(R.id.tv_book_title))).setText(strs[0]);
        ((TextView)(helper.getView(R.id.tv_book_author))).setText(strs[1]);
        ((TextView)(helper.getView(R.id.tv_book_jieshao))).setText("\u3000\u3000" + strs[2].substring(strs[2].indexOf("。", 1) + 2).trim());
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
