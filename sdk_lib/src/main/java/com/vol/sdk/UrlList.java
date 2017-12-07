package com.vol.sdk;


import com.vol.sdk.base.BaseInfo;

public class UrlList extends BaseInfo {
	private String payList;
	private String recommend;
	// private String union;
	private String topic;
	private String rankFilm;
	private String rankTeleplay;
	private String message;
	private String related;
	private String searchAll;
	private String uiHint;
	private String account;
	private String product;
	private String transScreen;
	// private String searchChannel;
	// private String loginAuth;
	private String upgradeCheck;
	// private String topView;
	private String orderAgreement;
	private String registerAgreement;
	// private String playAuth;
	// 手机支付
	private String uniPay;
	// private String skyworthRegister;
	// private String skyworthUpdateTel;

	private String epgAdUrl;
	private String interactionAdUrl;
	private String report;
	// private String liveDownload;

	private String reportAdUrl;

	// 测�?接口
	private String speedTest;
	// 测�?上报接口
	private String speedTestPost;
	// 首页推荐影片(点播2.2)
	private String vooleRecommenendedBottom;
	// 终端信息统计
	private String terminalInfoStat;

	private String adversion = "1.3.7";

	private String playReport;

	private String cacheNotice;

	private String karaokeDownload;

	// 安利专题
	private String topicAnli;
	// 三生专题
	private String topicSansheng;

	private String weixinqrCode;

	private String weixinReport;

	private String liveTVUrl;
	private String liveTVJson; //添加直播json url

	private String transscreenResume;
	private String transscreenFavorite;

	private String guideDownload;

	private String remoteDownload;

	private String linkShort;

	// 第三方牌照认证地址
	private String ottPlateAuthUrl;
	// 第三方认证策略
	private String ottPlateAuthPolicy;

	private String sohulogo;

	// 长虹院线
	private String tvCsCinema;
	// 院线获取签到规则及总积分
	private String querySigninRule;
	// 院线签到
	private String signIn;
	// 院线获取短片积分
	private String queryMoiveScore;
	// 院线播放完赚积分
	private String shortMoivePlayend;
	// 院线获取积分记录
	private String queryScoreRecord;
	// 院线获取活动配置信息
	private String actInfo;
	// 院线砸蛋接口
	private String zadan;
	// 院线获取砸蛋记录
	private String prizeRecords;
	// 院线获取兑换商品信息
	private String goodsInfo;
	// 院线商品兑换接口
	private String exchange;

	private String cinemaImg;

	// 终端日志上报域名
	private String terminalLogUrl;

	private String xmppInfo;

	private String babyepg;

	private String mosttvchannel;
	private String error_qq;
	private String error_weixin;
	private String currentTime; //获取动态入口当前时间接口

	//获取 xmpp 用户登陆信息
	private String xmppUser;

	public String getXmppUser() {
		return xmppUser;
	}

	public void setXmppUser(String xmppUser) {
		this.xmppUser = xmppUser;
	}

	public String getError_qq() {
		return error_qq;
	}

	public void setError_qq(String error_qq) {
		this.error_qq = error_qq;
	}

	public String getError_weixin() {
		return error_weixin;
	}

	public void setError_weixin(String error_weixin) {
		this.error_weixin = error_weixin;
	}

	public String getMosttvchannel() {
		return mosttvchannel;
	}

	public void setMosttvchannel(String mosttvchannel) {
		this.mosttvchannel = mosttvchannel;
	}

	public String getBabyepg() {
		return babyepg;
	}

	public void setBabyepg(String babyepg) {
		this.babyepg = babyepg;
	}

	public String getXmppInfo() {
		return xmppInfo;
	}

	public void setXmppInfo(String xmppInfo) {
		this.xmppInfo = xmppInfo;
	}

	public String getTerminalLogUrl() {
		return terminalLogUrl;
	}

	public void setTerminalLogUrl(String terminalLogUrl) {
		this.terminalLogUrl = terminalLogUrl;
	}

	public String getActInfo() {
		return actInfo;
	}

	public void setActInfo(String actInfo) {
		this.actInfo = actInfo;
	}

	public String getZadan() {
		return zadan;
	}

	public void setZadan(String zadan) {
		this.zadan = zadan;
	}

	public String getPrizeRecords() {
		return prizeRecords;
	}

	public void setPrizeRecords(String prizeRecords) {
		this.prizeRecords = prizeRecords;
	}

	public String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getQuerySigninRule() {
		return querySigninRule;
	}

	public void setQuerySigninRule(String querySigninRule) {
		this.querySigninRule = querySigninRule;
	}

	public String getSignIn() {
		return signIn;
	}

