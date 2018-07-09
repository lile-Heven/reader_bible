package com.sdattg.vip.read;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;


import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.sdattg.vip.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Vector;
//import static com.horizon.ui.MarkManager.single;

public class MainReader extends Activity {

	private String TAG = this.getClass().getSimpleName();
	/** Called when the activity is first created. */
	private PageWidget mPageWidget;
	Bitmap mCurPageBitmap, mNextPageBitmap;
	Canvas mCurPageCanvas, mNextPageCanvas;
	BookPageFactory pagefactory;
	boolean is_Horizontal;

	Book mBook;
	BookInfoDao mBookInfo;
	ConfigDao mConfig;
	//菜单
	private MyMenu myMenu;
	private int setTitle;
//	private int tmpe = -1;
//	private boolean isSelect = true;

	private static final int REQUEST = 0;
	private static final int RESULT = 0;

	SeekBar sb_jump;
	TextView tv_jump;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// 语音配置对象初始化(如果只使用 语音识别 或 语音合成 时都得先初始化这个)
		SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5b3ce7b3"); //core codes $$$$$$

		mPageWidget = new PageWidget(this);
		setContentView(mPageWidget);

		mConfig = new ConfigDao(MainReader.this);
		mBookInfo= new BookInfoDao(MainReader.this);

		int id = this.getIntent().getExtras().getInt("id");
		mBook = mBookInfo.getABook(id);

		DisplayMetrics mDM = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDM);
		int W = mDM.widthPixels;
		int H = mDM.heightPixels;

		Log.i("", "W is " + Integer.toString(W));
		Log.i("", "H is " + Integer.toString(H));

		mPageWidget.setScreen(W, H);

		// 若是要修改分辨率的话， 请自己手动该 480 800 两个值。
		mCurPageBitmap = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888);
		mNextPageBitmap = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888);
		//
		// 两画布
		mCurPageCanvas = new Canvas(mCurPageBitmap);
		mNextPageCanvas = new Canvas(mNextPageBitmap);
		pagefactory = new BookPageFactory(MainReader.this, W, H);
		// 设置一张背景图片
		//pagefactory.setBgBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.bg));
		if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			is_Horizontal = true;
		} else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			is_Horizontal = false;
		}
//		pagefactory.setBgBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.bg), W, H, is_Horizontal);

