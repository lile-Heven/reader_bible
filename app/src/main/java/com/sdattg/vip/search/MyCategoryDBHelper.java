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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MyCategoryDBHelper extends SQLiteOpenHelper {

    private static final int Version_1 = 1;
    private static final String DB_NAME = "lxgj.db";

    private final String SearchId_category = "SearchId_category";
    private final String FileHashName_category = "FileHashName_category";
    private final String FilePath_category = "FilePath_category";
    private final String FileName_category = "FileName_category";
    private final String ListFilesName_category = "ListFilesName_category";
    private final String InsertOrder = "InsertOrder";
    private final String Jieshao = "Jieshao";



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
                + Jieshao + " blob, "
                + FileName_category + " varchar(255), " + FilePath_category + " varchar(255), " + ListFilesName_category + " varchar(255))" ;
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
                , categoryBean.categoryJieshao);
    }

    public void insertData(String TABLE_NAME, String categoryHashName, int categoryInsertOrder,  String categoryPath, String categoryName, String categoryJieshao) {
        Log.d("MyCategoryDBHelper", "into insertData()");
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



            sql = " insert into '" + TABLE_NAME + "' (" + FileHashName_category + "," + InsertOrder + "," + FileName_category + "," + FilePath_category + "," + Jieshao + ") values ('" + categoryHashName
                    + "','" + categoryInsertOrder + "','" + categoryName + "','" + categoryPath + "','" + categoryJieshao + "')";

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
     * @param str
     * @return 如果userId 为空，不分用户返回的是所有下载的数据,否则返回指定用户的下载记录,不会返回空
     * String current_sql_sel = "SELECT  * FROM "+tab_name +" where "+tab_field02+" like '%"+str[0]+"%'";
     */
    public List<CategoryBean> getContains(String TABLE_NAME, String column, String str) {
        Log.d("MyCategoryDBHelper", "into getCategory()");
        List<CategoryBean> searchBeans = new ArrayList<>();
        Cursor cursor;
        if (TextUtils.isEmpty(str)) {
            //Log.d("MyCategoryDBHelper", "into TextUtils.isEmpty(userId) is true");
            //cursor = getWritableDatabase().rawQuery("select * from '" + TABLE_NAME + "'" + "order by InsertOrder asc", null);
            //Log.d("MyCategoryDBHelper", "into  TextUtils.isEmpty(userId) is tru cursor != null cursor.getCount():" + cursor.getCount() + "");
            cursor = null;
        } else {
            Log.d("MyCategoryDBHelper", "into getContains TextUtils.isEmpty(userId) is false");
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
        if(cursor.getBlob(jieshao) != null){
            /*ByteArrayInputStream stream = new ByteArrayInputStream(
                    cursor.getBlob(jieshao));
            bean.categoryJieshao = convertStreamToString(stream);*/
            bean.categoryJieshao = new String(cursor.getString(jieshao));
            Log.d("MyCategoryDB", "into bean.categoryJieshao:" + bean.categoryJieshao);
        }else{
            bean.categoryJieshao = "";
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
