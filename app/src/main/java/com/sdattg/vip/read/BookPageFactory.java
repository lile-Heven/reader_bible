package com.sdattg.vip.read;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.sdattg.vip.MainActivity;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Vector;

import static com.iflytek.cloud.SpeechSynthesizer.createSynthesizer;

public class BookPageFactory {

	/**  �û�����  */
	ConfigDao mConfig;
	Book mBook;
	BookInfoDao mBookInfo;
	
	final private Context context;
	/**  �鼮�ļ�  */
	private File book_file = null;
	/**  ��ȡ�鼮���ݻ���  */
	private MappedByteBuffer m_mbBuf = null;
	/**  �ֽ��ܳ���  */
	private int m_mbBufLen = 0;
	/**  �ֽڿ�ʼλ�� */
	private int m_mbBufBegin = 0;
	/**  �ֽڽ���λ�� */
	private int m_mbBufEnd = 0;
	/**  ���ֹ�����չ��  */
	//private String m_strCharsetName = "gbk";
	//private String m_strCharsetName = "UTF-16";
	private String m_strCharsetName = "UTF-8";
	/**  ����ͼƬ  */
	private Bitmap m_book_bg = null;
	/**  ��Ļ���  */
	private int mWidth;
	/**  ��Ļ�߶�  */
	private int mHeight;
	/**  ÿҳ�м�  */
	public Vector<String> m_lines = new Vector<String>();
	/**  ���ִ�С  */
	private int m_fontSize;// = 24;//
	/**  ������ɫ  */
	private int m_textColor;// = Color.BLACK;//
	/**  ������ɫ  */
	private int m_backColor;// = Color.argb(255, 254, 209, 137); // ������ɫ  
	/**  �������Ե�ľ���  */
	private int marginWidth;// = 15; // �������Ե�ľ���  
	/**  �������Ե�ľ���  */
	private int marginHeight;// = 20; // �������Ե�ľ���  
	/**  ÿҳ������ʾ������*/
	private int mLineCount; // ÿҳ������ʾ������
	/**  �������ݵĸ�  */
	private float mVisibleHeight; // �������ݵĿ�
	/**  �������ݵĿ�  */
	private float mVisibleWidth; // �������ݵĿ�
	/**  �ж��Ƿ�Ϊ��ҳ/βҳ  */
	//private boolean m_isfirstPage ,m_islastPage ;
	private boolean m_isfirstPage = true,m_islastPage = true;
	/**  �о�*/
	private int m_nLineSpaceing;// = 10;//
	/**  ����ı����� */
	private Paint mPaint;
	/**  ���ٷֱ�  */
	private Paint percentPaint;
	/**  �ٷֱ�  */
	private String strPercent;
	
	
	/**
	 * ����
	 * @param context ��ǰ������
	 * @param w ��Ļ���
	 * @param h ��Ļ�߶�
	 */
	public BookPageFactory(Context context, int w, int h) {
		// TODO Auto-generated constructor stub
		this.context = context;
		mConfig = new ConfigDao(context);
		mBookInfo = new BookInfoDao(context);
		mWidth = w;
		mHeight = h;
		
		// percentPaint.���ò�����ʽ  ��д�ٷֱ�ר��
		percentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		percentPaint.setTextAlign(Align.LEFT);
		percentPaint.setTextSize(24);//�����С 24
		// �����Ǽ�������paint�ġ�
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextAlign(Align.LEFT);
//		//�û�����
		getUserConfig();
	}
	
