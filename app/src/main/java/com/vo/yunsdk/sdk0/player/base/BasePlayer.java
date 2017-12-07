package com.vo.yunsdk.sdk0.player.base;

import com.vo.yunsdk.sdk0.ErrorCodes;
import com.vo.yunsdk.sdk0.player.VolMediaPlayerListener;
import com.vo.yunsdk.sdk0.player.VooleMediaPlayer;
import com.vo.yunsdk.sdk0.player.interfaces.IPlayReport;
import com.vo.yunsdk.sdk0.player.interfaces.IPlayer;
import com.vol.sdk.utils.LogUtil;

import android.content.Context;
import android.os.AsyncTask;


public abstract class BasePlayer implements IPlayer {
	protected Status mCurrentStatus = Status.IDLE;
	protected Context mContext = null;
	protected VooleMediaPlayer mVooleMediaPlayer = null;
	private VolMediaPlayerListener mVooleMediaPlayerListener = null;
	protected IPlayReport mPlayReport = null;

	protected boolean mIsPreview = false;
	protected int mPreviewTime = 0;
	protected String mIsLiveShow = "0";
	protected String mIsJumpPlay = "0";

	private int duration = 0;

	/*
	 * @Override public void initPlayer(String param, IAdUIController
	 * adUIController, VooleMediaPlayer vmp, Context context,
	 * VooleMediaPlayerListener l) { this.mContext = context;
	 * this.mCurrentStatus = Status.IDLE; this.mVooleMediaPlayer = vmp; }
	 */

	@Override
	public void initPlayer(VooleMediaPlayer vmp, Context context,
			VolMediaPlayerListener l, IPlayReport report) {
		this.mContext = context;
		this.mCurrentStatus = Status.IDLE;
		this.mVooleMediaPlayer = vmp;
		this.mVooleMediaPlayerListener = l;
		this.mPlayReport = report;
	}

	@Override
	public void reset() {
		mCurrentStatus = Status.IDLE;
		/*
		 * if(mPlayReport != null){ mPlayReport.notifyReset(); }
		 */
	}

	@Override
	public void release() {
		mCurrentStatus = Status.IDLE;
		if (mPlayReport != null) {
			mPlayReport.notifyRelease();
		}
	}

	@Override
	public Status getCurrentStatus() {
		return mCurrentStatus;
	}

	@Override
	public void prepare(String param, String sessionId) {
		mCurrentStatus = Status.Preparing;
		if (mPlayReport != null) {
			mPlayReport.notifyPrepare(param);
		}
	}

	@Override
	public void resume() {
	}

	@Override
	public void start(int msec) {
	}

	@Override
	public void seekTo(int msec) {
		if (mPlayReport != null) {
			if (mVooleMediaPlayer != null) {
				mPlayReport.notifySeek(mVooleMediaPlayer.getCurrentPosition(),
						msec);
			}
		}
	}

	@Override
	public void pause() {

	}

	@Override
	public void stop() {
		if (mPlayReport != null) {
			if (mVooleMediaPlayer != null) {
				LogUtil.d("BasePlayer->stop, duration = "
						+ mVooleMediaPlayer.getDuration()
						+ ", currentPosition = "
						+ mVooleMediaPlayer.getCurrentPosition());
				// duration != 0 && currentPosition ==
				// 0，5.1的乐播盒子在正常播放完成时会出现currentPosition为0的问题
				if (duration != 0
						&& mVooleMediaPlayer.getCurrentPosition() == 0) {
					mPlayReport.notifyStop(duration);
				} else {
					mPlayReport.notifyStop(mVooleMediaPlayer
							.getCurrentPosition());
				}
			}
		}
		mCurrentStatus = Status.IDLE;
	}

	@Override
	public void setSurfaceHolderFixedSize(int width, int height) {
		// TODO Auto-generated method stub

	}

	protected void notifyOnPrepared() {
		if (mPlayReport != null) {
			duration = mVooleMediaPlayer.getDuration();
			mPlayReport.notifyOnPrePared(duration);
		}
		if (mVooleMediaPlayerListener != null) {
			mVooleMediaPlayerListener.onPrepared();
		}
	}

