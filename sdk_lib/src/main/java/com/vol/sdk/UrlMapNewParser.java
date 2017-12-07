package com.vol.sdk;


import com.vol.sdk.base.BaseParser;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;
import java.util.Map;

public class UrlMapNewParser extends BaseParser {
	private UrlMap urlList;

	public UrlMap getUrlList() {
		return urlList;
	}
	
	private Map<String, String> urlMap = null;

	@Override
	public void parse(XmlPullParser xpp) throws Exception {
		String tagName;
		String key = null;
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				urlList = new UrlMap();
				urlMap = new HashMap<String, String>();
				urlList.setUrlMap(urlMap);
				break;
			case XmlPullParser.START_TAG:
				tagName = xpp.getName();
				if ("Status".equalsIgnoreCase(tagName)) {
					urlList.setStatus(xpp.nextText());
				} else if ("Message".equalsIgnoreCase(tagName)) {
					urlList.setResultDesc(xpp.nextText());
				} else if ("Url".equalsIgnoreCase(tagName)) {
					key = xpp.getAttributeValue(0);
				} else if ("SourceUrl".equalsIgnoreCase(tagName)) {
					String value = xpp.nextText();
					urlMap.put(key, value);
				}
				break;
			case XmlPullParser.END_TAG:
				break;
			}
			eventType = xpp.next();
		}
	}
}
