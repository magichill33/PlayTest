package com.vol.sdk;


import com.vol.sdk.base.BaseParser;

import org.xmlpull.v1.XmlPullParser;

public class UrlListNewParser extends BaseParser {
	private UrlList urlList;

	public UrlList getUrlList() {
		return urlList;
	}

	@Override
	public void parse(XmlPullParser xpp) throws Exception {
		String tagName;
		String value = null;
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				urlList = new UrlList();
				break;
			case XmlPullParser.START_TAG:
				tagName = xpp.getName();
				if ("Status".equalsIgnoreCase(tagName)) {
					urlList.setStatus(xpp.nextText());
				} else if ("Message".equalsIgnoreCase(tagName)) {
					urlList.setResultDesc(xpp.nextText());
				} else if ("Url".equalsIgnoreCase(tagName)) {
					value = xpp.getAttributeValue(0);
				} else if ("SourceUrl".equalsIgnoreCase(tagName)) {
					if ("paylist".equals(value)) {
						urlList.setPayList(xpp.nextText());
					} else if ("voole_recommenended".equals(value)) {
						urlList.setRecommend(xpp.nextText());
					} else if ("tv_cs_union".equals(value)) {
						// urlList.setUnion(xpp.nextText());
					} else if ("tv_cs_topic".equals(value)) {
						urlList.setTopic(xpp.nextText());
					} else if ("tv_cs_rank_film".equals(value)) {
						urlList.setRankFilm(xpp.nextText());
					} else if ("tv_cs_rank_teleplay".equals(value)) {
						urlList.setRankTeleplay(xpp.nextText());
					} else if ("tv_cs_message".equals(value)) {
						urlList.setMessage(xpp.nextText());
					} else if ("tv_cs_related".equals(value)) {
						urlList.setRelated(xpp.nextText());
					} else if ("tv_cs_searchall".equals(value)) {
						urlList.setSearchAll(xpp.nextText());
					} else if ("alert".equals(value)) {
						urlList.setUiHint(xpp.nextText());
					} else if ("tv_cs_account".equals(value)) {
						urlList.setAccount(xpp.nextText());
					} /*
						 * else if ("tv_cs_product".equals(value)) {
						 * urlList.setProduct(xpp.nextText()); }
						 */else if ("tv_cs_transscreen".equals(value)) {
						urlList.setTransScreen(xpp.nextText());
					} else if ("tv_cs_search_channel".equals(value)) {
						// urlList.setSearchChannel(xpp.nextText());
					} else if ("LoginAuth".equals(value)) {
						// urlList.setLoginAuth(xpp.nextText());
					} else if ("ad_upgrade_url".equals(value)) {
						urlList.setUpgradeCheck(xpp.nextText());
					} else if ("voole_topview".equals(value)) {
						// urlList.setTopView(xpp.nextText());
					} else if ("agreement".equals(value)) {
						urlList.setOrderAgreement(xpp.nextText());
					} else if ("user_register_agreement".equals(value)) {
						urlList.setRegisterAgreement(xpp.nextText());
					} else if ("playauth".equals(value)) {
						// urlList.setPlayAuth(xpp.nextText());
					} else if ("uni_pay".equals(value)) {
						urlList.setUniPay(xpp.nextText());
					} else if ("skyworth_register".equals(value)) {
						// urlList.setSkyworthRegister(xpp.nextText());
					} else if ("skyworth_update_tel".equals(value)) {
						// urlList.setSkyworthUpdateTel(xpp.nextText());
					} else if ("epg_ad_url".equals(value)) {
						urlList.setEpgAdUrl(xpp.nextText());
					} else if ("interaction_ad_url".equals(value)) {
						urlList.setInteractionAdUrl(xpp.nextText());
					} else if ("play_ad_report".equals(value)) {
						urlList.setReport(xpp.nextText());
					} else if ("live_download_url".equals(value)) {
						// urlList.setLiveDownload(xpp.nextText());
					} else if ("speedtest".equals(value)) {
						urlList.setSpeedTest(xpp.nextText());
					} else if ("speedtestpost".equals(value)) {
						urlList.setSpeedTestPost(xpp.nextText());
					} else if ("voole_recommenended_bottom".equals(value)) {
						urlList.setVooleRecommenendedBottom(xpp.nextText());
					} else if ("terminal_info_stat".equals(value)) {
						urlList.setTerminalInfoStat(xpp.nextText());
					} else if ("report_ad_url".equals(value)) {
						urlList.setReportAdUrl(xpp.nextText());
					} else if ("adversion".equals(value)) {
						urlList.setAdversion(xpp.nextText());
					} else if ("playReport".equals(value)) {
						urlList.setPlayReport(xpp.nextText());
					} else if ("cachenotice".equals(value)) {
						urlList.setCacheNotice(xpp.nextText());
					} else if ("karaoke_download_url".equals(value)) {
						urlList.setKaraokeDownload(xpp.nextText());
					} else if ("tv_cs_topic_anli".equals(value)) {
						urlList.setTopicAnli(xpp.nextText());
					} else if ("tv_cs_topic_sansheng".equals(value)) {
						urlList.setTopicSansheng(xpp.nextText());
					} else if ("weixinqrcode".equals(value)) {
						urlList.setWeixinqrCode(xpp.nextText());
					} else if ("weixinreport".equals(value)) {
						urlList.setWeixinReport(xpp.nextText());
					} else if ("livetvnew".equals(value)) {
						urlList.setLiveTVUrl(xpp.nextText());
					} else if ("tv_cs_transscreen_favorite".equals(value)) {
						urlList.setTransscreenFavorite(xpp.nextText());
					} else if ("tv_cs_transscreen_resume".equals(value)) {
						urlList.setTransscreenResume(xpp.nextText());
					} else if ("guidedownload".equals(value)) {
						urlList.setGuideDownload(xpp.nextText());
					} else if ("remotedownload".equals(value)) {
						urlList.setRemoteDownload(xpp.nextText());
					} else if ("linkshort".equals(value)) {
						urlList.setLinkShort(xpp.nextText());
					} else if ("ott_plate_auth_url".equals(value)) {
						urlList.setOttPlateAuthUrl(xpp.nextText());
					} else if ("ott_plate_auth_policy".equals(value)) {
						urlList.setOttPlateAuthPolicy(xpp.nextText());
					} else if ("sohulogo".equals(value)) {
						urlList.setSohulogo(xpp.nextText());
					} else if ("terminal_log_url".equals(value)) {
						urlList.setTerminalLogUrl(xpp.nextText());
					} else if ("XmppInfo".equals(value)) {
						urlList.setXmppInfo(xpp.nextText());
					} else if ("babyepg".equals(value)) {
						urlList.setBabyepg(xpp.nextText());
					} else if ("mosttvchannel".equals(value)) {
						urlList.setMosttvchannel(xpp.nextText());
					} else if ("error_qq".equals(value)) {
						urlList.setError_qq(xpp.nextText());
					} else if ("error_weixin".equals(value)) {
						urlList.setError_weixin(xpp.nextText());
					} else if ("xmppUser".equals(value)) {
						urlList.setXmppUser(xpp.nextText());
					}else if ("currentTime".equalsIgnoreCase(value)) {
						urlList.setCurrentTime(xpp.nextText());
					}else if ("livetvjson".equalsIgnoreCase(value)) {
						urlList.setLiveTVJson(xpp.nextText());
					}
				}
				break;
			case XmlPullParser.END_TAG:
				break;
			}
			eventType = xpp.next();
		}
	}
}
