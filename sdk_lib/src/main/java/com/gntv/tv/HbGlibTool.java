package com.gntv.tv;

public class HbGlibTool {
    static {
        System.loadLibrary("hbtvlib");
    }

    public static native String getToken();
}