	public void setSignIn(String signIn) {
		this.signIn = signIn;
	}

	public String getQueryMoiveScore() {
		return queryMoiveScore;
	}

	public void setQueryMoiveScore(String queryMoiveScore) {
		this.queryMoiveScore = queryMoiveScore;
	}

	public String getShortMoivePlayend() {
		return shortMoivePlayend;
	}

	public void setShortMoivePlayend(String shortMoivePlayend) {
		this.shortMoivePlayend = shortMoivePlayend;
	}

	public String getQueryScoreRecord() {
		return queryScoreRecord;
	}

	public void setQueryScoreRecord(String queryScoreRecord) {
		this.queryScoreRecord = queryScoreRecord;
	}

	public String getTvCsCinema() {
		return tvCsCinema;
	}

	public void setTvCsCinema(String tvCsCinema) {
		this.tvCsCinema = tvCsCinema;
	}

	public String getSohulogo() {
		return sohulogo;
	}

	public void setSohulogo(String sohulogo) {
		this.sohulogo = sohulogo;
	}

	public String getLiveTVUrl() {
		return liveTVUrl;
	}

	public void setLiveTVUrl(String liveTVUrl) {
		this.liveTVUrl = liveTVUrl;
	}

	public String getCacheNotice() {
		return cacheNotice;
	}

	public void setCacheNotice(String cacheNotice) {
		this.cacheNotice = cacheNotice;
	}

	public String getPlayReport() {
		return playReport;
	}

	public void setPlayReport(String playReport) {
		this.playReport = playReport;
	}

	public String getAdversion() {
		return adversion;
	}

	public void setAdversion(String adversion) {
		this.adversion = adversion;
	}

	public String getTerminalInfoStat() {
		return terminalInfoStat;
	}

	public void setTerminalInfoStat(String terminalInfoStat) {
		this.terminalInfoStat = terminalInfoStat;
	}

	public String getEpgAdUrl() {
		return epgAdUrl;
	}

	public void setEpgAdUrl(String epgAdUrl) {
		this.epgAdUrl = epgAdUrl;
	}

	public String getInteractionAdUrl() {
		return interactionAdUrl;
	}

