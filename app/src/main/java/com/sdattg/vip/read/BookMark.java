package com.sdattg.vip.read;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.sdattg.vip.R;

import java.util.List;
import java.util.Map;

public class BookMark extends Activity {

	protected static final int RESULT = 0;
	ListView lv;
	TextView tv;
	View view;

	BookInfoDao mBookInfo;
	Book mBook;
	
	// 拥有所有数据的Adapter
	SimpleAdapter adapter;
	List<Map<String, Object>> data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		mBookInfo = new BookInfoDao(BookMark.this);
		mBook = mBookInfo.getABook(this.getIntent().getExtras().getInt("bookId"));
		
		getData();
		LayoutInflater inflater = getLayoutInflater().from(this);
		view = inflater.inflate(R.layout.listview, null);
		lv = (ListView) view.findViewById(R.id.listview);
		tv = (TextView) view.findViewById(R.id.listView_text);
		setListView();
		
		adapter = new SimpleAdapter(BookMark.this, data, R.layout.list_item, new String[] { "labelName",
				"progress" }, new int[] { R.id.textView1, R.id.textView2, });
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
//				int begin = Integer.getInteger(data.get(position).get("rate").toString());
				Log.i("onItemClick", "rate is " +data.get(position).get("rate").toString()+"   "+ Integer.toString(mBook.getId()));
								
				mBookInfo.alterReadRate(mBook.getId(), Integer.parseInt(data.get(position).get("rate").toString()));
				BookMark.this.finish();
			}
		});
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view1, final int position, long id) {
//				HashMap<String, Object> mBookLabel = (HashMap<String, Object>)parent.getItemAtPosition(position);
//				final int blId = Integer.getInteger(((HashMap<String, Object>)(parent.getItemAtPosition(position))).get("id").toString());
				Log.i("onItemLongClick", "blid is " + Integer.parseInt(data.get(position).get("id").toString()));
				AlertDialog.Builder ad = new AlertDialog.Builder(BookMark.this);
				ad.setTitle("删除该书签").setMessage("确定删除?");
				ad.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						mBookInfo.deleteBookLabel(Integer.parseInt(data.get(position).get("id").toString()));

						adapter.notifyDataSetChanged();
						setListView();

					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();

				return false;
			}
		});
		setContentView(view);
	}
	
	private void getData() {
		data = mBookInfo.getAllBookLabels(mBook.getId());
	}
	
	private void setListView() {
		getData();
		if (data.isEmpty()) {
			tv.setVisibility(View.VISIBLE);
			lv.setVisibility(View.GONE);
			setContentView(view);
			return;
		} 

		tv.setVisibility(View.GONE);
		lv.setVisibility(View.VISIBLE);
		
		adapter = new SimpleAdapter(BookMark.this, data, R.layout.list_item, new String[] { "labelName",
				"progress" }, new int[] { R.id.textView1, R.id.textView2, });
		lv.setAdapter(adapter);

		setContentView(view);
	}


}
