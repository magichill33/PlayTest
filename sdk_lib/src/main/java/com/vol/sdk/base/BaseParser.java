package com.vol.sdk.base;


import com.vol.sdk.utils.NetUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

public abstract class BaseParser {
	private InputStream stream = null;

	public void setUrl(String url) throws Exception {
		stream = NetUtil.connectServer(url, 2, 5, 20);
		if (stream != null) {
			setInputStream(stream);
		}
	}

	public void setHttpsUrl(String url) throws Exception {
		stream = NetUtil.connectServerWithHttps(url, 2, 5, 20);
		if (stream != null) {
			setInputStream(stream);
		}
	}

	public void postUrl(String url, String param) throws Exception {
		stream = NetUtil.doPost(url, param);
		if (stream != null) {
			setInputStream(stream);
		}
	}

	public void postHttpsUrl(String url, String param) throws Exception {
		stream = NetUtil.doPostWithHttps(url, param);
		if (stream != null) {
			setInputStream(stream);
		}
	}

	public InputStream getStream() {
		return this.stream;
	}

	public void setInputStream(InputStream inputStream) throws Exception {
		if (inputStream != null) {
			XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
			xmlPullParserFactory.setNamespaceAware(true);
			XmlPullParser xpp = xmlPullParserFactory.newPullParser();
			xpp.setInput(inputStream, "UTF-8");
			parse(xpp);
			inputStream.close();
			inputStream = null;
		}
	}

	public abstract void parse(XmlPullParser xpp) throws Exception;

}
