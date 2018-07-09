package com.sdattg.vip.read;


public class Chapter {
	private int bookId; //书籍编号
	private String chaptersName; //章节名
	private int rate;//章节对应进度
	
	/**bookId:书籍编号，chapterName:章节名，rate：章节对应进度*/
	public Chapter(int bookId, String chaptersName, int rate) {
		this.bookId = bookId;
		this.chaptersName = chaptersName;
		this.rate = rate;
	}
	
	/**获取书籍编号*/
	public int getBookId() {
		return bookId;
	}
	/**获取章节名*/
	public String getChaptersName() {
		return chaptersName;
	}
	/**获取章节对应进度*/
	public int getRate() {
		return rate;
	}
	
	/**设置书籍编号*/
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	/**设置章节名*/
	public void setChaptersName(String chaptersName) {
		this.chaptersName = chaptersName;
	}
	/**设置章节对应进度*/
	public void setRate(int rate) {
		this.rate = rate;
	}
}
