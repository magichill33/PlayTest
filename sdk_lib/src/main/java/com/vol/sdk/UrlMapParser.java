package com.vol.sdk;


import com.vol.sdk.base.BaseParser;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;
import java.util.Map;

public class UrlMapParser extends BaseParser {
	private UrlMap urlList;

	public UrlMap getUrlList() {
		return urlList;
	}
	
	private Map<String, String> urlMap = null;

	@Override
	public void parse(XmlPullParser xpp) throws Exception {
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				urlList = new UrlMap();
				urlMap = new HashMap<String, String>();
				urlList.setUrlMap(urlMap);
				break;
			case XmlPullParser.START_TAG:
				if ("UrlList".equalsIgnoreCase(xpp.getName())) {
					String key = xpp.getAttributeValue(0);
					eventType = xpp.next();
					String value = xpp.getText().trim();
					urlMap.put(key, value);
				}
				break;
			default:
				break;
			}
			eventType = xpp.next();
		}
	}
}
