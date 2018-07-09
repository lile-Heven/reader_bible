package com.sdattg.vip.read;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 自定义菜单
 *
 */
public class MyMenu extends PopupWindow {

	private Context context;
	private GridView gvTitle;                 // 菜单项
	private LinearLayout mLayout;             // PopupWindow的布局
	private MyMenuAdapter myMenuAdapter;      // 菜单适配器
	private LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	int height;
	final int line = 2;


	/**
	 * MyMenu 自定义菜单
	 * @param context 当前上下文
	 * @param titleClick 菜单子项监听器
	 * @param myMenuBackgroundColor 菜单背景颜色
	 */
	public MyMenu(Context context, OnItemClickListener titleClick,
                  int myMenuBackgroundColor, int myMainMenuAnim, String[] titles, int height){
		super(context);

		this.context = context;
		this.height = height;

		// 配置适配器
		myMenuAdapter = (MyMenuAdapter) createMenuAdapter(titles);

		mLayout = new LinearLayout(context);
		mLayout.setOrientation(LinearLayout.VERTICAL);

		// 设置gridView
		gvTitle = new GridView(context);
		gvTitle.setLayoutParams(params);
		gvTitle.setNumColumns(myMenuAdapter.getCount()/2);
		gvTitle.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gvTitle.setVerticalSpacing(1);
		gvTitle.setHorizontalSpacing(1);
		gvTitle.setGravity(Gravity.CENTER);
		gvTitle.setOnItemClickListener(titleClick);
		gvTitle.setSelector(new ColorDrawable(Color.TRANSPARENT));//
		gvTitle.setAdapter(myMenuAdapter);

		// 添加到布局
		this.mLayout.addView(gvTitle);

		//设置popupWindow
		setContentView(this.mLayout);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new ColorDrawable(Color.argb(200, 0, 0, 0)));//myMenuBackgroundColor
		setAnimationStyle(myMainMenuAnim);
		setFocusable(true);

		mLayout.setFocusableInTouchMode(true);
		mLayout.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if(keyCode == KeyEvent.KEYCODE_MENU && isShowing()){
					myMenuAdapter.setFocus(-1);
					dismiss();
					return true;
				}
				if(keyCode == KeyEvent.KEYCODE_BACK && isShowing()) {
					myMenuAdapter.setFocus(-1);
					dismiss();
					return true;
				}
				return false;
			}
		});
	}

	public void allOfGone() {

	}

	public void setMenuLayout(GridView gv) {
		this.mLayout.addView(gv);
	}

	/**
	 * 设置选择项
	 * @param index 选项Position
	 */
	public void setTitleSelect(int index)
	{
		gvTitle.setSelection(index);
		this.myMenuAdapter.setFocus(index);
	}


	/**
	 * 创建菜单适配器
	 *
	 * @return BaseAdapter
	 */
	public BaseAdapter createMenuAdapter(String[] titles){

		return new MyMenuAdapter(context,
				titles, //读xml
				16,
				Color.GRAY, Color.GREEN,
				Color.TRANSPARENT, Color.TRANSPARENT);
	}

	/**
	 * 自定义适配器	 *
	 */
	class MyMenuAdapter extends BaseAdapter {

		private Context context;
		private TextView[] tvTitles;
		private int fontUnSelColor;
		private int fontSelColor;
		private int bgUnSelColor;
		private int bgSelColor;

		/**
		 * 配置 title
		 * @param context 当前上下文
		 * @param titles 标题
		 * @param fontSize 字体大小
		 * @param fontUnSelColor 未选中时字体颜色
		 * @param fontSelColor 选中时字体颜色
		 * @param bgUnSelColor 未选中时背景颜色
		 * @param bgSelColor 选中时背景颜色
		 */
		public MyMenuAdapter(Context context, String[] titles,
                             int fontSize, int fontUnSelColor, int fontSelColor, int bgUnSelColor, int bgSelColor){
			this.context = context;
			this.fontUnSelColor = fontUnSelColor;
			this.fontSelColor = fontSelColor;
			this.bgUnSelColor = bgUnSelColor;
			this.bgSelColor = bgSelColor;

			// titles放进menu中的各个TextView中
			tvTitles = new TextView[titles.length];
			for(int i=0; i<titles.length; i++){
				tvTitles[i] = new TextView(this.context) {
					@Override
					protected void onDraw(Canvas canvas) {
						super.onDraw(canvas);

						Paint paint = new Paint();
						paint.setColor(Color.GRAY);
						canvas.drawLine(1, 1, this.getWidth(), 1, paint);
						canvas.drawLine(1, 1, 1, this.getHeight(), paint);
						canvas.drawLine(this.getWidth(), 1, this.getWidth(), this.getHeight(), paint);
						canvas.drawLine(1, this.getHeight(), this.getWidth(), this.getHeight(), paint);
					}

				};

				tvTitles[i].setText(titles[i]);
				tvTitles[i].setTextSize(fontSize);
				tvTitles[i].setTextColor(fontUnSelColor);
				tvTitles[i].setGravity(Gravity.CENTER);
				tvTitles[i].setPadding(10, 10, 10, 10);//10,30,10,30
//				tvTitles[i].setHeight(height/(6*line));

			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tvTitles.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return tvTitles[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return tvTitles[position].getId();
		}

		/**
		 * 设置对焦
		 *
		 * @param index 菜单子项下标
		 *
		 */
		public void setFocus(int index){
			for(int i=0; i<tvTitles.length; i++){

				if(i != index){
					this.tvTitles[i].setBackgroundColor(this.bgUnSelColor);
					this.tvTitles[i].setTextColor(this.fontUnSelColor);
				}else{
					this.tvTitles[i].setBackgroundColor(this.bgSelColor);
					this.tvTitles[i].setTextColor(this.fontSelColor);
				}
			}
		}


		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = null;
			if(convertView == null){
				v = tvTitles[position];
			}else{
				v = convertView;
			}
			return v;
//			LinearLayout linear = new LinearLayout(this.context) {
//
//				@Override
//				protected void onDraw(Canvas canvas) {
//					// TODO Auto-generated method stub
//					super.onDraw(canvas);
//					Paint paint = new Paint();
//					paint.setColor(Color.GRAY);
//					canvas.drawLine(1, 1, this.getWidth(), 1, paint);
//					canvas.drawLine(1, 1, 1, this.getHeight(), paint);
//					canvas.drawLine(this.getWidth(), 1, this.getWidth(), this.getHeight(), paint);
//					canvas.drawLine(1, this.getHeight(), this.getWidth(), this.getHeight(), paint);
//				}
//				
//			};
//			linear.setOrientation(LinearLayout.VERTICAL);
//			if(convertView == null) {
//				linear.addView(tvTitles[position]);
//				linear.addView(tvTitles[position]);
//			} else {
//				linear = (LinearLayout)convertView;
//			}
//			return linear;
		}
	}
}