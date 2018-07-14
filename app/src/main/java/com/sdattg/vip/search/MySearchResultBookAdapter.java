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

import java.util.List;

public class MySearchResultBookAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Bitmap myicon_folder;
    //private Bitmap mIcon2;
    //private Bitmap mIcon3;
    //private Bitmap mIcon4;
    //private List<String> items;
    private List<String> paths;
    private Context context;

    public MySearchResultBookAdapter(Context context, List<String> paths) {
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
        MySearchResultBookAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_sresult_book, null);
            holder = new MySearchResultBookAdapter.ViewHolder();
            holder.tv_item_sresult_book_name = (TextView) convertView.findViewById(R.id.tv_item_sresult_book_name);
            convertView.setTag(holder);
        } else {
            holder = (MySearchResultBookAdapter.ViewHolder) convertView.getTag();
        }
        holder.tv_item_sresult_book_name.setText(paths.get(position).toString());
        return convertView;
    }
    private class ViewHolder {
        TextView tv_item_sresult_book_name;
    }

}
