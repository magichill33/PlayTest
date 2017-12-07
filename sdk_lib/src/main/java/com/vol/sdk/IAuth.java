package com.vol.sdk;

import android.content.Context;

public interface IAuth {
	
	public boolean startAuth(Context context, String param);
	
	public void exitAuth();
	
	public void deleteAuthFiles(Context context);
	
	public boolean isAuthRunning(Context context);
	
	public String getAuthServer();
	
	public String getAuthPort();
	
//	public String getMd5Port();
	
	public User getUser();
}
