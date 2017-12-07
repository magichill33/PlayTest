package com.vol.sdk.utils;
public class ClickUtils {
    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time; 
        if ( 0 < timeD && timeD < 1800) {   
            return true;   
        }   
          
        return false;   
    }
    
    public static boolean isDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time; 
        if ( 0 < timeD && timeD < 500) {   
            return true;   
        }   
          
        return false;   
    }
}