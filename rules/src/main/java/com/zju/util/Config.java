package com.zju.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
	
	@SuppressWarnings("unused")
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
	
	// 为每个配置项添加获取方法
	public static String getCatcherUrl() {
		return getCatcherUrl("");
	}

	public static String getCatcherUrl(String defaultValue) {
		return getString("catcher.url", defaultValue);
	}
	
	public static long getAuditInterval() {
		return getAuditInterval(300000);
	}

	public static long getAuditInterval(long defaultValue) {
		return getLong("audit.interval", defaultValue);
	}
	
	public static int getAuditMaxCount() {
		return getAuditMaxCount(50);
	}

	public static int getAuditMaxCount(int defaultValue) {
		return getInt("audit.max.count", defaultValue);
	}
	
	public static int getOnceAuditMaxCount() {
		return getOnceAuditMaxCount(100);
	}

	public static int getOnceAuditMaxCount(int defaultValue) {
		return getInt("once.audit.max.count", defaultValue);
	}
}
