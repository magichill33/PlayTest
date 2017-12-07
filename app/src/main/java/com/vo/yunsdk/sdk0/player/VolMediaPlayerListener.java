package com.vo.yunsdk.sdk0.player;


public interface VolMediaPlayerListener{
	public abstract void onPrepared();

	public abstract boolean onInfo(int what, int extra);

	public abstract boolean onError(String errorCode, String errorMsg);

	public abstract void onCompletion();

	public abstract void onSeekComplete();
	
	public abstract void onBufferingUpdate(int percent);
	
	// ad
	/*public abstract void canSeek(boolean canSeek);
	
	public abstract void canExit(boolean canExit);

	public abstract void onSeek(int pos);
	
	public abstract void onExit();
	
	public abstract void onAdEvent(AdEvent e);
	
	public abstract void onMovieStart();*/
}
