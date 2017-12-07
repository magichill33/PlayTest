package com.vo.yunsdk.sdk0.player.interfaces;

import com.vo.yunsdk.sdk0.player.VolMediaPlayerListener;
import com.vo.yunsdk.sdk0.player.VooleMediaPlayer;

import android.content.Context;

import java.io.IOException;


public interface IPlayer {
//	public void initPlayer(String param, IAdUIController adUIController, VooleMediaPlayer vmp, Context context, VooleMediaPlayerListener l);
//	public void setDataSource(String param);
	public void initPlayer(VooleMediaPlayer vmp, Context context, VolMediaPlayerListener l, IPlayReport report);
	public void prepare(String param, String sessionId);
	public void resume();
	public void start(int msec);
	public void seekTo(int msec);
	public void pause();
	public void stop();
	public void reset();
	public void release();
	public void setLooping(boolean looping);
	public int getCurrentPosition();
	public int getDuration();
	public boolean onKeyDown(int keyCode);
	public Status getCurrentStatus();

	public void setDataSource(String url) throws IOException;
	
	public int getVideoHeight();
	public int getVideoWidth();
	
	public void setVolume(float leftVolume, float rightVolume);
	
	public void setSurfaceHolderFixedSize(int width, int height);
	
	public void recycle();
	
	public enum Status{
		IDLE, Preparing, Prepared, Playing, Pause, Error,Completed
	}
}
