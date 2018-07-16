package com.sdattg.vip;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sdattg.vip.base.BaseTablayoutActivity;
import com.sdattg.vip.bean.CategoryBean;
import com.sdattg.vip.bean.InitDatas;
import com.sdattg.vip.myviews.NumberProgressBar;
import com.sdattg.vip.myviews.OnProgressBarListener;
import com.sdattg.vip.search.MyCategoryDBHelper;
import com.sdattg.vip.search.MyDBHelper;
import com.sdattg.vip.search.SerachActivity;
import com.sdattg.vip.tool.ZipTool;
import com.sdattg.vip.util.FileUtil;
import com.sdattg.vip.util.SharePreferencesUtil;

import org.apache.tools.ant.Main;
import org.apache.tools.ant.util.ReaderInputStream;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GuideActivity extends AppCompatActivity implements OnProgressBarListener {
    private String[] bookzips = {"01-书库.zip", "02-灵修.zip", "testunzip.zip"};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private MyCategoryDBHelper myCategoryDBHelper;
    private NumberProgressBar bnp;
    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initSQLite();

        initViews();
        initDatas();
    }

    private void initSQLite() {
        myCategoryDBHelper = new MyCategoryDBHelper(this);

    }

    private boolean initViews() {
        Log.d("GuideActivity", "into initViews()");
        final EditText et_queryname = findViewById(R.id.et_queryname);
        findViewById(R.id.bt_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("GuideActivity", "into onClick()");
                if (myCategoryDBHelper != null) {
                    //myCategoryDBHelper = new MyCategoryDBHelper(GuideActivity.this);
                    //String categoryHashName = FileUtil.getMD5Checksum(et_queryname.getText().toString().trim());
                    String name = FileUtil.replaceBy_(et_queryname.getText().toString().trim());
                    List<CategoryBean> list = myCategoryDBHelper.getCategory(name, null);
                    for (CategoryBean categoryBean :
                            list) {
                        //Log.d("GuideActivity", categoryBean.categoryHashName + ", " + categoryBean.categoryPath + ", " + categoryBean.categoryName);
                        //Toast.makeText(GuideActivity.this, "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName, Toast.LENGTH_SHORT).show();
                        Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
                    }
                }

            }
        });

        final EditText et_table = findViewById(R.id.et_table);
        final EditText et_like = findViewById(R.id.et_like);
        findViewById(R.id.bt_contains).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("GuideActivity", "into onClick() bt_contains");
                if (myCategoryDBHelper != null) {
                    //myCategoryDBHelper = new MyCategoryDBHelper(GuideActivity.this);
                    //String categoryHashName = FileUtil.getMD5Checksum(et_queryname.getText().toString().trim());
                    String table = FileUtil.replaceBy_(et_table.getText().toString().trim());
                    String like = FileUtil.replaceBy_(et_like.getText().toString().trim());
                    for (String table2:
                    myCategoryDBHelper.getAllTables()) {
                        List<CategoryBean> list = myCategoryDBHelper.getContains(table2, "Content", like);
                        Log.d("findbug0716", "list.size():" + list.size());
                        for (CategoryBean categoryBean :
                                list) {
                            //Log.d("GuideActivity", categoryBean.categoryHashName + ", " + categoryBean.categoryPath + ", " + categoryBean.categoryName);
                            //Toast.makeText(GuideActivity.this, "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName, Toast.LENGTH_SHORT).show();
                            //Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
                        }
                    }


                }

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
            Toast.makeText(this, "unzip done.", Toast.LENGTH_SHORT).show();
            //开始加载解压后的数据
            myCategoryDBHelper.deleteTable(MyCategoryDBHelper.MyAllCategoryTAble);
            myCategoryDBHelper.createAllCategoryTable(MyCategoryDBHelper.MyAllCategoryTAble);
            if (loadCategory2(ZipTool.APP_DIR_UNZIP)) {
                myCategoryDBHelper.close();
                Toast.makeText(GuideActivity.this, "loadCategory done.", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                //startActivity(intent);
            } else {
                Toast.makeText(GuideActivity.this, "loadCategory failed.", Toast.LENGTH_SHORT).show();
            }

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
                        Toast.makeText(GuideActivity.this, "unzip done.", Toast.LENGTH_SHORT).show();
                        //开始加载解压后的数据
                        myCategoryDBHelper.deleteTable(MyCategoryDBHelper.MyAllCategoryTAble);
                        myCategoryDBHelper.createAllCategoryTable(MyCategoryDBHelper.MyAllCategoryTAble);
                        if (loadCategory2(ZipTool.APP_DIR_UNZIP)) {
                            myCategoryDBHelper.close();
                            Toast.makeText(GuideActivity.this, "loadCategory done.", Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                            //startActivity(intent);
                        } else {
                            Toast.makeText(GuideActivity.this, "loadCategory failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                        //startActivity(intent);
                        Toast.makeText(GuideActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        InitDatas initDatas = new InitDatas();
                        //initDatas.getChapters(new File(ZipTool.APP_DIR_UNZIP + "/01-书库/02-怀著/07-布道/01-{}布道论/13-第13章 个人之工.txt"));
                        //initDatas.getChapters(new File(ZipTool.APP_DIR_UNZIP + "/01-书库/01-圣经/01-旧约/01-创世记/001-第01章.txt"));
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

    /*private boolean loadCategory(String path){
        if(FileUtil.isFileExist(path)){
            File rootDirFile = new File(path);
            if(rootDirFile.isDirectory()){
                CategoryBean categoryBean = new CategoryBean();
                categoryBean.categoryHashName = "";
                categoryBean.categoryPath = rootDirFile.getAbsolutePath();
                categoryBean.categoryName = rootDirFile.getName();
                if(myCategoryDBHelper != null){
                    myCategoryDBHelper.insertData(categoryBean);

                }
                File[] files = rootDirFile.listFiles();
                if(files.length > 0 && files[0].isDirectory()){
                    for (File file : files) {
                        loadCategory(file.getAbsolutePath());
                    }
                }
            }
            return true;
        }else{
            Toast.makeText(this, "loadCategory fail path not exists.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }*/

    private boolean loadCategory2(String path) {
        FileUtil fileUtil = new FileUtil();
        if (fileUtil.isPathAvailable(path)) {
            File rootDirFile = new File(path);
            if (rootDirFile.isDirectory()) {
                /*String listFilesNames = "";
                for (File file:
                     rootDirFile.listFiles()) {
                    listFilesNames += file.getName() + "$$";
                }
                categoryBean.categoryListFilesName = listFilesNames;*/
                File[] tempFiles = FileUtil.orderByName(rootDirFile.listFiles());
                if (myCategoryDBHelper != null) {
                    //String categoryHashName = FileUtil.getMD5Checksum(rootDirFile.getAbsolutePath());

                    String tableName = FileUtil.replaceBy_(rootDirFile.getName());
                    myCategoryDBHelper.deleteTable(tableName);
                    myCategoryDBHelper.createCategoryTable(tableName);
                    addToAllCategoryTable(tableName);

                    CategoryBean categoryBean = new CategoryBean();
                    int categoryInsertOrder = 1;
                    if (tempFiles[0].exists() && tempFiles[0].getName().contains("jieshao")) {
                        String temp = "";
                        //String result = "";

                        byte[] bytes = new byte[1024];
                        int count = 0;
                        FileInputStream fileInputStream = null;
                        //InputStreamReader inputStreamReader = null;
                        //BufferedReader bufferReader = null;
                        try {
                            fileInputStream = new FileInputStream(tempFiles[0]);
                            //new File(tempFiles[0].getAbsolutePath(), "utf-8");
                            //ReaderInputStream reader = new ReaderInputStream(new FileReader(tempFiles[0]), "gbk");
                            while ((count = fileInputStream.read(bytes)) != -1) {
                                temp += new String(bytes, 0, count, "gbk");
                            }
                            //Log.d("GuideActivity", "has jieshao temp:" + temp);
                            /*fileInputStream = new FileInputStream(tempFiles[0]);
                            inputStreamReader = new InputStreamReader(fileInputStream);
                            bufferReader = new BufferedReader(inputStreamReader);
                            while ((temp = bufferReader.readLine()) != null){
                                result += temp;
                            }
                            Log.d("GuideActivity", "has jieshao result:" + result);*/

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(fileInputStream != null){
                            try{
                                fileInputStream.close();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }

                        categoryBean.categoryHashName = "noHash";
                        categoryBean.categoryInsertOrder = 0;
                        categoryBean.categoryPath = FileUtil.replaceBy_(rootDirFile.getAbsolutePath());
                        categoryBean.categoryName = FileUtil.replaceBy_(rootDirFile.getName());
                        categoryBean.categoryJieshao = temp;
                        myCategoryDBHelper.insertData(tableName, categoryBean);
                    }

                    //File[] files2 = FileUtil.orderByName(rootDirFile.listFiles());
                    if (tempFiles[0].getName().endsWith(".txt")) {
                        keepOneBook(tempFiles, tableName);
                    } else {
                        for (File file :
                                tempFiles) {
                        /*if(file.getName().endsWith(".txt") ){


                            Log.d("findbug", "file.getName():" + file.getName());
                            categoryBean = new CategoryBean();
                            categoryBean.categoryHashName = "noHash";
                            categoryBean.categoryInsertOrder = categoryInsertOrder;
                            categoryInsertOrder++;
                            categoryBean.categoryPath = FileUtil.replaceBy_(file.getAbsolutePath());
                            categoryBean.categoryName = FileUtil.replaceBy_(file.getName());
                            myCategoryDBHelper.insertData(tableName, categoryBean);
                        }*/
                            Log.d("findbug", "file.getName():" + file.getName());
                            categoryBean = new CategoryBean();
                            categoryBean.categoryHashName = "noHash";
                            categoryBean.categoryInsertOrder = categoryInsertOrder;
                            categoryInsertOrder++;
                            categoryBean.categoryPath = FileUtil.replaceBy_(file.getAbsolutePath());
                            categoryBean.categoryName = FileUtil.replaceBy_(file.getName());
                            myCategoryDBHelper.insertData(tableName, categoryBean);
                        }
                    }
                }

                for (File file : FileUtil.orderByName(rootDirFile.listFiles())) {
                    loadCategory2(file.getAbsolutePath());
                }
            }

            /*else if(rootDirFile.getName().endsWith(".txt")){
                //此为具体的某本书籍
                CategoryBean categoryBean = new CategoryBean();
                rootDirFile
            }*/
            return true;
        } else {
            Toast.makeText(this, "loadCategory fail path not exists.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void keepOneBook(File[] tempFiles, String tableName) {
        int categoryInsertOrder = 1;
        List<File> list_temp = new ArrayList<File>();
        for (File file2 :
                tempFiles) {
            if (file2.getName().contains("jieshao") || file2.getName().contains("zuozhe")) {

            } else {
                list_temp.add(file2);
            }
        }


        for (File file :
                list_temp) {

            StringBuilder result = new StringBuilder("");
            String temp = "";
            byte[] bytes = new byte[1024];
            int count = 0;
            FileInputStream fileInputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferReader = null;
            try {
                fileInputStream = new FileInputStream(file);
                inputStreamReader = new InputStreamReader(fileInputStream);
                bufferReader = new BufferedReader(inputStreamReader);
                //new File(tempFiles[0].getAbsolutePath(), "utf-8");
                //ReaderInputStream reader = new ReaderInputStream(new FileReader(tempFiles[0]), "gbk");
                /*if ((count = fileInputStream.read(bytes)) != -1) {
                    temp += new String(bytes, 0, count, "gbk");
                }*/
                while ((temp = bufferReader.readLine()) != null){
                    result.append(temp);
                    //result += temp;
                }
                Log.d("GuideActivity", "has categoryContent result:" + result.toString());
                //Log.d("GuideActivity", "has categoryContent temp:" + new String(temp.getBytes(), "gb2312"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(fileInputStream != null){
                try{
                    fileInputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(inputStreamReader != null){
                try{
                    inputStreamReader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(bufferReader != null){
                try{
                    bufferReader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            /*categoryBean.categoryHashName = "noHash";
            categoryBean.categoryInsertOrder = 0;
            categoryBean.categoryPath = FileUtil.replaceBy_(rootDirFile.getAbsolutePath());
            categoryBean.categoryName = FileUtil.replaceBy_(rootDirFile.getName());
            categoryBean.categoryJieshao = temp;
            myCategoryDBHelper.insertData(tableName, categoryBean);*/


            Log.d("findbug", "into keepOneBook file.getName():" + file.getName());
            CategoryBean categoryBean = new CategoryBean();
            categoryBean.categoryHashName = "noHash";
            categoryBean.categoryInsertOrder = categoryInsertOrder;
            categoryInsertOrder++;
            categoryBean.categoryPath = FileUtil.replaceBy_(file.getAbsolutePath());
            categoryBean.categoryName = FileUtil.replaceBy_(file.getName());
            categoryBean.categoryContent = "test测试什么鬼";
            //categoryBean.categoryContent = temp;
            myCategoryDBHelper.insertData(tableName, categoryBean);
        }
    }

    private void addToAllCategoryTable(String tableName){
        Log.d("findbug0716", "into addToAllCategoryTable()");
        myCategoryDBHelper.addToAllCategoryTable(tableName);
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
