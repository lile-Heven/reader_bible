package com.sdattg.vip.read_my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.sdattg.vip.R;

public class MyReadActivity extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout read;
    RelativeLayout settings;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_read_my3);
        initView();
        initListens();
        initDates();
    }

    private void initView(){
        //read = findViewById(R.id.read);
        //settings = findViewById(R.id.settings);
    }

    private void initListens(){
        //read.setOnClickListener(this);
    }

    private void initDates(){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //case R.id.read:
                //settings.setAnimation(inAnima);//设置显示时动画
                //settings.setVisibility(View.VISIBLE);//设置显示
            //    break;
        }
    }
}
