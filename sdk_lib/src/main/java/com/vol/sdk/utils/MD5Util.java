package com.vol.sdk.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	protected static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {
			LogUtil.d(MD5Util.class.getName() + "初始化失败，MessageDigest不支持MD5Util。");
			nsaex.printStackTrace();
		}
	}

	public static String getApkMD5(Context context) {
		String path = context.getPackageResourcePath();
		LogUtil.d("MD5Util------------------>path---->"+path);
		File file = new File(path);
		try {
			String md5 = getFileMD5String(file);
			return md5;
		} catch (IOException e) {
			LogUtil.d("MD5Util------------------>"+e.getMessage());
		}

		return null;
	}

	/**
	 * 生成文件的md5校验值
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file) throws IOException {
		InputStream fis = new FileInputStream(file);
		return getFileInputStreamMD5String(fis);
	}
	
	public static String getFileInputStreamMD5String(InputStream inputStream) throws IOException {
		if(inputStream == null){
			LogUtil.d("getFileInputStreamMD5String----------inputStream-------->null");
			return "";
		}else{
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = inputStream.read(buffer)) > 0) {
				messagedigest.update(buffer, 0, numRead);
			}
			inputStream.close();
			return bufferToHex(messagedigest.digest());
		}
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			byte2hex(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	/*private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4]; // 取字节中高 4 位的数字转换, >>>
												// 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
		char c1 = hexDigits[bt & 0xf]; // 取字节中低 4 位的数字转换
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}*/

	/*private static String toHexString(byte[] block) {

		StringBuffer buf = new StringBuffer();
		int len = block.length;
		for (int i = 0; i < len; i++) {
			byte2hex(block[i], buf);
			if (i < len - 1) {
				buf.append(":");
			}
		}
		return buf.toString();
	}*/

	/**
	 * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
	 */
/*	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };*/

	private static void byte2hex(byte b, StringBuffer buf) {
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		int high = ((b & 0xf0) >> 4);
		int low = (b & 0x0f);
		buf.append(hexChars[high]);
		buf.append(hexChars[low]);
	}
}