//		pagefactory.setBgBitmap(W, H, is_Horizontal);

		try {
			pagefactory.openbook(mBook);// 打开文件 获取到一个缓存
//			pagefactory.openbook(bookfillPath);
			pagefactory.onDraw(mCurPageCanvas);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Toast.makeText(this, "电子书不存在,请将《test.txt》放在SD卡根目录下", Toast.LENGTH_SHORT).show();
		}

		mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);

		pagefactory.onDraw(mCurPageCanvas);
		speak(pagefactory.m_lines);

		mPageWidget.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				// TODO Auto-generated method stub

				boolean ret = false;
				if (v == mPageWidget) {
					//if (e.getAction() == MotionEvent.ACTION_MOVE) {
					/*if (e.getAction() == MotionEvent.ACTION_UP) {
						Log.d(TAG, "into e.getAction() == MotionEvent.ACTION_DOWN");
						mPageWidget.abortAnimation();
						Log.d(TAG, "into e.getAction() == MotionEvent.ACTION_DOWN2");
						mPageWidget.calcCornerXY(e.getX(), e.getY());
						Log.d(TAG, "into e.getAction() == MotionEvent.ACTION_DOWN3");
						pagefactory.onDraw(mCurPageCanvas);
						Log.d(TAG, "into e.getAction() == MotionEvent.ACTION_DOWN4");
						;
						if (mPageWidget.wantPrePage()) {
							//意图是上一页
							// 右边点击的时候为false; 前一页
							Log.d(TAG, "into mPageWidget.DragToRight()");
							Log.d(TAG, "into e.getAction() == MotionEvent.ACTION_DOWN8");

							try {
								if(pagefactory.prePage()){

								}else{
									//返回数值为false时候代表当前是第一页
									return true;
								}
							} catch (IOException e1) {
								Log.d(TAG, "into e.getAction() == MotionEvent.ACTION_DOWN IOException e1");
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							Log.d(TAG, "into e.getAction() == MotionEvent.ACTION_DOWN888");
							pagefactory.onDraw(mNextPageCanvas);
						} else {
							//意图是下一页
							Log.d(TAG, "into mPageWidget.DragToLeft()");
							Log.d(TAG, "into e.getAction() == MotionEvent.ACTION_DOWN5");

							try {
								pagefactory.nextPage();
							} catch (IOException e2) {
								Log.d(TAG, "into e.getAction() == MotionEvent.ACTION_DOWN IOException e2");
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							if (pagefactory.islastPage())
								return false;
							pagefactory.onDraw(mNextPageCanvas);
						}
						mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
					}*/

					if (e.getAction() == MotionEvent.ACTION_DOWN) {

						mPageWidget.calcCornerXY(e.getX(), e.getY());
						pagefactory.isFirstPage();
						pagefactory.isLastPage();
						//Log.d(TAG, "into e.getAction() == MotionEvent.ACTION_DOWN0000");
						if(mPageWidget.mCornerX == 0 && pagefactory.get_m_isfirstPage()){
							Toast.makeText(MainReader.this, "已经是第一页", Toast.LENGTH_SHORT).show();
							return true;
						}else if(mPageWidget.mCornerX == mPageWidget.mWidth && pagefactory.get_m_islastPage()){
							Toast.makeText(MainReader.this, "已经是最后一页", Toast.LENGTH_SHORT).show();
							return true;
						}else{
							if(mPageWidget.mCornerX == 0 ){
								pagefactory.prePage();
								pagefactory.onDraw(mNextPageCanvas);
							}else if(mPageWidget.mCornerX == mPageWidget.mWidth){
								pagefactory.nextPage();
								pagefactory.onDraw(mNextPageCanvas);
							}
							mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
							ret = mPageWidget.doTouchEvent(e, pagefactory);
						}
					}else if(e.getAction() == MotionEvent.ACTION_MOVE){
						if(mPageWidget.mCornerX == 0 && pagefactory.get_m_isfirstPage()){
							return true;
						}else if(mPageWidget.mCornerX == mPageWidget.mWidth && pagefactory.get_m_islastPage()){
							return true;
						}else{
							ret = mPageWidget.doTouchEvent(e, pagefactory);
						}
					}else if(e.getAction() == MotionEvent.ACTION_UP){
						if(mPageWidget.mCornerX == 0 && pagefactory.get_m_isfirstPage()){
							return true;
						}else if(mPageWidget.mCornerX == mPageWidget.mWidth && pagefactory.get_m_islastPage()){
							return true;
						}else{
						    /*if(mPageWidget.mCornerX == 0){
                                pagefactory.prePage();
                                pagefactory.onDraw(mNextPageCanvas);
                            }else if(mPageWidget.mCornerX == mPageWidget.mWidth){
                                pagefactory.nextPage();
                                pagefactory.onDraw(mNextPageCanvas);
                            }
                            mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);*/
							ret = mPageWidget.doTouchEvent(e, pagefactory);
							pagefactory.onDraw(mCurPageCanvas);
							speak(pagefactory.m_lines);
						}
					}

					/*if (e.getAction() == MotionEvent.ACTION_DOWN) {
						mPageWidget.calcCornerXY(e.getX(), e.getY());
						pagefactory.isfirstPage();
						pagefactory.islastPage();
					}
					if(mPageWidget.mCornerX == 0 && pagefactory.isfirstPage()){
						if(e.getAction() == MotionEvent.ACTION_DOWN){
							Toast.makeText(MainReader.this, "已经是第一页", Toast.LENGTH_SHORT).show();
						}
						return false;
					}else if(mPageWidget.mCornerX == mPageWidget.mWidth && pagefactory.islastPage()){
						if(e.getAction() == MotionEvent.ACTION_DOWN){
							Toast.makeText(MainReader.this, "已经是最后一页", Toast.LENGTH_SHORT).show();
						}
						return false;
					}else{
						ret = mPageWidget.doTouchEvent(e, pagefactory);
					}*/
					return ret;
				}
				return false;
			}

		});



