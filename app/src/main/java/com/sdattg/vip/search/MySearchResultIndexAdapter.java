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
import com.sdattg.vip.bean.NewBookBean;
import com.sdattg.vip.util.FileUtil;

import java.util.List;

public class MySearchResultIndexAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Bitmap myicon_folder;
    //private Bitmap mIcon2;
    //private Bitmap mIcon3;
    //private Bitmap mIcon4;
    //private List<String> items;
    private List<NewBookBean> paths;
    private Context context;

    public MySearchResultIndexAdapter(Context context, List<NewBookBean> paths) {
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
        MySearchResultIndexAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_sresult_index, null);
            holder = new MySearchResultIndexAdapter.ViewHolder();
            holder.tv_item_sresult_index_1 = (TextView) convertView.findViewById(R.id.tv_item_sresult_index_1);
            holder.tv_item_sresult_index_2 = (TextView) convertView.findViewById(R.id.tv_item_sresult_index_2);
            convertView.setTag(holder);
        } else {
            holder = (MySearchResultIndexAdapter.ViewHolder) convertView.getTag();
        }

        holder.tv_item_sresult_index_1.setText(FileUtil.substringFrom_(FileUtil.replaceBy_(FileUtil.getNameFromPath((paths.get(position)).parentPath))));
        holder.tv_item_sresult_index_2.setText(FileUtil.substringFrom_((paths.get(position)).chapter));
        return convertView;
    }
    private class ViewHolder {
        TextView tv_item_sresult_index_1;
        TextView tv_item_sresult_index_2;
    }

}
