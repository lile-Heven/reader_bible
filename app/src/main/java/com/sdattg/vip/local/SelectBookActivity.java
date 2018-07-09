package com.sdattg.vip.local;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdattg.vip.R;

public class SelectBookActivity extends AppCompatActivity {
    private static final String TAG = SelectBookActivity.class.getSimpleName() + "--->";
    public static final int FILE_RESULT_CODE = 1;
    private Button btn_open;
    private TextView changePath;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_READ_PHONE_STATE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.MOUNT_UNMOUNT_FILESYSTEMS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkMyPermission();
        initView();
        initListener();
    }

    private void checkMyPermission(){
        //兼容Android6.0运行时权限解决方案
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

    private void initView() {
        btn_open = (Button) findViewById(R.id.btn_open);
        changePath = (TextView) findViewById(R.id.changePath);
    }

    private void initListener() {
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBrowser();
            }
        });
    }

    private void openBrowser() {
        new AlertDialog.Builder(this).setTitle("选择存储区域").setIcon(
                R.mipmap.icon_opnefile_browser).setSingleChoiceItems(
                new String[]{"内置sd卡", "外部sd卡"}, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SelectBookActivity.this, FileBrowserActivity.class);
                        if (which == 0) {
                            Log.d("SelectBookActivity", "into openBrowser() which == 0");
                            intent.putExtra("area", false);
                        }else {
                            Log.d("SelectBookActivity", "into openBrowser() which == 1");
                            intent.putExtra("area", true);
                        }
                        startActivityForResult(intent, FILE_RESULT_CODE);
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (FILE_RESULT_CODE == requestCode) {
            Bundle bundle = null;
            if (data != null && (bundle = data.getExtras()) != null) {
                String path = bundle.getString("file");
                Log.d(TAG, "onActivityResult: " + path);
                changePath.setText("选择路径为 : " + path);
            }
        }
    }
}
