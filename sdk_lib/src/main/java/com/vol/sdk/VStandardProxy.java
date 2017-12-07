package com.vol.sdk;


public class VStandardProxy extends StandardProxy{
	private static final String PROXY_FILE_NAME = "heibaiagent";
	private static final String PROXY_RT_FILE_NAME = "agent.conf";
	private static final String P2P_PROXY_FILE_NAME = "p2pvod";
	private static final String P2P_PROXY_CONF_FILE_NAME = "p2pvod.conf";
	private static final String PROXY_PORT = "5656";
	private static final String PROXY_EXIT_PORT = "5655";
	@Override
	public String v_getProxyPort() {
		return PROXY_PORT;
	}
	@Override
	public String v_getProxyExitPort() {
		return PROXY_EXIT_PORT;
	}
	@Override
	public String v_getProxyFileName() {
		return PROXY_FILE_NAME;
	}
	@Override
	public String v_getP2pVodFileName() {
		return P2P_PROXY_FILE_NAME;
	}
	@Override
	public String v_getP2pVodConfFileName() {
		return P2P_PROXY_CONF_FILE_NAME;
	}
	@Override
	public String v_getProxyRtConfFileName() {
		return PROXY_RT_FILE_NAME;
	}

}
