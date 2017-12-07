package com.vol.sdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class NetUtil {
	private static final int RETRY_TIME = 1000;
	
	public static boolean connectServer(String url, StringBuffer httpMessage, int connectTimes, int timeOut, int readTimeOut) {
		HttpURLConnection httpConnection = null;
		long startTime = 0;
		for(int i = 0; i < connectTimes; i++){
			try {
				Log.d("NetUtil", "NetUtil--->connectServer--->start------->" + url );
				URL httpUrl = new URL(url);
				Log.d("NetUtil", "NetUtil--->connectServer--->openConnection---->start" );
				URLConnection connection = httpUrl.openConnection();
				Log.d("NetUtil", "NetUtil--->connectServer--->openConnection---->end" );
//				System.setProperty("sun.net.client.defaultConnectTimeout", "" + (1000 * timeOut));
//				System.setProperty("sun.net.client.defaultReadTimeout", "" + (1000 * readTimeOut));
				connection.setConnectTimeout(1000 * timeOut);
				connection.setReadTimeout(1000 * readTimeOut);
				httpConnection = (HttpURLConnection) connection;
				startTime = System.currentTimeMillis();
				Log.d("NetUtil", "NetUtil--->connectServer--->getResponseCode-->start" );
				int responseCode = httpConnection.getResponseCode();
				Log.d("NetUtil", "NetUtil--->connectServer--->getResponseCode-->end" );
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream urlStream = httpConnection.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlStream));
					String sCurrentLine = "";
					while ((sCurrentLine = bufferedReader.readLine()) != null) {
						httpMessage.append(sCurrentLine);
					}

					Log.d("NetUtil", "NetUtil--->connectServer--->end" );
					return true;
				}
			} catch (Exception e) {
				Log.e(NetUtil.class.getName(), "Exception",e);
				Log.d("NetUtil", "connect--->" + (System.currentTimeMillis() - startTime));
				if(i >= connectTimes - 1){
					return false;
				}
			}finally{
				if (httpConnection != null) {
					httpConnection.disconnect();
					httpConnection = null;
				}
			}
		}
		return false;
	}
	public static boolean connectServer(String url, StringBuffer httpMessage, int connectTimes, int timeOut) {
		return connectServer(url, httpMessage, connectTimes, timeOut, 10);
	}
	
	public static boolean connectServer(String url, StringBuffer httpMessage) {
		return connectServer(url, httpMessage, 5, 6);
	}
	
	public static boolean doGet(String url, int connectTimes, int timeOut,int readTimeOut){
		HttpURLConnection httpConnection = null;
		long startTime = 0;
		for(int i = 0; i < connectTimes; i++){
			try {
				URL httpUrl = new URL(url);
				URLConnection connection = httpUrl.openConnection();
				connection.setConnectTimeout(1000 * timeOut);
				connection.setReadTimeout(1000 * readTimeOut);
				httpConnection = (HttpURLConnection) connection;
				startTime = System.currentTimeMillis();
				int responseCode = httpConnection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					return true;
				}
			} catch (Exception e) {
				Log.e(NetUtil.class.getName(), "Exception",e);
				Log.d("NetUtil", "doGet--->" + (System.currentTimeMillis() - startTime));
				if(i >= connectTimes - 1){
					return false;
				}
			}finally{
				if (httpConnection != null) {
					httpConnection.disconnect();
					httpConnection = null;
				}
			}
		}
		return false;
		
		
	}
	
	public static InputStream connectServerWithHttps(String url, int connectTimes, int timeOut) {
		return connectServer(url, connectTimes, timeOut, 20);
	}
	
	public static InputStream connectServerWithHttps(String url) {
		return connectServer(url, 5, 6);
	}
	
	public static InputStream connectServerWithHttps(String url,
			int connectTimes, int timeOut, int readTimeOut) {
		for (int i = 0; i < connectTimes; i++) {
			try {
				HttpUriRequest request = new HttpPost(url);
				HttpClient httpClient = getHttpsClient();
				HttpResponse httpResponse = httpClient.execute(request);
				if (httpResponse != null) {
					StatusLine statusLine = httpResponse.getStatusLine();
					if (statusLine != null && statusLine.getStatusCode() == HttpStatus.SC_OK) {
						return httpResponse.getEntity().getContent();
					}
				}

			} catch (Exception e) {
				if (e != null) {
					LogUtil.d("connectServer--> exception-->" + e.toString());
				}
				if (i >= connectTimes - 1) {
					return null;
				}
			}
		}
		return null;
	}
	
	public static InputStream connectServer(String url, int connectTimes, int timeOut, int readTimeOut) {
		int retryTime = RETRY_TIME;
		for(int i = 0; i < connectTimes; i++){
			try {
				URL httpUrl = new URL(url);
				URLConnection connection = httpUrl.openConnection();
				connection.setConnectTimeout(1000 * timeOut);
				connection.setReadTimeout(1000 * readTimeOut);
//				connection.setRequestProperty("Accept-Encoding", "");
				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				int responseCode = httpConnection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					return httpConnection.getInputStream();
				}
			} catch (Exception e) {
				if(e != null){
					LogUtil.d("connectServer--> exception-->" + e.toString());
				}
				try {
					Thread.sleep(retryTime);
					retryTime += RETRY_TIME;
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(i >= connectTimes - 1){
					return null;
				}
			}
		}
		return null;
	}
	
	public static InputStream connectServer(String url, int connectTimes, int timeOut) {
		return connectServer(url, connectTimes, timeOut, 20);
	}
	
	public static InputStream connectServer(String url) {
		return connectServer(url, 5, 6);
	}
	
	public static boolean doPostWithHttps(String urlStr, String params, StringBuffer httpMessage) {
		int connectTimes = 5;
		for (int i = 0; i < connectTimes; i++) {
			try {
				HttpPost request = new HttpPost(urlStr);
				request.setEntity(new StringEntity(params));
				HttpClient httpClient = getHttpsClient();
				HttpResponse httpResponse = httpClient.execute(request);
				if (httpResponse != null) {
					StatusLine statusLine = httpResponse.getStatusLine();
					if (statusLine != null && statusLine.getStatusCode() == HttpStatus.SC_OK) {
						BufferedReader reader = null;
						try {
							reader = new BufferedReader(new InputStreamReader(
									httpResponse.getEntity().getContent(),
									"UTF-8"));
							String line = null;
							while ((line = reader.readLine()) != null) {
								httpMessage.append(line);
							}

						} catch (Exception e) {
							Log.e("https", e.getMessage());
						} finally {
							if (reader != null) {
								reader.close();
								reader = null;
							}
						}
					}
				}
				return true;
			} catch (Exception e) {
				LogUtil.d("doPostWithHttps--> exception-->" + e.toString());
				if (i >= connectTimes - 1) {
					return false;
				}
			}
		}
		return false;
	}
	
	public static boolean doPost(String urlStr, String params, StringBuffer httpMessage) {
		int connectTimes = 5;
		for (int i = 0; i < connectTimes; i++) {
			try {
				byte[] requestStringBytes = params.getBytes("utf-8");
				URL url = new URL(urlStr);
				HttpURLConnection httpurlconnection = (HttpURLConnection) url .openConnection();
				httpurlconnection.setConnectTimeout(1000 * 10);
				httpurlconnection.setReadTimeout(1000 * 30);
				httpurlconnection.setDoInput(true);
				httpurlconnection.setDoOutput(true);
				httpurlconnection.setUseCaches(false);
				httpurlconnection.setRequestMethod("POST");
				httpurlconnection.setRequestProperty("Content-length", "" + requestStringBytes.length);
				httpurlconnection.setRequestProperty("Content-Type", "application/octet-stream");
				httpurlconnection.setRequestProperty("Connection", "Keep-Alive");// ά�ֳ�����
				// httpurlconnection.setRequestProperty("Charset", "UTF-8");

				// �������������д�����
				OutputStream outputStream = httpurlconnection.getOutputStream();
				outputStream.write(requestStringBytes);
				outputStream.flush();
				outputStream.close();

				int code = httpurlconnection.getResponseCode();

				if (code == 200) {
					InputStream urlStream = httpurlconnection.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlStream));
					String sCurrentLine = "";
					String sTotalString = "";
					while ((sCurrentLine = bufferedReader.readLine()) != null) {
						// System.out.println(sCurrentLine);
						sTotalString += sCurrentLine + "\n";
						httpMessage.append(sCurrentLine);
					}
				}

				return true;
			} catch (Exception e) {
				LogUtil.d("doPost--> exception-->" + e.toString());
				if (i >= connectTimes - 1) {
					return false;
				}
			}
		}
		return false;
	}
	
	
	public static InputStream doPostWithHttps(String urlStr, String params) {
		try {
			HttpPost request = new HttpPost(urlStr);
			request.setEntity(new StringEntity(params));
			HttpClient httpClient = getHttpsClient();
			HttpResponse httpResponse = httpClient.execute(request);
			if (httpResponse != null) {
				StatusLine statusLine = httpResponse.getStatusLine();
				if (statusLine != null
						&& statusLine.getStatusCode() == HttpStatus.SC_OK) {
					return httpResponse.getEntity().getContent();
				}
			}
		} catch (Exception e) {
			LogUtil.d("doPostWithHttps--> exception-->" + e.toString());
		}
		
		return null;
	}

	
	public static InputStream doPost(String urlStr, String params) {
		try {
			byte[] requestStringBytes = params.getBytes("utf-8");
			URL url = new URL(urlStr);
			HttpURLConnection httpurlconnection = (HttpURLConnection) url .openConnection();
			httpurlconnection.setConnectTimeout(1000 * 10);
			httpurlconnection.setReadTimeout(1000 * 30);
			httpurlconnection.setDoInput(true);
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setUseCaches(false);
			httpurlconnection.setRequestMethod("POST");
			httpurlconnection.setRequestProperty("Content-length", "" + requestStringBytes.length);
			httpurlconnection.setRequestProperty("Content-Type", "application/octet-stream");
			httpurlconnection.setRequestProperty("Connection", "Keep-Alive");// ά�ֳ�����
			// httpurlconnection.setRequestProperty("Charset", "UTF-8");

			OutputStream outputStream = httpurlconnection.getOutputStream();
			outputStream.write(requestStringBytes);
			outputStream.flush();
			outputStream.close();

			int code = httpurlconnection.getResponseCode();

			if (code == 200) {
				return httpurlconnection.getInputStream();
			}
		} catch (Exception e) {
			LogUtil.d("doPost--> exception-->" + e.toString());
		}
		return null;
	}
	
	public static boolean checkUrlAvailable(String url){
		try {
			URL mUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)mUrl.openConnection();
			int state = conn.getResponseCode();
			if(state == 200){
				return true;
			}
			else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isNetWorkAvailable(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) 
					context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager == null){
			return false;
		}
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if(info != null){
			if(info.getState() == NetworkInfo.State.CONNECTED){
				return true;
			}
		}
		return false;
	}
	
	
	/** 获取网络数据，以String形式返回<br>
	 * <b>*注意此方法并不会将获取到的网络数据保存到本地文件中*</b>
	 * @param urlStr 需要获取数据的URL地址
	 * @return 读取到的数据 */
	public static String readString(String urlStr) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(8000*2);
			conn.setConnectTimeout(8000*2);
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			InputStream in = conn.getInputStream();
			int len = -1;
			while((len = in.read(buffer))>0){
				bout.write(buffer,0,len);
			}
			return new String(bout.toByteArray(),Charset.forName("UTF-8"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
				if (conn != null)
					conn.disconnect();
		}
		return null;
	}

	/** 获取网络数据，并保存到指定的文件中
	 * @param urlStr 需要获取数据的URL地址
	 * @param file 需要将数据保存到的文件，使用绝对路径 */
	public static void downLoadFile(String urlStr, File file) {
		//判断文件需要存储到的文件夹是否存在，若不存在，则创建文件夹
		String absolutePath = file.getAbsolutePath();
		String dir = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				fos = new FileOutputStream(file);

				int len = 0;
				byte[] buffer = new byte[1024];
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				fos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static HttpClient getHttpsClient() {
		BasicHttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params,
				HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);

		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https", MySSLSocketFactory
				.getSocketFactory(), 443));

		ClientConnectionManager connMgr = new ThreadSafeClientConnManager(
				params, schReg);

		return new DefaultHttpClient(connMgr, params);
	}
}
