package com.sdattg.vip.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdattg.vip.R;
import com.sdattg.vip.bean.NewChapterBean;
import com.sdattg.vip.bean.NewShowChapterBean;
import com.sdattg.vip.util.FileUtil;

import java.util.List;

public class MySearchResultContentAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Bitmap myicon_folder;
    //private Bitmap mIcon2;
    //private Bitmap mIcon3;
    //private Bitmap mIcon4;
    //private List<String> items;
    List<NewShowChapterBean> all_chapters;
    private Context context;

    public MySearchResultContentAdapter(Context context, List<NewShowChapterBean> all_chapters) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.all_chapters = all_chapters;
        //myicon_folder = BitmapFactory.decodeResource(context.getResources(), R.mipmap.myicon_folder);
        //mIcon2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_back02);
        //mIcon3 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.myicon_folder);
        //mIcon4 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.myicon_file);
    }
    public int getCount() {
        return all_chapters.size();
    }
    public Object getItem(int position) {
        return all_chapters.get(position);
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
        holder.tv_item_sresult_content_1.setText(FileUtil.substringFrom_(all_chapters.get(position).chapterName));
        holder.tv_item_sresult_content_2.setText(all_chapters.get(position).paragraphContent);
        return convertView;
    }
    private class ViewHolder {
        TextView tv_item_sresult_content_1;
        TextView tv_item_sresult_content_2;
    }

}
