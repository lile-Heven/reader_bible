package com.sdattg.vip.base;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdattg.vip.GuideActivity;
import com.sdattg.vip.R;
import com.sdattg.vip.bean.CheckNewDatas;
import com.sdattg.vip.bean.InitDatas;
import com.sdattg.vip.search.MyCategoryDBHelper;
import com.sdattg.vip.search.NewCategoryDBHelper;
import com.sdattg.vip.tool.ZipTool;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by yinqm on 2018/3/13.
 */

public abstract class BaseTitleBarActivity extends BaseActivity {
    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    public abstract String setTitle();
    TextView tvBack;
    public LinearLayout llLeft;
    TextView tvTitle;
    LinearLayout rlBar;
    LinearLayout topLayout;
    ImageView iv_mainactivity_setting;

    @Override
    protected int getLayoutId() {
        return R.layout.lib_toolbar;
    }


    @Override
    protected void addViewLayout() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        llLeft = (LinearLayout) findViewById(R.id.ll_left);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rlBar = (LinearLayout) findViewById(R.id.rl_bar);
        topLayout = (LinearLayout) findViewById(R.id.top_layout);
        iv_mainactivity_setting = (ImageView)findViewById(R.id.iv_mainactivity_setting);

        //TODO 目前作为书籍库更新来使用
        iv_mainactivity_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(BaseTitleBarActivity.this)
                        .setTitle("提示：")
                        .setMessage("是否更新书籍数据库？")
                        .setPositiveButton("确 定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CheckNewDatas checkNewDatas = new CheckNewDatas();
                                checkNewDatas.init(new NewCategoryDBHelper(BaseTitleBarActivity.this), ZipTool.APP_DIR_UNZIP, "unzip");
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
            }
        });


        View mContextView = LayoutInflater.from(this)
                .inflate(bindLayout(), null);
        topLayout.addView(mContextView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        if (setTitle() != null) {
            tvTitle.setText(setTitle());
        }else{
            rlBar.setVisibility(View.GONE);
        }
        llLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null) {
                    if (getCurrentFocus().getWindowToken() != null) {
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                finish();
            }
        });
    }


    /**
     * 设置是否显示返回键
     */
    public void setShowBack(boolean isShow) {
        if (isShow) {
            llLeft.setVisibility(View.VISIBLE);
        } else {
            llLeft.setVisibility(View.GONE);
        }
    }





}
