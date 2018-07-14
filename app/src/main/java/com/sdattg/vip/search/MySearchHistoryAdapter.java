package com.sdattg.vip.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdattg.vip.R;
import com.sdattg.vip.util.MyDateFormat;

import java.io.File;
import java.util.List;

public class MySearchHistoryAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Bitmap myicon_folder;
    //private Bitmap mIcon2;
    //private Bitmap mIcon3;
    //private Bitmap mIcon4;
    //private List<String> items;
    private List<String> paths;
    private Context context;

    public MySearchHistoryAdapter(Context context, List<String> paths) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.paths = paths;
        //myicon_folder = BitmapFactory.decodeResource(context.getResources(), R.mipmap.myicon_folder);
        //mIcon2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_back02);
        //mIcon3 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.myicon_folder);
        //mIcon4 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.myicon_file);
    }
    public int getCount() {
        return paths.size();
    }
    public Object getItem(int position) {
        return paths.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        MySearchHistoryAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_shistory, null);
            holder = new MySearchHistoryAdapter.ViewHolder();
            holder.tv_item_historyname = (TextView) convertView.findViewById(R.id.tv_item_historyname);
            holder.iv_item_lajitong = (ImageView) convertView.findViewById(R.id.iv_item_lajitong);
            convertView.setTag(holder);
        } else {
            holder = (MySearchHistoryAdapter.ViewHolder) convertView.getTag();
        }
        holder.tv_item_historyname.setText(paths.get(position).toString());
        return convertView;
    }
    private class ViewHolder {
        TextView tv_item_historyname;
        ImageView iv_item_lajitong;
    }

}
