package com.vol.sdk;


import com.vol.sdk.base.BaseParser;

import org.xmlpull.v1.XmlPullParser;

public class UrlListParser extends BaseParser {
	private UrlList urlList;

	public UrlList getUrlList() {
		return urlList;
	}

	@Override
	public void parse(XmlPullParser xpp) throws Exception {
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				urlList = new UrlList();
				break;
			case XmlPullParser.START_TAG:
				if ("UrlList".equalsIgnoreCase(xpp.getName())) {
					// String value = xpp.getAttributeValue(null, "Key");
					String value = xpp.getAttributeValue(0);
					eventType = xpp.next();
					if ("paylist".equals(value)) {
						// urlList.setPayList("http://apk23.int.voole.com/vod_apk_2.3/vod/service/index/?spid=20120629&epgid=100895&oemid=817&hid=5CC6D0CBDE00&uid=103757151");
						// urlList.setPayList("http:///172.16.10.121:8002/vod_apk_2.3/vod/service/index/?spid=20120629&epgid=100895&oemid=817&hid=5CC6D0CBDE00&uid=103757151");
						urlList.setPayList(xpp.getText().trim());
					} else if ("voole_recommenended".equals(value)) {
						// urlList.setRecommend("http://apk23.int.voole.com/vod_apk_2.3/vod/service/index/?ctype=3&spid=20120629&epgid=100895&column=cate__ypTVbzbjrtj_1380006665&oemid=817&hid=5CC6D0CBDE00&uid=103757151");
						// urlList.setRecommend("http://172.16.10.121:8002/vod_apk_2.3/vod/service/index/?ctype=3&spid=20120629&epgid=100895&column=cate__ypTVbzbjrtj_1380006665&oemid=817&hid=5CC6D0CBDE00&uid=103757151");
						urlList.setRecommend(xpp.getText().trim());
					} else if ("tv_cs_union".equals(value)) {
						// urlList.setUnion(xpp.getText().trim());
					} else if ("tv_cs_topic".equals(value)) {
						urlList.setTopic(xpp.getText().trim());
						// urlList.setTopic("http://apk23.int.voole.com/vod_apk_2.3/vod/service/index/?ctype=2&spid=20120629&epgid=100895&column=5566");
						// urlList.setTopic("http://172.16.10.121:8002/vod_apk_2.3/vod/service/index/?ctype=2&spid=20120629&epgid=100895&column=5566");
					} else if ("tv_cs_rank_film".equals(value)) {
						urlList.setRankFilm(xpp.getText().trim());
					} else if ("tv_cs_rank_teleplay".equals(value)) {
						urlList.setRankTeleplay(xpp.getText().trim());
					} else if ("tv_cs_message".equals(value)) {
						urlList.setMessage(xpp.getText().trim());
					} else if ("tv_cs_related".equals(value)) {
						urlList.setRelated(xpp.getText().trim());
					} else if ("tv_cs_searchall".equals(value)) {
						urlList.setSearchAll(xpp.getText().trim());
					} else if ("alert".equals(value)) {
						urlList.setUiHint(xpp.getText().trim());
					} else if ("tv_cs_account".equals(value)) {
						urlList.setAccount(xpp.getText().trim());
					} /*
						 * else if ("tv_cs_product".equals(value)) {
						 * urlList.setProduct(xpp.getText().trim()); }
						 */else if ("tv_cs_transscreen".equals(value)) {
						urlList.setTransScreen(xpp.getText().trim());
					} else if ("tv_cs_search_channel".equals(value)) {
						// urlList.setSearchChannel(xpp.getText().trim());
					} else if ("LoginAuth".equals(value)) {
						// urlList.setLoginAuth(xpp.getText().trim());
					} else if ("ad_upgrade_url".equals(value)) {
						urlList.setUpgradeCheck(xpp.getText().trim());
					} else if ("voole_topview".equals(value)) {
						// urlList.setTopView(xpp.getText().trim());
					} else if ("agreement".equals(value)) {
						urlList.setOrderAgreement(xpp.getText().trim());
					} else if ("user_register_agreement".equals(value)) {
						urlList.setRegisterAgreement(xpp.getText().trim());
					} else if ("playauth".equals(value)) {
						// urlList.setPlayAuth(xpp.getText().trim());
					} else if ("uni_pay".equals(value)) {
						urlList.setUniPay(xpp.getText().trim());
					} else if ("skyworth_register".equals(value)) {
						// urlList.setSkyworthRegister(xpp.getText().trim());
					} else if ("skyworth_update_tel".equals(value)) {
						// urlList.setSkyworthUpdateTel(xpp.getText().trim());
					} else if ("epg_ad_url".equals(value)) {
						urlList.setEpgAdUrl(xpp.getText().trim());
					} else if ("interaction_ad_url".equals(value)) {
						urlList.setInteractionAdUrl(xpp.getText().trim());
					} else if ("play_ad_report".equals(value)) {
						urlList.setReport(xpp.getText().trim());
					} else if ("live_download_url".equals(value)) {
						// urlList.setLiveDownload(xpp.getText().trim());
					} else if ("speedtest".equals(value)) {
						urlList.setSpeedTest(xpp.getText().trim());
					} else if ("speedtestpost".equals(value)) {
						urlList.setSpeedTestPost(xpp.getText().trim());
					} else if ("voole_recommenended_bottom".equals(value)) {
						// urlList.setVooleRecommenendedBottom("http://apk23.int.voole.com/vod_apk_2.3/vod/service/index/?ctype=3&spid=20120629&epgid=100895&column=cate__ypOTVsytjwlb_1393847192&oemid=817&hid=5CC6D0CBDE00&uid=103757151");
						// urlList.setVooleRecommenendedBottom("http://172.16.10.121:8002/vod_apk_2.3/vod/service/index/?ctype=3&spid=20120629&epgid=100895&column=cate__ypOTVsytjwlb_1393847192&oemid=817&hid=5CC6D0CBDE00&uid=103757151");
						urlList.setVooleRecommenendedBottom(xpp.getText().trim());
					} else if ("terminal_info_stat".equals(value)) {
						urlList.setTerminalInfoStat(xpp.getText().trim());
					} else if ("report_ad_url".equals(value)) {
						urlList.setReportAdUrl(xpp.getText().trim());
					} else if ("adversion".equals(value)) {
						urlList.setAdversion(xpp.getText().trim());
					} else if ("playReport".equals(value)) {
						urlList.setPlayReport(xpp.getText().trim());
					} else if ("cachenotice".equals(value)) {
						urlList.setCacheNotice(xpp.getText().trim());
					} else if ("karaoke_download_url".equals(value)) {
						urlList.setKaraokeDownload(xpp.getText().trim());
					} else if ("tv_cs_topic_anli".equals(value)) {
						urlList.setTopicAnli(xpp.getText().trim());
					} else if ("tv_cs_topic_sansheng".equals(value)) {
						urlList.setTopicSansheng(xpp.getText().trim());
					} else if ("weixinqrcode".equals(value)) {
						urlList.setWeixinqrCode(xpp.getText().trim());
					} else if ("weixinreport".equals(value)) {
						urlList.setWeixinReport(xpp.getText().trim());
					} else if ("livetvnew".equals(value)) {
						urlList.setLiveTVUrl(xpp.getText().trim());
					} else if ("tv_cs_transscreen_favorite".equals(value)) {
						urlList.setTransscreenFavorite(xpp.getText().trim());
					} else if ("tv_cs_transscreen_resume".equals(value)) {
						urlList.setTransscreenResume(xpp.getText().trim());
					} else if ("guidedownload".equals(value)) {
						urlList.setGuideDownload(xpp.getText().trim());
					} else if ("remotedownload".equals(value)) {
						urlList.setRemoteDownload(xpp.getText().trim());
					} else if ("linkshort".equals(value)) {
						urlList.setLinkShort(xpp.getText().trim());
					} else if ("ott_plate_auth_url".equals(value)) {
						urlList.setOttPlateAuthUrl(xpp.getText().trim());
					} else if ("ott_plate_auth_policy".equals(value)) {
						urlList.setOttPlateAuthPolicy(xpp.getText().trim());
					} else if ("sohulog".equals(value)) {
						urlList.setSohulogo(xpp.getText().trim());
					} else if ("tv_cs_cinema".equals(value)) {
						urlList.setTvCsCinema(xpp.getText().trim());
					} else if ("querysigninrule".equals(value)) {
						urlList.setQuerySigninRule(xpp.getText().trim());
					} else if ("querymoivescore".equals(value)) {
						urlList.setQueryMoiveScore(xpp.getText().trim());
					} else if ("signin".equals(value)) {
						urlList.setSignIn(xpp.getText().trim());
					} else if ("shortmoiveplayend".equals(value)) {
						urlList.setShortMoivePlayend(xpp.getText().trim());
					} else if ("queryscorerecord".equals(value)) {
						urlList.setQueryScoreRecord(xpp.getText().trim());
					} else if ("actinfo".equals(value)) {
						urlList.setActInfo(xpp.getText().trim());
					} else if ("zadan".equals(value)) {
						urlList.setZadan(xpp.getText().trim());
					} else if ("prizerecords".equals(value)) {
						urlList.setPrizeRecords(xpp.getText().trim());
					} else if ("goodsinfo".equals(value)) {
						urlList.setGoodsInfo(xpp.getText().trim());
					} else if ("exchange".equals(value)) {
						urlList.setExchange(xpp.getText().trim());
					} else if ("cinema_img".equals(value)) {
						urlList.setCinemaImg(xpp.getText().trim());
					} else if ("terminal_log_url".equals(value)) {
						urlList.setTerminalLogUrl(xpp.getText().trim());
					} else if ("XmppInfo".equals(value)) {
						urlList.setXmppInfo(xpp.getText().trim());
					} else if ("babyepg".equals(value)) {
						urlList.setBabyepg(xpp.getText().trim());
					} else if ("mosttvchannel".equals(value)) {
						urlList.setMosttvchannel(xpp.getText().trim());
					} else if ("error_qq".equals(value)) {
						urlList.setError_qq(xpp.getText().trim());
					} else if ("error_weixin".equals(value)) {
						urlList.setError_weixin(xpp.getText().trim());
					} else if ("xmppUser".equals(value)) {
						urlList.setXmppUser(xpp.getText().trim());
					}else if ("currentTime".equalsIgnoreCase(value)) {
						urlList.setCurrentTime(xpp.nextText());
					}else if ("livetvjson".equalsIgnoreCase(value)) {
						urlList.setLiveTVJson(xpp.nextText());
					}
				}
				break;
			default:
				break;
			}
			eventType = xpp.next();
		}
	}
}
