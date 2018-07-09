package com.sdattg.vip.read;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CutChapters {
	Context context;
	BookInfoDao mBookInfo;
	
	/**
     * 智能断章<BR>
     * @param context 上下文环境
     * @param bookId 书籍实体类
     * @param mContent 书籍内容
     */
    public CutChapters(Context context, int bookId, String mContent) {
    	this.context = context;
    	mBookInfo = new BookInfoDao(this.context); 
    	String str = mContent;
//        Pattern p = Pattern.compile("^[0-9一二三四五六七八九十百千 ]+$");
//        String[] key_array = { "章", "回", "节", "卷", "集", "幕", "计" };
//        String key;
        
        List<String> chapName = new ArrayList<String>();
        List<String> chapTemp = new ArrayList<String>();
        List<Integer> curStart = new ArrayList<Integer>();
//        List<Integer> curEnd = new ArrayList<Integer>();
        
        Log.i("测试", mContent.substring(0,1));
        Log.i("测试", Integer.toString(mContent.indexOf("无")));
        Log.i("测试", mContent.substring(1,2));
        Log.i("测试", Integer.toString(mContent.indexOf("限")));
        Log.i("测试", mContent.substring(2,3));
        Log.i("测试", Integer.toString(mContent.indexOf("恐")));
        Log.i("测试", mContent.substring(3,4));
        Log.i("测试", Integer.toString(mContent.indexOf("怖")));
        Log.i("测试", mContent.substring(4,5));
        Log.i("测试", Integer.toString(mContent.indexOf(mContent.substring(4,5))));
        Log.i("测试", Integer.toString(mContent.indexOf("第一章")));
        
        while(str.length()>0) {
            int start = str.indexOf("第");
            int end = str.indexOf("章");
            Log.i("CutChapter", Integer.toString(start)+"   "+ Integer.toString(end));
            if(start < 0 || end < 0) {
            	break;
            }
            if(end > start) {
                if(end-start <= 6) {
                	chapName.add(str.substring(start, end+1));
                    Log.d("chapName ", chapName.get(chapName.size()-1));
                	chapTemp.add(str.substring(start, end+32));
                    Log.d("章节位置 ", Integer.toString(mContent.indexOf(chapTemp.get(chapName.size()-1))));
                	Log.d("字符串", chapTemp.get(chapName.size()-1));
//                    if(curStart.size() == 0 && curEnd.size() == 0) {
//                        curStart.add(start);
//                        curEnd.add(end);
//                    } else {
//                    	curStart.add(curEnd.get(curEnd.size()-2)+start);
//                    	curEnd.add(curEnd.get(curEnd.size()-2)+end);
//                    }

//                    Log.i("cur.get ", Integer.toString(start));
                    str = str.substring(end+1);
                } else {
                	str = str.substring(start+1);
                }
            } else {
            	str = str.substring(end+1);
//            	curEnd.set(curEnd.size()-2, curEnd.get(curEnd.size()-2)+start);
            }
        }
        
        for(int i = 0; i < chapTemp.size(); i++) {
        	curStart.add(mContent.indexOf(chapTemp.get(i)));//.getBytes("gbk")));
        }
        Log.i("curStart.get ", curStart.toString());
               
        Log.i("chapName.size() ", Integer.toString(chapName.size()));
        if(chapName.size() > 0) {
        	Chapter[] chapters = new Chapter[chapName.size()];
            Log.i("chapters.length ", Integer.toString(chapters.length));
        	for(int i = 0; i < chapName.size(); i++) {
        		chapters[i] = new Chapter(bookId, chapName.get(i), curStart.get(i));
//        		chapters[i].setBookId(bookId);
                Log.i("chapName'Item ", Integer.toString(bookId));
//        		chapters[i].setChaptersName(chapName.get(i));
                Log.i("chapName'Item ", chapName.get(i));
//        		chapters[i].setRate(mContent.indexOf(chapName.get(i)));
                Log.i("chapName'Item ", Integer.toString(curStart.get(i)));
        	}
        	mBookInfo.addCatalogue(chapters);
        }
        return;

    }
}
