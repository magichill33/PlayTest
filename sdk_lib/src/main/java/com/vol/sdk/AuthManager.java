package com.vol.sdk;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.vol.sdk.utils.LogUtil;
import com.vol.sdk.utils.NetUtil;
import com.voole.tvutils.BaseApplication;


public class AuthManager {
//	private static final String PLAY_URL_VERSION = "2.0";

	private static final String CDN_TYPE_LETV = "3";
	private static AuthManager instance = new AuthManager();

	private AuthManager(){
	}

	public static AuthManager GetInstance(){
		return instance;
	}

	private IAuth auth = null;
	private Context context = null;
	private User mUser = null;
	private UrlList mUrlList = null;
	private UrlMap mUrlMap = null;
	private String mPlayUrlVersion = null;
	private boolean isUseNewTokenLib = false;

	public void init(IAuth auth, Context context, boolean useNewTokenLib, String playUrlVersion){
		this.auth = auth;
		this.context = context;
		this.isUseNewTokenLib = useNewTokenLib;
		this.mPlayUrlVersion = playUrlVersion;
	}

	public void init(IAuth auth, Context context, boolean useNewTokenLib){
		init(auth, context, useNewTokenLib, null);
	}

	public void init(IAuth auth, Context context){
		init(auth, context, false);
	}

	public void init( Context context){
		init(new VStandardAuth(), context);
	}

	public boolean startAuth(){
		LogUtil.i("AuthManager--->startAuth::mPlayUrlVersion--->"+mPlayUrlVersion);
		if(!TextUtils.isEmpty(mPlayUrlVersion)){
			String keyInfo = BaseApplication.GetInstance().getKeyInfo();
			LogUtil.i("AuthManager--->startAuth::BaseApplication.GetInstance().getKeyInfo()--->"+keyInfo);
			String param = "Para:" + keyInfo + "&" + mPlayUrlVersion;
			return auth.startAuth(context, param);
		}else{
			return auth.startAuth(context, null);
		}
	}

	public boolean startAuth(String param){
		LogUtil.i("AuthManager--->startAuth(String param)::mPlayUrlVersion--->"+mPlayUrlVersion+",param:"+param);
		return auth.startAuth(context, param);
	}

	public boolean startMobileAuth(){
		String imeiString = getImei();
		LogUtil.i("AuthManager--->startMobileAuth::imeiString--->"+imeiString);
		String param = null;
		if(!TextUtils.isEmpty(imeiString)) {
			param = "imei:"+imeiString;
			return auth.startAuth(context, param);
		} else {//tvbox or pad
			return auth.startAuth(context, param);
		}
	}

