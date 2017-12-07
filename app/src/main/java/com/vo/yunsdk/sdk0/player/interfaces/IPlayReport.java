package com.vo.yunsdk.sdk0.player.interfaces;

import android.content.Context;

public interface IPlayReport {
	public void initReport(Context context, String versionCode, String appid);
	public void notifyPlayAuthStart(String mid, String fid, String epgid, String cpid, String cdnType, String channelId, String sTime, String eTime, String sessionId);
	public void notifyPlayAuthEnd(String playUrl, String authCode);
	public void notifyPrepare(String param);
	public void notifyOnPrePared(int durationMsec);
	public void notifyStart();
	public void notifyPause(int currentPlayTimeMsec);
	public void notifyResume(int currentPlayTimeMsec);
	public void notifyBufferStart(int currentPlayTimeMsec);
	public void notifyBufferEnd(int currentPlayTimeMsec);
	public void notifySeek(int currentPlayTimeMsec, int seekToMsec);
	public void notifyStop(int currentPlayTimeMsec);
	public void notifyCompletion(int currentPlayTimeMsec);
	public void notifyError(int currentPlayTimeMsec);
	public void notifyResetStart();
	public void notifyResetEnd();
	public void notifyRelease();
}
