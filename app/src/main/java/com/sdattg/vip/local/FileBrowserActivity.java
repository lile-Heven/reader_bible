package com.sdattg.vip.local;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdattg.vip.R;
import com.sdattg.vip.tool.ZipTool;
import com.sdattg.vip.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by blue on 2016/10/23.
 */

public class FileBrowserActivity extends ListActivity {
    private static final String TAG = FileBrowserActivity.class.getSimpleName() + "--->";
    private String rootPath;
    private boolean pathFlag;
    private List<String> pathList;
    private List<String> itemsList;
    private TextView curPathTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filebrowser);
        initView();
        initInfo();
    }

    private void initInfo() {
        pathFlag = getIntent().getBooleanExtra("area", false);
        rootPath = getRootPath();
        Log.d("FileBrowserActivity", "into initInfo() rootPath；" + rootPath);
        if (rootPath == null) {
            Log.d("FileBrowserActivity", "into initInfo() rootPath == null is true");
            Toast.makeText(this, "所选SD卡为空！", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Log.d("FileBrowserActivity", "into initInfo() rootPath == null is false");

            //getFileDir(rootPath);
            getFileDir(ZipTool.APP_DIR_UNZIP);
        }
    }

    private void initView() {
        curPathTextView = (TextView) findViewById(R.id.curPath);
    }


    private void getFileDir(String filePath) {
        curPathTextView.setText(filePath);
        itemsList = new ArrayList<>();
        pathList = new ArrayList<>();
        File file = new File(filePath);
        File[] files = file.listFiles();
        FileUtil.orderByName(files);   // sort by Name
        if (!filePath.equals(rootPath)) {
            itemsList.add("b1");
            pathList.add(rootPath);
            itemsList.add("b2");
            pathList.add(file.getParent());
        }
        if (files == null) {
            Log.d("FileBrowserActivity", "into getFileDir() files == null is true");
            Toast.makeText(this, "所选SD卡为空！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
           // if (checkSpecificFile(f)) {
                itemsList.add(f.getName());
                pathList.add(f.getPath());
         //   }
        }
        setListAdapter(new MyAdapter(this, itemsList, pathList));
    }

    public boolean checkSpecificFile(File file) {
        String fileNameString = file.getName();
        String endNameString = fileNameString.substring(
                fileNameString.lastIndexOf(".") + 1, fileNameString.length())
                .toLowerCase();
        Log.d(TAG, "checkShapeFile: " + endNameString);
        if (file.isDirectory()) {
            return true;
        }
        if (endNameString.equals("txt")) {
            return true;
        } else {
            return false;
        }
    }

    private String getRootPath() {

        try {
            String rootPath;
                if (pathFlag) {
                    Log.d(TAG, "getRootPath: 正在获取内置SD卡根目录");
                    rootPath = Environment.getExternalStorageDirectory()
                            .toString();
                    Log.d(TAG, "getRootPath: 内置SD卡目录为:" + rootPath);
                    return rootPath;
                } else {
                    rootPath = System.getenv("SECONDARY_STORAGE");
                    if ((rootPath.equals(Environment.getExternalStorageDirectory().toString())))
                        rootPath = null;
                    Log.d(TAG, "getRootPath:  外置SD卡路径为：" + rootPath);
                    return rootPath;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        final File file = new File(pathList.get(position));
        if (file.isDirectory()) {
            getFileDir(file.getPath());
        } else if(file.getName().endsWith("txt")){
            /*Intent data = new Intent(FileBrowserActivity.this, SelectBookActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("file", file.getPath());
            data.putExtras(bundle);
            setResult(2, data);
            finish();*/
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("提示：")
                    .setMessage("点击确定后將加入个人书籍列表")
                    .setPositiveButton("确  定", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent data = new Intent(FileBrowserActivity.this, SelectBookActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("file", file.getPath());
                            data.putExtras(bundle);
                            setResult(1, data);
                            finish();
                            //Toast.makeText(FileBrowserActivity.this, "添加成功!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);

        } else{
            Toast.makeText(this, "此可能为无法识别的书籍文件", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkSDcard() {
        String sdStutusString = Environment.getExternalStorageState();
        if (sdStutusString.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }



    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }*/
}