	/**
	 *
	 * @Description: 获取手机imei
	 * @Author:zhangnan@voole.com
	 * @Since:2016-10-12
	 * @Version:1.1
	 * @return
	 */
	private String getImei() {
		String imei =null;
		try {
			imei =((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(TextUtils.isEmpty(imei))
			imei="pad";
		LogUtil.i("start auth imei:"+imei);

		return imei;
		//String IMEI =android.os.SystemProperties.get(android.telephony.TelephonyProperties.PROPERTY_IMEI);

		// 不过纯APP开发SystemProperties，TelephonyProperties汇报错误，
		// 因为android.os.SystemProperties在SDK的库中是没有的，需要把Android SDK
		// 目录下data下的layoutlib.jar文件加到当前工程的附加库路径中，就可以Import。
		// 如果Android Pad没有IMEI,用此方法获取设备ANDROID_ID：

		//String android_id = Secure.getString(context.getContentResolver(),Secure.ANDROID_ID);
	}

	/*public boolean startAuth(){
		return startAuth(null);
	}

	public boolean startAuth(String playUrlVersion){
		mPlayUrlVersion = playUrlVersion;
		LogUtil.i("AuthManager--->startAuth::mPlayUrlVersion--->"+mPlayUrlVersion);
		if(!TextUtils.isEmpty(mPlayUrlVersion)){
			String keyInfo = BaseApplication.GetInstance().getKeyInfo();
			LogUtil.i("AuthManager--->startAuth::BaseApplication.GetInstance().getKeyInfo()--->"+keyInfo);
			String param = "Para:" + keyInfo + "&" + mPlayUrlVersion;
			return auth.startAuth(context, param);
		}else{
			return auth.startAuth(context, null);
		}

	}*/

	/*public User getUser(){
		if(mUser == null){
			boolean b = startAuth(mPlayUrlVersion);
			if(b){
				int connectTimes = 20;
				for(int i = 0; i < connectTimes; i++){
					mUser = auth.getUser();
					if (mUser != null && "0".equals(mUser.getStatus())) {
						return mUser;
					}else{
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			return null;
		}else{
			return mUser;
		}
	}*/
	public User getUser(){
		if(mUser == null || !"0".equals(mUser.getStatus())){
			int connectTimes = 20;
			for(int i = 0; i < connectTimes; i++){
				mUser = auth.getUser();
				if (mUser != null && "0".equals(mUser.getStatus())) {
					return mUser;
				}else{
					if(mUser == null){
						startAuth();
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			/*mUser = null;
			return null;*/
			return mUser;
		}else{
			return mUser;
		}
	}

	public String getPortalUrl(){
		User user = getUser();
		if(user != null){
			String portal = user.getPortal();
			String uid = user.getUid();
			String oemid = user.getOemid();
			String hid = user.getHid();

			StringBuilder portal_url = new StringBuilder();
			portal_url.append(portal);
			if(portal.contains("?")){
				portal_url.append("&");
			}else{
				portal_url.append("?");
			}
			portal_url.append("uid=").append(uid).append("&oemid=").append(oemid).append("&hid=").append(hid);
			if(LevelManager.GetInstance().getLevelManagerListener()!=null){
				String interfaceVersionCode = LevelManager.GetInstance().getLevelManagerListener().getInterfaceVersionCode();
				if(!TextUtils.isEmpty(interfaceVersionCode)){
					portal_url.append("&app_version=").append(interfaceVersionCode);
				}
			}
			//portal="http://authentry.voole.com:9988/receiveinfo?uid=103757151&oemid=817&hid=5CC6D0CBDE00&app_version=vooleplay_gen_2.4";
			LogUtil.d("DDDDDDDDDDDDDDDDD AuthManager-->portal-->" + portal_url.toString());
			return portal_url.toString();
		}else {
			return null;
		}
	}

	public UrlList getUrlList(){
		if(mUrlList == null){
			User user = getUser();
			if(user != null){
				String portal = user.getPortal();
				String uid = user.getUid();
				String oemid = user.getOemid();
				String hid = user.getHid();
				boolean isNewInterface=false;

				StringBuilder portal_url = new StringBuilder();
				portal_url.append(portal);
				if(portal.contains("?")){
					portal_url.append("&");
				}else{
					portal_url.append("?");
				}
				portal_url.append("uid=").append(uid).append("&oemid=").append(oemid).append("&hid=").append(hid);
				if(LevelManager.GetInstance().getLevelManagerListener()!=null){
					String interfaceVersionCode = LevelManager.GetInstance().getLevelManagerListener().getInterfaceVersionCode();
					if(!TextUtils.isEmpty(interfaceVersionCode)){
						portal_url.append("&app_version=").append(interfaceVersionCode);
						isNewInterface=true;
					}
				}
				//portal="http://authentry.voole.com:9988/receiveinfo?uid=103757151&oemid=817&hid=5CC6D0CBDE00&app_version=vooleplay_gen_2.4";
				LogUtil.d("DDDDDDDDDDDDDDDDD AuthManager-->portal-->" + portal_url.toString());

				int connectTimes = 2;
				for(int i = 0; i < connectTimes; i++){
					try {
						if(isNewInterface){
							UrlListNewParser urlListParser = new UrlListNewParser();
							urlListParser.setUrl( portal_url.toString());
							mUrlList = urlListParser.getUrlList();
						}else{
							UrlListParser urlListParser = new UrlListParser();
							urlListParser.setUrl( portal_url.toString());
							mUrlList = urlListParser.getUrlList();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(mUrlList != null && ("0".equals(mUrlList.getStatus()) || TextUtils.isEmpty(mUrlList.getStatus()) || "1".equals(mUrlList.getStatus()) ) ){
						LogUtil.d("AuthManager-->getUrlListInfo-->Success");
						break;
					}else{
						LogUtil.d("AuthManager-->getUrlListInfo-->fail");
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return mUrlList;
			}else {
				return null;
			}
		}else{
			return mUrlList;
		}
	}

	public UrlMap getUrlMap(){
		if(mUrlMap == null){
			User user = getUser();
			if(user != null){
				String portal = user.getPortal();
				String uid = user.getUid();
				String oemid = user.getOemid();
				String hid = user.getHid();
				boolean isNewInterface=false;

				StringBuilder portal_url = new StringBuilder();
				portal_url.append(portal);
				if(portal.contains("?")){
					portal_url.append("&");
				}else{
					portal_url.append("?");
				}
				portal_url.append("uid=").append(uid).append("&oemid=").append(oemid).append("&hid=").append(hid);
				if(LevelManager.GetInstance().getLevelManagerListener()!=null){
					String interfaceVersionCode = LevelManager.GetInstance().getLevelManagerListener().getInterfaceVersionCode();
					if(!TextUtils.isEmpty(interfaceVersionCode)){
						portal_url.append("&app_version=").append(interfaceVersionCode);
						isNewInterface=true;
					}
				}
				//portal="http://authentry.voole.com:9988/receiveinfo?uid=103757151&oemid=817&hid=5CC6D0CBDE00&app_version=vooleplay_gen_2.4";
				LogUtil.d("DDDDDDDDDDDDDDDDD AuthManager-->portal-->" + portal_url.toString());

				int connectTimes = 2;
				for(int i = 0; i < connectTimes; i++){
					try {
						if(isNewInterface){
							UrlMapNewParser urlListParser = new UrlMapNewParser();
							urlListParser.setUrl( portal_url.toString());
							mUrlMap = urlListParser.getUrlList();
						}else{
							UrlMapParser urlListParser = new UrlMapParser();
							urlListParser.setUrl( portal_url.toString());
							mUrlMap = urlListParser.getUrlList();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(mUrlMap != null && ("0".equals(mUrlMap.getStatus()) || TextUtils.isEmpty(mUrlMap.getStatus()) || "1".equals(mUrlMap.getStatus()) ) ){
						LogUtil.d("AuthManager-->getUrlListInfo-->Success");
						break;
					}else{
						LogUtil.d("AuthManager-->getUrlListInfo-->fail");
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return mUrlMap;
			}else {
				return null;
			}
		}else{
			return mUrlMap;
		}
	}

	public boolean isAuthRunning(){
		return auth.isAuthRunning(context);
	}

	public void exitAuth(){
		if (auth!=null){
			auth.exitAuth();
		}

	}

	public void deleteAuthFiles(){
		auth.deleteAuthFiles(context);
	}

	public String getAuthServer(){
		return auth.getAuthServer();
	}

	public void clear(){
		mUser = null;
//		mUrlList = null;
		mUrlMap = null;
	}

	public Ad getPlayUrl(String mid, String sid, String fid, String pid,
						 String playtype, String downUrl, String mtype, String adCache,
						 String ispid, String coderate, String mediumtype, String epgid, String cpid, String cdnType, String adversion, String storeType, String adUrl, String playUrlVersion, String authInterfaceVersion) {
		return getPlayUrl(mid, sid, fid, pid, playtype, downUrl, mediumtype, adCache, ispid, coderate, mediumtype, epgid, cpid, cdnType, adversion, storeType,
				adUrl, playUrlVersion, authInterfaceVersion, "", "", "", "", "", "", "", "", "");
	}

	public Ad getPlayUrl(String mid, String sid, String fid, String pid,
						 String playtype, String downUrl, String mtype, String adCache,
						 String ispid, String coderate, String mediumtype, String epgid, String cpid, String cdnType, String adversion,
						 String storeType, String adUrl, String playUrlVersion, String authInterfaceVersion,
						 String resourceType, String tracker, String bkeUrl, String dataType, String proto, String resourceId, String mbp, String appid, String jumpPlay) {
		String httpMessage = "";
		String url = formatAuthPlayUrl(mid, sid, fid, pid, playtype, downUrl, mtype, adCache, ispid, coderate, mediumtype, epgid, cpid, cdnType, adversion, storeType,
				adUrl, playUrlVersion, authInterfaceVersion, resourceType, tracker, bkeUrl, dataType, proto, resourceId, mbp, appid, jumpPlay);
		int retryTimes = 2;
		for(int i=0; i<retryTimes; i++){
			StringBuffer buffer = new StringBuffer();
			if (NetUtil.connectServer(url, buffer, 1, 6)) {
				httpMessage = buffer.toString();
				LogUtil.d("getPlayUrl---" + i + "-->" + httpMessage);
				AdParser parser = new AdParser(httpMessage);
				parser.parser();
				if(parser.getAd() != null && parser.getAd().getReqno()!=null){
					return parser.getAd();
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				LogUtil.d("getPlayUrl----->null");
				startAuth();
				getUser();
				getUrlMap();
//				getUrlList();
			}
		}

		return null;
	}

	public String formatAuthPlayUrl(String mid, String sid, String fid,
									String pid, String playtype, String downloadUrl, String mtype, String adCache,
									String ispid, String coderate, String mediumtype, String epgid, String cpid, String cdnType, String adversion, String storeType, String adurl, String playUrlVersion, String authInterfaceVersion) {
		return formatAuthPlayUrl(mid, sid, fid, pid, playtype, downloadUrl, mediumtype, adCache, ispid, coderate, mediumtype, epgid, cpid, cdnType, adversion, storeType, adurl, playUrlVersion, authInterfaceVersion, "", "", "", "", "", "", "", "", "");
	}

	public String formatAuthPlayUrl(String mid, String sid, String fid,
									String pid, String playtype, String downloadUrl, String mtype, String adCache,
									String ispid, String coderate, String mediumtype, String epgid, String cpid, String cdnType, String adversion, String storeType, String adurl, String playUrlVersion,
									String authInterfaceVersion,  String is3rd, String tracker, String bkeUrl, String dataType, String proto, String resourceId, String mbp, String appid, String jumpPlay) {
		StringBuffer sb = new StringBuffer();
		if("18".equals(mtype)){
			/*String channelId = fid.substring(0, 12);
			String sTime = fid.substring(12, 22);
			String eTime = fid.substring(22, 32);
			String downUrl = "stime=" + sTime + "&etime=" + eTime + "&ext=oid:" + AuthManager.GetInstance().getUser().getOemid();
			String downUrlBase64 = Base64.encodeToString(downUrl.getBytes(), Base64.NO_WRAP);
			sb.append("{\"action\":\"playauth\",");
			sb.append("\"mid\":\"1\",");
			sb.append("\"sid\":\"1\",");
			channelId = delete0(channelId)+"_点播回看";
			try {
				sb.append("\"fid\":\"" + URLEncoder.encode(channelId, "UTF-8") + "\",");
			} catch (UnsupportedEncodingException e) {
				sb.append("\"fid\":\"" + channelId + "\",");
			}
			sb.append("\"pid\":\"1\",");
			sb.append("\"time\":\"" + System.currentTimeMillis() + "\",");
			sb.append("\"playtype\":\"1500\",");
			sb.append("\"usercache\":"+"\"" + adCache + "\",");
			sb.append("\"ext\":\"" + downUrlBase64 + "\"}");*/
		}else{
			if(CDN_TYPE_LETV.equals(cdnType)){
				sb.append("{\"action\":\"playauthLetv\",");
			}else{
				sb.append("{\"action\":\"playauth\",");
			}
			sb.append("\"mid\":\"" + mid + "\",");
			sb.append("\"sid\":\"" + sid + "\",");
			if(fid == null){
				fid = "";
			}
			sb.append("\"fid\":\"" + fid + "\",");
			if(ispid == null){
				ispid = "";
			}
			sb.append("\"ispid\":\"" + ispid + "\",");
			if(coderate == null){
				coderate = "";
			}
			sb.append("\"coderate\":\"" + coderate + "\",");
			if(mediumtype == null){
				mediumtype = "";
			}
			sb.append("\"mediumtype\":\"" + mediumtype + "\",");
			if(epgid == null){
				epgid = "";
			}
			sb.append("\"epgid\":\"" + epgid + "\",");
			if(cpid == null){
				cpid = "";
			}
			sb.append("\"cpid\":\"" + cpid + "\",");
			if(cdnType == null){
				cdnType = "";
			}
			sb.append("\"cdntype\":\"" + cdnType + "\",");
			if(adversion == null){
				adversion = "";
			}
			sb.append("\"adversion\":\"" + adversion + "\",");
			if(adurl == null){
				adurl = "";
			}
			sb.append("\"adurl\":\"" + adurl + "\",");
			if(storeType == null){
				storeType = "";
			}
			sb.append("\"storetype\":"+"\"" + storeType + "\",");
			sb.append("\"pid\":\"" + pid + "\",");
			sb.append("\"playtype\":\"" + playtype + "\",");
			if(adCache == null){
				adCache = "";
			}
			sb.append("\"usercache\":"+"\"" + adCache + "\",");
			if(playUrlVersion == null){
				playUrlVersion = "";
			}
			sb.append("\"v\":"+"\"" + playUrlVersion + "\",");
			if(authInterfaceVersion == null){
				authInterfaceVersion = "";
			}
			sb.append("\"version\":"+"\"" + authInterfaceVersion + "\",");
			if(!TextUtils.isEmpty(mbp)){
				sb.append("\"mpb\":"+"\"1\",");
			}
			if(!TextUtils.isEmpty(appid)){
				sb.append("\"appid\":"+"\"" + appid + "\",");
			}
			if(!TextUtils.isEmpty(jumpPlay)){
				sb.append("\"jumpplay\":"+"\"" + jumpPlay + "\",");
			}
			sb.append("\"proxyport\":"+"\"" + ProxyManager.GetInstance().getProxyPort() + "\",");
			if(downloadUrl == null){
				downloadUrl = "";
			}
			sb.append("\"ext\":\"" + downloadUrl + "\"}");
		}
		String url = null;
		if(!TextUtils.isEmpty(tracker)){
			url = AuthManager.GetInstance().getAuthServer()
					+ "/preurl?reqinfo=<req><data>" + sb.toString()
					+ "</data></req>" + "&sourcetype=" + is3rd + "&playtype=" + playtype +
					"&cid=" + resourceId + "&tracker=" + tracker + "&tkbke=" + bkeUrl +
					"&datatype=" + dataType + "&proto=" + proto + "&proxyport=" + ProxyManager.GetInstance().getProxyPort() +
					"&token=";
		}else{
			url = AuthManager.GetInstance().getAuthServer()
					+ "/query?reqinfo=<req><data>" + sb.toString()
					+ "</data></req>" + "&token=";
		}
		if(BaseApplication.GetInstance() != null){
			if(isUseNewTokenLib){
				LogUtil.d("formatAuthPlayUrl--md5----->getTokenWithPort-->" + auth.getAuthPort());
				int md5Port = 0;
				try {
					md5Port = Integer.parseInt(auth.getAuthPort());
				} catch (Exception e) {
					// TODO: handle exception
				}
				url = url + BaseApplication.GetInstance().getTokenWithPort(md5Port) ;
			}else{
				LogUtil.d("formatAuthPlayUrl--md5----->tokenGet-->");
				url = url + BaseApplication.GetInstance().tokenGet(null);
			}
		}else{
			LogUtil.d("formatAuthPlayUrl--BaseApplication.GetInstance() == null---->");
		}
		LogUtil.d("formatAuthPlayUrl--authUrl----->" + url);
		return url;
	}

	public Ad getPlayUrl40(String aid, String mclassify, String sid, String msid, String mid,
						   String pid, String playtype, String adversion, String adurl, String playUrlVersion,
						   String authInterfaceVersion, String appid, String jumpplay, String channelCode, String deduct) {
		String httpMessage = "";
		int retryTimes = 2;
		for(int i=0; i<retryTimes; i++){
			String url = formatAuthPlayUrl40(aid, mclassify, sid, msid, mid, pid, playtype, adversion, adurl, playUrlVersion, authInterfaceVersion, appid, jumpplay, channelCode, deduct);
			StringBuffer buffer = new StringBuffer();
			if (NetUtil.connectServer(url, buffer, 1, 6)) {
				httpMessage = buffer.toString();
				LogUtil.d("getPlayUrl40---" + i + "-->" + httpMessage);
				AdParser parser = new AdParser(httpMessage);
				parser.parser();
				if(parser.getAd() != null && parser.getAd().getReqno()!=null){
					return parser.getAd();
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				LogUtil.d("getPlayUrl40----->null");
				startAuth();
				getUser();
				getUrlMap();
//				getUrlList();
			}
		}

		return null;
	}

	public String formatAuthPlayUrl40(String aid, String mclassify, String sid, String msid, String mid,
									  String pid, String playtype, String adversion, String adurl, String playUrlVersion,
									  String authInterfaceVersion, String appid, String jumpplay, String channelCode, String deduct) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"action\":\"playauth\",");
		sb.append("\"aid\":\"" + aid + "\",");
		if(!TextUtils.isEmpty(mclassify)){
			sb.append("\"mclassify\":"+"\"" + mclassify + "\",");
		}
		if(!TextUtils.isEmpty(sid)){
			sb.append("\"sid\":"+"\"" + sid + "\",");
		}
		if(!TextUtils.isEmpty(msid)){
			sb.append("\"msid\":"+"\"" + msid + "\",");
		}
		if(!TextUtils.isEmpty(mid)){
			sb.append("\"mid\":"+"\"" + mid + "\",");
		}
		if(!TextUtils.isEmpty(pid)){
			sb.append("\"pid\":"+"\"" + pid + "\",");
		}
		if(!TextUtils.isEmpty(playtype)){
			sb.append("\"playtype\":"+"\"" + playtype + "\",");
		}
		if(!TextUtils.isEmpty(adversion)){
			sb.append("\"adversion\":"+"\"" + adversion + "\",");
		}
		if(!TextUtils.isEmpty(adurl)){
			sb.append("\"adurl\":"+"\"" + adurl + "\",");
		}
		if(!TextUtils.isEmpty(playUrlVersion)){
			sb.append("\"v\":"+"\"" + playUrlVersion + "\",");
		}
		if(!TextUtils.isEmpty(authInterfaceVersion)){
			sb.append("\"version\":"+"\"" + authInterfaceVersion + "\",");
		}
		if(!TextUtils.isEmpty(appid)){
			sb.append("\"appid\":"+"\"" + appid + "\",");
		}
		if(!TextUtils.isEmpty(jumpplay)){
			sb.append("\"jumpplay\":"+"\"" + jumpplay + "\",");
		}
		if(!TextUtils.isEmpty(channelCode)){
			sb.append("\"code\":"+"\"" + channelCode + "\",");
		}
		if(!TextUtils.isEmpty(deduct)){
			sb.append("\"deduct\":"+"\"" + deduct + "\",");
		}
		sb.append("\"proxyport\":\"" + ProxyManager.GetInstance().getProxyPort() + "\"}");

		String url = AuthManager.GetInstance().getAuthServer()
				+ "/query?reqinfo=<req><data>" + sb.toString()
				+ "</data></req>" + "&token=";
		if(BaseApplication.GetInstance() != null){
			if(isUseNewTokenLib){
				LogUtil.d("formatAuthPlayUrl40--md5----->getTokenWithPort-->" + auth.getAuthPort());
				int md5Port = 0;
				try {
					md5Port = Integer.parseInt(auth.getAuthPort());
				} catch (Exception e) {
					// TODO: handle exception
				}
				url = url + BaseApplication.GetInstance().getTokenWithPort(md5Port) ;
			}else{
				LogUtil.d("formatAuthPlayUrl40--md5----->tokenGet-->");
				url = url + BaseApplication.GetInstance().tokenGet(null);
			}
		}else{
			LogUtil.d("formatAuthPlayUrl40--BaseApplication.GetInstance() == null---->");
		}
		LogUtil.d("formatAuthPlayUrl40--authUrl----->" + url);
		return url;
	}

	public Ad getPlayUrlYun(String mid, String fid, String playtype, String playUrlVersion, String authInterfaceVersion, String serviceType){
		String httpMessage = "";
		String url = formatAuthPlayUrlYun(mid, fid, playtype, playUrlVersion, authInterfaceVersion, serviceType);
		int retryTimes = 2;
		for(int i=0; i<retryTimes; i++){
			StringBuffer buffer = new StringBuffer();
			if (NetUtil.connectServer(url, buffer, 1, 6)) {
				httpMessage = buffer.toString();
				LogUtil.d("getPlayUrlYun---" + i + "-->" + httpMessage);
				AdParser parser = new AdParser(httpMessage);
				parser.parser();
				if(parser.getAd() != null && parser.getAd().getReqno()!=null){
					return parser.getAd();
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				LogUtil.d("getPlayUrlYun----->null");
				startAuth();
				getUser();
				getUrlMap();
//				getUrlList();
			}
		}

		return null;
	}

	public String formatAuthPlayUrlYun(String mid, String fid, String playtype, String playUrlVersion, String authInterfaceVersion, String serviceType) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"action\":\"cloudPlayauth\",");
		if(!TextUtils.isEmpty(mid)){
			sb.append("\"mid\":"+"\"" + mid + "\",");
		}
		if(!TextUtils.isEmpty(fid)){
			sb.append("\"fid\":"+"\"" + fid + "\",");
		}
		if(!TextUtils.isEmpty(playtype)){
			sb.append("\"playtype\":"+"\"" + playtype + "\",");
		}
		if(!TextUtils.isEmpty(serviceType)){
			sb.append("\"servicetype\":"+"\"" + serviceType + "\",");
		}
		if(!TextUtils.isEmpty(playUrlVersion)){
			sb.append("\"v\":"+"\"" + playUrlVersion + "\",");
		}
		if(!TextUtils.isEmpty(authInterfaceVersion)){
			sb.append("\"version\":"+"\"" + authInterfaceVersion + "\",");
		}
		sb.append("\"proxyport\":\"" + ProxyManager.GetInstance().getProxyPort() + "\"}");

		String url = AuthManager.GetInstance().getAuthServer()
				+ "/query?reqinfo=<req><data>" + sb.toString()
				+ "</data></req>" + "&token=";
		if(BaseApplication.GetInstance() != null){
			if(isUseNewTokenLib){
				LogUtil.d("formatAuthPlayUrlYun--md5----->getTokenWithPort-->" + auth.getAuthPort());
				int md5Port = 0;
				try {
					md5Port = Integer.parseInt(auth.getAuthPort());
				} catch (Exception e) {
					// TODO: handle exception
				}
				url = url + BaseApplication.GetInstance().getTokenWithPort(md5Port) ;
			}else{
				LogUtil.d("formatAuthPlayUrlYun--md5----->tokenGet-->");
				url = url + BaseApplication.GetInstance().tokenGet(null);
			}
		}else{
			LogUtil.d("formatAuthPlayUrlYun--BaseApplication.GetInstance() == null---->");
		}
		LogUtil.d("formatAuthPlayUrlYun--authUrl----->" + url);
		return url;
	}



	public String getAuthPort(){
		return auth.getAuthPort();
	}
	
	/*public String getAuthMd5Port(){
		return auth.getMd5Port();
	}*/
}
