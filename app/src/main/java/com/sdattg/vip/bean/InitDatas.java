package com.sdattg.vip.bean;

import android.database.Cursor;
import android.util.Log;

import com.sdattg.vip.search.MyCategoryDBHelper;
import com.sdattg.vip.search.NewCategoryDBHelper;
import com.sdattg.vip.search.UpdatingBooksThread;
import com.sdattg.vip.search.UpdatingProgressThread;
import com.sdattg.vip.util.FileUtil;
import com.sdattg.vip.util.SharePreferencesUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InitDatas {
    private String unzip = "unzip";
    public static String column_id = "id";
    public static String column_name = "name";
    public static String column_path = "path";
    public static String column_parentPath = "parentPath";


    public static String column_jieshao = "jieshao";
    public static String column_jieshao2 = "jianjie";
    public static String column_chapter = "chapter";

    public static String column_paragraphIndex = "paragraphIndex";
    public static String column_paragraphContent = "paragraphparagraphContent";

    public static String table_books = "books";

    public static boolean onlyUpdateJieShao = true;
    public static boolean hasSQliteDatasInitOnce = false;

    public static int hasNotDone = 0;
    public static boolean hasAllDone = false;


    public boolean init(NewCategoryDBHelper DBhalper, String rootPath, String tableName) {

        if (hasSQliteDatasInitOnce) {
            initFromBooks(DBhalper);
        } else {
            DBhalper.deleteTable(table_books);
            DBhalper.createBooksTable(table_books, column_id, column_path, column_name);
            initNewCategoryBean(DBhalper, rootPath, tableName);
        }
        DBhalper.close();
        return true;
    }

    public void initNewCategoryBean(NewCategoryDBHelper DBhalper, String rootPath, String tableName) {
        Log.d("findbug0717", "into initNewCategoryBean()");


        FileUtil fileUtil = new FileUtil();
        if (fileUtil.isPathAvailable(rootPath)) {


            File[] files = new File(rootPath).listFiles();
            files = FileUtil.orderByName(files);
            if (files[0] != null && files[0].getName().endsWith(".txt")) {

                //此时已经进入到书籍目录
                initOneBook(DBhalper, rootPath, tableName);
            } else {
                DBhalper.deleteTable(tableName);
                DBhalper.createNewCategoryBeanTable(tableName,
                        column_id,
                        column_name,
                        column_path,
                        column_parentPath);
                for (File file :
                        files) {

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

    public static int bookCount = 0;
    public void initFromBooks(NewCategoryDBHelper DBhalper) {
        Log.d("findbug071717", "into 670");

        if(hasAllDone){
            Log.d("findbug071717", "into 671");
            return;
        }
        if (onlyUpdateJieShao) {
            Log.d("findbug071717", "into 672");
            return;
        }
        Log.d("findbug071717", "into 673");
        List<String> books_list = new ArrayList<String>();
        Cursor cursor = DBhalper.getWritableDatabase().rawQuery("select * from '" + table_books + "'", null);
        //Log.d("findbug0717", "into hasJieShao");
        if (cursor != null) {
            Log.d("findbug071717", "into initFromBooks cursor.getCount():" + cursor.getCount());
            if (cursor.getCount() > 0) {
                UpdatingProgressThread.booksCount = cursor.getCount();
                cursor.moveToFirst();
                String name = cursor.getString(cursor.getColumnIndex(InitDatas.column_name));
                String path = cursor.getString(cursor.getColumnIndex(InitDatas.column_path));
                Log.d("findbug071717", "into 674");
                initOneBook(DBhalper, path, name);
                bookCount ++;
                UpdatingProgressThread.progress = bookCount;
                Log.d("findbug071719", "progress:" + UpdatingProgressThread.progress);
                cursor.moveToNext();
                while (!cursor.isAfterLast()) {
                    Log.d("findbug071717", "into 674");
                    String name2 = cursor.getString(cursor.getColumnIndex(InitDatas.column_name));
                    String path2 = cursor.getString(cursor.getColumnIndex(InitDatas.column_path));
                    initOneBook(DBhalper, path2, name2);
                    bookCount ++;
                    UpdatingProgressThread.progress = bookCount;
                    Log.d("findbug071719", "progress:" + UpdatingProgressThread.progress);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }

    }

    public void initOneBook(NewCategoryDBHelper DBhalper, String bookPath, String bookName) {
        Log.d("findbug071717", "into initOneBook()");;
        addToBooksTable(DBhalper, bookPath, bookName);

        Log.d("findbug071718", "into initOneBook() hasNotDone:" + hasNotDone);
        FileUtil fileUtil = new FileUtil();
        if (fileUtil.isPathAvailable(bookPath)) {
            if (isBookDone(DBhalper, bookPath, bookName)) {
                return;
            }
            hasNotDone++;
            File[] files = null;

            if (!hasJieShao(DBhalper, bookPath, bookName)) {
                files = new File(bookPath).listFiles();
                files = FileUtil.orderByName(files);
                for (File file :
                        files) {
                    if (file.getName().contains(column_jieshao) || file.getName().contains(column_jieshao2)) {
                        String jieshao = getJieShao(file);
                        NewBookBean bean_jieshao = new NewBookBean();
                        bean_jieshao.chapter = column_jieshao;
                        bean_jieshao.path = column_jieshao;
                        bean_jieshao.parentPath = column_jieshao;
                        bean_jieshao.jieshao = jieshao;
                        DBhalper.insertData(bookName, bean_jieshao);
                        hasJieShao(DBhalper, bookPath, bookName);
                        Log.d("findbug0717", "DBhalper.insertData(bookName, bean_jieshao) bookName:" + bookName);
                    }
                }
            }


            if (onlyUpdateJieShao) {
                return;
            }
            //DBhalper.deleteTable(bookName);
            DBhalper.createBookBeanTable(bookName,
                    column_id,
                    column_chapter,
                    column_path,
                    column_parentPath,
                    column_jieshao);

            files = new File(bookPath).listFiles();
            files = FileUtil.orderByName(files);

            for (File file :
                    files) {
                NewBookBean bean = new NewBookBean();
                bean.chapter = FileUtil.replaceBy_(file.getName());
                bean.path = file.getAbsolutePath();
                bean.parentPath = new File(bookPath).getAbsolutePath();
                bean.jieshao = "";
                DBhalper.insertData(bookName, bean);

                initOneChapter(DBhalper, bean.path, bean.chapter);
            }

            NewBookBean bean = new NewBookBean();
            bean.chapter = "520520";
            bean.path = "520520";
            bean.parentPath = "520520";
            bean.jieshao = "520520";
            DBhalper.insertData(bookName, bean);


        }
    }

    private void initOneChapter(NewCategoryDBHelper DBhalper, String chapterPath, String chapterName) {
        FileUtil fileUtil = new FileUtil();
        if (fileUtil.isPathAvailable(chapterPath)) {
            if (isChapterDone(DBhalper, chapterPath, chapterName)) {
                return;
            }
            DBhalper.deleteTable(chapterName);
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
            if (chaptersMap != null) {
                if (chaptersMap.size() > 0) {
                    NewChapterBean bean = new NewChapterBean();
                    bean.paragraphIndex = chaptersMap.size() + "";
                    bean.paragraphContent = "#count#";
                    DBhalper.insertData(chapterName, bean);
                }
                for (int i = 1; i < chaptersMap.size(); i++) {
                    String paragraphContent = chaptersMap.get(i);

                    NewChapterBean bean = new NewChapterBean();
                    bean.paragraphIndex = i + "";
                    bean.paragraphContent = paragraphContent;
                    DBhalper.insertData(chapterName, bean);
                }

                NewChapterBean bean = new NewChapterBean();
                bean.paragraphIndex = 520520 + "";
                bean.paragraphContent = "520520";
                DBhalper.insertData(chapterName, bean);
            }

        }


    }

    public HashMap<Integer, String> getChapters(File file) {
        Log.d("findbug0717", "into getChapters:" + file);
        if (!file.exists()) {
            Log.w("findbug0717", "into getChapters !file.exists()");
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
        //Log.d("GuideActivity", "has jieshao:" + jieshao + " jieshao.length():" + jieshao.length());
        return jieshao;
    }

    private void addToBooksTable(NewCategoryDBHelper DBhalper, String bookPath, String bookName) {
        if (DBhalper.IsHasData(table_books, InitDatas.column_path, bookPath)) {//已经存在了，先删除
            DBhalper.delete(table_books, InitDatas.column_path, bookPath);
        }
        String sql = " insert into '" + table_books + "' (" + InitDatas.column_path + "," + InitDatas.column_name + ") values ('" + bookPath + "', '" + bookName + "')";
        DBhalper.getWritableDatabase().execSQL(sql);
        Log.d("findbug0717", "addToBooksTable sql:" + sql);
        DBhalper.getBooks();
    }


    private boolean isChapterDone(NewCategoryDBHelper DBhalper, String chapterPath, String chapterName) {
        DBhalper.createChapterBeanTable(chapterName,
                column_id,
                column_paragraphIndex,
                column_paragraphContent);

        Cursor cursor = DBhalper.getWritableDatabase().rawQuery("select * from '" + chapterName + "' where " + InitDatas.column_paragraphIndex + " = " + "'" + 520520 + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                //String done = cursor.getString(cursor.getColumnIndex(InitDatas.column_paragraphIndex));
                return true;
            }
        }
        return false;
    }

    private boolean isBookDone(NewCategoryDBHelper DBhalper, String bookPath, String bookName) {
        DBhalper.createBookBeanTable(bookName,
                column_id,
                column_chapter,
                column_path,
                column_parentPath,
                column_jieshao);

        Cursor cursor = DBhalper.getWritableDatabase().rawQuery("select * from '" + bookName + "' where " + InitDatas.column_chapter + " = " + "'" + 520520 + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                //String done = cursor.getString(cursor.getColumnIndex(InitDatas.column_paragraphIndex));
                return true;
            }
        }
        return false;
    }

    private boolean hasJieShao(NewCategoryDBHelper DBhalper, String bookPath, String bookName) {
        DBhalper.createBookBeanTable(bookName,
                column_id,
                column_chapter,
                column_path,
                column_parentPath,
                column_jieshao);

        Cursor cursor = DBhalper.getWritableDatabase().rawQuery("select * from '" + bookName + "' where " + InitDatas.column_chapter + " = " + "'" + column_jieshao + "'", null);
        //Log.d("findbug0717", "into hasJieShao");
        if (cursor != null) {
            //Log.d("findbug0717", "into hasJieShao cursor.getCount():" + cursor.getCount() + ", bookName:" + bookName);
            if (cursor.getCount() > 0) {
                //String done = cursor.getString(cursor.getColumnIndex(InitDatas.column_paragraphIndex));
                return true;
            }
        }
        return false;
    }

}
