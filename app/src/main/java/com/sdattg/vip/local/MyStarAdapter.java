package com.sdattg.vip.local;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdattg.vip.MainActivity;
import com.sdattg.vip.R;
import com.sdattg.vip.util.MyDateFormat;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyStarAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Bitmap myicon_folder;
    //private Bitmap mIcon2;
    //private Bitmap mIcon3;
    //private Bitmap mIcon4;
    //private List<String> items;
    private List<String> paths;
    private Context context;

    public MyStarAdapter(Context context, List<String> paths) {
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
        MyStarAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_stared, null);
            holder = new MyStarAdapter.ViewHolder();
            holder.icon_left = (ImageView) convertView.findViewById(R.id.icon_left);
            holder.bookname = (TextView) convertView.findViewById(R.id.bookname);
            holder.booktime = (TextView) convertView.findViewById(R.id.booktime);
            convertView.setTag(holder);
        } else {
            holder = (MyStarAdapter.ViewHolder) convertView.getTag();
        }

        File f = new File(paths.get(position).toString());
        if(f.exists()){
            holder.icon_left.setBackgroundResource(R.mipmap.myicon_folder);
            holder.bookname.setText(f.getName());
            holder.booktime.setText(MyDateFormat.formatter.format(f.lastModified()));
            return convertView;
        }else{
            //((MainActivity)context).updateFragmentBenDi();
        }
        //holder.icon_left.setImageBitmap(myicon_folder);
        return convertView;
    }
    private class ViewHolder {
        ImageView icon_left;
        ImageView dot;
        TextView bookname;
        TextView booktime;
    }

}
