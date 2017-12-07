package com.vo.yunsdk.sdk0.player;

import com.vo.yunsdk.sdk0.player.VoolePlayerFactory.PlayerType;
import com.vo.yunsdk.sdk0.player.interfaces.IPlayReport;
import com.vo.yunsdk.sdk0.player.interfaces.IPlayer;
import com.vo.yunsdk.sdk0.player.interfaces.IPlayer.Status;
import com.vol.sdk.utils.LogUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.io.IOException;

public class VooleMediaPlayer extends RelativeLayout {
	private static final String EPG_PLAY_URL_VERSION = "2.0";
	private static final String EPG_AUTH_INTERFACE_VERSION = "1.0";
	private static final String LIVE_PlAY_URL_VERSION = "1.0";// 直播URL版本号
	private static final String LIVE_AUTH_INTERFACE_VERSION = "2.0";// 直播版本号

	public static final String ERROR_PLAYER = "0194100001";
	public static final String ERROR_PLAY_AUTH_FAIL = "0191100007";
	public static final String ERROR_PLAY_AUTH_ERROR = "0191100008";
	public static final String ERROR_PLAY_TIME_ERROR = "0191100009";

	private String mMid = null;
	private String mFid = null;
	private String mPlaytype = null;
	private String mServiceType = null;
	private String mPlayUrlVersion = null;
	private String mAuthInterfaceVersion = null;
	private String apkVersionCode = "0";
	private String appid = "-1";

	public VooleMediaPlayer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public VooleMediaPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VooleMediaPlayer(Context context) {
		super(context);
	}

	private PlayerType mCurrentPlayerType = null;
	private PlayerType mPlayerType = null;
//	private GetUrlTask mGetUrlTask = null;

	private IPlayer mPlayer = null;
	private IPlayReport mReport = null;
	protected VolMediaPlayerListener mVooleMediaPlayerListener = null;

	public void setMediaPlayerListener(VolMediaPlayerListener l) {
		this.mVooleMediaPlayerListener = l;
	}