//        myMenu = new MyMenu(this,
//        		new TitleClickEvent(),
//        		Color.argb(255, 139, 106, 47),
//        		R.style.PopupAnimation, getResources().getStringArray(R.array.menu),H);
//        myMenu.update();
//        myMenu.setTitleSelect(-1);
//        mPageWidget.setOnKeyListener(new View.OnKeyListener() {
//
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//	//			if(keyCode == KeyEvent.KEYCODE_MENU && isShowing()){
////					myMenuAdapter.setFocus(-1);
////					dismiss();
////					return true;
////				}
//				if(keyCode == KeyEvent.KEYCODE_BACK && myMenu.isShowing()) {
//					myMenu.setTitleSelect(-1);
//					myMenu.dismiss();
//					return true;
//				}
//				return false;
//			}
//		});

	}

	private void speak(Vector<String> m_lines){
		/**
		 *  科大讯飞语音
		 */
		String speechContent = "";
		for (String one: m_lines) {
			speechContent += one;
			//speechContent += one +"\n";
		}
		beginToSpeech(speechContent);
	}

	// 菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "日/夜模式").setIcon(R.drawable.menu_day);
		menu.add(0, 1, 0, "调整字号").setIcon(R.drawable.add_size);
		menu.add(0, 2, 0, "跳转进度").setIcon(R.drawable.cut_size);
		menu.add(0, 3, 0, "查看目录").setIcon(R.drawable.chapters);
		menu.add(0, 4, 0, "查看书签").setIcon(R.drawable.bookmark);
		menu.add(0, 5, 0, "添加书签").setIcon(R.drawable.add_bookmark);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
			case 0:
				changeReadMode();
				break;
			case 1:
				changeTextSize();
				break;
			case 2:
				changeReadRate();
				break;
			case 3:
				Intent cIntent = new Intent(MainReader.this,BookChapter.class);
				cIntent.putExtra("bookId", mBook.getId());
				startActivityForResult(cIntent, REQUEST);
				break;
			case 4:
				Intent mIntent = new Intent(MainReader.this,BookMark.class);
				mIntent.putExtra("bookId", mBook.getId());
				startActivityForResult(mIntent, REQUEST);
				break;
			case 5:
				addTag();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void changeReadMode() {
		if(mConfig.getInNight()) {
			//yejian
			mConfig.setBackColour(0xff000000);
			mConfig.setTxtColour(0xff444644);
			mConfig.setInNight(false);
		} else {
			//rijian
			mConfig.setBackColour(0xfffed189);
			mConfig.setTxtColour(0xff000000);
			mConfig.setInNight(true);
		}
		pagefactory.setM_mbBufEnd(pagefactory.getM_mbBufBegin());

		/*try {
			pagefactory.nextPage();
			pagefactory.onDraw(mCurPageCanvas);
			pagefactory.onDraw(mNextPageCanvas);
			mPageWidget.invalidate();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		pagefactory.nextPage();
		pagefactory.onDraw(mCurPageCanvas);
		pagefactory.onDraw(mNextPageCanvas);
		mPageWidget.invalidate();
	}

	private void changeReadRate() {
		View view = getLayoutInflater().inflate(R.layout.reader_jump, null);
		int jump = (int) Float.parseFloat(pagefactory.getStrPercent().substring(0, pagefactory.getStrPercent().length()-1));
		sb_jump = (SeekBar)view.findViewById(R.id.sb_jump);
		sb_jump.setProgress(jump);
		tv_jump = (TextView)view.findViewById(R.id.tv_jump);
		tv_jump.setText(Float.toString(sb_jump.getProgress()/100f));
		new AlertDialog.Builder(MainReader.this)
				.setTitle("进度跳转")
				.setView(view).setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				int mjump = (int) ((sb_jump.getProgress()/100f)*mBook.getWordsNum());
				Log.d("进度跳转", Integer.toString(mjump));

				pagefactory.setM_mbBufBegin(mjump);
				pagefactory.setM_mbBufEnd(pagefactory.getM_mbBufBegin());
				try {
					pagefactory.clearM_lines();
					pagefactory.onDraw(mCurPageCanvas);
					pagefactory.onDraw(mNextPageCanvas);
					mPageWidget.invalidate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).setNegativeButton("取消", null).show();
		sb_jump.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				tv_jump.setText(Float.toString(sb_jump.getProgress()/100f));
			}
		});
	}

	private void changeTextSize() {
		AlertDialog.Builder ad = new AlertDialog.Builder(MainReader.this);
		ad.setTitle("修改字体大小").setMessage("请选择操作:");
		ad.setPositiveButton("放大", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				int size = pagefactory.getM_fontSize();
				int newsize = size + 2;
				mConfig.setTxtSize(newsize);
				pagefactory.setM_mbBufEnd(pagefactory.getM_mbBufBegin());
				try {
					pagefactory.clearM_lines();
					pagefactory.onDraw(mCurPageCanvas);
					pagefactory.onDraw(mNextPageCanvas);
					mPageWidget.invalidate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).setNegativeButton("缩小", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				int size1 = pagefactory.getM_fontSize();
				int newsize1 = size1 - 2;
				mConfig.setTxtSize(newsize1);

				pagefactory.setM_mbBufEnd(pagefactory.getM_mbBufBegin());
				try {
					pagefactory.clearM_lines();
					pagefactory.onDraw(mCurPageCanvas);
					pagefactory.onDraw(mNextPageCanvas);
					mPageWidget.invalidate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).show();
	}

	//添加书签
	private void addTag() {
		String firstLine = "";
		firstLine += ((pagefactory.getM_lines()).elementAt(0)).toString()+((pagefactory.getM_lines()).elementAt(1)).toString();
		String percent = pagefactory.getStrPercent();
		mBookInfo.addBookLabel(mBook.getId(), firstLine, pagefactory.getM_mbBufBegin(), percent);
		Log.i("addTag", percent);
//		Toast.makeText(MainReader.this, mBookInfo.getAllBookLabels(mBook.getId()).get(0).get("progress").toString(), Toast.LENGTH_SHORT).show();
		Toast.makeText(MainReader.this, "添加书签成功", Toast.LENGTH_SHORT).show();
	}


	// 改变横竖屏时
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

		Log.i("Test", "this is onConfigurationChanged");

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 横屏时
			Log.i("Test", "this is ORIENTATION_LANDSCAPE");
			is_Horizontal = true;
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			// 竖屏时
			Log.i("Test", "this is ORIENTATION_PORTRAIT");
			is_Horizontal = false;
		}

		// 检测实体键盘的状态：推出或者合上
		if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
			// 实体键盘处于推出状态，在此处添加额外的处理代码
			Log.i("Test", "this is HARDKEYBOARDHIDDEN_NO");
		} else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
			// 实体键盘处于合上状态，在此处添加额外的处理代码
			Log.i("Test", "this is HARDKEYBOARDHIDDEN_YES");
		}

	}



	//菜单

	//MainMenu监听器
	class TitleClickEvent implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long arg3) {
			setTitle=arg2;
			myMenu.setTitleSelect(setTitle);
			switch (arg2) {
				case 0:
					if(mConfig.getInNight()) {
						//yejian
						mConfig.setBackColour(0xff000000);
						mConfig.setTxtColour(0xff444644);
						mConfig.setInNight(false);
					} else {
						//rijian
						mConfig.setBackColour(0xfffed189);
						mConfig.setTxtColour(0xff000000);
						mConfig.setInNight(true);
					}
					pagefactory.setM_mbBufEnd(pagefactory.getM_mbBufBegin());

    				/*try {
    					pagefactory.nextPage();
    					pagefactory.onDraw(mCurPageCanvas);
    					pagefactory.onDraw(mNextPageCanvas);
    					mPageWidget.invalidate();
    				} catch (IOException e) {
    					e.printStackTrace();
    				}*/

					pagefactory.nextPage();
					pagefactory.onDraw(mCurPageCanvas);
					pagefactory.onDraw(mNextPageCanvas);
					mPageWidget.invalidate();

					break;
				case 1:
					int size = pagefactory.getM_fontSize();
					int newsize = size + 2;
					mConfig.setTxtSize(newsize);
					pagefactory.setM_mbBufEnd(pagefactory.getM_mbBufBegin());
					try {
						pagefactory.clearM_lines();
						pagefactory.onDraw(mCurPageCanvas);
						pagefactory.onDraw(mNextPageCanvas);
						mPageWidget.invalidate();
					} catch (Exception e) {
						e.printStackTrace();
					}

					break;
				case 2:
					int size1 = pagefactory.getM_fontSize();
					int newsize1 = size1 - 2;
					mConfig.setTxtSize(newsize1);

					pagefactory.setM_mbBufEnd(pagefactory.getM_mbBufBegin());
					try {
						pagefactory.clearM_lines();
						pagefactory.onDraw(mCurPageCanvas);
						pagefactory.onDraw(mNextPageCanvas);
						mPageWidget.invalidate();
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case 3:
//    				myMenu.dismiss();
//    				myMenu.setTitleSelect(-1);
					Intent cIntent = new Intent(MainReader.this,BookChapter.class);
					cIntent.putExtra("bookId", mBook.getId());
					startActivityForResult(cIntent, REQUEST);
					break;
				case 4:
//    				myMenu.dismiss();
//    				myMenu.setTitleSelect(-1);
					Intent mIntent = new Intent(MainReader.this,BookMark.class);
					mIntent.putExtra("bookId", mBook.getId());
					startActivityForResult(mIntent, REQUEST);
					break;
				case 5:
					addTag();
					break;
				default:
					break;
			}
			myMenu.dismiss();
			myMenu.setTitleSelect(-1);
		}

	}

	@Override
	//结果返回处理
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST) {
			if (resultCode == RESULT) {
//				Bundle bundle = data.getExtras();
//				mBook = mBookInfo.getABook(Integer.getInteger(bundle.getString("bId")));
				pagefactory.setM_mbBufBegin(mBookInfo.getReadRate(mBook.getId()));
				pagefactory.setM_mbBufEnd(mBookInfo.getReadRate(mBook.getId()));
//				pagefactory.setM_mbBufEnd(pagefactory.getM_mbBufBegin());
				pagefactory.clearM_lines();
				pagefactory.onDraw(mCurPageCanvas);
				pagefactory.onDraw(mNextPageCanvas);
				mPageWidget.invalidate();
				Log.i("onActivityResult", mBook.toString());
			}
		}
	}

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//    	menu.add("menu");
//    	return super.onCreateOptionsMenu(menu);
//    }

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

