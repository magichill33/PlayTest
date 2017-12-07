package com.vooleglib;

public class VooleGLib {
	static{
		System.loadLibrary("vooleglib30");
	}
	
    /*
     * example: execute("/data/data/com.voole.epg/files/vooleauthd");
     *
     * filepath is absolute path
     *
     * return value
     * 0   ---  start success
     * < 0 ---  start failed 
     */
	
	public static native int execute(String filepath);
	
	/*
	* param exename is the program which you want to check running or not
	*
	* return value
	* 0   --- program doesn't running
	* < 0 --- program is running
	*/

	public static native int isExeRunning(String exename);
	
	/*
	* to terminate the program of exename
	* parameter exename is the name of the program you want to terminamte
	*
	* return value
	* 0   --- success
	* < 0 --- failed
	*/

	public static native int killExe(String exename);

	/*
	* to terminate the program of exename with given num of signal
	* parameter exename is the name of the program you want to terminamte
	*
	* return value
	* 0   --- success 
	* < 0 --- failed.
	*/

	public static native int killExeBySig(String exename, int signum);

    /*
     * example: executeWithPara("/data/data/com.voole.epg/files/vooleauthd", "Para:key&urlver");
     *
     * processPath: is absolute path
     * para: start parameter or null
     *
     * return value:
     * 0   ---  start success
     * < 0 ---  start failed
     */

    public static native int executeWithPara(String processPath, String para);

    /*
     * example: executeAutoPort("/data/data/com.voole.epg/files/vooleauthd", "Para:key&urlver");
     *
     * path: is absolute path
     * para: start parameter or null
     *
     * return value:
     * >= 0 ---  listen port of start process
     * <  0 ---  start failed
     */

    public static native int executeAutoPort(String path, String para);

    /*
     * example: executePackName("/data/data/com.voole.epg/files/vooleauthd", "Para:key&urlver");
     * 
     * path: is absolute path
     * para: start parameter or null
     *
     * return value:
     * 0   ---  start success
     * < 0 ---  start failed
     */

    public static native int executePackName(String path, String para);

    /*
     * example: executeAutoPortPackName("/data/data/com.voole.epg/files/vooleauthd", "Para:key&urlver");
     *
     * path: is absolute path
     * para: start parameter or null
     *
     * return value:
     * >  0 ---  port of start process listen
     * <= 0 ---  start failed
     */

    public static native int executeAutoPortPackName(String path, String para);

    /*
     * return apk pack info
     *
     */

    public static native String getPackName();
    
    /*
     * return apk pack info stamp
     *
     */

    public static native String getPackNameStamp();
}
