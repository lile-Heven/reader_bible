package com.sdattg.vip.search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.sdattg.vip.bean.CategoryBean;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
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

public class MyCategoryDBHelper extends SQLiteOpenHelper {

    private static final int Version_1 = 1;
    private static final String DB_NAME = "lxgj2.db";

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



    public MyCategoryDBHelper(Context context) {
        this(context, DB_NAME, null, Version_1);
    }

    private MyCategoryDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createCategoryTable(String TABLE_NAME) {
        String sql_category = "create table if not exists '" + TABLE_NAME + "' (" + SearchId_category
                + " integer primary key autoincrement," + FileHashName_category + " varchar(255),"
                + InsertOrder + " integer, "
                + Jieshao + " text, "
                + Content + " text, "
                + FileName_category + " varchar(255), " + FilePath_category + " varchar(255), " + ListFilesName_category + " varchar(255))" ;
        getWritableDatabase().execSQL(sql_category);
    }

    public void createAllCategoryTable(String TABLE_NAME) {
        String sql_category = "create table if not exists '" + TABLE_NAME + "' (" + SearchId_category
                + " integer primary key autoincrement," + AllCategoryTableName + " varchar(255))" ;
        getWritableDatabase().execSQL(sql_category);
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

    /**
     * @param key
     * @param value
     */
    private void delete(String TABLE_NAME, String key, String value) {
        String sql = "delete from " + TABLE_NAME + " where " + key + " = " + "'" + value + "'";
        getWritableDatabase().execSQL(sql);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    private Cursor getCursorByKeyValue(String TABLE_NAME, String key, String value) {
        Cursor cursor = getWritableDatabase()
                .rawQuery("select * from '" + TABLE_NAME + "' where " + key + " = " + "'" + value + "'", null);
        return cursor;
    }

    /**
     * @param key
     * @param value
     * @return 是否有数据
     */
    private Boolean IsHasData(String TABLE_NAME, String key, String value) {
        Cursor cursor = getCursorByKeyValue(TABLE_NAME, key, value);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void insertData(String TABLE_NAME, CategoryBean categoryBean) {
        insertData(TABLE_NAME,
                categoryBean.categoryHashName
                , categoryBean.categoryInsertOrder
                , categoryBean.categoryPath
                , categoryBean.categoryName
                , categoryBean.categoryJieshao
                , categoryBean.categoryContent
        );
    }

    public void insertData(String TABLE_NAME, String categoryHashName, int categoryInsertOrder,  String categoryPath, String categoryName, String categoryJieshao, String categoryContent) {
        //Log.d("MyCategoryDBHelper", "into insertData()");
        if (!TextUtils.isEmpty(categoryPath)) {
            if (IsHasData(TABLE_NAME, FileHashName_category, categoryHashName)) {//已经存在了，先删除
                //deleteRecordsByFileHashName(fileHashName);
            }
            String sql = "";
            /*blob
            if(categoryJieshao == null){
                sql = " insert into '" + TABLE_NAME + "' (" + FileHashName_category + "," + InsertOrder + "," + FileName_category + "," + FilePath_category + ") values ('" + categoryHashName
                        + "','" + categoryInsertOrder + "','" + categoryName + "','" + categoryPath + "')";
            }else{
                sql = " insert into '" + TABLE_NAME + "' (" + FileHashName_category + "," + InsertOrder + "," + FileName_category + "," + FilePath_category + "," + Jieshao + ") values ('" + categoryHashName
                        + "','" + categoryInsertOrder + "','" + categoryName + "','" + categoryPath + "','" + categoryJieshao.getBytes() + "')";
            }*/



            sql = " insert into '" + TABLE_NAME + "' (" + FileHashName_category + "," + InsertOrder + "," + FileName_category + "," + FilePath_category + "," + Jieshao + "," + Content + ") values ('" + categoryHashName
                    + "','" + categoryInsertOrder + "','" + categoryName + "','" + categoryPath + "','" + categoryJieshao + "','" + categoryContent +"')";

            getWritableDatabase().execSQL(sql);
            //Log.d("MyCategoryDBHelper", sql);
        }
    }


    /**
     * @param categoryHashName 文件hash值
     * @return 如果没有记录，返回null
     */
    public CategoryBean getRecordByHashName(String TABLE_NAME, String categoryHashName) {
        if (!TextUtils.isEmpty(categoryHashName) && IsHasData(TABLE_NAME, FileHashName_category, categoryHashName)) {
            Cursor cursor = getCursorByKeyValue(TABLE_NAME, FileHashName_category, categoryHashName);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    CategoryBean bean = getCategoryBeanFromCursor(cursor);
                    cursor.close();
                    return bean;
                }
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param jieshao
     * @return 如果userId 为空，不分用户返回的是所有下载的数据,否则返回指定用户的下载记录,不会返回空
     */
    public List<CategoryBean> getCategory(String TABLE_NAME, String jieshao) {
        Log.d("MyCategoryDBHelper", "into getCategory()");
        List<CategoryBean> searchBeans = new ArrayList<>();
        Cursor cursor;
        if (TextUtils.isEmpty(jieshao)) {
            Log.d("MyCategoryDBHelper", "into TextUtils.isEmpty(userId) is true");
            cursor = getWritableDatabase().rawQuery("select * from '" + TABLE_NAME + "'" + "order by InsertOrder asc", null);
            Log.d("MyCategoryDBHelper", "into  TextUtils.isEmpty(userId) is tru cursor != null cursor.getCount():" + cursor.getCount() + "");
        } else {
            Log.d("MyCategoryDBHelper", "into TextUtils.isEmpty(userId) is false");
            cursor = getCursorByKeyValue(TABLE_NAME, InsertOrder, "0");
        }
        if (cursor != null) {
            Log.d("MyCategoryDBHelper", "into cursor != null");
            Log.d("MyCategoryDBHelper", cursor.getCount() + "");
            if (cursor.getCount() > 0) {
                Log.d("MyCategoryDBHelper", cursor.getCount() + "");
                cursor.moveToFirst();
                searchBeans.add(getCategoryBeanFromCursor(cursor));
                cursor.moveToNext();
                while (!cursor.isAfterLast()) {
                    searchBeans.add(getCategoryBeanFromCursor(cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }else{
            Log.d("MyCategoryDBHelper", "into getCategory cursor == null TABLE_NAME:" + TABLE_NAME);
        }
        return searchBeans;

    }

    /**
     * 获取某一个类目的搜索书籍情况，比如：圣经 这个类目
     * @param TABLE_NAME
     * @param args
     * @return
     */
    public HashMap<String, List<String>> getQueryBooks(String TABLE_NAME, String args){
        HashMap<String, List<String>> results = new HashMap<String, List<String>>();
        Cursor cursor;
        if (TextUtils.isEmpty(args)) {
            cursor = getWritableDatabase().rawQuery("select * from '" + TABLE_NAME + "'" + "order by InsertOrder asc", null);
        } else {
            //cursor = getCursorByKeyValue(TABLE_NAME, InsertOrder, "0");
            cursor = null;
        }
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                Log.d("MyCategoryDBHelper", cursor.getCount() + "");
                cursor.moveToFirst();
                results.put(cursor.getString(cursor.getColumnIndex(FileName_category)), new ArrayList<String>());
                cursor.moveToNext();
                while (!cursor.isAfterLast()) {
                    results.put(cursor.getString(cursor.getColumnIndex(FileName_category)), new ArrayList<String>());
                    cursor.moveToNext();
                }
            }
            cursor.close();

            /*Cursor cursor2;
            for (int i = 0; i < results.size(); i++){
                cursor2 = getWritableDatabase().rawQuery("select * from '" + results.keySet() + "'" + "order by InsertOrder asc", null);
            }*/

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

            Cursor cursor2;
            for (String name:
                 keys) {
                Log.d("findbug0715", "name:" + name);
                cursor2 = getWritableDatabase().rawQuery("select * from '" + name + "'" + "order by InsertOrder asc", null);
                if (cursor2 != null) {
                    if (cursor2.getCount() > 0) {
                        List<String> list = results.get(name);
                        Log.d("MyCategoryDBHelper", cursor2.getCount() + "");
                        cursor2.moveToFirst();
                        list.add(cursor2.getString(cursor2.getColumnIndex(FileName_category)));

                        cursor2.moveToNext();
                        while (!cursor2.isAfterLast()) {
                            list.add(cursor2.getString(cursor2.getColumnIndex(FileName_category)));
                            cursor2.moveToNext();
                        }

                        results.put(name, list);
                    }
                    cursor.close();
                }else {
                    Log.d("MyCategoryDBHelper", "into getCategory cursor == null TABLE_NAME:" + name);
                }

            }

            showResults(results);

        }else{
            Log.d("MyCategoryDBHelper", "into getCategory cursor == null TABLE_NAME:" + TABLE_NAME);
        }
        return results;
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
        Cursor cursor = getWritableDatabase().rawQuery("select * from '" + MyCategoryDBHelper.MyAllCategoryTAble + "' ", null);

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

    public void addToAllCategoryTable(String tableName){
        String sql = "";
        sql = " insert into '" + MyCategoryDBHelper.MyAllCategoryTAble + "' (" + AllCategoryTableName + ") values ('" + tableName + "')";
        getWritableDatabase().execSQL(sql);
        getAllTables();
        //Log.d("MyCategoryDB", "into addToAllCategoryTable sql:" + sql);
    }

    /**
     * @param str
     * @return 如果userId 为空，不分用户返回的是所有下载的数据,否则返回指定用户的下载记录,不会返回空
     * String current_sql_sel = "SELECT  * FROM "+tab_name +" where "+tab_field02+" like '%"+str[0]+"%'";
     */
    public List<CategoryBean> getContains(String TABLE_NAME, String column, String str) {
        Log.d("MyCategoryDBHelper", "into getCategory()");
        List<CategoryBean> searchBeans = new ArrayList<>();
        Cursor cursor;
        if (TextUtils.isEmpty(TABLE_NAME)) {
            //Log.d("MyCategoryDBHelper", "into TextUtils.isEmpty(userId) is true");
            //cursor = getWritableDatabase().rawQuery("select * from '" + TABLE_NAME + "'" + "order by InsertOrder asc", null);
            //Log.d("MyCategoryDBHelper", "into  TextUtils.isEmpty(userId) is tru cursor != null cursor.getCount():" + cursor.getCount() + "");
            cursor = getWritableDatabase().rawQuery("select * from all where " + column + " like '%" + str +"%'" + "order by InsertOrder asc", null);
            Log.d("MyCategoryDBHelper", "into getContains TextUtils.isEmpty(str) is true");
        } else {
            Log.d("MyCategoryDBHelper", "into getContains TextUtils.isEmpty(str) is false");
            cursor = getWritableDatabase().rawQuery("select * from '" + TABLE_NAME + "'"  +" where " + column + " like '%" + str +"%'" + "order by InsertOrder asc", null);
            //cursor = getCursorContains(TABLE_NAME, FileHashName_category, str);
        }
        if (cursor != null) {
            //Log.d("MyCategoryDBHelper", "into cursor != null");
            Log.d("MyCategoryDBHelper", "into getContains " + cursor.getCount() + "");
            if (cursor.getCount() > 0) {
                Log.d("MyCategoryDBHelper", cursor.getCount() + "");
                cursor.moveToFirst();
                searchBeans.add(getCategoryBeanFromCursor(cursor));
                cursor.moveToNext();
                while (!cursor.isAfterLast()) {
                    searchBeans.add(getCategoryBeanFromCursor(cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return searchBeans;

    }


    private CategoryBean getCategoryBeanFromCursor(Cursor cursor) {
        CategoryBean bean = new CategoryBean();
        int userIdIndex = cursor.getColumnIndex(FileHashName_category);
        bean.categoryHashName = cursor.getString(userIdIndex);

        int order = cursor.getColumnIndex(InsertOrder);
        bean.categoryInsertOrder = cursor.getInt(order);

        int searchIdIndex = cursor.getColumnIndex(SearchId_category);
        bean.id = cursor.getInt(searchIdIndex);

        int CachePathIndex = cursor.getColumnIndex(FilePath_category);
        bean.categoryPath = cursor.getString(CachePathIndex);

        int fileNameIndex = cursor.getColumnIndex(FileName_category);
        bean.categoryName = cursor.getString(fileNameIndex);

        int jieshao = cursor.getColumnIndex(Jieshao);
        if(cursor.getString(jieshao) != null){
            /*ByteArrayInputStream stream = new ByteArrayInputStream(
                    cursor.getBlob(jieshao));
            bean.categoryJieshao = convertStreamToString(stream);*/
            bean.categoryJieshao = new String(cursor.getString(jieshao));
            Log.d("MyCategoryDB", "into bean.categoryJieshao:" + bean.categoryJieshao);
        }else{
            bean.categoryJieshao = "";
        }

        int content = cursor.getColumnIndex(Content);
        if(cursor.getString(content) != null){
            bean.categoryContent = new String(cursor.getString(content));
            Log.d("MyCategoryDB", "into bean.categoryJieshao:" + bean.categoryContent);
        }else{
            //bean.categoryContent = "";
        }

        return bean;
    }

    public String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder sb = new StringBuilder();

        String line = null;

        try {

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                is.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        return sb.toString();

    }



}
