package com.vo.yunsdk.sdk0;

public interface ErrorCodes {
	String START_PROXY_ERROR = "SDKA0I02001F"; //启动代理失败
	String START_P2P_ERROR = "SDKA0I02002F";  //启动p2p失败
	String FINISH_P2P_ERROR = "SDKA0I02003E";  //通知代理停止下载数据错误
	String FINISHPLAYLIST_P2P_ERROR = "SDKA0I02005E";  //通知代理停止预下载数据错误
	String EXIT_PROXY_ERROR = "SDKA0I02004E";  //退出代理出错
	String NOTIFY_NETSTATE = "SDKA0I02006E";  //通知p2p网络状态变更出错
	String NOTIFY_PREDOWNLOAD = "SDKA0I02007E";  //通知代理多播放列表预下载接口出错
	String GET_UD_ERROR = "SDKA0I01001E";  //获取ud参数失败
	String UPGRADE_PROXY_ERROR = "SDKA0I01002E";  //升级代理文件出错
	String PLAYER_PLAYING_ERROR = "SDKA0I01003E";  //播放过程中出错
	String ILLEGAL_PLAYING_ERROR = "SDKA0I01004E";  //非法播放串
}
