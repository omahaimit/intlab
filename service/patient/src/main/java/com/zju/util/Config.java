package com.zju.util;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Config {

	// 简单点，把变量公开
	public static AtomicBoolean currentFlag = new AtomicBoolean(false);
	public static AtomicBoolean smsStatus = new AtomicBoolean(false);
	public static AtomicInteger maxCount = new AtomicInteger(0);
	public static AtomicInteger fromAge = new AtomicInteger(0);
	public static AtomicInteger toAge = new AtomicInteger(100);
	public static String enbleStatus = "0,2";
	public static AtomicInteger sex = new AtomicInteger(0);
	public static AtomicLong interval = new AtomicLong(300000);
	public static String nativeUrl = "http://localhost/";
	
	// 辅助方法
	public static void setAge(int fromAge, int toAge) {
		if (fromAge >= 0 && toAge <= 100 && fromAge <= toAge) {
			Config.fromAge.set(fromAge);
			Config.toAge.set(toAge);
		}
	}
}
