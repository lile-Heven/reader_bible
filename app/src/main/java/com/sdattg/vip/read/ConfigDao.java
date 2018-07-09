package com.sdattg.vip.read;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/** ���������û�������Ϣ */
public class ConfigDao {
	private SharedPreferences sp;

	public ConfigDao(Context context) {
		sp = context.getSharedPreferences("userConfig", Context.MODE_PRIVATE);
	}

	/**��ȡ�û�����
	 * �������� 
	 * ����ֵ��String�ͣ��û����룬Ĭ�ϲ�ʹ������ʱ����ֵΪnull */
	public String getPaw() {
		return sp.getString("pwd", null);// ��ȡ����
	}

	/**�ж�����Ƿ�ҹ��ģʽinNight 
	 * �������� 
	 * ����ֵ��boolean��true��ҹ��ģʽ��false���ռ�ģʽ*/
	public boolean getInNight() {
		return sp.getBoolean("inNight", false);
	}

	/**�ж���Ļ�����Ƿ����ϵͳlightFollowSys 
	 * �������� 
	 * ����ֵ��boolean��true������ϵͳ��false���Զ���*/
	public boolean getLightFollowSys() {
		return sp.getBoolean("lightFollowSys", true);
	}

	/**��ȡ��Ļ����screenLight 
	 * �������� 
	 * ����ֵ��int*/
	public int getScreenLight() {
		return sp.getInt("screenLight", 1);// ע��Ĭ��ֵ�ݶ�Ϊ1��������
	}

	/**��ȡ��Ϣ����ʱ��restTime 
	 * �������� 
	 * ����ֵ��int �����ӣ�*/
	public int getRestTime() {
		return sp.getInt("restTime", -1);
	}

	/**��ȡ��Ļ�ر�ʱ��screenCloseTime 
	 * �������� 
	 * ����ֵ��int�����ӣ�*/
	public int getScreenCloseTime() {
		return sp.getInt("screenCloseTime", -1);
	}

	//ɾ��
	/**��ȡ�Ķ�ͼ��ʱ����Ļ����screenOrientation�� Ĭ��Ϊ��ֱ����ScreenOrientation. VERTICAL 
	 * ��������
	 * ����ֵ��int 
	 *     com.horizon.global���е����ȫ�ֱ����� 
	 *     ��Ļˮƽ��ʾ�� ScreenOrientation.HORIZONTAL = 0 
	 *     ��Ļ��ֱ��ʾ��ScreenOrientation.VERTICAL = 1*/
	public int getScreenOrientation() {
		return sp.getInt("screenOrientation", ScreenOrientation.VERTICAL);
	}

	/**��ȡ�Ķ�ҳ�����ұ߾�pageRLMargin default:15
	 * �������� 
	 * ����ֵ��int*/
	public int getPageRLMargin() {
		return sp.getInt("pageRLMargin", 15);
	}

	/**��ȡ�Ķ�ҳ�����±߾�pageTBMargin default:20
	 * �������� 
	 * ����ֵ��int*/
	public int getPageTBMargin() {
		return sp.getInt("pageTBMargin", 25);
	}

	/**��ȡ�Ķ�ҳ���м��lineSpacing default:10
	 * �������� 
	 * ����ֵ��int*/
	public int getLineSpacing() {
		return sp.getInt("lineSpacing", 15);
	}

	//ɾ��
	/**��ȡ�Ķ�ҳ��μ��passageSpacing default:10
	 * �������� 
	 * ����ֵ��int*/
	public int getPassageSpacing() {
		return sp.getInt("passageSpacing", 10);
	}

	//ɾ��
	/**�ж��Ķ�ҳ���Ƿ���������textIndent 
	 * �������� 
	 * ����ֵ��boolean��true������������false�����в�����*/
	public boolean getTextIndent() {
		return sp.getBoolean("textIndent", true);
	}

	//ɾ��
	/**��ȡ�Ķ�ҳ�淭ҳģʽpageTurnType 
	 * �������� 
	 * ����ֵ��int 
	 *     com.horizon.global���е����ȫ�ֱ����� 
	 *     �޶�����PageTurnType. NO_ANIMATION = 0 
	 *     ��ʵ��ҳ��PageTurnType. IMITATE = 1
	 *     ���򸲸ǣ�PageTurnType. CROSSWISE = 2 
	 *     �����϶���PageTurnType. FREE_DRAG = 3*/
	public int getPageTurnType() {
		return sp.getInt("pageTurnType", PageTurnType.CROSSWISE);
	}

