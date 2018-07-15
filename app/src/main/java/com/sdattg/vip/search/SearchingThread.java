package com.sdattg.vip.search;

import android.content.Context;

import java.net.ContentHandler;

public class SearchingThread extends Thread {
    Context context;
    String search_content, category_selected_str1, category_selected_str2, category_selected_str3;

    public SearchingThread(SerachActivity context, String search_content, String category_selected_str1, String category_selected_str2, String category_selected_str3){
        this.context = context;
        this.search_content = search_content;
        this.category_selected_str1 = category_selected_str1;
        this.category_selected_str2 = category_selected_str2;
        this.category_selected_str3 = category_selected_str3;
    }

    @Override
    public void run() {
        super.run();
        MyCategoryDBHelper myCategoryDBHelper = new MyCategoryDBHelper(context);
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //when successed.
        if(SerachActivity.updateUIHandler != null){
            SerachActivity.updateUIHandler.sendEmptyMessage(1);
        }
    }
}
