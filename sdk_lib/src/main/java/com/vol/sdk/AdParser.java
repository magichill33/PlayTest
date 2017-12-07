package com.vol.sdk;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

public class AdParser {
	private String httpMessage;
	private Ad ad;

	public AdParser(String httpMessage) {
		this.httpMessage = httpMessage;
	}

	public Ad getAd() {
		return ad;
	}

	public void parser() {
		XmlPullParserFactory xmlPullParserFactory;
		try {
			xmlPullParserFactory = XmlPullParserFactory.newInstance();
			xmlPullParserFactory.setNamespaceAware(true);
			XmlPullParser xpp = xmlPullParserFactory.newPullParser();
			xpp.setInput(new StringReader(httpMessage));
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					ad = new Ad();
					ad.setAdxml(httpMessage);
					break;
				case XmlPullParser.START_TAG:
					if ("reqno".equals(xpp.getName())) {
						ad.setReqno(xpp.nextText());
					}
					if ("status".equals(xpp.getName())) {
						ad.setStatus(xpp.nextText());
					}
					if ("resultdesc".equals(xpp.getName())) {
						ad.setResultDesc(xpp.nextText());
					}
					if ("priview".equals(xpp.getName())) {
						ad.setPriview(xpp.nextText());
					}
					if ("play_url".equals(xpp.getName())) {
						ad.setPlayUrl(xpp.nextText());
					}
					if ("etime".equals(xpp.getName())) {
						String priviewTime = xpp.nextText();
						if (priviewTime == null || "".equals(priviewTime)) {
							priviewTime = "0";
						}
						ad.setPriviewTime(priviewTime);
					}
					if ("delaydeduct".equals(xpp.getName())) {
						ad.setDelayDeduct(xpp.nextText());
					}
					if ("pid".equals(xpp.getName())) {
						ad.setPid(xpp.nextText());
					}
					if ("auth".equals(xpp.getName())) {
						ad.setAuthCode(xpp.nextText());
					}
					if ("leuu".equals(xpp.getName())) {
						ad.setLeuu(xpp.nextText());
					}
					if ("lep".equals(xpp.getName())) {
						ad.setLep(xpp.nextText());
					}
					if ("lefid".equals(xpp.getName())) {
						ad.setLefid(xpp.nextText());
					}
					if ("isliveshow".equals(xpp.getName())) {
						ad.setIsLiveShow(xpp.nextText());
					}
					if ("mpbtime".equals(xpp.getName())) {
						ad.setMpbTime(xpp.nextText());
					}
					if ("isjump".equals(xpp.getName())) {
						ad.setIsJumpPlay(xpp.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				eventType = xpp.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