	public void setApkVersionCode(String apkVersionCode) {
		this.apkVersionCode = apkVersionCode;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	/*protected void prepareVCVod(String mid, String fid, String playtype,
			String epgid, String cpid, String cdnType, String serviceType) {
		setPlayerType(PlayerType.EPG_YUN);
		if (mReport != null) {
			mReport.notifyPlayAuthStart(mid, fid, epgid, cpid, cdnType, null,
					null, null, mSessionId);
		}
		setPlayData(mid, fid, playtype, EPG_PLAY_URL_VERSION,
				EPG_AUTH_INTERFACE_VERSION, serviceType);
		startGetUrlTask();
	}
*/
	protected void prepareAccUrl(String playUrl, String epgid, String cpid,
			String cdnType) {
		setPlayerType(PlayerType.EPG_ACC);
		/*if (mReport != null) {
			mReport.notifyPlayAuthStart("", "", epgid, cpid, cdnType, null,
					null, null, mSessionId);
			mReport.notifyPlayAuthEnd(null, null);
		}*/
		prepare(playUrl);
	}

	/*protected void prepareLive(String cid, String playtype, String serviceType) {
		setPlayerType(PlayerType.LIVE_VOOLE);
		if (mReport != null) {
			mReport.notifyPlayAuthStart(null, null, null, null, null, cid,
					null, null, mSessionId);
		}
		setPlayData(null, cid, playtype, EPG_PLAY_URL_VERSION,
				LIVE_AUTH_INTERFACE_VERSION, serviceType);
		startGetUrlTask();
	}*/

	private void setPlayData(String mid, String fid, String playtype,
			String playUrlVersion, String authInterfaceVersion,
			String serviceType) {
		mMid = mid;
		mFid = fid;
		mPlaytype = playtype;
		mPlayUrlVersion = playUrlVersion;
		mAuthInterfaceVersion = authInterfaceVersion;
		mServiceType = serviceType;
	}

	/*private void startGetUrlTask() {
		if (!isGetUrlTaskRunning) {
			mGetUrlTask = new GetUrlTask();
			mGetUrlTask.execute();
		}
	}*/

/*	private boolean isGetUrlTaskRunning = false;

	private class GetUrlTask extends AsyncTask<String, Integer, Ad> {
		@Override
		protected void onPreExecute() {
			LogUtil.d("YunGetUrlTask-->onPreExecute");
			isGetUrlTaskRunning = true;
		}

		@Override
		protected Ad doInBackground(String... params) {
			LogUtil.d("YunGetUrlTask-->doInBackground");
			isGetUrlTaskRunning = true;
			Ad ad = null;
			String mid = mMid;
			String fid = mFid;
			String playtype = mPlaytype;
			String serviceType = mServiceType;
			String playUrlVersion = mPlayUrlVersion;
			String authInterfaceVersion = mAuthInterfaceVersion;
			do {
				mid = mMid;
				fid = mFid;
				playtype = mPlaytype;
				serviceType = mServiceType;
				playUrlVersion = mPlayUrlVersion;
				authInterfaceVersion = mAuthInterfaceVersion;
				LogUtil.d("YunGetUrlTask--->start getplayurl");
				ad = AuthManager.GetInstance().getPlayUrlYun(mid, fid,
						playtype, playUrlVersion, authInterfaceVersion,
						serviceType);
				LogUtil.d("YunGetUrlTask-->adUrl-->" + ad.getPlayUrl());
				LogUtil.d("YunGetUrlTask-->mFid-->" + mFid);
			} while (!fid.equals(mFid));
			isGetUrlTaskRunning = false;
			if ("1000".equals(playtype) && ad != null
					&& "0".equals(ad.getStatus())) {
				final String url = ad.getPlayUrl();
				new Thread() {
					public void run() {
						ProxyManager.GetInstance().preDownloadUrl(url);
					};
				}.start();
			}
			return ad;
		}

		@Override
		protected void onProgressUpdate(Integer... progresses) {
			LogUtil.d("YunGetUrlTask-->onProgressUpdate");
		}

		@Override
		protected void onPostExecute(Ad result) {
			LogUtil.d("YunGetUrlTask-->onPostExecute");
			isGetUrlTaskRunning = false;
			if (isCancelled()) {
				LogUtil.d("YunGetUrlTask-->onPostExecute--->isCancelled-->$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			} else {
				LogUtil.d("YunonPostExecute prepare-->>>>>>>>>>>>>>>>>>>>"
						+ result);
				prepare(result);
			}
		}

		@Override
		protected void onCancelled() {
			isGetUrlTaskRunning = false;
			LogUtil.d("YunGetUrlTask-->onCancelled-->$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		}
	}
*/
	private void setPlayerType(PlayerType type) {
		mPlayerType = type;
	/*	if (mReport == null || mCurrentPlayerType == null
				|| mCurrentPlayerType != mPlayerType) {
			mReport = VoolePlayerFactory.getPlayReport(mPlayerType);
			mReport.initReport(getContext(), apkVersionCode, appid);
		}*/
	}

/*	private void prepare(Ad ad) {
		LogUtil.d("Yunprepare-->>>>>>>>>>>>>>>>>>>>");
		if (SdkUtils.isOpenNetwork(getContext())) {
			if (ad != null) {
				if ("0".equals(ad.getStatus())
						|| !TextUtils.isEmpty(ad.getPlayUrl())) {
					if (mReport != null) {
						mReport.notifyPlayAuthEnd(ad.getPlayUrl(),
								ad.getAuthCode());
					}
					prepare(ad.getAdxml());
				} else {
					if (mVooleMediaPlayerListener != null) {
						if (mPlayer == null
								|| mPlayer.getCurrentStatus() == Status.IDLE) {
							mVooleMediaPlayerListener.onError(
									ERROR_PLAY_AUTH_ERROR, ad.getResultDesc());
						}
					}
				}
			} else {
				LogUtil.d("Yunprepare-->ad==null");
				if (mVooleMediaPlayerListener != null) {
					if (mPlayer == null
							|| mPlayer.getCurrentStatus() == Status.IDLE) {
						mVooleMediaPlayerListener.onError(ERROR_PLAY_AUTH_FAIL,
								"认证失败");
					}
				}
			}
		} else {
			if (mVooleMediaPlayerListener != null) {
				mVooleMediaPlayerListener.onError(ERROR_PLAY_TIME_ERROR,
						"网络中断！");
			}
		}
	}*/

	private void prepare(String param) {
		initPlayer();
		if (mPlayer != null) {
			mPlayer.prepare(param, "123456789");
		}
	}

	private void initPlayer() {
		if (mCurrentPlayerType == null || mCurrentPlayerType != mPlayerType) {
			if (mPlayer != null) {
				mPlayer.recycle();
			}
			this.removeAllViews();
			mPlayer = VoolePlayerFactory.getPlayer(mPlayerType);
			mPlayer.initPlayer(this, getContext(), mVooleMediaPlayerListener,
					mReport);
			mCurrentPlayerType = mPlayerType;
		}
	}

	public void start(int msec) {
		if (mPlayer != null) {
			mPlayer.start(msec);
		}
	}

	public void seekTo(int msec) {
		if (mPlayer != null) {
			mPlayer.seekTo(msec);
		}
	}

	public void pause() {
		if (mPlayer != null) {
			mPlayer.pause();
		}
	}

	public void resume() {
		if (mPlayer != null) {
			mPlayer.resume();
		}
	}

	public void stop() {
		if (mPlayer != null) {
			mPlayer.stop();
		}
	}

	public void setLooping(boolean looping) {
		if (mPlayer != null) {
			mPlayer.setLooping(looping);
		}
	}

	public void reset() {
		LogUtil.d("VooleMediaPlayer-->reset");
		/*
		 * if(mGetUrlTask != null){ mGetUrlTask.cancel(true); }
		 */
		if (mPlayer != null) {
			mPlayer.reset();
		}
	}

	public void release() {
		LogUtil.d("VooleMediaPlayer-->release");
	/*	if (mGetUrlTask != null) {
			mGetUrlTask.cancel(true);
		}*/
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
			mReport = null;
		}
		mCurrentPlayerType = null;

	}

	public int getCurrentPosition() {
		if (mPlayer != null) {
			return mPlayer.getCurrentPosition();
		}
		return 0;
	}

	public int getDuration() {
		if (mPlayer != null) {
			return mPlayer.getDuration();
		}
		return 0;
	}

	public Status getCurrentStatus() {
		if (mPlayer != null) {
			return mPlayer.getCurrentStatus();
		}
		return Status.IDLE;
	}

	public boolean onKeyDown(int keyCode) {
		if (mPlayer != null) {
			return mPlayer.onKeyDown(keyCode);
		}
		return false;
	}

	public int getVideoHeight() {
		if (mPlayer != null) {
			return mPlayer.getVideoHeight();
		}
		return 0;
	}

	public int getVideoWidth() {
		if (mPlayer != null) {
			return mPlayer.getVideoWidth();
		}
		return 0;
	}

	public void setVolume(float leftVolume, float rightVolume) {
		if (mPlayer != null) {
			mPlayer.setVolume(leftVolume, rightVolume);
		}
	}

	public void setSurfaceHolderFixedSize(int width, int height) {
		if (mPlayer != null) {
			mPlayer.setSurfaceHolderFixedSize(width, height);
		}
	}

	public void setDataSource(String url) throws IOException {
		mPlayer.setDataSource(url);
	}
}
