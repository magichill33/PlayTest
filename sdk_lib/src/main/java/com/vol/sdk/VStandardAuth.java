package com.vol.sdk;


public class VStandardAuth extends StandardAuth{

	private static final String AUTH_CONF_NAME = "vooleauth.conf";
	private static final String AUTH_CONF_RT_NAME = "voolert.conf";
	private static final String AUTH_FILE_NAME = "vooleauthd";
	private static final String AUTH_PORT = "18080";
	private static final String AUTH_MD5_PORT = "3568";
	@Override
	public String v_getAuthPort() {
		return AUTH_PORT;
	}
	@Override
	public String v_getAuthMd5Port() {
		return AUTH_MD5_PORT;
	}
	@Override
	public String v_getAuthConfName() {
		return AUTH_CONF_NAME;
	}
	@Override
	public String v_getAuthConfRtName() {
		return AUTH_CONF_RT_NAME;
	}
	@Override
	public String v_getAuthFileName() {
		return AUTH_FILE_NAME;
	}



}
