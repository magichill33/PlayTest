package com.vol.sdk;

import android.content.Context;

import com.gntv.tv.HbGlibTool;
import com.vol.sdk.model.Product;
import com.vol.sdk.model.ProductModel;
import com.vol.sdk.model.ProductParser;
import com.vol.sdk.utils.DateUtil;
import com.vol.sdk.utils.DeviceUtil;
import com.vol.sdk.utils.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017-9-19.
 */

public class VolManager {
    private String appid = "-1";
    private static final String LIVE_PLAY_URL_VERSION = "2.0";
    private static final String LIVE_AUTH_INTERFACE_VERSION = "3.0";

    private static VolManager instance = new VolManager();

    private VolManager() {
    }

    public static VolManager getInstance() {
        return instance;
    }
    private boolean isInit = false;
    public void initSDK(Context context, final VolInitCallBack callBack){
        LogUtil.d("VolManager--->initSDK");
        Config.GetInstance().init(context);
        String token = HbGlibTool.getToken();
       // PcdnManager.start(context, PcdnType.LIVE,token,null,Config.GetInstance().getOemid(),null);

        initLevel();
        initLog();
        AuthManager.GetInstance().init(new ConfigAuth(), context, true, "2.0");
        ProxyManager.GetInstance().init(new ConfigProxy(),context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                AuthManager.GetInstance().startAuth();
                User user = AuthManager.GetInstance().getUser();
                AuthManager.GetInstance().getUrlMap();
                if (user!=null&&"0".equals(user.getStatus())){
                    isInit = ProxyManager.GetInstance().startProxy();
                    ProxyManager.GetInstance().startProxyCheckTimer();
                }
                if (callBack!=null){
                    callBack.onInitCompleted(isInit);
                }
            }
        }).start();

    }

    public void release(){
        LogUtil.d("VolManager--->release");
       // PcdnManager.stop(PcdnType.LIVE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AuthManager.GetInstance().exitAuth();
                    AuthManager.GetInstance().clear();
                    ProxyManager.GetInstance().exitProxy();
                    ProxyManager.GetInstance().stopProxyCheckTimer();
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.d("VolManager--->release--->error::"+e);
                }
            }
        }).start();
    }

    public interface VolInitCallBack {
        public void onInitCompleted(boolean isSuccess);
    }

    public boolean isInitSuccess(){
        return isInit;
    }

    /**
     * @param cid 频道id
     * @return
     */
    public String getPlayUrl(String cid){
        LogUtil.d("VolManager--->getPlayUrl--->cid:"+cid);
        //PlayModel playModel = new PlayModel();
        String mid = "1";
        String sid = "1";
        String fid = "";
        try {
            fid = URLEncoder.encode(1 + "_" + cid, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("VolManager--->getPlayUrl--->"+e);
        }
        String pid = "1";
        String ptype = "1000";
        String downUrl = null;
        String mtype = null;
        String adCache = null;
        String ispid = null;
        String coderate = null;
        String mediumtype = null;
        String epgid = null;
        String cpid = null;
        String cdnType = null;
        String adversion = "";
        String storeType = null;
        String adurl = null;
        String playUrlVersion = LIVE_PLAY_URL_VERSION;
        String authInterfaceVersion = LIVE_AUTH_INTERFACE_VERSION;
        String is3rd = "1";
        String tracker = "1";
        String bkeUrl = "";
        String dataType = "0";
        String proto = "0";
        String resourceId = cid;
        String mpb = "";
        String jumpPlay = null;
        String url = AuthManager.GetInstance().formatAuthPlayUrl(mid, sid, fid, pid, ptype, downUrl, mtype, adCache, ispid,
                coderate, mediumtype, epgid, cpid, cdnType, adversion, storeType, adurl, playUrlVersion,
                authInterfaceVersion, is3rd, tracker, bkeUrl, dataType, proto, resourceId, mpb, appid,
                jumpPlay);
       // playModel.setStatus(0);
        url = url + "&time=60";
        String playUrl = "http://127.0.0.1:" + ProxyManager.GetInstance().getProxyPort() + "/play?url='" + url + "'&urltype=1";
        //playModel.setPlayUrl(playUrl);
        /*Ad ad = AuthManager.GetInstance().getPlayUrl(mid, sid, fid, pid, ptype, downUrl, mtype, adCache, ispid,
                coderate, mediumtype, epgid, cpid, cdnType, adversion, storeType, adurl, playUrlVersion,
                authInterfaceVersion, is3rd, tracker, bkeUrl, dataType, proto, resourceId, mpb, appid,
                jumpPlay);
        String url = ad!=null?ad.getPlayUrl():null;
        if (TextUtils.isEmpty(url)){
            url = "http://127.0.0.1:" + ProxyManager.GetInstance().getProxyPort() + "/play";
        }
        String playUrl = PcdnManager.PCDNAddress(PcdnType.LIVE,url);*/
        return playUrl;
    }

    public ProductModel doProductQuery(){
        ProductModel productModel = new ProductModel();
        User user = AuthManager.GetInstance().getUser();
        String uid = user.getUid();
        String hid = user.getHid();
        String oemid = user.getOemid();
        String ip = DeviceUtil.getLocalIpAddress();
        String url = AuthManager.GetInstance().getAuthServer()
                + "/query?reqinfo=<req><data>{\"action\":\"livequery\",\"version\":\"1.0\"}</data></req>" + "&uid=" + uid +"&hid=" + hid + "&oemid=" + oemid + "&ip=" + ip;
        LogUtil.e("PlayerView--->doPlayQuery--->url"+url);
        ProductParser parser = new ProductParser();
        Product product = null;
        try {
            parser.setUrl(url);
            product = parser.getProduct();
        } catch (Exception e) {
            LogUtil.e("PlayerView--->doPlayQuery--->"+e.toString());
        }
        LogUtil.i("PlayerView--->doPlayQuery--->"+product);


        String text = getMobileOrderUrl(product.getPortalid(),product.getSpid(),
                product.getFee(), product.getName(), product.getPid(), product.getPtype(),product.getStarttime(),product.getStoptime());
        if(product!=null&&"0".equalsIgnoreCase(product.getIsorder())){
            /*String text = getMobileOrderUrl(product.getPortalid(),product.getSpid(),
                    product.getFee(), product.getName(), product.getPid(), product.getPtype(),product.getStarttime(),product.getStoptime());*/
            productModel.setStatus(0);
           // productModel.setPayUrl(text);
            productModel.setDesc("没有订购此产品，请根据payurl生成二维码扫码订购");
        }else {
            productModel.setStatus(1);
            productModel.setDesc("已订购此产品，请换台重试");
        }
        productModel.setPayUrl(text);
        productModel.setStartTime(product!=null?product.getStarttime():"0");
        productModel.setEndTime(product!=null?product.getStoptime():"0");
        return productModel;
    }


    private String getMobileOrderUrl(String epgid,String spid,String price, String title, String pid, String ptype,String sTime,String eTime) {
        try {
            title = URLEncoder.encode(title, "UTF-8");
            long period = getDistanceDays(sTime, eTime);
            String url = getUniPayUrl() + "&pid=" + pid + "&ptype=" + ptype + "&title=" + title + "&price=" + price + "&period=" + period
                    + "&responseformat=xml&Datetime=" + DateUtil.getDateTime() + "&ordertype=2";
            LogUtil.d("getMobileOrderUrl--------->" + url);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    private String getUniPayUrl() {
        //String url = AuthManager.GetInstance().getUrlList().getUniPay() + "&issendxmpp=1";
        String url = null;
        if(getUrlMap()!=null){
            url = getUrlMap().get("uni_pay") + "&issendxmpp=1";
        }
        //User usr = AuthManager.GetInstance().getUser();
        //url = "http://10.88.6.39/index.php/mac/macpay/?spid=20120629&epgid=100951&pac=zhibo&uid=1188995&oemid=100250&hid=a089e432c7d90000000000000000000000000000&app_version=51tv_gen_2.0";
        //url = "http://10.88.6.39/index.php/mac/macpay/?spid=20120629&epgid=100951&pac=zhibo&hid=" + user.getHid() + "&uid=" + user.getUid() + "&oemid=" + user.getOemid() + "&app_version=51tv_gen_2.0";
        LogUtil.d("getUniPayUrl--------->" + url);
        return url;
    }

    private Map<String,String> getUrlMap(){
        UrlMap urlMap = AuthManager.GetInstance().getUrlMap();
        return urlMap!=null?urlMap.getUrlMap():null;
    }

    private long getDistanceDays(String str1, String str2) throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days=30;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    private void initLog(){
        if("1".equals(Config.GetInstance().getCloseLog())){
            LogUtil.setDisplayLog(false);
        }
    }

    private void initLevel() {
        LevelManager.GetInstance().setLevelManagerListener(new LevelManager.LevelManagerListener() {
            @Override
            public boolean getSupport3D() {
                return true;
            }

            @Override
            public LevelManager.Level getLevel() {
                return LevelManager.GetInstance().getCurrentLevel();
            }

            @Override
            public String getInterfaceVersionCode() {
                return Config.GetInstance().getInterfaceVersion();
            }
        });
    }
}
