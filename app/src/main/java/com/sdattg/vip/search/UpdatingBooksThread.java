package com.sdattg.vip.search;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.sdattg.vip.bean.InitDatas;
import com.sdattg.vip.bean.NewBookBean;
import com.sdattg.vip.bean.NewChapterBean;
import com.sdattg.vip.util.FileUtil;
import com.sdattg.vip.util.SharePreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class UpdatingBooksThread extends Thread {
    private static UpdatingBooksThread SingleInstance = null;

    Context context;
    NewCategoryDBHelper DBHelper;

    private UpdatingBooksThread(SerachActivity context){
        this.context = context;
        DBHelper = new NewCategoryDBHelper(context);
    }

    public static UpdatingBooksThread getSingleInstance(SerachActivity context){
        synchronized (context){
            if(SingleInstance == null){
                SingleInstance = new UpdatingBooksThread(context);
                return SingleInstance;
            }else{
                return SingleInstance;
            }
        }
    }
    @Override
    public void run() {
        super.run();
        new UpdatingProgressThread(context){}.start();
        try{
            //open updatedSwitch
            InitDatas.onlyUpdateJieShao = false;

            Log.d("findbug071717", "into 666");
            InitDatas init = new InitDatas();
            init.initFromBooks(DBHelper);
            Log.d("findbug071717", "into 669");



        }catch (Exception e){
            Log.d("findbug071717", "into UpdatingBooksThread Exception");
            e.printStackTrace();
        }finally {
            InitDatas.onlyUpdateJieShao = true;
        }

        Log.d("findbug071717", "into 666667");
    }
}
