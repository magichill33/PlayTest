package com.vol.sdk;

import android.content.Context;


import com.vol.sdk.utils.LogUtil;

import java.util.Timer;
import java.util.TimerTask;

public class ProxyManager {
	private static ProxyManager instance = new ProxyManager();

	private ProxyManager() {
	}

	public static ProxyManager GetInstance() {
		return instance;
	}
	
	private IProxy proxy = null;
	private Context context = null;
	
	public void init(IProxy proxy, Context context){
		this.proxy = proxy;
		this.context = context;
	}
	
	public void init(Context context){
		init(new VStandardProxy(), context);
	}
	
	public ProxyInfo getProxyInfo() {
		return proxy.getPorxyInfo();
	}
	
	public boolean startProxy(){
		return proxy.startProxy(context);
	}
	
	public boolean startP2pProxy(){
		return proxy.startP2pProxy(context);
	}
	
	public void exitProxy(){
		stopProxyCheckTimer();
		if (proxy!=null){
			proxy.exitProxy();
		}
	}
	
	public boolean isProxyRunning(){
		return proxy.isProxyRunning();
	}
	
	public void finishPlay(){
		proxy.finishPlay();
	}
	
	public void stop(){
		proxy.stop();
	}
	
	public void clean(){
		proxy.clean();
	}
	
	public void deleteProxyFiles(){
		proxy.deleteProxyFiles(context);
	}
	
	public boolean checkUrl(String playUrl){
		return proxy.checkUrl(playUrl);
	}
	
	public ProxyError getError(){
		return proxy.getErrorCode();
	}
	
	public String getProxyPort(){
		return proxy.getProxyPort();
	}
	
	/*public String getProxyExitPort(){
		return proxy.getProxyExitPort();
	}*/
	
	/**
	 * 预下载数据
	 * @param playUrl
	 * @return 
	 */
	public void preDownloadUrl(String playUrl){
		proxy.preDownloadUrl(playUrl);
	}

	public void preUrl(String playUrl){
		proxy.preUrl(playUrl);
	}
	
	private Timer mTimer = null;
	private TimerTask mTimerTask = null;
	private static final int TIME_PERIOD = 1000;
	
	public void startProxyCheckTimer() {
		LogUtil.d("StandardPlayer-->startProxyCheckTimer");
		if (mTimer == null) {
			mTimer = new Timer();
		}
		if (mTimerTask == null) {
			mTimerTask = new TimerTask() {
				@Override
				public void run() {
					if(!isProxyRunning()){
						startProxy();
					}
				}
			};
			mTimer.schedule(mTimerTask, 0, TIME_PERIOD);
		}
	}

	public void stopProxyCheckTimer() {
		LogUtil.d("StandardPlayer-->stopProxyCheckTimer");
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}

		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}
	}
	
}
