package com.sdattg.vip.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdattg.vip.R;

import java.util.List;

public class MySearchResultContentAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Bitmap myicon_folder;
    //private Bitmap mIcon2;
    //private Bitmap mIcon3;
    //private Bitmap mIcon4;
    //private List<String> items;
    private List<String> paths1;
    private List<String> paths2;
    private Context context;

    public MySearchResultContentAdapter(Context context, List<String> paths1, List<String> paths2) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.paths1 = paths1;
        this.paths2 = paths2;
        //myicon_folder = BitmapFactory.decodeResource(context.getResources(), R.mipmap.myicon_folder);
        //mIcon2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_back02);
        //mIcon3 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.myicon_folder);
        //mIcon4 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.myicon_file);
    }
    public int getCount() {
        return paths1.size();
    }
    public Object getItem(int position) {
        return paths1.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        MySearchResultContentAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_sresult_content, null);
            holder = new MySearchResultContentAdapter.ViewHolder();
            holder.tv_item_sresult_content_1 = (TextView) convertView.findViewById(R.id.tv_item_sresult_content_1);
            holder.tv_item_sresult_content_2 = (TextView) convertView.findViewById(R.id.tv_item_sresult_content_2);
            convertView.setTag(holder);
        } else {
            holder = (MySearchResultContentAdapter.ViewHolder) convertView.getTag();
        }
        holder.tv_item_sresult_content_1.setText(paths1.get(position).toString());
        holder.tv_item_sresult_content_2.setText(paths2.get(position).toString());
        return convertView;
    }
    private class ViewHolder {
        TextView tv_item_sresult_content_1;
        TextView tv_item_sresult_content_2;
    }

}
