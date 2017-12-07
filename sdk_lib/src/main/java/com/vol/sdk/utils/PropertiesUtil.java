package com.vol.sdk.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	private static Properties prop = null;
	
	public PropertiesUtil(){}

	public PropertiesUtil(Context context, String filePath){
		InputStream in = null;
		try {
			in = context.getAssets().open(filePath);
			prop = new Properties();
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getProperty(String name, String defValue) {
		if(prop != null){
			return prop.getProperty(name, defValue);
		}
		return "";
	}
	public String getPropForKey(InputStream fileStream, String key) {
		String value = "";
		Properties properties = getProperties(fileStream);
		if (properties != null) {
			value = trimQuotationMark((String) properties.get(key));
		}
		return value;
	}
	
	/**
	 * 去掉双引号
	 * @param path
	 * @return
	 */
	private static String trimQuotationMark(String value) {
		if (TextUtils.isEmpty(value)) {
			return value;
		}
		if (value.contains("\"")) {
			value  = value.replace("\"", "");
			if (!TextUtils.isEmpty(value)) {
				value = value.trim();
			}
		}
		return value;
	}
	
	
	private Properties getProperties(InputStream fileStream){
		try {
			Properties prop = new Properties();
			prop.load(fileStream);
			return prop;
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
