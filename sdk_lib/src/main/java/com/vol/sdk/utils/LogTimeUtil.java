package com.vol.sdk.utils;

import android.util.Log;

public class LogTimeUtil {

	private static long startTime = 0;

	public static void startLog() {
		startTime = System.currentTimeMillis();
	}

	public static void logTime(String tagName) {
		long currentTime = System.currentTimeMillis();
		Log.d("LogUtil",tagName + "--------->"
				+ (System.currentTimeMillis() - startTime));
		startTime = currentTime;
	}

}
