package com.sdattg.vip.bean;

import android.util.Log;

import com.sdattg.vip.search.MyCategoryDBHelper;
import com.sdattg.vip.search.NewCategoryDBHelper;
import com.sdattg.vip.util.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class InitDatas {
    private String unzip = "unzip";
    public static String column_id = "id";
    public static String column_name = "name";
    public static String column_path = "path";
    public static String column_parentPath = "parentPath";


    public static String column_jieshao = "jieshao";
    public static String column_chapter = "chapter";

    public static String column_paragraphIndex = "paragraphIndex";
    public static String column_paragraphContent = "paragraphparagraphContent";

    public static String table_books = "books";

    public boolean init(NewCategoryDBHelper DBhalper, String rootPath, String tableName){
        initNewCategoryBean(DBhalper, rootPath, tableName);
        return true;
    }

    public void initNewCategoryBean(NewCategoryDBHelper DBhalper, String rootPath, String tableName) {
        FileUtil fileUtil = new FileUtil();
        if (fileUtil.isPathAvailable(rootPath)) {
            File[] files = new File(rootPath).listFiles();

            if (files[0] != null && files[0].getName().endsWith(".txt")) {
                //此时已经进入到书籍目录
                initOneBook(DBhalper, rootPath, tableName);
            } else {
                for (File file :
                        files) {
                    DBhalper.createNewCategoryBeanTable(tableName,
                            column_id,
                            column_name,
                            column_path,
                            column_parentPath);

                    NewCategoryBean bean = new NewCategoryBean();
                    bean.name = FileUtil.replaceBy_(file.getName());
                    bean.path = file.getAbsolutePath();
                    bean.parentPath = new File(rootPath).getAbsolutePath();

                    DBhalper.insertData(tableName, bean);

                    initNewCategoryBean(DBhalper, file.getAbsolutePath(), bean.name);
                }
            }
        }
    }

    private void initOneBook(NewCategoryDBHelper DBhalper, String bookPath, String bookName) {
        FileUtil fileUtil = new FileUtil();
        if (fileUtil.isPathAvailable(bookPath)) {
            DBhalper.createBookBeanTable(bookName,
                    column_id,
                    column_chapter,
                    column_path,
                    column_parentPath,
                    column_jieshao);

            File[] files = new File(bookPath).listFiles();
            String jieshao = "";
            for (File file :
                    files) {
                if (file.getName().contains("jieshao")) {
                    jieshao = getJieShao(file);

                }
            }

            NewBookBean bean_jieshao = new NewBookBean();
            bean_jieshao.chapter = "jieshao";
            bean_jieshao.path = "jieshao";
            bean_jieshao.parentPath = "jieshao";
            bean_jieshao.jieshao = jieshao;
            DBhalper.insertData(bookName, bean_jieshao);


            jieshao = "";
            for (File file :
                    files) {
                NewBookBean bean = new NewBookBean();
                bean.chapter = FileUtil.replaceBy_(file.getName());
                bean.path = file.getAbsolutePath();
                bean.parentPath = new File(bookPath).getAbsolutePath();
                bean.jieshao = jieshao;
                DBhalper.insertData(bookName, bean);

                initOneChapter(DBhalper, bean.path, bean.chapter);
            }
        }

        addToBooksTable(DBhalper, bookName);
    }

    private void initOneChapter(NewCategoryDBHelper DBhalper, String chapterPath, String chapterName) {
        FileUtil fileUtil = new FileUtil();
        if (fileUtil.isPathAvailable(chapterPath)) {
            DBhalper.createChapterBeanTable(chapterName,
                    column_id,
                    column_paragraphIndex,
                    column_paragraphContent);

            /*File[] files = new File(chapterPath).listFiles();
            for (File file :
                    files) {
                NewBookBean bean = new NewBookBean();
                bean.chapter = FileUtil.replaceBy_(file.getName());
                bean.path = file.getAbsolutePath();
                bean.parentPath = new File(bookPath).getAbsolutePath();
                bean.jieshao = jieshao;
                DBhalper.insertData(tableName, bean);

                initOneChapter(DBhalper, bean.path, bean.chapter);
            }*/
            HashMap<Integer, String> chaptersMap = getChapters(new File(chapterPath));
            if(chaptersMap != null){
                if(chaptersMap.size() > 0){
                    NewChapterBean bean = new NewChapterBean();
                    bean.paragraphIndex = chaptersMap.size() + "";
                    bean.paragraphContent = "#count#";
                    DBhalper.insertData(chapterName, bean);
                }
                for (int i = 1; i < chaptersMap.size(); i++){
                    String paragraphContent = chaptersMap.get(i);

                    NewChapterBean bean = new NewChapterBean();
                    bean.paragraphIndex = i + "";
                    bean.paragraphContent = paragraphContent;
                    DBhalper.insertData(chapterName, bean);
                }
            }

        }


    }

    public HashMap<Integer, String> getChapters(File file) {
        Log.d("fingbug0717", "into getChapters:" + file);
        if(!file.exists()){
            Log.w("fingbug0717", "into getChapters !file.exists()");
            return null;
        }
        HashMap<Integer, String> chaptersMap = new HashMap<Integer, String>();

        StringBuilder result = new StringBuilder("");
        String temp = "";
        byte[] bytes = new byte[1024];
        int paragraphIndex = 0;
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferReader = null;
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferReader = new BufferedReader(inputStreamReader);
            //new File(tempFiles[0].getAbsolutePath(), "utf-8");
            //ReaderInputStream reader = new ReaderInputStream(new FileReader(tempFiles[0]), "gbk");
                /*if ((count = fileInputStream.read(bytes)) != -1) {
                    temp += new String(bytes, 0, count, "gbk");
                }*/
            while ((temp = bufferReader.readLine()) != null) {
                paragraphIndex++;
                Log.d("fingbug0717", "temp:" + temp);
                chaptersMap.put(paragraphIndex, temp);
                result.append(temp); //findbug
                //result += temp;
            }
            Log.d("GuideActivity", "has categoryContent result:" + result.toString());//findbug
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (inputStreamReader != null) {
            try {
                inputStreamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (bufferReader != null) {
            try {
                bufferReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return chaptersMap;
    }

    private String getJieShao(File file) {
        String jieshao = "";
        byte[] bytes = new byte[1024];
        int count = 0;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            while ((count = fileInputStream.read(bytes)) != -1) {
                jieshao += new String(bytes, 0, count, "gbk");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("GuideActivity", "has jieshao:" + jieshao + " jieshao.length():" + jieshao.length());
        return jieshao;
    }

    private void addToBooksTable(NewCategoryDBHelper DBhalper, String bookName){
        DBhalper.createBooksTable(table_books, column_id, column_name);
        DBhalper.insertData(table_books, column_name);

        DBhalper.close();
    }

}
