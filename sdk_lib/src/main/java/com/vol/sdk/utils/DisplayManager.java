package com.vol.sdk.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayManager {
	private static DisplayManager instance = new DisplayManager();
	private float value;

	public void init(Context context) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		displayMetrics = context.getResources().getDisplayMetrics();
		screenWidth = displayMetrics.widthPixels;
		screenHeight = displayMetrics.heightPixels;
		LogUtil.d("DisplayManager real size-->" + screenWidth + "x" + screenHeight + ",screenDpi:" + screenDpi);
		if (screenHeight < 720 && screenHeight > 480) {
			screenHeight = 720;
		} else if (screenHeight > 720 && screenHeight < 1080) {
			screenHeight = 1080;
		}
		screenDpi = displayMetrics.densityDpi;
		value = displayMetrics.scaledDensity;
		LogUtil.d("DisplayManager adapt size-->" + screenWidth + "x" + screenHeight + ",screenDpi:" + screenDpi);
	}

	private DisplayManager() {

	}

	public static DisplayManager GetInstance() {
		return instance;
	}

	private static final int STANDARD_WIDTH = 1280;
	private static final int STANDARD_HEIGHT = 720;
	private static final int STANDARD_DENSITY = 160;

	private int screenWidth = 0;
	private int screenHeight = 0;
	private int screenDpi = 0;

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public int getScreenDpi() {
		return screenDpi;
	}

	public int changeTextSize(int size) {
		float rate_width = (float) screenWidth / STANDARD_WIDTH;
		// float rate_height = (float) screenHeight / STANDARD_HEIGHT;
		// float rate_density = (float) STANDARD_DENSITY / screenDpi;
		// return (int)(size * rate_width * rate_height * rate_density);
		// return (int)(size * rate_density);
		// return (int) (size/value);
		// return (int)(size * rate_density);
		if (value != 1.0 && STANDARD_WIDTH == screenWidth) {
			// return size;
			return (int) (size / value);
		}
		if (screenDpi == 240) {
			return size;
		}
		return (int) (size / rate_width);
	}

	public int changePaintSize(int size) {
		return changeWidthSize(size);
	}

	public int changeWidthSize(int size) {
		return size * screenWidth / STANDARD_WIDTH;
	}

	public int changeHeightSize(int size) {
		return size * screenHeight / STANDARD_HEIGHT;
	}
}
