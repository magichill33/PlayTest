package com.vol.sdk;

public class ConfigProxy extends VStandardProxy {
	
	@Override
	public String v_getProxyPort() {
		String proxyport = Config.GetInstance().getProxyPort() ;
		if(!proxyport.equals("")){
			return proxyport ;
		}
		return super.v_getProxyPort();
	}
	
	@Override
	public String v_getProxyExitPort() {
		String proxyport = Config.GetInstance().getProxyExitPort() ;
		if(!proxyport.equals("")){
			return proxyport ;
		}
		return super.v_getProxyExitPort();
	}
	
	@Override
	public String v_getProxyFileName() {
		String name = Config.GetInstance().getProxyFileName() ;
		if(!name.equals("")){
			return name ;
		}
		return super.v_getProxyFileName();
	}
}
