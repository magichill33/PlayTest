package com.voole.tvutils;

import android.app.Application;

public class BaseApplication extends Application{
	static{
		System.loadLibrary("vooletoken20");
	}
	
	private static BaseApplication instance;

	public BaseApplication() {
		instance = this;
	}

	public static BaseApplication GetInstance() {
		return instance;
	}

	public native String tokenGet(String path);
	public native String getTokenWithPort(int port);
	public native String getKeyInfo();
	
	/*private void registerMyCallbacks(){
		registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
	}
	
	public class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks { 
	    private int mForegroundActivities = 0;
	    private boolean hasSeenFirstActivity = false;
	    private boolean isChangingConfiguration = false;
	    @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
			LogUtil.d("MyActivityLifecycleCallbacks-->onActivityCreated-->" + activity);
	    }
	  
	    @Override public void onActivityStarted(Activity activity) {
			LogUtil.d("MyActivityLifecycleCallbacks-->onActivityStarted-->" + activity);
	        mForegroundActivities++;
	        if (hasSeenFirstActivity && mForegroundActivities == 1 && !isChangingConfiguration) {
	        	new Thread(){
	        		public void run() {
	        			LogUtil.d("MyActivityLifecycleCallbacks-->applicationDidEnterForeground-->");
	    	        	AuthManager.GetInstance().startAuth();
	    				ProxyManager.GetInstance().startProxy();
	        		};
	        	}.start();
	        }
	        hasSeenFirstActivity = true;
	        isChangingConfiguration = false;
	    }
	    
	    @Override public void onActivityResumed(Activity activity) {
			LogUtil.d("MyActivityLifecycleCallbacks-->onActivityResumed-->" + activity);
	    }
	  
	    @Override public void onActivityPaused(Activity activity) {
			LogUtil.d("MyActivityLifecycleCallbacks-->onActivityPaused-->" + activity);
	    }
	  
	    @Override public void onActivityStopped(Activity activity) {
			LogUtil.d("MyActivityLifecycleCallbacks-->onActivityStopped-->" + activity);
	    	mForegroundActivities--;
	        if (mForegroundActivities == 0) {
	        	new Thread(){
	        		public void run() {
	        			LogUtil.d("MyActivityLifecycleCallbacks-->applicationDidEnterBackground-->");
	    	        	AuthManager.GetInstance().exitAuth();
	    				ProxyManager.GetInstance().exitProxy();
	        		};
	        	}.start();
	        }
	        isChangingConfiguration = activity.isChangingConfigurations();
	    }
	  
	    @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
			LogUtil.d("MyActivityLifecycleCallbacks-->onActivitySaveInstanceState-->" + activity);
	    }
	  
	    @Override public void onActivityDestroyed(Activity activity) {
			LogUtil.d("MyActivityLifecycleCallbacks-->onActivityDestroyed-->" + activity);
	    }
	}*/
}