	public void setInteractionAdUrl(String interactionAdUrl) {
		this.interactionAdUrl = interactionAdUrl;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	// public String getLiveDownload() {
	// return liveDownload;
	// }
	// public void setLiveDownload(String liveDownload) {
	// this.liveDownload = liveDownload;
	// }
	// public String getSkyworthUpdateTel() {
	// return skyworthUpdateTel;
	// }
	// public void setSkyworthUpdateTel(String skyworthUpdateTel) {
	// this.skyworthUpdateTel = skyworthUpdateTel;
	// }
	// public String getSkyworthRegister() {
	// return skyworthRegister;
	// }
	// public void setSkyworthRegister(String skyworthRegister) {
	// this.skyworthRegister = skyworthRegister;
	// }
	public String getUniPay() {
		return uniPay;
	}

	public void setUniPay(String uniPay) {
		this.uniPay = uniPay;
	}

	// public String getPlayAuth() {
	// return playAuth;
	// }
	// public void setPlayAuth(String playAuth) {
	// this.playAuth = playAuth;
	// }
	public String getOrderAgreement() {
		return orderAgreement;
	}

	public void setOrderAgreement(String orderAgreement) {
		this.orderAgreement = orderAgreement;
	}

	public String getRegisterAgreement() {
		return registerAgreement;
	}

	public void setRegisterAgreement(String registerAgreement) {
		this.registerAgreement = registerAgreement;
	}

	// public String getTopView() {
	// return topView;
	// }
	// public void setTopView(String topView) {
	// this.topView = topView;
	// }
	public String getUpgradeCheck() {
		return upgradeCheck;
	}

	public void setUpgradeCheck(String upgradeCheck) {
		this.upgradeCheck = upgradeCheck;
	}

	// public String getLoginAuth() {
	// return loginAuth;
	// }
	// public void setLoginAuth(String loginAuth) {
	// this.loginAuth = loginAuth;
	// }
	// public String getSearchChannel() {
	// return searchChannel;
	// }
	// public void setSearchChannel(String searchChannel) {
	// this.searchChannel = searchChannel;
	// }
	public String getTransScreen() {
		return transScreen;
	}

	public void setTransScreen(String transScreen) {
		this.transScreen = transScreen;
	}

	public String getPayList() {
		return payList;
	}

	public void setPayList(String payList) {
		this.payList = payList;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	// public String getUnion() {
	// return union;
	// }
	// public void setUnion(String union) {
	// this.union = union;
	// }
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getRankFilm() {
		return rankFilm;
	}

	public void setRankFilm(String rankFilm) {
		this.rankFilm = rankFilm;
	}

	public String getRankTeleplay() {
		return rankTeleplay;
	}

	public void setRankTeleplay(String rankTeleplay) {
		this.rankTeleplay = rankTeleplay;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRelated() {
		return related;
	}

	public void setRelated(String related) {
		this.related = related;
	}

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

	public String getUiHint() {
		return uiHint;
	}

	public void setUiHint(String uiHint) {
		this.uiHint = uiHint;
	}

	public String getAccount() {
		// return
		// "http://accounttestlt.voole.com/tv/playauth.php?oemid=650&hid=00CE39B8ABCB&uid=156839";
		// return
		// "http://accounttestlt.voole.com/tv/playauth.php?spid=20120629&epgid=100895&uid=1143996&oemid=817&hid=04e6763357c70000000000000000000000000000&app_version=vooleplay_gen_2.4";
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getProduct() {
		// return
		// "http://userauthtestlt.voole.com/Service.do?oemid=650&hid=00CE39B8ABCB&uid=156839";
		// return
		// "http://userauthtestlt.voole.com/Service.do?spid=20120629&epgid=100895&uid=105861252&oemid=817&hid=04e6763357c70000000000000000000000000000&app_version=vooleplay_gen_2.4";
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getSpeedTest() {
		return speedTest;
	}

	public void setSpeedTest(String speedTest) {
		this.speedTest = speedTest;
	}

	public String getSpeedTestPost() {
		return speedTestPost;
	}

	public void setSpeedTestPost(String speedTestPost) {
		this.speedTestPost = speedTestPost;
	}

	public String getVooleRecommenendedBottom() {
		return vooleRecommenendedBottom;
	}

	public void setVooleRecommenendedBottom(String vooleRecommenendedBottom) {
		this.vooleRecommenendedBottom = vooleRecommenendedBottom;
	}

	public String getReportAdUrl() {
		return reportAdUrl;
	}

	public void setReportAdUrl(String reportAdUrl) {
		this.reportAdUrl = reportAdUrl;
	}

	public String getKaraokeDownload() {
		return karaokeDownload;
	}

	public void setKaraokeDownload(String karaokeDownload) {
		this.karaokeDownload = karaokeDownload;
	}

	public String getTopicAnli() {
		return topicAnli;
	}

	public void setTopicAnli(String topicAnli) {
		this.topicAnli = topicAnli;
	}

	public String getWeixinqrCode() {
		return weixinqrCode;
	}

	public void setWeixinqrCode(String weixinqrCode) {
		this.weixinqrCode = weixinqrCode;
	}

	public String getWeixinReport() {
		return weixinReport;
	}

	public void setWeixinReport(String weixinReport) {
		this.weixinReport = weixinReport;
	}

	public String getTransscreenResume() {
		return transscreenResume;
	}

	public void setTransscreenResume(String transscreenResume) {
		this.transscreenResume = transscreenResume;
	}

	public String getTransscreenFavorite() {
		return transscreenFavorite;
	}

	public void setTransscreenFavorite(String transscreenFavorite) {
		this.transscreenFavorite = transscreenFavorite;
	}

	public String getGuideDownload() {
		return guideDownload;
	}

	public void setGuideDownload(String guideDownload) {
		this.guideDownload = guideDownload;
	}

	public String getRemoteDownload() {
		return remoteDownload;
	}

	public void setRemoteDownload(String remoteDownload) {
		this.remoteDownload = remoteDownload;
	}

	public String getLinkShort() {
		return linkShort;
	}

	public void setLinkShort(String linkShort) {
		this.linkShort = linkShort;
	}

	public String getTopicSansheng() {
		return topicSansheng;
	}

	public void setTopicSansheng(String topicSansheng) {
		this.topicSansheng = topicSansheng;
	}

	public String getOttPlateAuthUrl() {
		return ottPlateAuthUrl;
	}

	public void setOttPlateAuthUrl(String ottPlateAuthUrl) {
		this.ottPlateAuthUrl = ottPlateAuthUrl;
	}

	public String getOttPlateAuthPolicy() {
		return ottPlateAuthPolicy;
	}

	public void setOttPlateAuthPolicy(String ottPlateAuthPolicy) {
		this.ottPlateAuthPolicy = ottPlateAuthPolicy;
	}

	public String getCinemaImg() {
		return cinemaImg;
	}

	public void setCinemaImg(String cinemaImg) {
		this.cinemaImg = cinemaImg;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public String getLiveTVJson() {
		return liveTVJson;
	}

	public void setLiveTVJson(String liveTVJson) {
		this.liveTVJson = liveTVJson;
	}
	
}
