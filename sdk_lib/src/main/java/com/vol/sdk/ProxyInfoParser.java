package com.vol.sdk;


import com.vol.sdk.base.BaseParser;

import org.xmlpull.v1.XmlPullParser;

public class ProxyInfoParser extends BaseParser {

	private ProxyInfo proxy;
	public ProxyInfo getProxy() {
		return proxy;
	}
	
	@Override
	public void parse(XmlPullParser xpp) throws Exception {
		int eventType = xpp.getEventType();
		String tag = "";
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				proxy = new ProxyInfo();
				break;
			case XmlPullParser.START_TAG:
				tag = xpp.getName();
				// Log.d("ACCOUNTINFOPARSER","Start:" + xpp.getName());
				break;
			case XmlPullParser.END_TAG:
				// Log.d("ACCOUNTINFOPARSER","End:" + xpp.getName());
				break;
			case XmlPullParser.TEXT:
				if ("info".equals(tag)) {
				} else if ("version".equals(tag)) {
					proxy.setVersion(xpp.getText());
				} else if ("build_time".equals(tag)) {
					proxy.setBuildTime(xpp.getText());
				} else if ("vlive_version".equals(tag)) {
					proxy.setVliveVersion(xpp.getText());
				} else if ("p2plive_version".equals(tag)) {
					proxy.setP2pliveVersion(xpp.getText());
				} else if ("vooletv_version".equals(tag)) {
					proxy.setVooletvVersion(xpp.getText());
				} else if ("m3u8_version".equals(tag)) {
					proxy.setM3u8Version(xpp.getText());
				} else if ("vbr_version".equals(tag)) {
					proxy.setVbrVersion(xpp.getText());
				} else if ("downspeed".equals(tag)) {
					proxy.setDownspeed(xpp.getText());
				}  
				break;
			}
			eventType = xpp.next();
		}
	}
	
	
	
}
