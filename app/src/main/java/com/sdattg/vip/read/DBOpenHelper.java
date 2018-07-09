package com.sdattg.vip.read;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1; //���ݿ�汾��
    public static final String DBNAME = "horizon.db"; //���ݿ���
    public static final String TB_BOOK = "tb_bookInfo"; //�鼮��Ϣ��
    public static final String TB_CLASSIFY = "tb_classifyInfo"; //�鼮�����
    public static final String TB_CATALOGUE = "tb_catalogue"; //�鼮Ŀ¼��
    public static final String TB_LABEL = "tb_bookLabel"; //�鼮��ǩ��
    
    public DBOpenHelper(Context context) {
    	super(context, DBNAME, null, VERSION);
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//�����鼮��Ϣ��
		db.execSQL("create table tb_bookInfo(_id integer primary key autoincrement,name text NOT NULL,address text UNIQUE," + 
	            "cover integer NOT NULL,classifyId integer CONSTRAINT fk_classify_id REFERENCES tb_classifyInfo(_id)," + 
	            "latestReadTime integer,myLove integer NOT NULL,readRate integer,wordsNum integer)");
		//�����鼮�����
		db.execSQL("create table tb_classifyInfo(_id integer primary key autoincrement,name text UNIQUE)");
		//�����鼮Ŀ¼��
		db.execSQL("create table tb_catalogue(bookId integer CONSTRAINT fk_book_id1 REFERENCES tb_bookInfo(_id)," + 
		         "chaptersName text NOT NULL,rate integer NOT NULL)");
		
		//�����鼮��ǩ��                                                                     
				db.execSQL("create table tb_bookLabel(_Id integer primary key autoincrement,bookId integer CONSTRAINT fk_book_id2 REFERENCES tb_bookInfo(_id)," + 
				         "labelName text NOT NULL,rate integer NOT NULL,progress text NOT NULL)");
		
		//����3��ʾ���鼮
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
