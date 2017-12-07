package com.vol.sdk.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FileUtil {
	
	/**
	 * 
	 * @param urlstr
	 * @param filePath
	 * @param fileName
	 * @return 1:file exists; 0:file downLoad success; -1:file download fail
	 */
	public static int downLoadFile(String urlstr, String filePath, String fileName){
		URL url;
		FileOutputStream fileOutputStream;
		InputStream inputStream;
		try {
			url = new URL(urlstr);
			URLConnection connection = url.openConnection();
			inputStream = connection.getInputStream();
			/*fileSize = connection.getContentLength();
			if (fileSize < 0) {
//				throw new RuntimeException("�ļ���С��ȡ����");
				return -1;
			}
			if (inputStream == null) {
//				throw new RuntimeException("�ļ���ݻ�ȡ����");
				return -1;
			}*/
			File file = new File(filePath , fileName);
			if (file.exists()) {
				return 1;
			}
			fileOutputStream = new FileOutputStream(file);
			byte[] buffer = new byte[1024*4];
			int count = 0;
			while ((count = inputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, count);
				Log.d("VooleEpg2.0","fileDown--->" + count);
			}
			Log.d("VooleEpg2.0", "fileDown--->finish");
			fileOutputStream.close();
			inputStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	/**
	 * 
	 * @param urlstr
	 * @param filePath
	 * @param fileName
	 * @return 0:file save Success; -1:file save Fail
	 */
	public static int saveFileToLocal(String urlstr, String filePath, String fileName){
//		int fileSize = 0;
		URL url;
		FileOutputStream fileOutputStream;
		InputStream inputStream;
		try {
			url = new URL(urlstr);
			URLConnection connection = url.openConnection();
			inputStream = connection.getInputStream();
			/*fileSize = connection.getContentLength();
			if (fileSize < 0) {
				throw new RuntimeException("�ļ���С��ȡ����");
			}
			if (inputStream == null) {
				throw new RuntimeException("�ļ���ݻ�ȡ����");
			}*/
			File file = new File(filePath , fileName);
			if (file.exists()) {
				file.delete();
			}
			fileOutputStream = new FileOutputStream(file);
			byte[] buffer = new byte[1024*4];
			int count = 0;
			while ((count = inputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, count);
			}
			fileOutputStream.close();
			inputStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	public static InputStream getFileInputStream(String filePath){
		File file = new File(filePath);
		if (file.exists()) {
			try {
				return new FileInputStream(file);
			} catch (Exception e) {
				return null;
			}  
		}else{
			return null;
		}
	}
	
}
