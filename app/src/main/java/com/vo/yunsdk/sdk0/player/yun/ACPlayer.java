package com.vo.yunsdk.sdk0.player.yun;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

import com.vo.yunsdk.sdk0.player.VolMediaPlayerListener;
import com.vo.yunsdk.sdk0.player.VooleMediaPlayer;
import com.vo.yunsdk.sdk0.player.base.BasePlayer;
import com.vo.yunsdk.sdk0.player.interfaces.IPlayReport;
import com.vol.sdk.utils.LogUtil;

import java.io.IOException;


public class ACPlayer extends BasePlayer {
	private MediaPlayer mMediaPlayer = null;
	private SurfaceView mSurfaceView = null;
	private SurfaceHolder mSurfaceHolder = null;
	private String mPlayUrl = null;

	@Override
	public void initPlayer(VooleMediaPlayer vmp, Context context,
			VolMediaPlayerListener l, IPlayReport report) {
		initMediaPlayer();
		super.initPlayer(vmp, context, l, report);

	}

	private void initMediaPlayer() {
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setScreenOnWhilePlaying(true);
		StandardPlayerListener l = new StandardPlayerListener();
		mMediaPlayer.setOnPreparedListener(l);
		mMediaPlayer.setOnCompletionListener(l);
		mMediaPlayer.setOnSeekCompleteListener(l);
		mMediaPlayer.setOnBufferingUpdateListener(l);
		mMediaPlayer.setOnErrorListener(l);
		mMediaPlayer.setOnInfoListener(l);
	}

	@Override
	public void prepare(final String param, String sessionId) {
		LogUtil.d("ACPlayer-->prepare-->param-->" + param);
		LogUtil.d("ACPlayer-->prepare-->sessionId-->" + sessionId);
/*		long curTime = System.currentTimeMillis();
		LogUtil.d("ACPlayer-->prepare-->curTime-->" + curTime);
		String ud = YunGLib.yunGetPackNameStamp();
		LogUtil.d("ACPlayer-->prepare-->ud-->" + ud);*/
		mPlayUrl = param;
	/*	if (SdkUtils.checkStr(param)) {
			mPlayUrl = param;
		} else {
			mPlayUrl = "http://127.0.0.1:"
					+ ProxyManager.GetInstance().getProxyPort() + "/play?url='"
					+ param + "'&up='ua=" + curTime + "&ub=1&ud=" + ud + "&ug="
					+ sessionId + "'";
		}*/
		LogUtil.d("ACPlayer-->prepare-->mPlayUrl-->" + mPlayUrl);
		if (mSurfaceView == null) {
			LogUtil.d("ACPlayer-->prepare-->mSurfaceView == null");
			mSurfaceView = new SurfaceView(mContext);
			mSurfaceHolder = mSurfaceView.getHolder();
			mSurfaceHolder.addCallback(new Callback() {
				@Override
				public void surfaceDestroyed(SurfaceHolder arg0) {
					LogUtil.d("ACPlayer-->surfaceDestroyed");
					mSurfaceHolder = null;
				}

				@Override
				public void surfaceCreated(SurfaceHolder sh) {
					LogUtil.d("ACPlayer-->surfaceCreated");
					mSurfaceHolder = sh;
					mMediaPlayer.setDisplay(mSurfaceHolder);
					reset();
					doPrepare();
				}

				@Override
				public void surfaceChanged(SurfaceHolder arg0, int arg1,
						int arg2, int arg3) {
					LogUtil.d("ACPlayer-->surfaceChanged");
					mMediaPlayer.setDisplay(mSurfaceHolder);
				}
			});
			mVooleMediaPlayer.addView(mSurfaceView,
					new RelativeLayout.LayoutParams(
							RelativeLayout.LayoutParams.MATCH_PARENT,
							RelativeLayout.LayoutParams.MATCH_PARENT));
		} else {
			LogUtil.d("ACPlayer-->prepare-->mSurfaceView != null");
			mSurfaceView.setVisibility(View.VISIBLE);
			doPrepare();
		}
		super.prepare(param, sessionId);
	}