//        if (myMenu != null) {
//            if (myMenu.isShowing()) {
//            	myMenu.dismiss();
//            	myMenu.setTitleSelect(-1);
//            }
//            else {
//            	myMenu.setTitleSelect(-1);
//            	myMenu.showAtLocation(this.mPageWidget,
//                        Gravity.BOTTOM, 0, 0);
//            }
//        }
//        return false;
		return super.onMenuOpened(featureId, menu);
	}

	private SpeechSynthesizer mTts;
	public void beginToSpeech(String recordResult){
		//1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
		mTts = SpeechSynthesizer.createSynthesizer(this, null);
		Log.d(TAG, "into beginToSpeech() recordResult:" + recordResult);

		/**
		 2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
		 *
		 */

		// 清空参数
		mTts.setParameter(SpeechConstant.PARAMS, null);

		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
		mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
		//设置合成音调
		mTts.setParameter(SpeechConstant.PITCH, "50");
		mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
		mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
		// 设置播放合成音频打断音乐播放，默认为true
		mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		// 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
//        boolean isSuccess = mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts2.wav");
//        Toast.makeText(MainActivity.this, "语音合成 保存音频到本地：\n" + isSuccess, Toast.LENGTH_LONG).show();
		//3.开始合成
		Log.d(TAG, "into beginToSpeech()3");
		int code = mTts.startSpeaking(recordResult, mSynListener);
		//int code = mTts.startSpeaking(recordResult, mSynListener);
		Log.d(TAG, "into beginToSpeech()4");
		if (code != ErrorCode.SUCCESS) {
			if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
				//上面的语音配置对象为初始化时：
				Log.d(TAG, "into 语音组件未安装");
				Toast.makeText(this, "语音组件未安装", Toast.LENGTH_LONG).show();
			} else {
				Log.d(TAG, "into 语音合成失败,错误码");
				Toast.makeText(this, "语音合成失败,错误码: " + code, Toast.LENGTH_LONG).show();
			}
		}

	}

	//合成监听器
	private SynthesizerListener mSynListener = new SynthesizerListener() {
		//会话结束回调接口，没有错误时，error为null
		public void onCompleted(SpeechError error) {
			Log.d(TAG, "into onCompleted()");
		}

		//缓冲进度回调
		//percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
		public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
			Log.d(TAG, "into onBufferProgress()");
		}

		//开始播放
		public void onSpeakBegin() {
			Log.d(TAG, "into onSpeakBegin()");
		}

		//暂停播放
		public void onSpeakPaused() {
			Log.d(TAG, "into onSpeakPaused()");
		}

		//播放进度回调
		//percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
			Log.d(TAG, "into onSpeakProgress()");
		}

		//恢复播放回调接口
		public void onSpeakResumed() {
			Log.d(TAG, "into onSpeakResumed()");
		}

		//会话事件回调接口
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			Log.d(TAG, "into onEvent()");
		}
	};


	@Override
	protected void onResume() {
		super.onResume();
		if(mTts !=null){
			mTts.resumeSpeaking();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(mTts !=null && mTts.isSpeaking()){
			mTts.pauseSpeaking();
		}

	}
}