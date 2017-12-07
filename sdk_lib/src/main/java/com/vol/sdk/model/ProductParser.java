package com.vol.sdk.model;


import com.vol.sdk.utils.LogUtil;
import com.vol.sdk.utils.NetUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

public class ProductParser {
	private InputStream stream = null;
	protected Product product = null;
	
	public Product getProduct() {
		return product;
	}

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

	public void setInputStream(InputStream inputStream) throws Exception{
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
	
	private void parse(XmlPullParser xpp) throws Exception {
		LogUtil.i("ProductParser--->parse------>start");
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if ("filmproduct".equalsIgnoreCase(xpp.getName())) {
					product = new Product();
					int size = xpp.getAttributeCount();
					for (int i = 0; i < size; i++) {
						if ("portalid".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setPortalid(xpp.getAttributeValue(i));
						}else if("parentid".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setParentid(xpp.getAttributeValue(i));
						}else if("pid".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setPid(xpp.getAttributeValue(i));
						}else if("name".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setName(xpp.getAttributeValue(i));
						}else if("usefullife".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setUsefullife(xpp.getAttributeValue(i));
						}else if("spid".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setSpid(xpp.getAttributeValue(i));
						}else if("mtype".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setMtype(xpp.getAttributeValue(i));
						}else if("note".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setNote(xpp.getAttributeValue(i));
						}else if("ptype".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setPtype(xpp.getAttributeValue(i));
						}else if("fee".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setFee(xpp.getAttributeValue(i));
						}else if("oemtype".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setOemtype(xpp.getAttributeValue(i));
						}else if("isshare".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setIsshare(xpp.getAttributeValue(i));
						}else if("starttime".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setStarttime(xpp.getAttributeValue(i));
						}else if("stoptime".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setStoptime(xpp.getAttributeValue(i));
						}else if("costfee".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setCostfee(xpp.getAttributeValue(i));
						}else if("isorder".equalsIgnoreCase(xpp.getAttributeName(i))){
							product.setIsorder(xpp.getAttributeValue(i));
						}
					}
				} 
				break;
			case XmlPullParser.END_TAG:
				break;
			}
			eventType = xpp.next();
		}

		LogUtil.i("ProductParser--->parse------>end");
	}

}