	private void doPrepare() {
		if (mMediaPlayer == null) {
			initMediaPlayer();
		}
		try {
			LogUtil.d("ACPlayer-->setDataSource-->url-->" + mPlayUrl);
			mCurrentStatus = Status.Preparing;
			mMediaPlayer.setDataSource(mPlayUrl);
			mMediaPlayer.prepareAsync();

		} catch (Exception e) {
			LogUtil.d("ACPlayer-->setDataSource-->exception-->" + e.toString());
		}
	}

	/*
	 * private boolean isGetUrlTaskRunning = false; private class GetProxyTask
	 * extends AsyncTask<String, Integer, Boolean> {
	 * 
	 * @Override protected void onPreExecute() {
	 * LogUtil.d("GetProxyTask-->onPreExecute"); isGetUrlTaskRunning = true; }
	 * 
	 * @Override protected Boolean doInBackground(String... params) {
	 * LogUtil.d("GetProxyTask-->doInBackground"); isGetUrlTaskRunning = true;
	 * return isGetUrlTaskRunning; }
	 * 
	 * @Override protected void onProgressUpdate(Integer... progresses) {
	 * LogUtil.d("GetProxyTask-->onProgressUpdate"); }
	 * 
	 * @Override protected void onPostExecute(Boolean b) {
	 * LogUtil.d("GetProxyTask-->onPostExecute"); isGetUrlTaskRunning = false;
	 * if(isCancelled()){ LogUtil.d(
	 * "GetUrlTask-->onPostExecute--->isCancelled-->$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"
	 * ); }else{ doPrepare(); } }
	 * 
	 * @Override protected void onCancelled() { isGetUrlTaskRunning = false;
	 * LogUtil
	 * .d("GetUrlTask-->onCancelled-->$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"); } }
	 */

	@Override
	public void resume() {
		LogUtil.d("ACPlayer-->resume");
		if (mMediaPlayer != null) {
			if (mPlayReport != null) {
				if (mCurrentStatus == Status.Pause) {
					mPlayReport.notifyResume(getCurrentPosition());
				} else {
					mPlayReport.notifyStart();
				}
			}
			mCurrentStatus = Status.Playing;
			mMediaPlayer.start();
		}
		super.resume();
	}

	@Override
	public void start(int msec) {
		LogUtil.d("ACPlayer-->start msec -->" + msec);
		if (mMediaPlayer != null) {
			if (mPlayReport != null) {
				if (mCurrentStatus == Status.Pause) {
					mPlayReport.notifyResume(getCurrentPosition());
				} else {
					mPlayReport.notifyStart();
				}
			}
			mMediaPlayer.start();
			if (msec > 0) {
				mMediaPlayer.seekTo(msec);
			}
			mCurrentStatus = Status.Playing;
		}
		super.start(msec);
	}

	@Override
	public void seekTo(int msec) {
		LogUtil.d("ACPlayer-->seekTo msec -->" + msec);
		super.seekTo(msec);
		if (mMediaPlayer != null) {
			mMediaPlayer.seekTo(msec);
		}
	}

	@Override
	public void pause() {
		LogUtil.d("ACPlayer-->pause");
		if (mMediaPlayer != null) {
			mMediaPlayer.pause();
			mCurrentStatus = Status.Pause;
			if (mPlayReport != null) {
				if (mVooleMediaPlayer != null) {
					mPlayReport.notifyPause(mVooleMediaPlayer
							.getCurrentPosition());
				}
			}
		}
		super.pause();
	}

