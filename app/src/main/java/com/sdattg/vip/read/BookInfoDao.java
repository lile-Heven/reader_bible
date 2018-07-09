package com.sdattg.vip.read;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookInfoDao {
	public static DBOpenHelper helper; //����DBOpenHelper���ݿ���������
	public static SQLiteDatabase db;  //����SQLiteDatabase���ݿ����
	
	public BookInfoDao(Context context) {
		if(db == null) {
			helper = new DBOpenHelper(context);
			db = helper.getWritableDatabase();
		}
	}
	
	/**���һ���鼮��¼
	 * ������Book��name, address, cover�ֶα��cover��ʹ��Book��getRandomCover()������ȡ������棩
	 *         ��id:���ݿ����������classifyIdĬ��Ϊ0��Ϊ0�������ݿ�д��null��ѡ�myloveĬ��Ϊ1ѡ������ֶ�Ĭ��Ϊ0��
	 * ����ֵ��boolean �Ƿ�ִ�гɹ�*/
	public boolean addBook(Book bookInfo) {
		Object[] values = new Object[8];
		values[0] = bookInfo.getName();
		values[1] = bookInfo.getAddress();
		values[2] = bookInfo.getCover();
		int classifyId = bookInfo.getClassifyId();
		values[3] = classifyId == 0 ? null:classifyId;
		values[4] = bookInfo.getLatestReadTime();
		values[5] = bookInfo.getMyLove();
		values[6] = bookInfo.getReadRate();
		values[7] = bookInfo.getWordsNum();
		boolean success = true;
		try {
			db.execSQL("insert into tb_bookInfo(name,address,cover,classifyId,latestReadTime,myLove,readRate,wordsNum) "
					+ "values(?,?,?,?,?,?,?,?)",values);
		} catch (SQLException e) {
			success = false;
		}
		return success;
	}
	
	/**���һ�������¼
	 * ������classifyName ��String�� ��������
	 * ����ֵ��boolean �Ƿ�ִ�гɹ�*/
	public boolean addClassify(String classifyName) {
		ContentValues values = new ContentValues();
		values.put("name", classifyName);
		int result = (int)db.insert("tb_classifyInfo", "_id", values);
		if(result == -1) {
			return false;
		}
		return true;
	}

	
	/**����鼮Ŀ¼
	 * ������chapters (Chapter[]):�½ڶ�������
	 * ����ֵ����*/
	public void addCatalogue(Chapter[] chapters) {
		Object[] values = new Object[3];
		for (Chapter chap : chapters) {
			values[0] = chap.getBookId();
			values[1] = chap.getChaptersName();
			values[2] = chap.getRate();
			db.execSQL("insert into tb_catalogue(bookId,chaptersName,rate) values(?,?,?)", values);
		}
	}
	
	/**���һ���鼮��ǩ
	 * @param bookId  �鼮���
	 * @param labelName ��ǩ��
	 * @param rate �ֽ�λ��
	 * @param progress ��ǩ����
	 * ����ֵ����*/
    public void addBookLabel(int bookId, String labelName, int rate, String progress) {//execSQL����
		Object[] values = new Object[4];
		values[0] = bookId;
		values[1] = labelName;
		values[2] = rate;
		values[3] = progress;
		db.execSQL("insert into tb_bookLabel(bookId,labelName,rate,progress) values(?,?,?,?)", values);
	}
    
    /**ɾ��һ���鼮��¼
	 * ������bookId ��int�� ���鼮���
	 * ����ֵ����*/
    public void deleteBook(int bookId) {
    	db.execSQL("delete from tb_bookInfo where _id=?",new Object[]{bookId});
    }
    
    /**ɾ��һ�������¼��ע���ڴ�֮ǰ�����н��÷����µ��鼮ɾ�����޸������
	 * ������classifyId ��int�� ��������
	 * ����ֵ����*/
    public void deleteClassify(int classifyId) {
    	db.execSQL("delete from tb_classifyInfo where _id=?",new Object[]{classifyId});
    }
    
	/**ɾ��һ�����Ŀ¼
	 * ������bookId ��int�� ���鼮���
	 * ����ֵ����*/
    public void deleteCatalogue(int bookId) {
    	db.execSQL("delete from tb_catalogue where bookId=?",new Object[]{bookId});
    }
    
	/**ɾ��һ���鼮��ǩ
	 * ������labelId ��int�� ����ǩ���
	 * ����ֵ����*/
    public void deleteBookLabel(int labelId) {
    	db.execSQL("delete from tb_bookLabel where _Id=?",new Object[]{labelId});
    }
    
    /**ɾ��ĳ�����µ������鼮��¼
	 * ������classifyId ��int�� ��������
	 * ����ֵ����*/
    public void deleteClassifyBooks(int classifyId) {
    	db.execSQL("delete from tb_bookInfo where classifyId=?",new Object[]{classifyId});
    }
    
    /**ɾ��δ������鼮��¼
	 * ��������
	 * ����ֵ����*/
    public void deleteNoClassifyBooks() {
    	db.execSQL("delete from tb_bookInfo where classifyId is null");
    }
    
    /**ɾ�������鼮��¼
	 * ��������
	 * ����ֵ����*/
    public void deleteAllBooks() {
    	db.execSQL("delete from tb_bookInfo");
    }
    
	/**��ѯһ���������
	 * ������bookId ��int�� ���鼮���
	 * ����ֵ��String ������ */
    public String getBookName(int bookId) {
    	Cursor cur = null;
    	String name = null;
    	try {
    		cur = db.rawQuery("select name from tb_bookInfo where _id=?", new String[]{String.valueOf(bookId)});
        	if (cur.moveToNext()) {
    			name = cur.getString(cur.getColumnIndex("name"));
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
    	
    	return name;
    }
    
    /**��ȡ��������²����¼��_id��
	 * ��������
	 * ����ֵ��int �����²����_id�� */
    public int getLastTBClassifyId() {
    	Cursor cur = null;
    	int id = -1;
    	try {
    		cur = db.rawQuery("select last_insert_rowid() from tb_classifyInfo", null);
    		if (cur.moveToNext()) {
    			id = cur.getInt(0);
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
    	return id;
    }
    
    /**��ȡ�鼮�����²����¼��_id��
	 * ��������
	 * ����ֵ��int �����²����_id�� */
    public int getLastTBBookInfoId() {
    	Cursor cur = null;
    	int id = -1;
    	try {
    		cur = db.rawQuery("select last_insert_rowid() from tb_bookInfo", null);
        	if (cur.moveToNext()) {
    			id = cur.getInt(0);
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
    	return id;
    }
    
    /**ͨ����ַ����ѯһ�����������Ϣ
	 * ������address ��String�� ���鼮��ַ
	 * ����ֵ��Book ��һ���鼮����*/
    public Book getBookByAddress(String address) {
    	Cursor cur = null;
    	Book book = null;
    	try {
    		cur = db.rawQuery("select * from tb_bookInfo where address=?", new String[]{address});
        	if (cur.moveToNext()) {
        		book = new Book();
        		book.set_id(cur.getInt(cur.getColumnIndex("_id")));
        		book.setName(cur.getString(cur.getColumnIndex("name")));
        		book.setAddress(cur.getString(cur.getColumnIndex("address")));
        		book.setCover(cur.getInt(cur.getColumnIndex("cover")));
        		book.setClassifyId(cur.getInt(cur.getColumnIndex("classifyId")));
        		book.setLatestReadTime(cur.getLong(cur.getColumnIndex("latestReadTime")));
        		book.setMyLove(cur.getInt(cur.getColumnIndex("myLove")));
        		book.setReadRate(cur.getInt(cur.getColumnIndex("readRate")));
        		book.setWordsNum(cur.getLong(cur.getColumnIndex("wordsNum")));
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
    	return book;
    }
    
    /**ͨ����ַ����ȡ�鼮���
	 * ������address ��String�� ���鼮��ַ
	 * ����ֵ��int ���鼮��ţ��������򷵻�-1*/
    public int checkBookExistByAddress(String address) {
    	Cursor cur = null;
    	int id = -1;
    	try {
    		cur = db.rawQuery("select _id from tb_bookInfo where address=?", new String[]{address});
        	if (cur.moveToNext()) {
        		id = cur.getInt(cur.getColumnIndex("_id"));
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
    	return id;
    }
    
    /**��ȡĳ�����鼮�������鼮��ַ
	 * ������address ��String�� ���鼮��ַ
	 * ����ֵ��int ���鼮��ţ��������򷵻�-1*/
    public List<String> getAllAddressInClassify(int classifyId) {
    	Cursor cur = null;
    	List<String> list = new ArrayList<String>();
    	try {
    		cur = db.rawQuery("select address from tb_bookInfo where classifyId=?", new String[]{classifyId+""});
        	if (cur.moveToNext()) {
        		list.add(cur.getString(cur.getColumnIndex("address")));
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
    	return list;
    } 
    
	/**��ѯһ������鼮��ַ
	 * ������bookId ��int�� ���鼮���
	 * ����ֵ��String ���鼮��ַ*/
    public String getBookAddress(int bookId) {
    	Cursor cur = null;
    	String str = null;
    	try {
    		cur = db.rawQuery("select address from tb_bookInfo where _id=?", new String[]{String.valueOf(bookId)});
        	if (cur.moveToNext()) {
    			str = cur.getString(cur.getColumnIndex("address"));
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
    	return str;
    }
	
    /**��ѯһ������Ķ�����
	 * ������bookId ��int�� ���鼮���
	 * ����ֵ��int ���Ķ����ȣ��ֽ�����*/
    public int getReadRate(int bookId) {
    	Cursor cur = null;
    	int result = 0;
    	try {
    		cur = db.rawQuery("select readRate from tb_bookInfo where _id=?", new String[]{String.valueOf(bookId)});
        	if (cur.moveToNext()) {
        		result = cur.getInt(cur.getColumnIndex("readRate"));
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
    	return result;
    }
    
    /**��ѯһ�����������
	 * ������bookId ��int�� ���鼮���
	 * ����ֵ��int ��������*/
    public int getWordsNum(int bookId) {
    	Cursor cur = null;
    	int result = -1;
    	try {
    		cur = db.rawQuery("select wordsNum from tb_bookInfo where _id=?", new String[]{String.valueOf(bookId)});
        	if (cur.moveToNext()) {
        		result = cur.getInt(cur.getColumnIndex("wordsNum"));
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
    	return result;
    }
    
	/**��ѯһ�����������Ϣ
	 * ������bookId ��int�� ���鼮���
	 * ����ֵ��Book ��һ���鼮����*/
    public Book getABook(int bookId) {
    	String sql = "select b._id,b.name,address,cover,classifyId,c.name as classifyName,latestReadTime,myLove,readRate,wordsNum "
    			+ "from tb_bookInfo as b left join tb_classifyInfo as c where b._id = ?";
    	Cursor cur = null;
    	Book book = null;
    	try {
        	cur = db.rawQuery(sql, new String[]{String.valueOf(bookId)});
        	if (cur.moveToNext()) {
        		book = new Book();
        		book.set_id(cur.getInt(cur.getColumnIndex("_id")));
        		book.setName(cur.getString(cur.getColumnIndex("name")));
        		book.setAddress(cur.getString(cur.getColumnIndex("address")));
        		book.setCover(cur.getInt(cur.getColumnIndex("cover")));
        		book.setClassifyId(cur.getInt(cur.getColumnIndex("classifyId")));
        		book.setClassifyName(cur.getString(cur.getColumnIndex("classifyName")));
        		book.setLatestReadTime(cur.getLong(cur.getColumnIndex("latestReadTime")));
        		book.setMyLove(cur.getInt(cur.getColumnIndex("myLove")));
        		book.setReadRate(cur.getInt(cur.getColumnIndex("readRate")));
        		book.setWordsNum(cur.getLong(cur.getColumnIndex("wordsNum")));
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
    	return book;
    }
    
	/**��ѯһ�����Ŀ¼
	 * ������bookId ��int�� ���鼮���
	 * ����ֵ��List<Map<String,Object>>  ���½ڼ���
	 * ��Map��key�У�
	 *      "bookId"�����鼮��ţ�value���ͣ�int��
	 *      ��chaptersName�������½����ƣ�value���ͣ�String��
	 *      ��rate�������½ڶ�Ӧ���ȣ�value���ͣ�int����*/
    public List<Map<String, Object>> getCatalogue(int bookId) {
    	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
    	String sql = "select * from tb_catalogue where bookId=?";
    	Cursor cur = null;
    	try {
    		cur = db.rawQuery(sql, new String[]{String.valueOf(bookId)});
        	while (cur.moveToNext()) {
    			Map<String, Object> map = new HashMap<String, Object>();
    			map.put("bookId", cur.getString(cur.getColumnIndex("bookId")));
    			map.put("chaptersName", cur.getString(cur.getColumnIndex("chaptersName")));
    			map.put("rate", cur.getInt(cur.getColumnIndex("rate")));
    			list.add(map);
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
    	return list;
    	
    }
    
    /**��ѯһ�����������ǩ
	 * ������bookId ��int�� ���鼮���
	 * ����ֵ��List<Map<String,Object>>  ����ǩ����
	 * ��Map��key�У�
	 *      ��id��������ǩ��ţ�value���ͣ�int��
	 *      ��bookId�������鼮��ţ�value���ͣ�int��
	 *      ��labelName��������ǩ���ƣ�value���ͣ�String��
	 *      ��rate�������ַ�λ�ã�value���ͣ�int��
	 *      ��progress��������ǩ��Ӧ���ȣ�value���ͣ�String����*/
    public List<Map<String, Object>> getAllBookLabels(int bookId) {
    	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
    	String sql = "select * from tb_bookLabel where bookId=?";
    	Cursor cur = null;
    	try {
    		cur = db.rawQuery(sql, new String[]{String.valueOf(bookId)});
        	while (cur.moveToNext()) {
    			Map<String, Object> map =  new HashMap<String, Object>();
    			map.put("id", cur.getInt(cur.getColumnIndex("_Id")));
    			map.put("bookId", cur.getString(cur.getColumnIndex("bookId")));
    			map.put("labelName", cur.getString(cur.getColumnIndex("labelName")));
    			map.put("rate", cur.getInt(cur.getColumnIndex("rate")));
    			map.put("progress", cur.getString(cur.getColumnIndex("progress")));
    			list.add(map);
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
    	return list;
    }
    
	/**��������鼮��¼
	 * ��������
	 * ����ֵ��List<Map<String,Object>>
	 *       Map�е�Key�� ��
	 *           "id"����int�����鼮���
	 *           "name"����String�����鼮��
	 *           "address"����String�����鼮��ַ
	 *           "cover"����int�����鼮����,�����int��ָR�ļ��е�ͼƬ�Ķ�Ӧid
	 *           "classifyId����int��������id
	 *           "latestReadTime����long�������һ���Ķ�ʱ��
	 *           "myLove����int�����ҵ��
	 *           "readRate����int�����Ķ�����
	 *           "wordsNum����long����������"*/
	public List<Map<String, Object>> getAllBooks() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Cursor cur = null;
    	try {
    		cur = db.rawQuery("select * from tb_bookInfo", null);
    		while (cur.moveToNext()) {
    			Map<String, Object> map = new HashMap<String, Object>();
    			map.put("id", cur.getString(cur.getColumnIndex("_id")));
    			map.put("name", cur.getString(cur.getColumnIndex("name")));
    			map.put("address", cur.getString(cur.getColumnIndex("address")));
    			map.put("cover", cur.getInt(cur.getColumnIndex("cover")));
    			map.put("classifyId", cur.getInt(cur.getColumnIndex("classifyId")));
    			map.put("latestReadTime", cur.getLong(cur.getColumnIndex("latestReadTime")));
    			map.put("myLove", cur.getInt(cur.getColumnIndex("myLove")));
    			map.put("readRate", cur.getInt(cur.getColumnIndex("readRate")));
    			map.put("wordsNum", cur.getLong(cur.getColumnIndex("wordsNum")));
    			list.add(map);
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
		return list;
	}

	/**������з����¼
	 * ��������
	 * ����ֵ��List<Map<String,Object>>
	 *       Map�е�Key�� ��
	 *           "id"����int����������
	 *           "name"����String����������*/
	public List<Map<String, Object>> getAllClassify() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Cursor cur = null;
    	try {
    		cur = db.rawQuery("select * from tb_classifyInfo", null);
    		while (cur.moveToNext()) {
    			Map<String, Object> map = new HashMap<String, Object>();
    			map.put("id", cur.getInt(cur.getColumnIndex("_id")));
    			map.put("name", cur.getString(cur.getColumnIndex("name")));
    			list.add(map);
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
		return list;
	}
	
	/**��ó�ĳ������������з����¼
	 * ������exceptClassifyId (int) : �����������
	 * ����ֵ��List<Map<String,Object>>
	 *       Map�е�Key�� ��
	 *           "id"����int����������
	 *           "name"����String����������*/
	public List<Map<String, Object>> getAllClassifyExceptSome(int exceptClassifyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Cursor cur = null;
    	try {
    		cur = db.rawQuery("select * from tb_classifyInfo", null);
    		while (cur.moveToNext()) {
    			int id = cur.getInt(cur.getColumnIndex("_id"));
    			if(id != exceptClassifyId) {
    				Map<String, Object> map = new HashMap<String, Object>();
    				map.put("id", id);
    				map.put("name", cur.getString(cur.getColumnIndex("name")));
    				list.add(map);
    			}
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
		return list;
	}
	
	/**��ѯĳ���������ͼ��
	 * ������classifyId (int) ��������
	 * ����ֵ��List<Map<String,Object>> ���鼮����
	 *      Map�е�Key�� ��
	 *           "id"����int�����鼮���
	 *           "name"����String�����鼮��
	 *           "address"����String�����鼮��ַ
	 *           "cover"����int�����鼮����,�����int��ָR�ļ��е�ͼƬ�Ķ�Ӧid
	 *           "classifyId����int��������id
	 *           "latestReadTime����long�������һ���Ķ�ʱ��
	 *           "myLove����int�����ҵ��
	 *           "readRate����int�����Ķ�����
	 *           "wordsNum����long����������"*/
	public List<Map<String, Object>> getClassificatoryBooks(int classifyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Cursor cur = null;
    	try {
    		cur = db.rawQuery("select * from tb_bookInfo where classifyId=?", new String[]{String.valueOf(classifyId)});
    		while (cur.moveToNext()) {
    			Map<String, Object> map = new HashMap<String, Object>();
    			map.put("id", cur.getString(cur.getColumnIndex("_id")));
    			map.put("name", cur.getString(cur.getColumnIndex("name")));
    			map.put("address", cur.getString(cur.getColumnIndex("address")));
    			map.put("cover", cur.getInt(cur.getColumnIndex("cover")));
    			map.put("classifyId", cur.getInt(cur.getColumnIndex("classifyId")));
    			map.put("latestReadTime", cur.getLong(cur.getColumnIndex("latestReadTime")));
    			map.put("myLove", cur.getInt(cur.getColumnIndex("myLove")));
    			map.put("readRate", cur.getInt(cur.getColumnIndex("readRate")));
    			map.put("wordsNum", cur.getLong(cur.getColumnIndex("wordsNum")));
    			list.add(map);
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
		
		return list;
	}


	public static final int DIRFILE= -1; //�ļ�Ŀ¼
	public static final int MYLOVE = -2; //�ҵ��ղ�
	public static final int LATEST = -3; //����Ķ�
	public static final int ALLBOOK = -4; //ȫ��ͼ��
	public static final int NOCLASSIFY = -5; //δ����
	/**��ѯĳ��������ͼ��,������ܵ�ר�÷���
	 * ������category (int) �������
	 * ����ֵ��List<Map<String,Object>> ���鼮����
	 *      Map�е�Key�� ��
	 *           "id"����int�����鼮���
	 *           "name"����String�����鼮��
	 *           "address"����String�����鼮��ַ
	 *           "cover"����int�����鼮����,�����int��ָR�ļ��е�ͼƬ�Ķ�Ӧid
	 *           "classifyId����int��������id
	 *           "latestReadTime����long�������һ���Ķ�ʱ��
	 *           "myLove����int�����ҵ��
	 *           "readRate����int�����Ķ�����
	 *           "wordsNum����long����������
	 *           "isCheck����boolean����ͼ���Ƿ�ѡ�У�Ĭ�ϲ�ѡ�У�"*/
	public List<Map<String, Object>> getCategoryBooks(int category) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Cursor cur = null;
    	try {
    		switch (category) {
            case ALLBOOK:
            	cur = db.rawQuery("select * from tb_bookInfo", null); 
            	break;
            case NOCLASSIFY:
            	cur = db.rawQuery("select * from tb_bookInfo where classifyId is null", null);
            	break;
    		default:
    			cur = db.rawQuery("select * from tb_bookInfo where classifyId=?", new String[]{String.valueOf(category)});
    			break;
    		}
    		while (cur.moveToNext()) {
    			Map<String, Object> map = new HashMap<String, Object>();
    			map.put("id", cur.getString(cur.getColumnIndex("_id")));
    			map.put("name", cur.getString(cur.getColumnIndex("name")));
    			map.put("address", cur.getString(cur.getColumnIndex("address")));
    			map.put("cover", cur.getInt(cur.getColumnIndex("cover")));
    			map.put("classifyId", cur.getInt(cur.getColumnIndex("classifyId")));
    			map.put("latestReadTime", cur.getLong(cur.getColumnIndex("latestReadTime")));
    			map.put("myLove", cur.getInt(cur.getColumnIndex("myLove")));
    			map.put("readRate", cur.getInt(cur.getColumnIndex("readRate")));
    			map.put("wordsNum", cur.getLong(cur.getColumnIndex("wordsNum")));
    			map.put("isCheck", false);
    			list.add(map);
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
		return list;
	}
	
	/**��ѯδ���������ͼ��
	 * ��������
	 * ����ֵ��List<Map<String,Object>> ���鼮����
	 *      Map�е�Key�� ��
	 *           "id"����int�����鼮���
	 *           "name"����String�����鼮��
	 *           "address"����String�����鼮��ַ
	 *           "cover"����int�����鼮����,�����int��ָR�ļ��е�ͼƬ�Ķ�Ӧid
	 *           "classifyId����int��������id
	 *           "latestReadTime����long�������һ���Ķ�ʱ��
	 *           "myLove����int�����ҵ��
	 *           "readRate����int�����Ķ�����
	 *           "wordsNum����long����������"*/
	public List<Map<String, Object>> getNoClassifyBooks() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Cursor cur = null;
    	try {
    		cur = db.rawQuery("select * from tb_bookInfo where classifyId is null", null);
    		while (cur.moveToNext()) {
    			Map<String, Object> map = new HashMap<String, Object>();
    			map.put("id", cur.getString(cur.getColumnIndex("_id")));
    			map.put("name", cur.getString(cur.getColumnIndex("name")));
    			map.put("address", cur.getString(cur.getColumnIndex("address")));
    			map.put("cover", cur.getInt(cur.getColumnIndex("cover")));
    			map.put("classifyId", cur.getInt(cur.getColumnIndex("classifyId")));
    			map.put("latestReadTime", cur.getLong(cur.getColumnIndex("latestReadTime")));
    			map.put("myLove", cur.getInt(cur.getColumnIndex("myLove")));
    			map.put("readRate", cur.getInt(cur.getColumnIndex("readRate")));
    			map.put("wordsNum", cur.getLong(cur.getColumnIndex("wordsNum")));
    			list.add(map);
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
		return list;
	}
	
	/**��ѯ�ҵ���е�����ͼ��
	 * ��������
	 * ����ֵ��List<Map<String,Object>> ���鼮����
	 *      Map�е�Key�� ��
	 *           "id"����int�����鼮���
	 *           "name"����String�����鼮��
	 *           "address"����String�����鼮��ַ
	 *           "cover"����int�����鼮����,�����int��ָR�ļ��е�ͼƬ�Ķ�Ӧid
	 *           "classifyId����int��������id
	 *           "latestReadTime����long�������һ���Ķ�ʱ��
	 *           "myLove����int�����ҵ��
	 *           "readRate����int�����Ķ�����
	 *           "wordsNum����long����������"*/
	public List<Map<String, Object>> getLoveBooks() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Cursor cur = null;
    	try {
    		cur = db.rawQuery("select * from tb_bookInfo where myLove=0", null);
    		while (cur.moveToNext()) {
    			Map<String, Object> map = new HashMap<String, Object>();
    			map.put("id", cur.getString(cur.getColumnIndex("_id")));
    			map.put("name", cur.getString(cur.getColumnIndex("name")));
    			map.put("address", cur.getString(cur.getColumnIndex("address")));
    			map.put("cover", cur.getInt(cur.getColumnIndex("cover")));
    			map.put("classifyId", cur.getInt(cur.getColumnIndex("classifyId")));
    			map.put("latestReadTime", cur.getLong(cur.getColumnIndex("latestReadTime")));
    			map.put("myLove", cur.getInt(cur.getColumnIndex("myLove")));
    			map.put("readRate", cur.getInt(cur.getColumnIndex("readRate")));
    			map.put("wordsNum", cur.getLong(cur.getColumnIndex("wordsNum")));
    			list.add(map);
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
		return list;
	}
	
	/**��ѯ����Ķ���ͼ��
	 * ������num��int��������Ķ���ǰnum��ͼ��
	 * ����ֵ��List<Map<String,Object>> ���鼮����
	 *      Map�е�Key�� ��
	 *           "id"����int�����鼮���
	 *           "name"����String�����鼮��
	 *           "address"����String�����鼮��ַ
	 *           "cover"����int�����鼮����,�����int��ָR�ļ��е�ͼƬ�Ķ�Ӧid
	 *           "classifyId����int��������id
	 *           "latestReadTime����long�������һ���Ķ�ʱ��
	 *           "myLove����int�����ҵ��
	 *           "readRate����int�����Ķ�����
	 *           "wordsNum����long����������"*/
	public List<Map<String, Object>> getLatestBooks(int num) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select * from tb_bookInfo order by latestReadTime desc limit " + num;
		Cursor cur = null;
    	try {
    		cur = db.rawQuery(sql, null);
    		while (cur.moveToNext()) {
    			long time = cur.getLong(cur.getColumnIndex("latestReadTime"));
    			if(time != 0) {
    				Map<String, Object> map = new HashMap<String, Object>();
    				map.put("id", cur.getString(cur.getColumnIndex("_id")));
    				map.put("name", cur.getString(cur.getColumnIndex("name")));
    				map.put("address", cur.getString(cur.getColumnIndex("address")));
    				map.put("cover", cur.getInt(cur.getColumnIndex("cover")));
    				map.put("classifyId", cur.getInt(cur.getColumnIndex("classifyId")));
    				map.put("latestReadTime", time);
    				map.put("myLove", cur.getInt(cur.getColumnIndex("myLove")));
    				map.put("readRate", cur.getInt(cur.getColumnIndex("readRate")));
    				map.put("wordsNum", cur.getLong(cur.getColumnIndex("wordsNum")));
    				list.add(map);
    			}
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
		
		return list;
	}
	
	/**ģ����ѯ����������ͼ��
	 * ������name��String������������ 
	 * ����ֵ��List<Map<String,Object>> ���鼮����
	 *      Map�е�Key�� ��
	 *           "id"����int�����鼮���
	 *           "name"����String�����鼮��
	 *           "address"����String�����鼮��ַ
	 *           "cover"����int�����鼮����,�����int��ָR�ļ��е�ͼƬ�Ķ�Ӧid
	 *           "classifyId����int��������id
	 *           "latestReadTime����long�������һ���Ķ�ʱ��
	 *           "myLove����int�����ҵ��
	 *           "readRate����int�����Ķ�����
	 *           "wordsNum����long����������"*/
	public List<Map<String, Object>> getSimilarNameBooks(String name) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select * from tb_bookInfo where name like '%" + name + "%'";
		Cursor cur = null;
    	try {
    		cur = db.rawQuery(sql, null);
    		while (cur.moveToNext()) {
    			Map<String, Object> map = new HashMap<String, Object>();
    			map.put("id", cur.getString(cur.getColumnIndex("_id")));
    			map.put("name", cur.getString(cur.getColumnIndex("name")));
    			map.put("address", cur.getString(cur.getColumnIndex("address")));
    			map.put("cover", cur.getInt(cur.getColumnIndex("cover")));
    			map.put("classifyId", cur.getInt(cur.getColumnIndex("classifyId")));
    			map.put("latestReadTime", cur.getLong(cur.getColumnIndex("latestReadTime")));
    			map.put("myLove", cur.getInt(cur.getColumnIndex("myLove")));
    			map.put("readRate", cur.getInt(cur.getColumnIndex("readRate")));
    			map.put("wordsNum", cur.getLong(cur.getColumnIndex("wordsNum")));
    			list.add(map);
    		}
		} 
    	catch (SQLException e) {
		}
    	finally {
    		if(cur != null) {
    			cur.close();
    		}
    	}
		return list;
	}
	
	/**�޸��鼮������ע����ʱͬʱҲ�޸����鼮��ַ��
	 * ������bookId ��int�� ���鼮���
	 *     newBookName (String) �����鼮��
	 *     newAddress(String):�µ�ַ
	 * ����ֵ����*/
	public void alterBookName(int bookId, String newBookName, String newAddress) {
		String sql = "update tb_bookInfo set name=?,address=? where _id=?";
		db.execSQL(sql, new Object[]{newBookName, newAddress, bookId});
	}
	
	/**�޸��鼮������
	 * ������classifyId��int�� ��������
	 *     newClassifyName��String�����µķ�����
	 * ����ֵ����*/
	public void alterClassifyName(int classifyId, String newClassifyName) {
		String sql = "update tb_classifyInfo set name=? where _id=?";
		db.execSQL(sql, new Object[]{newClassifyName, classifyId});
	}
	
	/**�޸��鼮����
	 * ������bookId ��int�� ���鼮���
	 *     classifyId��int�� ��������
	 * ����ֵ����*/
	public void alterClassify(int bookId, int classifyId) {
		String sql = "update tb_bookInfo set classifyId=? where _id=?";
		db.execSQL(sql, new Object[]{classifyId, bookId});
	}
	
	/**�޸��鼮���״̬���Ƿ�Ϊ�ҵ����
	 * ������bookId ��int�� ���鼮���
	 *     myLove(int) ���Ƿ�Ϊ�ҵ����0��true��1��false
	 * ����ֵ����*/
	public void alterLoveBook(int bookId, int myLove) {
		String sql = "update tb_bookInfo set myLove=? where _id=?";
		db.execSQL(sql, new Object[]{myLove, bookId});
	}
	
	/**�޸��鼮�����һ���Ķ�ʱ��
	 * ������bookId ��int�� ���鼮���
	 *     latestReadTime (long) �����һ���Ķ�ʱ�䣨��������
	 * ����ֵ����*/
	public void alterLatestReadTime(int bookId, long latestReadTime) {
		String sql = "update tb_bookInfo set latestReadTime=? where _id=?";
		db.execSQL(sql, new Object[]{latestReadTime, bookId});
	}
	
	/**�޸��鼮���Ķ�����
	 * ������bookId ��int�� ���鼮���
	 *     rate(int) ���Ķ����ȣ��ֽ�����
	 * ����ֵ����*/
	public void alterReadRate(int bookId, int rate) {
		String sql = "update tb_bookInfo set readRate=? where _id=?";
		db.execSQL(sql, new Object[]{rate, bookId});
	}
}
