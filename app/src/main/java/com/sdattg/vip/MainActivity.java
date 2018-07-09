package com.sdattg.vip;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdattg.vip.base.BaseFragment;
import com.sdattg.vip.base.BaseTablayoutActivity;
import com.sdattg.vip.search.SerachActivity;
import com.sdattg.vip.tool.ZipTool;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.sdattg.vip.MyFragmentBendi.FILE_RESULT_CODE;

public class MainActivity extends BaseTablayoutActivity {
    private TextView tv_serach;
    private final String TAG = MainActivity.class.getSimpleName();
    private String[] bookzips = {"02-灵修.zip", "testunzip.zip"};

    private MyFragmentShuKu mFragmentShuku;
    private MyFragmentLingXiu mFragmentLingXiu;
    private MyFragmentBaiKe mFragmentBaike;
    private MyFragmentBendi mFragmentBenDi;

    @Override
    protected List<BaseFragment> getFragmentList() {
        List<BaseFragment> list = new ArrayList<>();
        mFragmentShuku = new MyFragmentShuKu();
        mFragmentLingXiu = new MyFragmentLingXiu();
        mFragmentBaike = new MyFragmentBaiKe();
        mFragmentBenDi = new MyFragmentBendi();
        list.add(mFragmentShuku);
        list.add(mFragmentLingXiu);
        list.add(mFragmentBaike);
        list.add(mFragmentBenDi);
        return list;
    }

    @Override
    protected List<String> getFragmentTitleList() {
        List<String> list = new ArrayList<>();
        list.add("书库");
        list.add("灵修");
        list.add("百科");
        list.add("本地");
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkMyPermission();
        unZip();
    }

    @Override
    public String setTitle() {
        return "";
    }

    @Override
    protected void iniLogic() {
        tv_serach = findViewById(R.id.tv_serach);
        try {
            settab();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        tv_serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SerachActivity.class);
                startActivity(intent);
            }
        });
    }

    public void settab() throws NoSuchFieldException, IllegalAccessException {
        Class<?> tablayout = tabLayout.getClass();
        Field tabStrip = tablayout.getDeclaredField("mTabStrip");
        tabStrip.setAccessible(true);
        LinearLayout ll_tab = (LinearLayout) tabStrip.get(tabLayout);
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.setMarginStart(dip2px(this, 15f));
            params.setMarginEnd(dip2px(this, 15f));
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @Override
    protected void httpRequest() {
        Log.d("SelectBookActivity", "into httpRequest()");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Toast.makeText(this, "findbug1 requestCode:" + requestCode + ",resultCode:" + resultCode, Toast.LENGTH_SHORT).show();
        if (FILE_RESULT_CODE == resultCode) {
            Bundle bundle = null;
            if (data != null && (bundle = data.getExtras()) != null) {
                String path = bundle.getString("file");
                if(!mFragmentBenDi.contains(path)){
                    mFragmentBenDi.add(path);
                }else{
                    Toast.makeText(this, "此书籍之前已添加。", Toast.LENGTH_SHORT).show();
                }
                //String path = bundle.getString("file");
                //Log.d(TAG, "onActivityResult: " + path);
                //changePath.setText("选择路径为 : " + path);
                //Toast.makeText(this, "添加成功:\n" + path, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void updateFragmentBenDi(){
        mFragmentBenDi.pathsChanged();
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    private void checkMyPermission(){
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(this,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unZip(){
        Log.d("Tab01ProductAdapter", ZipTool.APP_DIR);
        isAPP_DIRExists();
        //ZipTool.upZipFileDir();
        for (String one:bookzips) {
            File result = checkBooksExists(one);
            if(result != null){
                ZipTool.upZipFileDir(result, ZipTool.APP_DIR_UNZIP);

            }
        }
    }

    private void isAPP_DIRExists(){
        File appFile = new File(ZipTool.APP_DIR);
        if(!appFile.exists()){
            appFile.mkdirs();
            Log.d("Tab01ProductAdapter", "!appFile.exists()");
        }

        File appUnzipFile = new File(ZipTool.APP_DIR_UNZIP);
        if(!appUnzipFile.exists()){
            appUnzipFile.mkdirs();
            Log.d("Tab01ProductAdapter", "!appUnzipFile.exists()");
        }
    }

    private File checkBooksExists(String book){
        String bookFilePath = ZipTool.APP_DIR + "/" + book;
        Log.d("Tab01ProductAdapter", "into checkBooksExists() bookFilePath:" + bookFilePath);
        File bookFile = new File(bookFilePath);
        if(!bookFile.exists()){
            Log.d("Tab01ProductAdapter", "!bookFile.exists() is true");
            return null;
        }else{
            Log.d("Tab01ProductAdapter", "!bookFile.exists() is false");
            return bookFile;
        }
    }


}
