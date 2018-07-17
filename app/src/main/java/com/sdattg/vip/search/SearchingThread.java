package com.sdattg.vip.search;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.sdattg.vip.R;
import com.sdattg.vip.bean.NewBookBean;
import com.sdattg.vip.bean.NewChapterBean;
import com.sdattg.vip.bean.NewShowChapterBean;
import com.sdattg.vip.util.FileUtil;

import java.io.File;
import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

public class SearchingThread extends Thread {
    public static final String searchTitleFlag = "520<b>";
    Context context;
    String search_content, category_selected_str1, category_selected_str2, category_selected_str3;
    NewCategoryDBHelper DBHelper;
    public SearchingThread(SerachActivity context, String search_content, String category_selected_str1, String category_selected_str2, String category_selected_str3){
        this.context = context;
        this.search_content = search_content;
        this.category_selected_str1 = category_selected_str1;
        this.category_selected_str2 = category_selected_str2;
        this.category_selected_str3 = category_selected_str3;
        DBHelper = new NewCategoryDBHelper(context);

    }

    @Override
    public void run() {
        super.run();

        /*try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }*/
        switch (SerachActivity.SEARCH_KIND){
            //0-书籍搜索  1-标题搜索  2-目录搜索  3-内容搜索
            case 0:
                Log.d("findbug0717", "into SerachActivity.SEARCH_KIND 0");
                queryBooks();
                break;
            case 1:
                Log.d("findbug0717", "into SerachActivity.SEARCH_KIND 1");
                queryTitles();
                break;
            case 2:
                Log.d("findbug0717", "into SerachActivity.SEARCH_KIND 2");
                queryIndexs();
                break;
            case 3:
                Log.d("findbug0717", "into SerachActivity.SEARCH_KIND 3");
                queryContents();
                break;
        }
        //when successed.
        /*if(SerachActivity.updateUIHandler != null){
            SerachActivity.updateUIHandler.sendEmptyMessage(9);
        }*/
        DBHelper.close();
    }

    private void queryBooks(){
        DBHelper.getBooks();
        List<String> books = DBHelper.queryBooks(search_content);
        List<String> lineupBooks = new ArrayList<String>();
        for (String book:
                books) {
            lineupBooks.add(FileUtil.addShumingHao(FileUtil.substringFrom_(book)));
            Log.d("findbug0717", book);
        }
        Message msg = new Message();
        msg.what = 0;
        msg.obj = lineupBooks;
        SerachActivity.updateUIHandler.sendMessage(msg);
    }

    private void queryTitles(){
        List<NewShowChapterBean> all_chapters = DBHelper.queryContents(searchTitleFlag + search_content);
        for (NewShowChapterBean chapterBean:
                all_chapters) {
            Log.d("findbug0717", chapterBean.toString());
        }

        Message msg = new Message();
        msg.what = 1;
        msg.obj = all_chapters;
        SerachActivity.updateUIHandler.sendMessage(msg);
    }

    private void queryIndexs(){
        List<NewBookBean> indexs = DBHelper.queryIndexs(search_content);
        for (NewBookBean bookBean:
                indexs) {
            Log.d("findbug0717", bookBean.toString());
        }

        Message msg = new Message();
        msg.what = 2;
        msg.obj = indexs;
        SerachActivity.updateUIHandler.sendMessage(msg);
    }

    private void queryContents(){
        List<NewShowChapterBean> all_chapters = DBHelper.queryContents(search_content);
        for (NewShowChapterBean chapterBean:
             all_chapters) {
            Log.d("findbug0717", chapterBean.toString());
        }

        Message msg = new Message();
        msg.what = 3;
        msg.obj = all_chapters;
        SerachActivity.updateUIHandler.sendMessage(msg);
    }
}