	//ɾ��
	/**��ȡ�Ķ�ҳ��Ĭ������defaultTheme�� default:
	 * ��������
	 * ����ֵ��int:����R�ļ��еĶ�Ӧid*/
	public int getDefaultTheme() {
		return sp.getInt("defaultTheme", 0);// ע��Ĭ�����������
	}

	/**��ȡ�Ķ�ҳ��������ɫtxtColour default:0xff000000
	 * �������� 
	 * ����ֵ��int:����R�ļ��еĶ�Ӧid*/
	public int getTxtColour() {
		return sp.getInt("txtColour", 0xff000000);
	}

	/**��ȡ�Ķ�ҳ�����ִ�СtxtSize default:24
	 * �������� 
	 * ����ֵ��int:����R�ļ��еĶ�Ӧid*/
	public int getTxtSize() {
		return sp.getInt("txtSize", 60);
	}

	/**��ȡ�Ķ�ҳ�汳����ɫbackColour default:0xfffed189
	 * �������� 
	 * ����ֵ��int:����R�ļ��еĶ�Ӧid*/
	public int getBackColour() {
		return sp.getInt("backColour", 0xfffed189);
	}

	//ɾ��
	/**��ȡ�Ķ�ҳ�汳��ͼƬ
	 * backImg �������� 
	 * ����ֵ��int:����R�ļ��еĶ�Ӧid*/
	public int getBackImg() {
		return sp.getInt("backImg", 0);// ע������ͼƬ����ȶ
	}

	
	
	/**�����û�����
	 *  ������paw (String) 
	 *  ����ֵ����*/
	public void setPaw(String paw) {
		Editor editor = sp.edit();
		editor.putString("pwd", paw);
		editor.commit();
	}
	
	/**ɾ���û�����
	 *  ������paw (String) 
	 *  ����ֵ����*/
	public void removePaw() {
		Editor editor = sp.edit();
		editor.remove("pwd");
		editor.commit();
	}

	/**����ҹ��ģʽinNight 
	 * ������inNight(boolean) 
	 * ����ֵ����*/
	public void setInNight(boolean inNight) {
		Editor editor = sp.edit();
		editor.putBoolean("inNight", inNight);
		editor.commit();
	}

	/**������Ļ���ȸ���ϵͳlightFollowSys 
	 * ������lightFollowSys(boolean) 
	 * ����ֵ����*/
	public void setLightFollowSys(boolean lightFollowSys) {
		Editor editor = sp.edit();
		editor.putBoolean("lightFollowSys", lightFollowSys);
		editor.commit();
	}
	
	/**������Ļ����screenLight
	 * ������screenLight  (int)
	 * ����ֵ����*/
	public void setScreenLight(int screenLight) {
		Editor editor = sp.edit();
		editor.putInt("screenLight", screenLight);
		editor.commit();
	}
	
	/**������Ϣ����ʱ��restTime
	 * ������restTime  (int)
	 * ����ֵ����*/
	public void setRestTime(int restTime) {
		Editor editor = sp.edit();
		editor.putInt("restTime", restTime);
		editor.commit();
	}
	
	/**������Ļ�ر�ʱ��screenCloseTime
	 * ������screenCloseTime  (int)
	 * ����ֵ����*/
	public void setScreenCloseTime(int screenCloseTime) {
		Editor editor = sp.edit();
		editor.putInt("screenCloseTime", screenCloseTime);
		editor.commit();
	}
	
	//ɾ��
	/**�����Ķ�ͼ��ʱ����Ļ����screenOrientation��Ĭ��Ϊ��ֱ����ScreenOrientation. VERTICAL��
	 * ������screenOrientation (int)
	 * com . horizon . global���е����ȫ�ֱ�����
	 * ��Ļˮƽ��ʾ�� ScreenOrientation. HORIZONTAL = 0
	 * ��Ļ��ֱ��ʾ��ScreenOrientation. VERTICAL = 1
	 * ����ֵ����*/
	public void setScreenOrientation(int screenOrientation) {
		Editor editor = sp.edit();
		editor.putInt("screenOrientation", screenOrientation);
		editor.commit();
	}
	
