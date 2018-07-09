package com.sdattg.vip.read;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.sdattg.vip.R;

import java.util.List;
import java.util.Map;

public class BookChapter extends Activity {

	protected static final int RESULT = 0;
	ListView lv;
	TextView tv;
	View view;

	BookInfoDao mBookInfo = new BookInfoDao(BookChapter.this);
	Book mBook;
	
	// 拥有所有数据的Adapter
	SimpleAdapter adapter;
	List<Map<String, Object>> data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

//		getWindow().setTitle(mBook.getName() + " - 目录");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		mBook = mBookInfo.getABook(this.getIntent().getExtras().getInt("bookId"));

		LayoutInflater inflater = getLayoutInflater().from(this);
		view = inflater.inflate(R.layout.listview, null);
		lv = (ListView) view.findViewById(R.id.listview);
		tv = (TextView) view.findViewById(R.id.listView_text);
		setListView();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Log.i("onItemClick", "ChapterId is " +data.get(position).get("rate").toString());
//				int begin = Integer.getInteger(data.get(position).get("rate").toString());
				
				mBookInfo.alterReadRate(mBook.getId(), Integer.parseInt(data.get(position).get("rate").toString()));
				
				BookChapter.this.finish();
			}
		});
	}
	
	private void getData() {
		data = mBookInfo.getCatalogue(mBook.getId());
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
		
		adapter = new SimpleAdapter(BookChapter.this, data, R.layout.list_item, new String[] { "chaptersName" }, new int[] { R.id.textView1});
		lv.setAdapter(adapter);
		setContentView(view);
	}	
}
