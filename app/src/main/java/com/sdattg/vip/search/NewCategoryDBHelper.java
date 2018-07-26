package com.sdattg.vip.search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.sdattg.vip.bean.CategoryBean;
import com.sdattg.vip.bean.InitDatas;
import com.sdattg.vip.bean.NewBookBean;
import com.sdattg.vip.bean.NewCategoryBean;
import com.sdattg.vip.bean.NewChapterBean;
import com.sdattg.vip.bean.NewShowChapterBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NewCategoryDBHelper extends SQLiteOpenHelper {

    private static final int Version_1 = 1;
    private static final String DB_NAME = "lxgj07170040.db";

    private final String SearchId_category = "SearchId_category";
    private final String FileHashName_category = "FileHashName_category";
    private final String FilePath_category = "FilePath_category";
    private final String FileName_category = "FileName_category";
    private final String ListFilesName_category = "ListFilesName_category";
    private final String InsertOrder = "InsertOrder";
    private final String Jieshao = "Jieshao";
    private final String Content = "Content";


    private final String AllCategoryTableName = "name";
    public static final String MyAllCategoryTAble = "lile_category";



    public NewCategoryDBHelper(Context context) {
        this(context, DB_NAME, null, Version_1);
    }

    private NewCategoryDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createCategoryTable(String TABLE_NAME) {
        String sql_category = "create table if not exists '" + TABLE_NAME + "' ("
                + SearchId_category + " integer primary key autoincrement,"
                + FileHashName_category + " varchar(255),"
                + InsertOrder + " integer, "
                + Jieshao + " text, "
                + Content + " text, "
                + FileName_category + " varchar(255), " + FilePath_category + " varchar(255), " + ListFilesName_category + " varchar(255))" ;
        getWritableDatabase().execSQL(sql_category);
    }

    public void createNewCategoryBeanTable(String TABLE_NAME, String id, String name, String path, String parentPath) {
        String sql = "create table if not exists '" + TABLE_NAME + "' "
                + "("
                + id + " integer primary key autoincrement,"
                + name + " varchar(255), "
                + path + " varchar(255), "
                + parentPath + " varchar(255)"
                + ")";
        getWritableDatabase().execSQL(sql);
        Log.d("findbug071705", "tableName:" + TABLE_NAME);
    }

    public void createBookBeanTable(String TABLE_NAME, String id, String name, String path, String parentPath, String jieshao){
        String sql = "create table if not exists '" + TABLE_NAME + "' "
                + "("
                + id + " integer primary key autoincrement,"
                + name + " varchar(255), "
                + path + " varchar(255), "
                + parentPath + " varchar(255),"
                + jieshao + " text"
                + ")";
        getWritableDatabase().execSQL(sql);
    }

    public void createBooksTable(String TABLE_NAME, String column_id, String column_path, String column_name){
        String sql = "create table if not exists '" + TABLE_NAME + "' "
                + "("
                + column_id + " integer primary key autoincrement,"
                + column_path + " varchar(255), "
                + column_name + " varchar(255) "
                + ")";
        getWritableDatabase().execSQL(sql);
    }

    public void createChapterBeanTable(String TABLE_NAME, String column_id, String column_paragraphIndex, String column_paragraphContent){
        String sql = "create table if not exists '" + TABLE_NAME + "' "
                + "("
                + column_id + " integer primary key autoincrement,"
                + column_paragraphIndex + " varchar(255), "
                + column_paragraphContent + " text"
                + ")";
        try{
            getWritableDatabase().execSQL(sql);
        }catch (Exception e){
            //SQLiteException: near "s": syntax error (Sqlite code 1): , while compiling: create table if not exists '一天的路程(Day's Journey).txt' (id integer primary key autoincrement,paragraphIndex varchar(255), paragraphparagraphContent text), (OS error - 2:No such file or directory)
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据表
     */
    public void closeTable() {
        close();
    }

    /**
     * 删除数据表
     */
    public void deleteTable(String TABLE_NAME) {
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + "'" + TABLE_NAME + "'");
    }

    public void delete(String TABLE_NAME, String colomn, String path) {
        String sql = "delete from '" + TABLE_NAME + "' where " + colomn + " = " + "'" + path + "'";
        getWritableDatabase().execSQL(sql);
    }

    private Cursor getCursorByKeyValue(String TABLE_NAME, String column, String path) {
        Cursor cursor = null;
        try{
            cursor = getWritableDatabase()
                    .rawQuery("select * from '" + TABLE_NAME + "' where " + column + " = " + "'" + path + "'", null);
        }catch (Exception e){
            e.printStackTrace();
        }

        return cursor;
    }

    /*public void insertData(String TABLE_NAME, String bookPath, String book_name) {
        if (IsHasData(TABLE_NAME, InitDatas.column_path, bookPath)) {//已经存在了，先删除
            delete(TABLE_NAME, InitDatas.column_path, bookPath);
        }
        String sql = " insert into '" + TABLE_NAME + "' (" + InitDatas.column_path + "," + InitDatas.column_name + ") values ('" + book_name + "')";
        getWritableDatabase().execSQL(sql);
    }*/

    public void insertData(String TABLE_NAME, NewChapterBean bean) {
        insertData(TABLE_NAME
                , bean.paragraphIndex
                , bean.paragraphContent
        );
    }

    private void insertData(String TABLE_NAME, String paragraphIndex, String paragraphContent) {

        String sql = " insert into '" + TABLE_NAME + "' (" + InitDatas.column_paragraphIndex + "," + InitDatas.column_paragraphContent + ") values ('" + paragraphIndex
                + "','" + paragraphContent + "')";
        try{
            getWritableDatabase().execSQL(sql);
        }catch (Exception e){
            sql = " insert into '" + TABLE_NAME + "' (" + InitDatas.column_paragraphIndex + "," + InitDatas.column_paragraphContent + ") values ('" + paragraphIndex
                    + "','" + paragraphContent.replace("'", "_") + "')";
            getWritableDatabase().execSQL(sql);
        }
    }

    public void insertData(String TABLE_NAME, NewBookBean bean) {
        insertData(TABLE_NAME
                , bean.chapter
                , bean.path
                , bean.parentPath
                , bean.jieshao
        );
    }

    private void insertData(String TABLE_NAME, String chapter, String path,  String parentPath, String jieshao) {
        if (IsHasData(TABLE_NAME, InitDatas.column_path, path)) {//已经存在了，先删除
            delete(TABLE_NAME, InitDatas.column_path, path);
        }
        String sql = " insert into '" + TABLE_NAME + "' (" + InitDatas.column_chapter + "," + InitDatas.column_path + "," + InitDatas.column_parentPath + "," +  InitDatas.column_jieshao + ") values ('" + chapter
                + "','" + path + "','" + parentPath + "','" + jieshao + "')";
        try{
            getWritableDatabase().execSQL(sql);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void insertData(String TABLE_NAME, NewCategoryBean bean) {
        insertData(TABLE_NAME
                , bean.name
                , bean.path
                , bean.parentPath
        );
    }

    private void insertData(String TABLE_NAME, String name, String path,  String parentPath) {
        if (IsHasData(TABLE_NAME, InitDatas.column_path, path)) {//已经存在了，先删除
            delete(TABLE_NAME, InitDatas.column_path, path);
        }
        String sql = " insert into '" + TABLE_NAME + "' (" + InitDatas.column_name + "," + InitDatas.column_path + "," + InitDatas.column_parentPath + ") values ('" + name
                + "','" + path + "','" + parentPath + "')";
        getWritableDatabase().execSQL(sql);
    }

    public Boolean IsHasData(String TABLE_NAME, String column, String path) {
        Cursor cursor = getCursorByKeyValue(TABLE_NAME, column, path);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public List<String> getBooks(){
        List<String> books = new ArrayList<>();
        Cursor cursor = getWritableDatabase().rawQuery("select * from '" + InitDatas.table_books + "'", null);
        if (cursor != null) {
            Log.d("NewCategoryDB", "into getBooks()" + cursor.getCount() + "");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                Log.d("NewCategoryDB", "into getBooks() cursor.getString(cursor.getColumnIndex(InitDatas.column_name)):" + cursor.getString(cursor.getColumnIndex(InitDatas.column_name)) );
                books.add(cursor.getString(cursor.getColumnIndex(InitDatas.column_name)));
                cursor.moveToNext();
                while (!cursor.isAfterLast()) {
                    Log.d("NewCategoryDB", "into getBooks() cursor.getString(cursor.getColumnIndex(InitDatas.column_name)):" + cursor.getString(cursor.getColumnIndex(InitDatas.column_name)) );
                    books.add(cursor.getString(cursor.getColumnIndex(InitDatas.column_name)));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }else{
            Log.d("NewCategoryDB", "into getCategory cursor == null ");
        }
        Log.d("NewCategoryDB", "into getBooks() books.size():" + books.size() );
        return books;
    }


    public String getJieShao(String name){
        Cursor cursor = getWritableDatabase()
                .rawQuery("select * from '" + name + "' where " + InitDatas.column_chapter + " = " + "'" + InitDatas.column_jieshao + "'", null);
        if (cursor != null) {
            Log.d("MyCategoryDBHelper", cursor.getCount() + "");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getString(cursor.getColumnIndex(InitDatas.column_jieshao));
            }
            cursor.close();
        }
        return null;
    }

    public List<NewCategoryBean> queryCategory(String category){
        List<NewCategoryBean> searchBeans = new ArrayList<>();
        Cursor cursor;
        if (TextUtils.isEmpty(category)) {
            cursor = null;
            Log.w("findbug", "into queryCategory() TextUtils.isEmpty(category)");
        } else {
            cursor = getWritableDatabase().rawQuery("select * from '" + category + "'", null);
        }
        if (cursor != null) {
            Log.d("MyCategoryDBHelper", cursor.getCount() + "");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                searchBeans.add(getNewCategoryBeanFromCursor(cursor));
                cursor.moveToNext();
                while (!cursor.isAfterLast()) {
                    searchBeans.add(getNewCategoryBeanFromCursor(cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }else{

        }
        return searchBeans;
    }

    private NewCategoryBean getNewCategoryBeanFromCursor(Cursor cursor) {
        NewCategoryBean bean = new NewCategoryBean();
        int name = cursor.getColumnIndex(InitDatas.column_name);
        bean.name = cursor.getString(name);

        int path = cursor.getColumnIndex(InitDatas.column_path);
        bean.path = cursor.getString(path);

        int parentPath = cursor.getColumnIndex(InitDatas.column_parentPath);
        bean.parentPath = cursor.getString(parentPath);

        return bean;
    }

    private void showResults(HashMap<String, List<String>> results){
        Set<String> keySet = results.keySet();
        Iterator iterator = keySet.iterator();
        String[] keys = new String[keySet.size()];
        int ii = 0;
        while (iterator.hasNext()){
            String temp = (String)iterator.next();
            Log.d("findbug0715", "temp:" + temp);
            keys[ii] = temp;
            ii++;
        }

        List fileList = Arrays.asList(keys);
        Collections.sort(fileList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        for (String category:
        keys) {
            Log.d("findbug0715", "showResults category:" + category);
            for (String bookName:
            results.get(category)) {
                Log.d("findbug0715", "showResults bookName:" + bookName);
            }
        }
    }

    public List<String> getAllTables(){
        List<String> allTables = new ArrayList<String>();
        Cursor cursor = getWritableDatabase().rawQuery("select * from '" + NewCategoryDBHelper.MyAllCategoryTAble + "' ", null);

        if (cursor != null) {
            //Log.d("MyCategoryDBHelper", "into cursor != null");
            Log.d("MyCategoryDBHelper", "into getAllTables " + cursor.getCount() + "");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                allTables.add(cursor.getString(cursor.getColumnIndex(AllCategoryTableName)));
                cursor.moveToNext();
                while (!cursor.isAfterLast()) {
                    allTables.add(cursor.getString(cursor.getColumnIndex(AllCategoryTableName)));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }else{
            Log.d("MyCategoryDBHelper", "into getAllTables cursor is null");
        }
        //Log.d("MyCategoryDB", "into getAllTables() count:" + allTables.size());
        return allTables;
    }


    public List<String> queryBooks(String like_content){
        Log.d("findbug0717", "into DBHelper queryBooks()");
        List<String> books = new ArrayList<>();
        Cursor cursor = getWritableDatabase().rawQuery("select * from '" + InitDatas.table_books + "' where " + InitDatas.column_name + " like '%" + like_content + "%'", null);
        if (cursor != null) {
            Log.d("findbug0717", "into getBooks()" + cursor.getCount() + "");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                books.add(cursor.getString(cursor.getColumnIndex(InitDatas.column_name)));
                cursor.moveToNext();
                while (!cursor.isAfterLast()) {
                    books.add(cursor.getString(cursor.getColumnIndex(InitDatas.column_name)));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }else{
            Log.d("findbug0717", "into getCategory cursor == null ");
        }
        Log.d("findbug0717", "books.size():" + books.size());
        return books;
    }


    public List<NewBookBean> queryIndexs(String like_content){
        List<NewBookBean> results = new ArrayList<NewBookBean>();

        List<String> all_books = getBooks();
        for (String one_book:
             all_books) {
            List<NewBookBean> bookBeans = queryOneBookIndexs(one_book, like_content);
            for (NewBookBean one:
                 bookBeans) {
                results.add(one);
            }
        }

        return results;
    }

    public List<NewBookBean> queryOneBookIndexs(String one_book, String like_content){
        List<NewBookBean> books = new ArrayList<>();
        Cursor cursor = getWritableDatabase().rawQuery("select * from '" + one_book + "' where " + InitDatas.column_chapter + " like '%" + like_content +"%'", null);
        if (cursor != null) {
            Log.d("NewCategoryDB", "into getBooks()" + cursor.getCount() + "");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                books.add(getNewBookBeanFromCursor(cursor));
                cursor.moveToNext();
                while (!cursor.isAfterLast()) {
                    books.add(getNewBookBeanFromCursor(cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }else{
            Log.d("NewCategoryDB", "into getCategory cursor == null ");
        }
        Log.d("NewCategoryDB", "books.size():" + books.size());
        return books;
    }

    private NewBookBean getNewBookBeanFromCursor(Cursor cursor){
        NewBookBean bean = new NewBookBean();
        bean.chapter = cursor.getString(cursor.getColumnIndex(InitDatas.column_chapter));
        bean.parentPath = cursor.getString(cursor.getColumnIndex(InitDatas.column_parentPath));
        bean.path = cursor.getString(cursor.getColumnIndex(InitDatas.column_path));
        return bean;
    }

    /*public List<NewChapterBean> queryTitles(String like_content){
        List<NewChapterBean> all_chapters = new ArrayList<>();
        List<String> all_books = getBooks();
        for (String one_book:
                all_books) {
            List<NewChapterBean> one_book_chapters = queryOneBookChapters(one_book, like_content);
            for (NewChapterBean chapterBean:
                    one_book_chapters) {
                all_chapters.add(chapterBean);
            }
        }
        return all_chapters;
    }*/

    public List<NewShowChapterBean> queryContents(String like_content){
        List<NewShowChapterBean> all_chapters = new ArrayList<>();
        List<String> all_books = getBooks();
        for (String one_book:
                all_books) {
            List<NewShowChapterBean> one_book_chapters = queryOneBookChapters(one_book, like_content);
            for (NewShowChapterBean chapterBean:
                    one_book_chapters) {
                all_chapters.add(chapterBean);
            }
        }
        return all_chapters;
    }

    public List<NewShowChapterBean> queryOneBookChapters(String one_book, String like_content){
        List<NewShowChapterBean> one_book_chapters = new ArrayList<>();
        Cursor cursor = getWritableDatabase().rawQuery("select * from '" + one_book + "' ", null);
        List<NewBookBean> bookBeans = getChapters(cursor);
        for (NewBookBean oneBookBean:
        bookBeans) {
            List<NewShowChapterBean> chapters = queryChapter(oneBookBean.chapter, like_content);
            for (NewShowChapterBean chapterBean:
                 chapters) {
                one_book_chapters.add(chapterBean);
            }
        }
        Log.d("NewCategoryDB", "books.size():" + one_book_chapters.size());
        return one_book_chapters;
    }

    private List<NewBookBean> getChapters(Cursor cursor){
        List<NewBookBean> bookBeans = new ArrayList<>();
        if (cursor != null) {
            Log.d("NewCategoryDB", "into getBooks()" + cursor.getCount() + "");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                bookBeans.add(getNewBookBeanFromCursor(cursor));
                cursor.moveToNext();
                while (!cursor.isAfterLast()) {
                    bookBeans.add(getNewBookBeanFromCursor(cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }else{
            Log.d("NewCategoryDB", "into getCategory cursor == null ");
        }
        return bookBeans;
    }

    public List<NewShowChapterBean> queryChapter(String chapter, String like_content){
        List<NewShowChapterBean> chapters = new ArrayList<>();
        if(chapter.equals("jieshao") || chapter.equals("520520")){
            return chapters;
        }
        Cursor cursor = null;
        if(like_content.startsWith(SearchingThread.searchTitleFlag)){
            //want to search titles-chapter
            like_content = (like_content.split(SearchingThread.searchTitleFlag))[1];
            Log.d("findbug071723", "like_content:" + like_content);
            cursor = getWritableDatabase().rawQuery("select * from '" + chapter + "' where " + InitDatas.column_paragraphContent + " like '%b%" + like_content +"%'", null);
        }else {
            cursor = getWritableDatabase().rawQuery("select * from '" + chapter + "' where " + InitDatas.column_paragraphContent + " like '%" + like_content +"%'", null);
        }

        if (cursor != null) {
            Log.d("NewCategoryDB", "into getBooks()" + cursor.getCount() + "");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                chapters.add(getNewChapterBeanFromCursor(chapter, cursor));
                cursor.moveToNext();
                while (!cursor.isAfterLast()) {
                    chapters.add(getNewChapterBeanFromCursor(chapter, cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }else{
            Log.d("NewCategoryDB", "into getCategory cursor == null ");
        }
        Log.d("NewCategoryDB", "chapters.size():" + chapters.size());
        return chapters;
    }

    private NewShowChapterBean getNewChapterBeanFromCursor(String chapter, Cursor cursor){
        NewShowChapterBean bean = new NewShowChapterBean();
        bean.chapterName = chapter;
        bean.paragraphIndex = cursor.getString(cursor.getColumnIndex(InitDatas.column_paragraphIndex));
        bean.paragraphContent = cursor.getString(cursor.getColumnIndex(InitDatas.column_paragraphContent));
        return bean;
    }
}
