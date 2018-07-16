package com.sdattg.vip;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sdattg.vip.bean.CategoryBean;
import com.sdattg.vip.bean.InitDatas;
import com.sdattg.vip.myviews.NumberProgressBar;
import com.sdattg.vip.myviews.OnProgressBarListener;
import com.sdattg.vip.search.MyCategoryDBHelper;
import com.sdattg.vip.search.NewCategoryDBHelper;
import com.sdattg.vip.tool.ZipTool;
import com.sdattg.vip.util.FileUtil;
import com.sdattg.vip.util.SharePreferencesUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NewGuideActivity extends AppCompatActivity implements OnProgressBarListener {
    private String TAG = this.getClass().getSimpleName();
    private String[] bookzips = {"01-书库.zip", "02-灵修.zip", "testunzip.zip"};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private NumberProgressBar bnp;
    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initViews();
        initDatas();
    }

    private boolean initViews() {
        Log.d("findbug0717", "into initViews()");
        final EditText et_queryname = findViewById(R.id.et_queryname);
        findViewById(R.id.bt_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("findbug0717", "into onClick()");
                NewCategoryDBHelper dbHelper = new NewCategoryDBHelper(NewGuideActivity.this);
                if (dbHelper != null) {
                    String name = FileUtil.replaceBy_(et_queryname.getText().toString().trim());
                    List<String> list = dbHelper.getBooks();
                    //List<CategoryBean> list = myCategoryDBHelper.getCategory(name, null);
                    for (String one :
                            list) {
                        //Log.d("GuideActivity", categoryBean.categoryHashName + ", " + categoryBean.categoryPath + ", " + categoryBean.categoryName);
                        //Toast.makeText(GuideActivity.this, "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName, Toast.LENGTH_SHORT).show();
                        Log.d("findbug0717", "one book:" + one);
                    }
                }
                dbHelper.close();

            }
        });

        final EditText et_table = findViewById(R.id.et_table);
        final EditText et_like = findViewById(R.id.et_like);
        findViewById(R.id.bt_contains).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("findbug0717", "into onClick() bt_contains");
                NewCategoryDBHelper dbHelper = new NewCategoryDBHelper(NewGuideActivity.this);
                if (dbHelper != null) {
                    //myCategoryDBHelper = new MyCategoryDBHelper(GuideActivity.this);
                    //String categoryHashName = FileUtil.getMD5Checksum(et_queryname.getText().toString().trim());
                    /*String table = FileUtil.replaceBy_(et_table.getText().toString().trim());
                    String like = FileUtil.replaceBy_(et_like.getText().toString().trim());
                    for (String table2:
                            dbHelper.getAllTables()) {
                        List<CategoryBean> list = dbHelper.getContains(table2, "Content", like);
                        Log.d("findbug0716", "list.size():" + list.size());
                        for (CategoryBean categoryBean :
                                list) {
                        }
                    }*/
                }
                dbHelper.close();

            }
        });

        bnp = (NumberProgressBar) findViewById(R.id.number_progressbar1);
        bnp.setOnProgressBarListener(this);

        return false;
    }

    @Override
    public void onProgressChange(int current, int max) {
        if (current == max) {
            //Toast.makeText(getApplicationContext(), getString(R.string.finish), Toast.LENGTH_SHORT).show();
            bnp.setProgress(0);
        }
    }

    private boolean initDatas() {
        checkMyPermission();

        if (SharePreferencesUtil.isFristRun(this)) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bnp.incrementProgressBy(1);
                        }
                    });
                }
            }, 1000, 100);
            unZip();
            initSQLite(ZipTool.APP_DIR_UNZIP);
            /*if (loadCategory2(ZipTool.APP_DIR_UNZIP)) {

                Toast.makeText(NewGuideActivity.this, "loadCategory done.", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                //startActivity(intent);
            } else {
                Toast.makeText(NewGuideActivity.this, "loadCategory failed.", Toast.LENGTH_SHORT).show();
            }*/

        } else {
            updateDialog();
            /*new Thread(){
                @Override
                public void run() {
                    super.run();
                    try{
                        sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }.start();
            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(intent);*/
        }
        return false;
    }

    private boolean initSQLite(String rootPath) {
        Log.d("findbug0717", "into initSQLite()");
        SharePreferencesUtil.getSQliteDatas(getApplicationContext());
        InitDatas init = new InitDatas();
        boolean done = init.init(new NewCategoryDBHelper(this), rootPath, "unzip");
        if(InitDatas.hasSQliteDatasInitOnce){

        }else{
            SharePreferencesUtil.setSQliteDatasInitOnceTrue(getApplicationContext());
        }

        if(InitDatas.hasNotDone == 0){
            SharePreferencesUtil.setHasAllDoneTrue(getApplicationContext());
        }else{
            InitDatas.hasNotDone = 0;
        }
        Log.d("findbug0717", "done initSQLite()");
        return done;
    }

    private void updateDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示：")
                .setMessage("是否更新书籍数据库？")
                .setPositiveButton("确 定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        bnp.incrementProgressBy(1);
                                    }
                                });
                            }
                        }, 1000, 100);
                        unZip();
                        if (initSQLite(ZipTool.APP_DIR_UNZIP)) {
                            Toast.makeText(NewGuideActivity.this, "loadCategory done.", Toast.LENGTH_LONG).show();
                            //Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                            //startActivity(intent);
                        } else {
                            Toast.makeText(NewGuideActivity.this, "loadCategory failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                        //startActivity(intent);
                    }
                })
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
    }


    private void unZip() {
        Log.d("Tab01ProductAdapter", ZipTool.APP_DIR);
        isAPP_DIRExists();
        //ZipTool.upZipFileDir();
        for (String one : bookzips) {
            File result = checkBooksExists(one);
            if (result != null) {
                ZipTool.upZipFileDir(result, ZipTool.APP_DIR_UNZIP); //好像是单线程的
            }
        }
    }


    private void isAPP_DIRExists() {
        File appFile = new File(ZipTool.APP_DIR);
        if (!appFile.exists()) {
            appFile.mkdirs();
            Log.d("Tab01ProductAdapter", "!appFile.exists()");
        }

        File appUnzipFile = new File(ZipTool.APP_DIR_UNZIP);
        if (!appUnzipFile.exists()) {
            appUnzipFile.mkdirs();
            Log.d("Tab01ProductAdapter", "!appUnzipFile.exists()");
        }
    }

    private File checkBooksExists(String book) {
        String bookFilePath = ZipTool.APP_DIR + "/" + book;
        Log.d("Tab01ProductAdapter", "into checkBooksExists() bookFilePath:" + bookFilePath);
        File bookFile = new File(bookFilePath);
        if (!bookFile.exists()) {
            Log.d("Tab01ProductAdapter", "!bookFile.exists() is true");
            return null;
        } else {
            Log.d("Tab01ProductAdapter", "!bookFile.exists() is false");
            return bookFile;
        }
    }

    private void checkMyPermission() {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(this,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
