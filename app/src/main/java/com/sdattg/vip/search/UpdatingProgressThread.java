package com.sdattg.vip.search;

import android.content.Context;
import android.util.Log;

import com.sdattg.vip.bean.InitDatas;
import com.sdattg.vip.util.SharePreferencesUtil;

public class UpdatingProgressThread extends Thread {
    public static int progress = 0;
    public static double booksCount = 1.0;
    private boolean whileFlag = true;
    private Context context;
    public UpdatingProgressThread(Context context){
        this.context = context;
    }

    @Override
    public void run() {
        super.run();
        while(whileFlag){
            try{
                sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            double updating = ((progress/booksCount) * 100 );

            updateUI((int)updating);
        }
    }

    private void updateUI(int progress){
        Log.d("findbug071717", "into updateUI() progress:" + progress);

        if(progress == 100){
            if(InitDatas.hasNotDone == 0){
                Log.d("findbug071717", "into 66666666666666666666");
                SharePreferencesUtil.setHasAllDoneTrue(context);
            }else{
                Log.d("findbug071717", "into 668");
                InitDatas.hasNotDone = 0;
            }
            Log.d("findbug071717", "into 66666");
            SerachActivity.updateUIHandler.sendEmptyMessage(99);
        }
    }
}
