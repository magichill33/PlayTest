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

public abstract class StandardProxy implements IProxy {
	public abstract String v_getProxyPort();

	public abstract String v_getProxyExitPort();

	public abstract String v_getProxyFileName();

	public abstract String v_getP2pVodFileName();

	public abstract String v_getP2pVodConfFileName();

	public abstract String v_getProxyRtConfFileName();

	private int mPort = 35656;

	//private boolean mHasStartP2p = false;

	@Override
	public String getProxyServer() {
		final String url = "http://127.0.0.1:" + getProxyPort();
		return url;
	}

	protected String getDir(Context context) {
		return context.getFilesDir().getAbsolutePath();
	}

	@Override
	public boolean startProxy(Context context) {
		//startP2pProxy(context);
		String modulepath = getDir(context) + "/" + v_getProxyFileName();
		File modulepathFile = new File(modulepath);
		if (modulepathFile.exists() && modulepathFile.length() == 0) {
			modulepathFile.delete();
		}
		if (!modulepathFile.exists()) {
			LogUtil.d("StandardProxy-->startProxy-->copy proxy file");
			InputStream is = null;
			FileOutputStream fos = null;
			try {
				int sdkVersion = DeviceUtil.getSDKVersionNumber();
				if (sdkVersion >= 21) {
					try {
						is = context.getAssets().open(
								v_getProxyFileName() + "_50");
					} catch (Exception e) {
						is = context.getAssets().open(v_getProxyFileName());
					}
				} else {
					is = context.getAssets().open(v_getProxyFileName());
				}
				byte[] buffer = new byte[1024 * 8];
				int count = 0;
				fos = new FileOutputStream(modulepath + "_tmp");
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				File file1 = new File(modulepath + "_tmp");
				File file2 = new File(modulepath);
				file1.renameTo(file2);
				fos.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
						fos = null;
					}
					if (is != null) {
						is.close();
						is = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		String rtConfig = getDir(context) + "/" + v_getProxyRtConfFileName();
		File rtConfigFile = new File(rtConfig);
		if (rtConfigFile.exists() && rtConfigFile.length() == 0) {
			rtConfigFile.delete();
		}
		if (!rtConfigFile.exists()) {
			LogUtil.d("StandardProxy-->startProxy-->copy rtconfig file");
			try {
				InputStream is = context.getAssets().open(v_getProxyRtConfFileName());
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

		/*
		 * LogUtil.d("VooleGLib execute startProxy before"); int a =
		 * VooleGLib.execute(modulepath);
		 * LogUtil.d("VooleGLib execute startProxy after:" + a);
		 */
		LogUtil.d("VooleGLib execute startProxy before");
		mPort = VooleGLib.executeAutoPortPackName(modulepath, null);
		// mPort = VooleGLib.executeAutoPort(modulepath, null);
		LogUtil.d("VooleGLib execute startProxy after:" + mPort);
		/*
		 * if (a == 0) { return true; } else { return false; }
		 */

		if (mPort > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void exitProxy() {
		LogUtil.d("ProxyManager--->exitProxy");
		// VooleGLib.killExe(PROXY_FILE_NAME);
		/*
		 * if(pid > 0){ android.os.Process.killProcess(pid); pid = -1; }
		 */
		/*
		 * NetUtil.connectServer("http://127.0.0.1:" + getProxyExitPort() +
		 * "/exit", new StringBuffer(), 1, 3);
		 */
		NetUtil.connectServer("http://127.0.0.1:" + getProxyPort() + "/exit",
				new StringBuffer(), 1, 3);
	}

	private int getAgentPid() {
		try {
			StringBuffer buffer = new StringBuffer();
			String httpMessage = "";
			if (NetUtil.connectServer(getProxyServer() + "/getpid", buffer, 1,
					6)) {
				httpMessage = buffer.toString().trim();
				if (httpMessage != null) {
					int id = Integer.parseInt(httpMessage.trim());
					return id;
				}
			}
		} catch (Exception e) {
			return -1;
		}
		return 0;
	}

	@Override
	public boolean isProxyRunning() {
		int pid = getAgentPid();
		if (pid <= 0) {
			return false;
		} else {
			return true;
		}
		/*
		 * int status = VooleGLib.isExeRunning(PROXY_FILE_NAME); if(status < 0){
		 * LogUtil.d("ProxyManager--->isProxyRunning--->true"); return true;
		 * }else{ LogUtil.d("ProxyManager--->isProxyRunning--->false"); return
		 * false; }
		 */
	}

	@Override
	public void deleteProxyFiles(Context context) {
		String modulepath = getDir(context) + "/" + v_getProxyFileName();
		File f = new File(modulepath);
		if (f.exists()) {
			f.delete();
		}
		String moduleconfig = getDir(context) + "/" + v_getProxyRtConfFileName();
		File f3 = new File(moduleconfig);
		if (f3.exists()) {
			f3.delete();
		}
		String p2pConfPath = getDir(context) + "/" + v_getP2pVodConfFileName();
		File f1 = new File(p2pConfPath);
		if (f1.exists()) {
			f1.delete();
		}
		String p2pModulepath = getDir(context) + "/" + v_getP2pVodFileName();
		File f2 = new File(p2pModulepath);
		if (f2.exists()) {
			f2.delete();
		}
	}

	@Override
	public void finishPlay() {
		LogUtil.d("ProxyManager--->finishPlay-->start");
		String url = getProxyServer() + "/finish";
		NetUtil.connectServer(url, new StringBuffer(), 1, 4);
		LogUtil.d("ProxyManager--->finishPlay-->end");
	}

	@Override
	public void stop() {
		LogUtil.d("ProxyManager--->stop-->start");
		String url = getProxyServer() + "/stop";
		NetUtil.connectServer(url, new StringBuffer(), 1, 4);
		LogUtil.d("ProxyManager--->stop-->end");
	}

	@Override
	public void clean() {
		LogUtil.d("ProxyManager--->clean-->start");
		String url = getProxyServer() + "/clean";
		NetUtil.connectServer(url, new StringBuffer(), 1, 4);
		LogUtil.d("ProxyManager--->clean-->end");
	}

	@Override
	public String getProxyPort() {
		return String.valueOf(mPort);
	}

	/*
	 * @Override public String getProxyPort() { return v_getProxyPort(); }
	 */

	/*
	 * @Override public String getProxyExitPort() { return v_getProxyExitPort();
	 * }
	 */

	@Override
	public ProxyInfo getPorxyInfo() {
		String url = getProxyServer() + "/info";
		try {
			ProxyInfoParser proxyParser = new ProxyInfoParser();
			proxyParser.setUrl(url);
			return proxyParser.getProxy();
		} catch (Exception e) {
			LogUtil.d("getProxyInfo----->Exception");
		}
		return null;
	}

	@Override
	public boolean checkUrl(String playUrl) {
		String url = getProxyServer() + "/testplay?url='" + playUrl + "'";
		LogUtil.d("StandardProxy----->checkUrl===>" + url);
		StringBuffer buffer = new StringBuffer();
		String httpMessage = "";
		if (NetUtil.connectServer(url, buffer, 1, 10, 10)) {
			httpMessage = buffer.toString().trim();
			LogUtil.d("connectionDetect----->msg===>" + httpMessage);
			if ("ok".equalsIgnoreCase(httpMessage)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ProxyError getErrorCode() {
		StringBuffer buffer = new StringBuffer();
		String httpMessage = "";
		ProxyError e = new ProxyError();
		String url = getProxyServer() + "/geterrno";
		LogUtil.d("StandardProxy----->getErrorCode===>" + url);
		if (NetUtil.connectServer(url, buffer, 1, 6)) {
			httpMessage = buffer.toString();
			e.setErrorCode(httpMessage);
		}
		return e;
	}

	@Override
	public void preUrl(String playUrl) {
		String url = getProxyServer() + "/p2pinit?url='" + playUrl + "'";
		LogUtil.d("StandardProxy----->preUrl===>" + url);
		NetUtil.doGet(url, 1, 1, 1);
	}

	@Override
	public void preDownloadUrl(String playUrl) {
		String url = getProxyServer() + "/apkpredown?url='" + playUrl + "'";
		LogUtil.d("StandardProxy----->preDownloadUrl===>" + url);
		NetUtil.doGet(url, 1, 1, 1);
	}

	@Override
	public boolean startP2pProxy(Context context) {
		//if (mHasStartP2p) {
		//	return true;
		//}
		String p2pModuleConfig = getDir(context) + "/"
				+ v_getP2pVodConfFileName();
		File moduleConfigFile = new File(p2pModuleConfig);
		if (moduleConfigFile.exists() && moduleConfigFile.length() == 0) {
			moduleConfigFile.delete();
		}
		if (!(new File(p2pModuleConfig)).exists()) {
			LogUtil.d("StandardProxy-->startProxy-->copy p2pModuleConfig file");
			InputStream is = null;
			FileOutputStream fos = null;
			try {
				is = context.getAssets().open(v_getP2pVodConfFileName());
				byte[] buffer = new byte[1024 * 8];
				int count = 0;
				fos = new FileOutputStream(p2pModuleConfig + "_tmp");
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				File file1 = new File(p2pModuleConfig + "_tmp");
				File file2 = new File(p2pModuleConfig);
				file1.renameTo(file2);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
						fos = null;
					}
					if (is != null) {
						is.close();
						is = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		String p2pVodModulepath = getDir(context) + "/" + v_getP2pVodFileName();
		File p2pModulepathFile = new File(p2pVodModulepath);
		if (p2pModulepathFile.exists() && p2pModulepathFile.length() == 0) {
			p2pModulepathFile.delete();
		}
		if (!p2pModulepathFile.exists()) {
			LogUtil.d("StandardProxy-->startProxy-->copy p2pVodModulepath file");
			InputStream is = null;
			FileOutputStream fos = null;
			try {
				int sdkVersion = DeviceUtil.getSDKVersionNumber();
				if (sdkVersion >= 21) {
					try {
						is = context.getAssets().open(
								v_getP2pVodFileName() + "_50");
					} catch (Exception e) {
						is = context.getAssets().open(v_getP2pVodFileName());
					}
				} else {
					is = context.getAssets().open(v_getP2pVodFileName());
				}
				byte[] buffer = new byte[1024 * 8];
				int count = 0;
				fos = new FileOutputStream(p2pVodModulepath + "_tmp");
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				File file1 = new File(p2pVodModulepath + "_tmp");
				File file2 = new File(p2pVodModulepath);
				file1.renameTo(file2);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
						fos = null;
					}
					if (is != null) {
						is.close();
						is = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		LogUtil.d("VooleGLib execute start p2p Proxy before");
		int b = VooleGLib.executePackName(p2pVodModulepath, "");
		LogUtil.d("VooleGLib execute start p2p Proxy after:" + b);
		if (b == 0) {
			//mHasStartP2p = true;
			return true;
		} else {
			return false;
		}
	}
}
