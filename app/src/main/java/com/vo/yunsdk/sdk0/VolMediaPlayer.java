package com.vo.yunsdk.sdk0;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;

import com.vo.yunsdk.sdk0.player.VooleMediaPlayer;
import com.vol.sdk.VolManager;
import com.vol.sdk.utils.LogUtil;

public class VolMediaPlayer extends VooleMediaPlayer {
	public static final String ERROR_SDK_INIT_FAIL = "0194110001";
	private static final String PLAYTYPE_VOD = "0";
	private static final String PLAYTYPE_LIVE = "1000";
	private static final String SERVICETYPE_VOD = "0";
	private static final String SERVICETYPE_LIVE = "1";
	private static final String EPGID = "-1";
	private String mMid = null;
	private String mFid = null;
	private String mPlayUrl = null;
	private String mCid = null;

	private int mType = 0;

	public VolMediaPlayer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public VolMediaPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VolMediaPlayer(Context context) {
		super(context);
	}
	//
/*	public void prepareVod(String mid, String fid) {
		mType = 0;
		setApkVersionCode(VolManager.SDK_VERSION);
		setAppid(VolManager.APPID);
		LogUtil.d("VoPlayer-->prepareVod-->mid-->" + mid);
		LogUtil.d("VoPlayer-->prepareVod-->fid-->" + fid);
		if (VolManager.getInstance().isInitSuccess()) {
			LogUtil.d("VoPlayer-->prepareVod-->-->-->InitSuccess  is  true");
			super.prepareVCVod(mid, fid, PLAYTYPE_VOD, EPGID, "", "",
					SERVICETYPE_VOD);
		} else {
			this.mMid = mid;
			this.mFid = fid;
			startLoginTask(true);
		}
	}*/

	public void prepareTrdUrl(String playUrl) {
		mType = 1;
		LogUtil.d("VoPlayer-->prepareUrl-->playUrl-->" + playUrl);
		if (VolManager.getInstance().isInitSuccess()) {
			LogUtil.d("VoPlayer-->prepareUrl-->-->-->InitSuccess  is  true");
			super.prepareAccUrl(playUrl, EPGID, "", "");
		} else {
			this.mPlayUrl = playUrl;
			startLoginTask(false);
		}
	}

/*	public void prepareLive(String cid) {
		mType = 2;
		setApkVersionCode(VolManager.SDK_VERSION);
		setAppid(VolManager.APPID);
		LogUtil.d("VoPlayer-->prepareLive-->cid-->" + cid);
		if (VolManager.getInstance().isInitSuccess()) {
			LogUtil.d("VoPlayer-->prepareLive-->-->-->InitSuccess  is  true");
			super.prepareLive(cid, PLAYTYPE_LIVE, SERVICETYPE_LIVE);
		} else {
			this.mCid = cid;
			startLoginTask(true);
		}
	}
*/
	private void startLoginTask(boolean isStartAuth) {
		LoginTask task = new LoginTask();
		task.execute(isStartAuth);
	}

	private class LoginTask extends AsyncTask<Boolean, Integer, Boolean> {
		@Override
		protected void onPreExecute() {
			LogUtil.d("YunLoginTask-->onPreExecute");
		}

		@Override
		protected Boolean doInBackground(Boolean... params) {
			LogUtil.d("YunLoginTask-->doInBackground-->-->-->isStartAuth-->-->-->"
					+ params[0]);

			return true;
		}

		@Override
		protected void onProgressUpdate(Integer... progresses) {
			LogUtil.d("YunLoginTask-->onProgressUpdate");
		}

		@Override
		protected void onPostExecute(Boolean result) {
			LogUtil.d("YunLoginTask-->onPostExecute");
			if (VolManager.getInstance().isInitSuccess()) {
				/*if (mType == 0) {
					VolMediaPlayer.this.prepareVCVod(mMid, mFid, PLAYTYPE_VOD,
							EPGID, "", "", SERVICETYPE_VOD);
				} else if (mType == 1) {
					VolMediaPlayer.this.prepareAccUrl(mPlayUrl, EPGID, "", "");
				} else if (mType == 2) {
					VolMediaPlayer.this.prepareLive(mCid, PLAYTYPE_LIVE,
							SERVICETYPE_LIVE);
				}*/
				VolMediaPlayer.this.prepareAccUrl(mPlayUrl, EPGID, "", "");
			} else {
				if (mVooleMediaPlayerListener != null) {
					mVooleMediaPlayerListener.onError(ERROR_SDK_INIT_FAIL,
							"init error");
				}
			}
		}

		@Override
		protected void onCancelled() {
		}

	}
}
