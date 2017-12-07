package com.vol.sdk.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.net.Uri;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

public class DeviceUtil {
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (inetAddress instanceof Inet6Address)
						continue;
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString().trim();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("IpAddress", ex.toString());
		}
		return null;
	}

	public static String getSequenceNo() {
		String sequenceno = null;
		long time = System.currentTimeMillis();
		final Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTimeInMillis(time);
		int mYear = mCalendar.get(Calendar.YEAR);
		int mHour = mCalendar.get(Calendar.HOUR);
		int mMinutes = mCalendar.get(Calendar.MINUTE);
		int mSeconds = mCalendar.get(Calendar.SECOND);
		int mMiliSeconds = mCalendar.get(Calendar.MILLISECOND);
		sequenceno = String.format("%s_%4d%02d%02d%02d%07d", "10000101", mYear,
				mHour, mMinutes, mSeconds, mMiliSeconds);
		return sequenceno;
	}

	public static String getAppVersionName(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static int getAppVersionCode(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public static String getAppName(Context context) {
		return context.getPackageName();
	}

	public static boolean hasEnoughSpaceOnCache(File path, long updateSize) {
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return (updateSize < availableBlocks * blockSize);
	}

	public static void installApk(Context context, File apkFile) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(apkFile),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	public static void setCurrentPlayerMute(Context context) {
		AudioManager audioManager = (AudioManager) context
				.getApplicationContext()
				.getSystemService(Context.AUDIO_SERVICE);
		if (getCurrentPlayerVolume(context) <= 0) {
			audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
		} else {
			audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
		}
	}

	public static void setCurrentPlayerMute(Context context, boolean b) {
		AudioManager audioManager = (AudioManager) context
				.getApplicationContext()
				.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamMute(AudioManager.STREAM_MUSIC, b);
	}

	public static int getCurrentSystemVolume(Context context) {
		AudioManager audioManager = (AudioManager) context
				.getApplicationContext()
				.getSystemService(Context.AUDIO_SERVICE);
		return audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
	}

	public static int getCurrentPlayerVolume(Context context) {
		AudioManager audioManager = (AudioManager) context
				.getApplicationContext()
				.getSystemService(Context.AUDIO_SERVICE);
		return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	}

	public static void increasePlayerVolume(Context context) {
		AudioManager audioManager = (AudioManager) context
				.getApplicationContext()
				.getSystemService(Context.AUDIO_SERVICE);
		if (getCurrentPlayerVolume(context) <= 0) {
			audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
		}
		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
				AudioManager.ADJUST_RAISE, AudioManager.FX_FOCUS_NAVIGATION_UP);
	}

	public static void decreasePlayerVolume(Context context) {
		AudioManager audioManager = (AudioManager) context
				.getApplicationContext()
				.getSystemService(Context.AUDIO_SERVICE);
		if (getCurrentPlayerVolume(context) <= 0) {
			audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
		}
		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
				AudioManager.ADJUST_LOWER, AudioManager.FX_FOCUS_NAVIGATION_UP);
	}

	public static void setPlayerVolume(Context context, int v) {
		AudioManager audioManager = (AudioManager) context
				.getApplicationContext()
				.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, v, 0);
	}

	public static void setSystemVolume(Context context, int v) {
		AudioManager audioManager = (AudioManager) context
				.getApplicationContext()
				.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, v, 0);
	}

	public static int getPlayerMaxVolume(Context context) {
		AudioManager audioManager = (AudioManager) context
				.getApplicationContext()
				.getSystemService(Context.AUDIO_SERVICE);
		return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	}

	public static int getSystemMaxVolume(Context context) {
		AudioManager audioManager = (AudioManager) context
				.getApplicationContext()
				.getSystemService(Context.AUDIO_SERVICE);
		return audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
	}

	public static void setPlayerMaxVolume(Context context) {
		int max = getPlayerMaxVolume(context);
		setPlayerVolume(context, max);
	}

	public static void setSystemMaxVolume(Context context) {
		int max = getSystemMaxVolume(context);
		setSystemVolume(context, max);
	}

	public static String findPidOfAgent(String pName) {
		String pid;
		String temp;
		pid = "";
		try {
			// String[] cmd = { "/bin/sh", "-c", "ps > /dev/null 2>&1" };
			// Process p = Runtime.getRuntime().exec(cmd);
			Process p = Runtime.getRuntime().exec("ps");
			p.waitFor();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((temp = stdInput.readLine()) != null) {
				if (temp.contains(pName)) {
					String[] cmdArray = temp.split(" +");
					// for (int i = 0; i < cmdArray.length; i++) {
					// Log.d("DDDDDDDDDDD", "loop i=" + i + " => " +
					// cmdArray[i]);
					// }
					pid = cmdArray[1];
				}
			}
		} catch (IOException e) {
			Log.d("VooleEpg", "DeviceUtil-->findPidOfAgent-->" + e.toString());
		} catch (InterruptedException e) {
			Log.d("VooleEpg", "DeviceUtil-->findPidOfAgent-->" + e.toString());
		}
		return pid;
	}

	public static boolean checkPackageExist(Context context, String packageName) {
		if (packageName == null || packageName.equals("")) {
			return false;
		}
		try {
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(packageName,
							PackageManager.GET_UNINSTALLED_PACKAGES);
			if (info != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public static void setFileToPermission(final String fileName) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Process process = null;
				try {
					process = Runtime.getRuntime()
							.exec("chmod 777 " + fileName);
					process.waitFor();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					if (process != null) {
						try {
							process.getInputStream().close();
							process.getOutputStream().close();
							process.getErrorStream().close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}
		}).start();
	}

	public static String getCloudUUID() {
		try {
			Class<?> cloudUuid = Class
					.forName("com.yunos.baseservice.clouduuid.CloudUUID");
			Method m = cloudUuid.getMethod("getCloudUUID");
			String result = (String) m.invoke(null);
			return result;
		} catch (Exception e) {
			return "false";
		}

	}

	public static String getIMEI(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		if (PackageManager.PERMISSION_GRANTED == context.getPackageManager()
				.checkPermission(Manifest.permission.READ_PHONE_STATE,
						context.getPackageName())) {
			return manager.getDeviceId();
		} else {
			return null;
		}
	}

	public static String getMacAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				if (intf.getName().equals("eth0")) {
					StringBuffer sb = new StringBuffer();
					byte[] macBytes = intf.getHardwareAddress();
					for (int i = 0; i < macBytes.length; i++) {
						String sTemp = Integer.toHexString(0xFF & macBytes[i]);
						if (sTemp.length() == 1) {
							sb.append("0");
						}
						sb.append(sTemp);
					}
					return sb.toString();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean copyFileFromAssets(Context context, String fileName,
			String path) {
		boolean copyIsFinish = false;
		try {
			File file = new File(path + "/" + fileName);
			if (file.exists()) {
				return true;
			}
			InputStream is = context.getAssets().open(fileName);
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024 * 4];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
			copyIsFinish = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return copyIsFinish;
	}

	public static boolean isInstalled(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		int len = pinfo.size();
		for (int i = 0; i < len; i++) {
			if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
				return true;
		}
		return false;
	}

	private String getCPUSerialNumber() {
		ProcessBuilder cmd;
		String cpuInfo = "";
		String result = "";
		int serialIndex = -1;
		final int CPU_SERIAL_NUM = 17;

		try {
			String[] args = { "/system/bin/cat", "/proc/cpuinfo" };
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[1024];

			while (in.read(re) != -1) {
				cpuInfo = cpuInfo + new String(re);
			}

			serialIndex = cpuInfo.indexOf("Serial");
			serialIndex = cpuInfo.indexOf(": ", serialIndex) + 2;
			result = cpuInfo.substring(serialIndex, serialIndex
					+ CPU_SERIAL_NUM);
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		result = result.replace("\n", "");
		result = result.replace("\r", "");

		// 去掉可能的无效CPU序列号
		/*
		 * if (result.matches(REGEX_ONE_MORE_ZERO)) { return null; }
		 */

		return result;
	}
	
	public static int getSDKVersionNumber() {  
	    int sdkVersion;  
	    try {  
	        sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK_INT); 
	    } catch (NumberFormatException e) {  
	        sdkVersion = 0;  
	    }  
	    return sdkVersion;  
	}  


}