	protected boolean notifyOnInfo(int what, int extra) {
		/*LogUtil.d("what>>>>>>>>>>>>>" + what + ">>>>>>>>>>>>>>extra>>>>>>>>"
				+ extra);*/
		if (mPlayReport != null) {
			if (what == 701) {
				mPlayReport.notifyBufferStart(mVooleMediaPlayer
						.getCurrentPosition());
			} else if (what == 702) {
				mPlayReport.notifyBufferEnd(mVooleMediaPlayer
						.getCurrentPosition());
			}
		}
		if (mVooleMediaPlayerListener != null) {
			return mVooleMediaPlayerListener.onInfo(what, extra);
		}
		return false;
	}

	protected boolean notifyOnError(int what, int extra) {
		if (mPlayReport != null) {
			mPlayReport.notifyError(mVooleMediaPlayer.getCurrentPosition());
		}
		GetProxyErrorTask getProxyErrorTask = new GetProxyErrorTask();
		getProxyErrorTask.execute();
		return true;
	}

	protected void notifyOnCompletion() {
		mCurrentStatus = Status.Completed;
		LogUtil.d("BasePlayer->notifyOnCompletion, mCurrentStatus = "
				+ mCurrentStatus);
		if (mPlayReport != null) {
			mPlayReport
					.notifyCompletion(mVooleMediaPlayer.getCurrentPosition());
		}
		if (mVooleMediaPlayerListener != null) {
			mVooleMediaPlayerListener.onCompletion();
		}
	}

	protected void notifyOnSeekComplete() {
		if (mVooleMediaPlayerListener != null) {
			mVooleMediaPlayerListener.onSeekComplete();
		}
	}

	protected void notifyOnBufferingUpdate(int percent) {
		if (mVooleMediaPlayerListener != null) {
			mVooleMediaPlayerListener.onBufferingUpdate(percent);
		}
	}

	// ad
	/*
	 * protected void notifyCanSeek(boolean canSeek){
	 * if(mVooleMediaPlayerListener != null){
	 * mVooleMediaPlayerListener.canSeek(canSeek); } }
	 * 
	 * protected void notifyCanExit(boolean canExit){
	 * if(mVooleMediaPlayerListener != null){
	 * mVooleMediaPlayerListener.canExit(canExit); } }
	 * 
	 * protected void notifyOnSeek(int pos){ if(mVooleMediaPlayerListener !=
	 * null){ mVooleMediaPlayerListener.onSeek(pos); } }
	 * 
	 * protected void notifyOnAdEvent(AdEvent e){ if(mVooleMediaPlayerListener
	 * != null){ mVooleMediaPlayerListener.onAdEvent(e); } }
	 * 
	 * protected void notifyOnExit(){ if(mVooleMediaPlayerListener != null){
	 * mVooleMediaPlayerListener.onExit(); } }
	 * 
	 * protected void notifyOnMovieStart(){ if(mVooleMediaPlayerListener !=
	 * null){ mVooleMediaPlayerListener.onMovieStart(); } }
	 */
	private class GetProxyErrorTask extends
			AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			LogUtil.d("GetProxyErrorTask-->onPreExecute-->onPreExecute");
		}

		@Override
		protected String doInBackground(String... params) {
			LogUtil.d("GetProxyErrorTask-->doInBackground");
			return "123456789";
		}

		@Override
		protected void onProgressUpdate(Integer... progresses) {
			LogUtil.d("GetProxyErrorTask-->onProgressUpdate");
		}

		@Override
		protected void onPostExecute(String errorCode) {
			LogUtil.d("GetProxyErrorTask-->onPostExecute-->"+errorCode);
			if (mVooleMediaPlayerListener != null) {
				mVooleMediaPlayerListener.onError(errorCode, "播放出错");
			}
		}

		@Override
		protected void onCancelled() {
			LogUtil.d("GetProxyErrorTask-->onCancelled");
		}
	}
}