	/**�����Ķ�ҳ�����ұ߾�pageRLMargin��
	 * ������pageRLMargin (int)
	 * ����ֵ����*/
	public void setPageRLMargin(int pageRLMargin) {
		Editor editor = sp.edit();
		editor.putInt("pageRLMargin", pageRLMargin);
		editor.commit();
	}
	
	/**�����Ķ�ҳ�����±߾�pageTBMargin��
	 * ������pageTBMargin (int)
	 * ����ֵ����*/
	public void setPageTBMargin(int pageTBMargin) {
		Editor editor = sp.edit();
		editor.putInt("pageTBMargin", pageTBMargin);
		editor.commit();
	}
	
	/**�����Ķ�ҳ���м��lineSpacing��
	 * ������lineSpacing (int)
	 * ����ֵ����*/
	public void setLineSpacing(int lineSpacing) {
		Editor editor = sp.edit();
		editor.putInt("lineSpacing", lineSpacing);
		editor.commit();
	}
	
	//ɾ��
	/**�����Ķ�ҳ��μ��passageSpacing��
	 * ������passageSpacing (int)
	 * ����ֵ����*/
	public void setPassageSpacing(int passageSpacing) {
		Editor editor = sp.edit();
		editor.putInt("passageSpacing", passageSpacing);
		editor.commit();
	}
	
	//ɾ��
	/**�����Ķ�ҳ����������textIndent��
	 * ������textIndent (boolean)
	 * ����ֵ����*/
	public void setTextIndent(boolean textIndent) {
		Editor editor = sp.edit();
		editor.putBoolean("textIndent", textIndent);
		editor.commit();
	}
	
	//ɾ��
	/**�����Ķ�ҳ�淭ҳģʽpageTurnType��
	 * ������pageTurnType (int)
	 * com . horizon . global���е����ȫ�ֱ�����
	 * �޶�����  PageTurnType. NO_ANIMATION = 0
	 * ��ʵ��ҳ��PageTurnType. IMITATE = 1
	 * ���򸲸ǣ�PageTurnType. CROSSWISE = 2
	 * �����϶���PageTurnType. FREE_DRAG = 3
	 * ����ֵ����*/
	public void setPageTurnType(int pageTurnType) {
		Editor editor = sp.edit();
		editor.putInt("pageTurnType", pageTurnType);
		editor.commit();
	}
	
	//ɾ��
	/**�����Ķ�ҳ��Ĭ������defaultTheme��
	 * ������defaultTheme (int)������R�ļ��еĶ�Ӧid
	 * ����ֵ����*/
	public void setDefaultTheme(int defaultTheme) {
		Editor editor = sp.edit();
		editor.putInt("defaultTheme", defaultTheme);
		editor.commit();
	}
	
	/**�����Ķ�ҳ��������ɫtxtColour��
	 * ������txtColour (int)������R�ļ��еĶ�Ӧid
	 * ����ֵ����*/
	public void setTxtColour(int txtColour) {
		Editor editor = sp.edit();
		editor.putInt("txtColour", txtColour);
		editor.commit();
	}

	/**�����Ķ�ҳ�����ִ�СtxtSize
	 * �������� 
	 * ����ֵ����*/
	public void setTxtSize(int txtSize) {
		Editor editor = sp.edit();
		editor.putInt("txtSize", txtSize);
		editor.commit();
	}

	/**�����Ķ�ҳ�汳����ɫbackColour��
	 * ������backColour (int)������R�ļ��еĶ�Ӧid
	 * ����ֵ����*/
	public void setBackColour(int backColour) {
		Editor editor = sp.edit();
		editor.putInt("backColour", backColour);
		editor.commit();
	}
	
	//ɾ��
	/**�����Ķ�ҳ�汳��ͼƬbackImg
	 * ������backImg (int)������R�ļ��еĶ�Ӧid
	 * ����ֵ����*/
	public void setBackImg(int backImg) {
		Editor editor = sp.edit();
		editor.putInt("backImg", backImg);
		editor.commit();
	}
}