	/** 
	 * ���鼮
	 * @param book �鼮ʵ��
	 *  */
	public void openbook(Book book) throws IOException {
		this.mBook = book;
		book_file = new File(book.getAddress());
		/** ���ֽڳ���  */
		long lLen;//�ֽڳ��ȣ��ж��ٸ��ֽ�
		if(book.getWordsNum() == 0) {
			lLen = book_file.length();
			m_mbBufLen = (int) lLen;//һ����
			book.setWordsNum(m_mbBufLen);
		} else {
			m_mbBufLen = (int)book.getWordsNum();
		}
		m_mbBufBegin = book.getReadRate();
		m_mbBufEnd = book.getReadRate();
		Log.d("openbook", Integer.toString(m_mbBufLen)+"   "+ Integer.toString(m_mbBufBegin)+"   "+ Integer.toString(m_mbBufEnd));
		//MappedByteBuffer ����
		m_mbBuf = new RandomAccessFile(book_file, "r").getChannel().map( FileChannel.MapMode.READ_ONLY, 0, m_mbBufLen);
		//Log.d("BookPageFactory", new String(m_mbBuf));
		getCutChapters();
	}
	
	public void getCutChapters() {
		//ƥ��Ŀ¼����
		if(mBookInfo.getCatalogue(mBook.getId()).size() == 0) {
			Thread handlerBarThread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					byte[] buf = new byte[m_mbBufLen];
					for (int j = 0; j < m_mbBufLen; j++) {
						buf[j] = m_mbBuf.get(j);
					}
					String cont ="";
					try {
						cont = new String(buf, m_strCharsetName);
						cont.replaceAll("\r\n", "");
						cont.replaceAll("\n", "");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Log.i("m_mbBuf", cont.substring(0, 100));
					CutChapters cutChap = new CutChapters(context, mBook.getId(), cont);
					
//					String cont = "";
//					Vector<String> sTemp = new Vector<String>();
//					sTemp = getAllString(0);
//					for(int i = 0; i<sTemp.size();i++) {
//						cont += sTemp.get(i); 
//					}
//					Log.i("getAllString()", Integer.toString(sTemp.size()));
//					Log.i("getAllString()", cont.substring(0, 100));
//					CutChapters cutChap = new CutChapters(context, mBook.getId(), cont);
					


					
				}
				
			});
			handlerBarThread.start();
		}
	}
	
	/**
	 * ��ȡ�����ı�
	 * @return Vector<String> �ַ���ʸ����
	 */
	protected Vector<String> getAllString(int abegin) {
		String strParagraph = "";
		Vector<String> lines = new Vector<String>();
		while (abegin < m_mbBufLen) {
			byte[] paraBuf = readParagraphForward(abegin); // ��ȡһ������
			abegin += paraBuf.length;//�µĽ�����Ϊÿһ�ε����λ��
//			Log.e("BookPageFactory.pageDown()", Integer.toString(abegin));
			try {
				strParagraph = new String(paraBuf, m_strCharsetName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			String strReturn = "";
			if (strParagraph.indexOf("\r\n") != -1) {
//				strReturn = "\r\n";
				strParagraph = strParagraph.replaceAll("\r\n", "");
			} else if (strParagraph.indexOf("\n") != -1) {
//				strReturn = "\n";
				strParagraph = strParagraph.replaceAll("\n", "");
			}
			lines.add(strParagraph);
		}
		return lines;//������һҳ������
	}
	
	/** 
	 * ��ȡǰһ���� 
	 *  @param nFromPos ������
	 *  */
	protected byte[] readParagraphBack(int nFromPos) {
		int nEnd = nFromPos;
		int i;//ǰһ�εĿ�ʼλ��
		byte b0, b1;
		if (m_strCharsetName.equals("UTF-16LE")) {
			i = nEnd - 2;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				b1 = m_mbBuf.get(i + 1);
				if (b0 == 0x0a && b1 == 0x00 && i != nEnd - 2) {//0x0aΪ����,0x00Ϊ��λ��
					i += 2;
					break;
				}
				i--;
			}

		} else if (m_strCharsetName.equals("UTF-16BE")) {
			i = nEnd - 2;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				b1 = m_mbBuf.get(i + 1);
				if (b0 == 0x00 && b1 == 0x0a && i != nEnd - 2) {
					i += 2;
					break;
				}
				i--;
			}
		} else {
			i = nEnd - 1;// ֮ǰ�Ľ����㣬 ���ػ�ȡ
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				if (b0 == 0x0a && i != nEnd - 1) {
					i++;
					break;
				}
				i--;
			}
		}
		if (i < 0)
			i = 0;
		
		int nParaSize = nEnd - i;//ǰһ�εĳ���
		int j;
		byte[] buf = new byte[nParaSize];
		for (j = 0; j < nParaSize; j++) {
			buf[j] = m_mbBuf.get(i + j);
		}
		return buf;
	}

	/** 
	 * ��ȡ��һ���� 
	 *  @param nFromPos ������
	 * */
	protected byte[] readParagraphForward(int nFromPos) {
		int nStart = nFromPos;
		int i = nStart;//��һ�ν�����λ��
		byte b0, b1;
		// ���ݱ����ʽ�жϻ���
		if (m_strCharsetName.equals("UTF-16LE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (b0 == 0x0a && b1 == 0x00) {
					break;
				}
			}
		} else if (m_strCharsetName.equals("UTF-16BE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (b0 == 0x00 && b1 == 0x0a) {
					break;
				}
			}
		} else {
			while (i < m_mbBufLen) {
				b0 = m_mbBuf.get(i++);
				if (b0 == 0x0a) {  // \r\n?
					break;
				}
			}
		}
		int nParaSize = i - nStart;//��һ�εĳ���
		byte[] buf = new byte[nParaSize];
		for (i = 0; i < nParaSize; i++) {
			buf[i] = m_mbBuf.get(nFromPos + i);
		}
		return buf;
	}

	/**
	 * ��ȡ��һҳ
	 * @return Vector<String> �ַ���ʸ����
	 */
	protected Vector<String> pageDown() {
//		if(m_mbBufEnd > 0 && m_mbBufEnd != m_mbBufLen) {
//			m_mbBufBegin = m_mbBufEnd;
//		}
		String strParagraph = "";
		Vector<String> lines = new Vector<String>();
		while (lines.size() < mLineCount && m_mbBufEnd < m_mbBufLen) {
			byte[] paraBuf = readParagraphForward(m_mbBufEnd); // ��ȡһ������
			Log.d("findbug", new String(paraBuf));
			m_mbBufEnd += paraBuf.length;//�µĽ�����Ϊÿһ�ε����λ��
			Log.e("BookPageFactory.page", Integer.toString(m_mbBufEnd));
			try {
				strParagraph = new String(paraBuf, m_strCharsetName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String strReturn = "";
			if (strParagraph.indexOf("\r\n") != -1) {
				strReturn = "\r\n";
				strParagraph = strParagraph.replaceAll("\r\n", "");
			} else if (strParagraph.indexOf("\n") != -1) {
				strReturn = "\n";
				strParagraph = strParagraph.replaceAll("\n", "");
			}

			if (strParagraph.length() == 0) {
				lines.add(strParagraph);
			}
			while (strParagraph.length() > 0) { 
				float str_pixel = mPaint.measureText(strParagraph);
				System.out.println(str_pixel);
				int nSize = mPaint.breakText(strParagraph, true, mVisibleWidth, null);//�и��ı�, ���ر��г���
				lines.add(strParagraph.substring(0, nSize));// �м�����ӱ���
				strParagraph = strParagraph.substring(nSize);// ȥ������ӵ���
				if (lines.size() >= mLineCount) {
					System.out.println(lines.size());
					if (strParagraph.length() != 0) {
						try {
//							m_mbBufEnd -= strParagraph.getBytes(m_strCharsetName).length;
							m_mbBufEnd -= (strParagraph + strReturn).getBytes(m_strCharsetName).length;
							Log.e("BookPageFactory.pageDo", Integer.toString(m_mbBufEnd));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				}
			}
		}
		return lines;//������һҳ������
	}
	
	/**
	 * ��ȡǰһҳ
	 */
	protected void pageUp() { 
		if (m_mbBufBegin <= 0) {
			m_mbBufBegin = 0;
		} //else {
//			m_mbBufEnd = m_mbBufBegin;
//		}
		Vector<String> lines = new Vector<String>();
		String strParagraph = "";
		while (lines.size() < mLineCount && m_mbBufBegin > 0) {
			Vector<String> paraLines = new Vector<String>();
			byte[] paraBuf = readParagraphBack(m_mbBufBegin);
			m_mbBufBegin -= paraBuf.length;//�����µ����Ϊǰһ�����ַ���λ��
			try {
				strParagraph = new String(paraBuf, m_strCharsetName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			strParagraph = strParagraph.replaceAll("\r\n", "$");
			strParagraph = strParagraph.replaceAll("\n", "$");

			if (strParagraph.length() == 0) {
				paraLines.add(strParagraph);
			}
			while (strParagraph.length() > 0) {
				int nSize = mPaint.breakText(strParagraph, true, mVisibleWidth,
						null);
				paraLines.add(strParagraph.substring(0, nSize));
				strParagraph = strParagraph.substring(nSize);
			}
			lines.addAll(0, paraLines);
		}
		while (lines.size() > mLineCount) {
			try {
				m_mbBufBegin += lines.get(0).getBytes(m_strCharsetName).length;//���������Ϊ��һҳ���ַ���λ��
				lines.remove(0);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		m_mbBufEnd = m_mbBufBegin;
		return;
	}


	public void isFirstPage(){
		if(m_mbBufBegin <= 0){
			m_mbBufBegin = 0;//��һҳ
			m_isfirstPage=true;
		}else{
			m_isfirstPage=false;
		}
	}
	/**
	 * �����һҳ
	 * @throws IOException
	 */
	protected void prePage(){
		m_lines.clear();//���
		pageUp();// m_mbBufEnd = m_mbBufBegin; �����ҳ���ĸ��ֿ�ʼ�����ĸ��ֽ���
		m_lines = pageDown();
	}

	public void isLastPage(){
		if(m_mbBufEnd >= m_mbBufLen){
			m_islastPage=true;
		}else{
			m_islastPage=false;
		}
	}
	/**
	 * �����һҳ
	 * @throws IOException
	 */
	public void nextPage(){
		if (m_mbBufEnd >= m_mbBufLen) {
			m_islastPage=true;

			return;
		}else{
			m_islastPage=false;
			m_lines.clear();
			m_mbBufBegin = m_mbBufEnd;//��֮ǰ�Ľ�β�ط� Ϊ��һ����ʼλ��
			m_lines = pageDown();
		}
	}

	public void onDraw(Canvas c) {
		getUserConfig();
		Log.d("BookPageFactory.onDraw", "into onDraw() m_lines:");

		Log.d("BookPageFactory.onDraw", Integer.toString(m_mbBufBegin));

		if (m_lines.size() == 0)
			m_lines = pageDown();//��ʼ��ʱΪ0
		if (m_lines.size() > 0) {
//			if (m_book_bg == null)
				c.drawColor(m_backColor);
//			else
//				c.drawBitmap(m_book_bg, 0, 0, null);
			int y = marginHeight;
			for (String strLine : m_lines) {//ÿ��ȡһ�оͻ�һ�е����������marginwidth,x,marginHeight+�����С
				Log.d("BookPageFactory", strLine);

				/*try {
                    strLine = new String(strLine.getBytes("gbk"), "utf-8");
					Log.d("BookPageFactory process", strLine);
					         } catch (UnsupportedEncodingException e) {
					             // TODO Auto-generated catch block
					             e.printStackTrace();
					         }*/
				y += (m_fontSize + m_nLineSpaceing);//+ �о�
				c.drawText(strLine, marginWidth, y, mPaint); // core codes $$$$$$$
				Log.d("BookPageFactory", "strLine:" + strLine);
			}


		}
		//��ʼ���ٷֱ�
		calcStrPercent(m_mbBufBegin);
		int nPercentWidth = (int) percentPaint.measureText("999.9%") + 1;
		c.drawText(strPercent, mWidth - nPercentWidth, mHeight - 5, percentPaint);
		Log.d("BookPageFactory.onDraw", strPercent);
		mBookInfo.alterReadRate(mBook.getId(), m_mbBufBegin);
		Log.d("BookPageFactory.onDraw", Integer.toString(mBook.getId())+" and "+ Integer.toString(m_mbBufBegin));
        mBookInfo.alterLatestReadTime(mBook.getId(), Calendar.getInstance().getTimeInMillis());
//		Toast.makeText(context, "Time is " + Long.toString(day1)+"  "+sdf.format(day1), Toast.LENGTH_LONG).show();
	}



	/**
	 * �����Ķ�ģʽ
	 * @param textColor ������ɫ
	 * @param bgColor ������ɫ
	 */
	public void setTheme(int textColor, int bgColor) {
		this.m_textColor = textColor;
		this.m_backColor = bgColor;
	}
	
	/**
	 * ��ȡ�û�������Ϣ
	 */
	public void getUserConfig() {
		marginWidth = mConfig.getPageRLMargin();
		marginHeight = mConfig.getPageTBMargin();
		m_nLineSpaceing = mConfig.getLineSpacing();
		m_backColor = mConfig.getBackColour();
		m_textColor = mConfig.getTxtColour();
		m_fontSize = mConfig.getTxtSize();
		mVisibleWidth = mWidth - marginWidth * 2;// �������ݵĿ�   �������ҵı�Ե����һ���ľ��� ����*2
		mVisibleHeight = mHeight - marginHeight * 2;// �������ݵĸ�
		mLineCount = (int) (mVisibleHeight / (m_fontSize + m_nLineSpaceing)); // ����ʾ������ / ����ʾ�ĸ߶ȳ���ÿ������ĸ߶�+�о�
		
		//��������
		percentPaint.setColor(m_textColor);
		mPaint.setColor(m_textColor);//����
		mPaint.setTextSize(m_fontSize);//�����С 24
	}
	
	
	/**
	 * ��ȡ�ǵ�һҳ
	 * @return m_isfirstPage
	 */
	public boolean get_m_isfirstPage() {
		return m_isfirstPage;
	}
	/**
	 * ��ȡ�����һҳ
	 * @return m_islastPage
	 */
	public boolean get_m_islastPage() {
		return m_islastPage;
	}
	
	
	// ��������ǩ ��Ҫʹ�õ�����������
	public int getM_mbBufBegin() {
		return m_mbBufBegin;
	}
	public void setM_mbBufBegin(int m_mbBufBegin) {
		this.m_mbBufBegin = m_mbBufBegin;
	}

	public Vector<String> getM_lines() {
		return m_lines;
	}
	public void setM_lines(Vector<String> m_lines) {
		this.m_lines = m_lines;
	}
	public void clearM_lines(){
		this.m_lines.clear();
	}
	
	public int getM_mbBufEnd() {
		return m_mbBufEnd;
	}
	public void setM_mbBufEnd(int m_mbBufEnd) {
		this.m_mbBufEnd = m_mbBufEnd;
	}

	public int getM_fontSize() {
		return m_fontSize;
	}
	public void setM_fontSize(int m_fontSize) {
		this.m_fontSize = m_fontSize;
		mPaint.setTextSize(m_fontSize);
		mLineCount = (int) (mVisibleHeight / m_fontSize); // ����ʾ������ / ����ʾ�ĸ߶ȳ���ÿ������ĸ߶�
	}

	public String getStrPercent() {
		return strPercent;
	}
	public void setStrPercent(String strPercent) {
		this.strPercent = strPercent;
	}
	public String calcStrPercent(int position) {
		float fPercent= (float) (position * 1.0 / m_mbBufLen);
		DecimalFormat df = new DecimalFormat("#0.0");
		 strPercent = df.format(fPercent * 100) + "%";
		return strPercent;
	}
	
}
