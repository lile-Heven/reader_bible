package com.sdattg.vip.read;


public class Chapter {
	private int bookId; //�鼮���
	private String chaptersName; //�½���
	private int rate;//�½ڶ�Ӧ����
	
	/**bookId:�鼮��ţ�chapterName:�½�����rate���½ڶ�Ӧ����*/
	public Chapter(int bookId, String chaptersName, int rate) {
		this.bookId = bookId;
		this.chaptersName = chaptersName;
		this.rate = rate;
	}
	
	/**��ȡ�鼮���*/
	public int getBookId() {
		return bookId;
	}
	/**��ȡ�½���*/
	public String getChaptersName() {
		return chaptersName;
	}
	/**��ȡ�½ڶ�Ӧ����*/
	public int getRate() {
		return rate;
	}
	
	/**�����鼮���*/
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	/**�����½���*/
	public void setChaptersName(String chaptersName) {
		this.chaptersName = chaptersName;
	}
	/**�����½ڶ�Ӧ����*/
	public void setRate(int rate) {
		this.rate = rate;
	}
}
