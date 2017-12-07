package com.vol.sdk;

import android.content.Context;

public interface IProxy {
	public boolean startProxy(Context context);
	
	public void exitProxy();
	
	public void deleteProxyFiles(Context context);
	
	public boolean isProxyRunning();
	
	public void finishPlay();
	
	public void stop();
	
	public void clean();
	
	public String getProxyServer();
	
	public String getProxyPort();
	
//	public String getProxyExitPort();
	
	public ProxyInfo getPorxyInfo();
	
	public boolean checkUrl(String playUrl);
	
	public ProxyError getErrorCode();
	
	public void preUrl(String playUrl);
	
	public void preDownloadUrl(String playUrl);
	
	public boolean startP2pProxy(Context context);
}
