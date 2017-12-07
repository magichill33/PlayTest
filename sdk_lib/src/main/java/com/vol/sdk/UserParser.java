package com.vol.sdk;


import com.vol.sdk.base.BaseParser;

import org.xmlpull.v1.XmlPullParser;

public class UserParser extends BaseParser {
	private User user = null;

	public User getUser() {
		return user;
	}

	@Override
	public void parse(XmlPullParser xpp) throws Exception {
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					user = new User();
					break;
				case XmlPullParser.START_TAG:
					String tag = xpp.getName();
					if ("user".equals(tag)) {
					} else if ("status".equals(tag)) {
						user.setStatus(xpp.nextText());
					} else if ("uid".equals(tag)) {
						user.setUid(xpp.nextText());
					} else if ("hid".equals(tag)) {
						user.setHid(xpp.nextText());
					} else if ("oemid".equals(tag)) {
						user.setOemid(xpp.nextText());
					} else if ("sid".equals(tag)) {
						user.setSid(xpp.nextText());
					} else if ("portal".equals(tag)) {
						user.setPortal(xpp.nextText());
//						user.setPortal("http://172.16.10.25:8080/interface/receiveinfo?");
					}else if ("version".equals(tag)) {
						user.setVersion(xpp.nextText());
					}else if ("buildtime".equals(tag)) {
						user.setBuildtime(xpp.nextText());
					}
					
					break;
				default:				
					break;
			}
			eventType = xpp.next();
		}
	}
}