	@Override
	public void stop() {
		LogUtil.d("ACPlayer-->stop");
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
		}
		super.stop();
	}

	@Override
	public void reset() {
		LogUtil.d("ACPlayer-->reset");
		if (mMediaPlayer != null) {
			if (mPlayReport != null) {
				mPlayReport.notifyResetStart();
			}
			mMediaPlayer.reset();
			if (mPlayReport != null) {
				mPlayReport.notifyResetEnd();
			}
		}
		super.reset();
	}

	@Override
	public void release() {
		LogUtil.d("ACPlayer-->release");

		if (mMediaPlayer != null) {
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		super.release();
	}

	@Override
	public void setLooping(boolean looping) {
		if (mMediaPlayer != null) {
			mMediaPlayer.setLooping(looping);
		}
	}

	@Override
	public int getCurrentPosition() {
		int currentPosition = 0;
		int duration = 0;
		if (mMediaPlayer != null && mCurrentStatus != Status.Completed) {
			duration = mMediaPlayer.getDuration();
			currentPosition = mMediaPlayer.getCurrentPosition();
		}
		LogUtil.d("mMediaPlayer->getCurrentPosition, currentPosition = "
				+ currentPosition + ", duration = " + duration
				+ ", status = " + mCurrentStatus);
		if (mCurrentStatus != Status.Completed && duration > 0
				&& currentPosition > duration) {
			notifyOnCompletion();
		}
		return currentPosition;
	}

	@Override
	public int getDuration() {
		if (mMediaPlayer != null && mCurrentStatus != Status.Completed) {
			return mMediaPlayer.getDuration();
		}
		return 0;
	}

	@Override
	public boolean onKeyDown(int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDataSource(String url) throws IOException {
		mMediaPlayer.setDataSource(mPlayUrl);
	}

	@Override
	public void recycle() {
		LogUtil.d("ACPlayer-->recycle");
		release();
		mSurfaceView.setVisibility(View.GONE);
	}

	@Override
	public int getVideoHeight() {
		if (mMediaPlayer != null) {
			return mMediaPlayer.getVideoHeight();
		}
		return 0;
	}

	@Override
	public int getVideoWidth() {
		if (mMediaPlayer != null) {
			return mMediaPlayer.getVideoWidth();
		}
		return 0;
	}

	public class StandardPlayerListener implements OnPreparedListener,
			OnSeekCompleteListener, OnInfoListener, OnErrorListener,
			OnBufferingUpdateListener, OnCompletionListener {

		@Override
		public void onCompletion(MediaPlayer arg0) {
			LogUtil.d("MediaPlayer-->onCompletion, mCurrentStatus = "
					+ mCurrentStatus);
			if (mCurrentStatus != Status.Completed) {
				notifyOnCompletion();
			}
		}

		@Override
		public void onBufferingUpdate(MediaPlayer arg0, int percent) {
			// LogUtil.d("MediaPlayer-->onBufferingUpdate-->percent-->" +
			// percent);
			notifyOnBufferingUpdate(percent);
		}

		@Override
		public boolean onError(MediaPlayer arg0, int what, int extra) {
			LogUtil.d("MediaPlayer-->onError-->what-->" + what + "-->extra-->"
					+ extra);
			if (mCurrentStatus == Status.IDLE) {
				return true;
			}
			mCurrentStatus = Status.Error;
			return notifyOnError(what, extra);
		}

		@Override
		public boolean onInfo(MediaPlayer arg0, int what, int extra) {
			// LogUtil.d("MediaPlayer-->onInfo-->what-->" + what + "-->extra-->"
			// + extra);
			return notifyOnInfo(what, extra);
		}

		@Override
		public void onSeekComplete(MediaPlayer arg0) {
			LogUtil.d("MediaPlayer-->onSeekComplete");
			notifyOnSeekComplete();
		}

		@Override
		public void onPrepared(MediaPlayer arg0) {
			LogUtil.d("MediaPlayer-->onPrepared");
			mCurrentStatus = Status.Prepared;
			notifyOnPrepared();
		}
	}

	@Override
	public void setVolume(float leftVolume, float rightVolume) {
		if (mMediaPlayer != null) {
			mMediaPlayer.setVolume(leftVolume, rightVolume);
		}
	}

}
