package com.vol.sdk;

import android.content.Context;

import com.vol.sdk.utils.DeviceUtil;
import com.vol.sdk.utils.LogUtil;
import com.vol.sdk.utils.NetUtil;
import com.vooleglib.VooleGLib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public abstract class StandardAuth implements IAuth{
	public abstract String v_getAuthPort();
	public abstract String v_getAuthMd5Port();
	public abstract String v_getAuthConfName();
	public abstract String v_getAuthConfRtName();
	public abstract String v_getAuthFileName();
	
	private int mPort = 18080;
	
	protected String getDir(Context context){
		return context.getFilesDir().getAbsolutePath();
	}
	
	@Override
	public boolean startAuth(Context context, String param) {
		LogUtil.d("StandardAuth-->startAuth-->start");
		String moduleConfig = getDir(context) + "/" + v_getAuthConfName();
		File moduleConfigFile = new File(moduleConfig);
		if (moduleConfigFile.exists() && moduleConfigFile.length() == 0) {
			moduleConfigFile.delete();
		}
		if (!moduleConfigFile.exists()) {
			LogUtil.d("StandardAuth-->startAuth-->copy config file");
			try {
				InputStream is = context.getAssets().open(v_getAuthConfName());
				byte[] buffer = new byte[1024 * 8];
				int count = 0;
				FileOutputStream fos = new FileOutputStream(moduleConfig+"_tmp");
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				File file1 = new File(moduleConfig+"_tmp") ;
				File file2 = new File(moduleConfig) ;
				file1.renameTo(file2) ;
				fos.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String rtConfig = getDir(context) + "/" + v_getAuthConfRtName();
		File rtConfigFile = new File(rtConfig);
		if (rtConfigFile.exists() && rtConfigFile.length() == 0) {
			rtConfigFile.delete();
		}
		if (!rtConfigFile.exists()) {
			LogUtil.d("StandardAuth-->startAuth-->copy rtconfig file");
			try {
				InputStream is = context.getAssets().open(v_getAuthConfRtName());
				byte[] buffer = new byte[1024 * 8];
				int count = 0;
				FileOutputStream fos = new FileOutputStream(rtConfig+"_tmp");
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				File file1 = new File(rtConfig+"_tmp") ;
				File file2 = new File(rtConfig) ;
				file1.renameTo(file2) ;
				fos.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String modulepath = getDir(context) + "/" + v_getAuthFileName();
		File modulepathFile = new File(modulepath);
		if (modulepathFile.exists() && modulepathFile.length() == 0) {
			modulepathFile.delete();
		}
		if (!modulepathFile.exists()) {
			LogUtil.d("StandardAuth-->startAuth-->copy auth file");
			try {
				int sdkVersion = DeviceUtil.getSDKVersionNumber();
				InputStream is = null;
				if(sdkVersion >= 21){
					try {
						is = context.getAssets().open(v_getAuthFileName() + "_50");
					} catch (Exception e) {
						is = context.getAssets().open(v_getAuthFileName());
					}
				}else{
					is = context.getAssets().open(v_getAuthFileName());
				}
				byte[] buffer = new byte[1024 * 8];
				int count = 0;
				FileOutputStream fos = new FileOutputStream(modulepath + "_tmp");
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				File file1 = new File(modulepath+"_tmp") ;
				File file2 = new File(modulepath) ;
				file1.renameTo(file2) ;
				fos.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/*int s ;
		LogUtil.d("VooleGLib execute start auth before");
		if(TextUtils.isEmpty(param)){
			s = VooleGLib.execute(modulepath);
		}else{
			s = VooleGLib.executeWithPara(modulepath, param);
		}
		LogUtil.d("VooleGLib execute start auth after:" + s);
		if(s == 0){
			return true;
		}else{
			return false;
		}*/
		
		LogUtil.d("VooleGLib execute start auth before");
		mPort = VooleGLib.executeAutoPort(modulepath, param);
		LogUtil.d("VooleGLib execute start auth after:" + mPort);
		if(mPort > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void exitAuth() {
		LogUtil.d("AuthManager--->exitAuth");
//		VooleGLib.killExe(v_getAuthFileName());
		NetUtil.connectServer("http://127.0.0.1:" + getAuthPort() + "/exit", new StringBuffer(), 1, 3);
	}

	@Override
	public void deleteAuthFiles(Context context) {
		LogUtil.d("AuthManager--->deleteAuthFiles");
		String moduleConfig = getDir(context) + "/" + v_getAuthConfName();
		File config = new File(moduleConfig);
		if (config.exists()) {
			config.delete();
		}
		String rtConfig = getDir(context) + "/" + v_getAuthConfRtName();
		File rtConfFile = new File(rtConfig);
		if (rtConfFile.exists()) {
			rtConfFile.delete();
		}
		String modulepath = getDir(context) + "/" + v_getAuthFileName();
		File auth = new File(modulepath);
		if (auth.exists()) {
			auth.delete();
		}
	}

	@Override
	public boolean isAuthRunning(Context context) {
		int status = VooleGLib.isExeRunning(v_getAuthFileName());
		String modulepath = getDir(context) + "/" + v_getAuthFileName();
		File auth = new File(modulepath);
		if(status < 0){
			LogUtil.d("AuthManager--->isAuthRunning--->true");
			if(!auth.exists()){
				exitAuth();
				return false;
			}else{
				return true;
			}
		}else{
			LogUtil.d("AuthManager--->isAuthRunning--->false");
			return false;
		}
	}

	@Override
	public String getAuthServer() {
		final String url = "http://127.0.0.1:" + getAuthPort();
		return url;
	}

	@Override
	public String getAuthPort() {
//		return v_getAuthPort();
		return String.valueOf(mPort);
	}

	/*@Override
	public String getMd5Port() {
		return v_getAuthMd5Port();
	}*/

	@Override
	public User getUser() {
		String url = getAuthServer() + "/user";
		LogUtil.d("getUserInfo url----->"+url);
		try {
			UserParser userParser = new UserParser();
			userParser.setUrl(url);
			User user = userParser.getUser();
			LogUtil.d("AuthManager-->getUserInfo-->status--->" + user.getStatus());
			return user;
		} catch (Exception e) {
			LogUtil.d("AuthManager-->getUserInfo-->fail-->connect fail");
		}
		return null;
	}

}
