package com.vol.sdk.utils;

import android.util.Log;

public class LogUtil {
	private static String TAG = "live2.0";
	
	private static final byte LV = 0x20;
	private static final byte LD = 0x10;
	private static final byte LI = 0x08;
	private static final byte LW = 0x04;
	private static final byte LE = 0x02;
	private static final byte LA = 0x01;
	
	private static int sLevel = 0x3f;
	
	private static boolean isDisplayLog = true;
	
	public static void setDisplayLog(boolean b){
		isDisplayLog = b;
	}
	

	public static void setTag(String tag){
		if(tag != null && tag.length() > 0){
			TAG = tag;
		}
	}
	
	public static void setLevel(String level){
		int le = Integer.valueOf(level);
		setLevel(le);
	}
	
	public static void setLevel(int level){
		if(level >=0 && level <= 0x3f){
			sLevel = level;
		}
	}
	
	public static void v(String msg){
		if(( LV & sLevel) > 0 && isDisplayLog){
			Log.v(TAG,msg);
		}
	}
	

	public static void d(String msg){
		if(( LD & sLevel) > 0 && isDisplayLog){
			Log.d(TAG,msg);
		}

	}
	
	public static void i(String msg){
		if(( LI & sLevel) > 0 && isDisplayLog){
			Log.i(TAG,msg);
		}
	}
	
	public static void w(String msg){
		if(( LW & sLevel) > 0 && isDisplayLog){
			Log.w(TAG,msg);
		}
	}
	
	public static void e(String msg){
		if(( LE & sLevel) > 0 && isDisplayLog){
			Log.e(TAG,msg);
		}
	}

}
