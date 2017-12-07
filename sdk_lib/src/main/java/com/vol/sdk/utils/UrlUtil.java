package com.vol.sdk.utils;

import android.net.Uri;


public class UrlUtil {
	public static String getValueFromUrl(String url, String param) {
		Uri uri = Uri.parse(url);
		return uri.getQueryParameter(param);
	}
}
