package com.sdattg.vip.search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.sdattg.vip.R;

import java.util.ArrayList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final int Version_1 = 1;
    private static final String DB_NAME = "lxgjbooks";

    private String TABLE_NAME = "FileReadRecord";
    private final String FileHashName = "fileHashName";
    private final String SearchId = "searchId";
    private final String FilePath = "filePath";
    private final String FileName = "fileName";
    private final String ParagraphIndex = "paragraphIndex";
    private final String ChartIndex = "chartIndex";

    private String BOOKS_INDEX_NAME = "BooksIndex";
    private final String SearchId_books = "searchId_books";
    private final String FileHashName_books = "fileHashName_books";
    private final String FilePath_books = "filePath_books";
    private final String FileCategory = "FileCategory";

    private String sql = "create table if not exists " + TABLE_NAME + " (" + SearchId
            + " integer primary key autoincrement," + FileHashName + " varchar(50),"
            + FilePath + " varchar(100), " + FileName + " varchar(100),"
            + ParagraphIndex + " integer, " + ChartIndex + " integer)";

    private String sql_category = "create table if not exists " + BOOKS_INDEX_NAME + " (" + SearchId_books
            + " integer primary key autoincrement," + FileHashName_books + " varchar(50),"
            + FileCategory + " varchar(100), " + FilePath_books + " varchar(100))";

    public MyDBHelper(Context context) {
        this(context, DB_NAME, null, Version_1);
    }

    private MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createTable() {
        getWritableDatabase().execSQL(sql);
    }

    public void createCategoryTable() {
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
    public void deleteTable() {
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    /**
     * @param key
     * @param value
     */
    private void delete(String key, String value) {
        String sql = "delete from " + TABLE_NAME + " where " + key + " = " + "'" + value + "'";
        getWritableDatabase().execSQL(sql);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    private Cursor getCursorByKeyValue(String key, String value) {
        Cursor cursor = getWritableDatabase()
                .rawQuery("select * from " + TABLE_NAME + " where " + key + " = " + "'" + value + "'", null);
        return cursor;
    }

    /**
     * @param key
     * @param value
     * @return 是否有数据
     */
    private Boolean IsHasData(String key, String value) {
        Cursor cursor = getCursorByKeyValue(key, value);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    /**
     * @param fileHashName fileHashName
     */
    public void deleteRecordsByFileHashName(String fileHashName) {
        if (IsHasData(FileHashName, fileHashName + "")) {
            delete(FileHashName, fileHashName + "");
        }

    }

    /**
     * @param recordBean 要删除的记录
     */
    public void deleteRecord(FileReadRecordBean recordBean) {
        if (IsHasData(SearchId, recordBean.id + "")) {
            delete(SearchId, recordBean.id + "");
        }
    }

    /**
     * @return 获取所有记录
     */
    public List<FileReadRecordBean> getAllFileRecord() {
        return getRecord(null);
    }

    /**
     * @param userId
     * @return 如果userId 为空，不分用户返回的是所有下载的数据,否则返回指定用户的下载记录,不会返回空
     */
    public List<FileReadRecordBean> getRecord(String userId) {
        List<FileReadRecordBean> searchBeans = new ArrayList<>();
        Cursor cursor;
        if (TextUtils.isEmpty(userId)) {
            cursor = getWritableDatabase().rawQuery("select * from " + TABLE_NAME, null);
        } else {
            cursor = getCursorByKeyValue(FileHashName, userId);
        }
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    searchBeans.add(getRecordBeanFromCursor(cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return searchBeans;

    }

    private FileReadRecordBean getRecordBeanFromCursor(Cursor cursor) {
        FileReadRecordBean bean = new FileReadRecordBean();
        int userIdIndex = cursor.getColumnIndex(FileHashName);
        bean.fileHashName = cursor.getString(userIdIndex);

        int searchIdIndex = cursor.getColumnIndex(SearchId);
        bean.id = cursor.getInt(searchIdIndex);

        int CachePathIndex = cursor.getColumnIndex(FilePath);
        bean.filePath = cursor.getString(CachePathIndex);

        int fileNameIndex = cursor.getColumnIndex(FileName);
        bean.fileName = cursor.getString(fileNameIndex);

        int paragraphIndex = cursor.getColumnIndex(ParagraphIndex);
        bean.paragraphIndex = cursor.getInt(paragraphIndex);

        int chartIndex = cursor.getColumnIndex(ChartIndex);
        bean.chartIndex = cursor.getInt(chartIndex);

        return bean;
    }

    public void insertData(@NonNull FileReadRecordBean recordBean) {
        insertData(
                recordBean.fileHashName
                , recordBean.filePath
                , recordBean.fileName
                , recordBean.paragraphIndex
                , recordBean.chartIndex);
    }

    public void insertData(String fileHashName, String path, String fileName, int paragraphIndex, int chartIndex) {
        if (!TextUtils.isEmpty(FileHashName)) {
            if (IsHasData(FileHashName, fileHashName)) {//已经存在了，先删除
                deleteRecordsByFileHashName(fileHashName);
            }
            String sql = " insert into " + TABLE_NAME + " (" + FileHashName + "," + FilePath + "," + FileName + "," + ParagraphIndex + "," + ChartIndex + ") values ('" + fileHashName
                    + "','" + path + "','" + fileName + "','" + paragraphIndex + "','" + chartIndex + "')";
            getWritableDatabase().execSQL(sql);
        }
    }

    /**
     * @param fileHashName 文件hash值
     * @return 如果没有记录，返回null
     */
    public FileReadRecordBean getRecordByHashName(String fileHashName) {
        if (!TextUtils.isEmpty(fileHashName) && IsHasData(FileHashName, fileHashName)) {
            Cursor cursor = getCursorByKeyValue(FileHashName, fileHashName);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    FileReadRecordBean bean = getRecordBeanFromCursor(cursor);
                    cursor.close();
                    return bean;
                }
                cursor.close();
            }
        }
        return null;
    }

}
