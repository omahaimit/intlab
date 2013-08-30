package com.zju.catcher.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SuppressWarnings("unused")
public class Config {

	private static Properties prop = new Properties();
	
	static {
		init();
	}

	private static void init()  {
		
		try {
			InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties");
			prop.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Config() {}
	
	private static int getInt(String name, int defaultValue) {
		String value = prop.getProperty(name, String.valueOf(defaultValue));
		return Integer.parseInt(value);
	}
	
	private static boolean getBoolean(String name, boolean defaultValue) {
		String value = prop.getProperty(name, String.valueOf(defaultValue));
		return Boolean.parseBoolean(value);
	}
	
	private static long getLong(String name, long defaultValue) {
		String value = prop.getProperty(name, String.valueOf(defaultValue));
		return Long.parseLong(value);
	}
	
	private static String getString(String name, String defaultValue) {
		return prop.getProperty(name, defaultValue);
	}
	
	// Ϊÿ����������ӻ�ȡ����
	
	// ����һͬ�����ݵ�ʱ���� (����)
	public static long getSyncDataInterval() {
		return getSyncDataInterval(120000);
	}
	
	public static long getSyncDataInterval(long defaultValue) {
		return getLong("sync.data.interval", defaultValue);
	}
	
	// �� ͬ�������Ƿ�������
	public static boolean getSyncSelfStart() {
		return getSyncSelfStart(true);
	}
	
	public static boolean getSyncSelfStart(boolean defaultValue) {
		return getBoolean("sync.self.start", defaultValue);
	}
	
	// ���������д����һ��ʱ���� (����)
	public static long getWriteBackInterval() {
		return getWriteBackInterval(120000);
	}
	
	public static long getWriteBackInterval(long defaultValue) {
		return getLong("write.back.interval", defaultValue);
	}

	// �� ����д���Ƿ�������
	public static boolean getWriteBackSelfStart() {
		return getWriteBackSelfStart(false);
	}
	
	public static boolean getWriteBackSelfStart(boolean defaultValue) {
		return getBoolean("write.back.self.start", defaultValue);
	}
	
	// ���������д����һ��ʱ���� (����)
	public static long getMeasureInterval() {
		return getMeasureInterval(120000);
	}
	
	public static long getMeasureInterval(long defaultValue) {
		return getLong("measure.time.interval", defaultValue);
	}
}
