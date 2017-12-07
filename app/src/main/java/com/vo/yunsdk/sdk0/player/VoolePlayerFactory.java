package com.vo.yunsdk.sdk0.player;

import com.vo.yunsdk.sdk0.player.interfaces.IPlayReport;
import com.vo.yunsdk.sdk0.player.interfaces.IPlayer;
import com.vo.yunsdk.sdk0.player.yun.ACPlayer;

class VoolePlayerFactory {
	public enum PlayerType{
		NORMAL,
		EPG_VOOLE,
		EPG_SOHU,
		EPG_LETV,
		LIVE_VOOLE, 
		PLAYBACK_VOOLE,
		FILM_VOOLE,
		EPG_YUN,
		EPG_ACC
	}
	public static IPlayer getPlayer(PlayerType param){
		switch (param) {
			case EPG_ACC:
				return new ACPlayer();
			default:
				return new ACPlayer();
		}
	}
	
/*	public static IPlayReport getPlayReport(PlayerType param){
		switch (param) {
		case NORMAL:
		case EPG_LETV:
		case EPG_SOHU:
		case EPG_VOOLE:
		case EPG_YUN:
			return new EpgReport();
		case LIVE_VOOLE:
			return new LiveReport();
//			return new NoReport();
		case PLAYBACK_VOOLE:
			return new PlayBackReport();
		case FILM_VOOLE:
			return new FilmReport();
		case EPG_ACC:
			return new AccReport();
		default:
			return new EpgReport();
	}
	}*/
}
