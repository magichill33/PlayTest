package com.vol.sdk;


public class ConfigAuth extends VStandardAuth {
	
	@Override
	public String v_getAuthPort() {
		String auth_port = Config.GetInstance().getAuthPort() ;
		if (!auth_port.equals("")) {
			return auth_port;
		}
		return super.v_getAuthPort();
	}
	
	@Override
	public String v_getAuthMd5Port() {
		String auth_port = Config.GetInstance().getAuthPort() ;
		if (!auth_port.equals("")) {
			return auth_port;
		}
		return super.v_getAuthMd5Port();
	}
	
	@Override
	public String v_getAuthFileName() {
		String authFileName = Config.GetInstance().getAuthFileName() ;
		if (!authFileName.equals("")) {
			return authFileName;
		}
		return super.v_getAuthFileName();
	}

}
