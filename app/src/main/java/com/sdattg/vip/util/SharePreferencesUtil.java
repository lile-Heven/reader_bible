package com.sdattg.vip.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharePreferencesUtil {
    // 判断是否是第一次启动程序 利用 SharedPreferences 将数据保存在本地
    public static boolean isFristRun(Context context) {
        //实例化SharedPreferences对象（第一步）
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "share", MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!isFirstRun) {
            return false;
        } else {
            //保存数据 （第三步）
            editor.putBoolean("isFirstRun", false);
            //提交当前数据 （第四步）
            editor.commit();
            return true;
        }
    }

}
