package com.vol.sdk.utils;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MySSLSocketFactory extends SSLSocketFactory {
	SSLContext sslContext = SSLContext.getInstance("TLS");

	public MySSLSocketFactory(KeyStore truststore)
			throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
		super(truststore);
		TrustManager tm = new X509TrustManager() {

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

			}

			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

			}
		};

		sslContext.init(null, new TrustManager[] { tm }, null);
	}

	@Override
	public Socket createSocket() throws IOException {
		return sslContext.getSocketFactory().createSocket();
	}

	@Override
	public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
			throws IOException, UnknownHostException {
		return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
	}

	public static SSLSocketFactory getSocketFactory() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory factory = new MySSLSocketFactory(trustStore);
			return factory;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
}
