package com.gntv.sdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vo.yunsdk.sdk0.VolMediaPlayer;
import com.vo.yunsdk.sdk0.player.VolMediaPlayerListener;
import com.vo.yunsdk.sdk0.player.interfaces.IPlayer;
import com.vol.sdk.AuthManager;
import com.vol.sdk.ProxyManager;
import com.vol.sdk.model.PlayModel;
import com.vol.sdk.VolManager;
import com.vol.sdk.model.Product;
import com.vol.sdk.model.ProductModel;
import com.vol.sdk.utils.DisplayManager;
import com.vol.sdk.utils.LogUtil;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.logging.ErrorManager;

import static com.gntv.sdk.R.id.vooleMediaPlayer;


public class MainActivity extends Activity implements OnClickListener{
    private RelativeLayout rootView;
    private Button btnInit,btnRelease,btnExit;
    private Button btnUrl1,btnUrl2;
    private TextView tvTime;
    private VolMediaPlayer volMediaPlayer;
    private RelativeLayout rl_verify;
    private Button btnSure;
    private boolean isStart = false;
    private boolean isSwitch = false;
    private int intervalTime = 30;// 快进快退间隔时间
    private String timeStr = "";

    //音量设置相关
    private int maxVolume = 50; // 最大音量值
    private int curVolume = 20; // 当前音量值
    private int stepVolume = 0; // 每次调整的音量幅度
    private AudioManager audioMgr = null; // Audio管理器，用于控制音量
    private ProgressDialog bufferDialog = null;
    private boolean isStartInit = false;
    protected boolean isInit = false;
    protected String hid = null;
    private PurchaseView purchaseView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayManager.GetInstance().init(this);
        initView();
        //initSDK();
        initBufferView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("MainActivity--->onResume");
        if(volMediaPlayer!=null){
            if(volMediaPlayer.getCurrentStatus() == IPlayer.Status.Pause){
                volMediaPlayer.resume();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i("MainActivity--->onPause");
    }


    private void initView(){
        rootView = $(R.id.rootView);
        btnInit = $(R.id.btn_init);
        btnRelease = $(R.id.btn_release);
        btnExit = $(R.id.btn_exit);
        btnUrl1 = $(R.id.btn_url1);
        btnUrl2 = $(R.id.btn_url2);
        btnInit.setOnClickListener(this);
        btnRelease.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnUrl1.setOnClickListener(this);
        btnUrl2.setOnClickListener(this);

        rl_verify = $(R.id.rl_verify);
        btnSure = $(R.id.btn_sure);
        btnSure.setOnClickListener(this);

        tvTime = $(R.id.tv_time);
        initPurchaseView(this);
        volMediaPlayer = $(vooleMediaPlayer);
        volMediaPlayer.setMediaPlayerListener(new VolMediaPlayerListener() {

            @Override
            public void onSeekComplete() {
                LogUtil.i("MainActivity--->onSeekComplete");

            }

            @Override
            public void onPrepared() {
                LogUtil.i("MainActivity--->onPrepared");
                LogUtil.i("MainActivity--->onPrepared--->影片时间:"+volMediaPlayer.getDuration());
                volMediaPlayer.start(0);
                bufferDialog.dismiss();
                //playFast();
            }



            @Override
            public boolean onInfo(int what, int extra) {
                if(what == MediaPlayer.MEDIA_INFO_BUFFERING_START){
                    bufferDialog.show();
                }else if(what == MediaPlayer.MEDIA_INFO_BUFFERING_END){
                    bufferDialog.dismiss();
                }
                return false;
            }

            AlertDialog errorDialog = null;
            @Override
            public boolean onError(String errorCode, String errorMsg) {
                LogUtil.i("MainActivity--->onError--->errorCode:"+errorCode + ", errorMsg:"+errorMsg);
                bufferDialog.dismiss();
                isStart = false;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ProductModel productModel = VolManager.getInstance().doProductQuery();
                        LogUtil.i("MainActivity--->onError--->ProductModel:"+productModel);
                        Message msg = handler.obtainMessage();
                        msg.what = TIMER_PAY;
                        msg.obj = productModel.getPayUrl();
                        handler.sendMessage(msg);
                    }
                }).start();

                if(errorDialog == null)
                {
                    errorDialog = new AlertDialog.Builder(MainActivity.this).setTitle("播放提示框")
                            .setMessage(errorCode + ":" + errorMsg)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                if(!errorDialog.isShowing())
                {
                    errorDialog.show();
                }
                return false;
            }

            @Override
            public void onCompletion() {
                LogUtil.i("MainActivity--->onCompletion");
                isStart = false;


            }

            @Override
            public void onBufferingUpdate(int percent) {
                // TODO Auto-generated method stub

            }
        });
        initVolume();
    }



    public <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i("MainActivity--->onDestroy");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_init:
                initSDK();
                break;
            case R.id.btn_release:
                release();
                break;
            case R.id.btn_exit:
                exit();
                break;
            case R.id.btn_url1:
                doPrepare("qcctv1");
                break;
            case R.id.btn_url2:
                doPrepare("qbeijing");
                break;
            case R.id.btn_sure:
                exit();
                break;


            default:
                break;
        }

    }


    private void initSDK(){
        if (!isInit) {
            if (!isStartInit ) {
                isStartInit = true;
                btnInit.setText("初始化中...");

                VolManager.getInstance().initSDK(getApplicationContext(), new VolManager.VolInitCallBack() {
                    @Override
                    public void onInitCompleted(final boolean isSuccess) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                isInit = isSuccess;
                                isStartInit = false;
                                btnInit.setText("初始化：" + (isSuccess ? "成功！" : "失败！"));
                            }
                        });
                    }
                });
            } else {
                Toast.makeText(MainActivity.this, "上一次初始化未完成！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void release(){
        if(isInit){
            volMediaPlayer.release();
            //cancelTask();
            VolManager.getInstance().release();
            isInit = false;
            btnInit.setText("初始化");
            isStart = false;

        }
    }



    private void exit(){
        release();
		/*new Thread(new Runnable() {
			@Override
			public void run() {
				NetUtil.connectServer("http://127.0.0.1:5658" + "/Service.do?Action=exit",
							new StringBuffer(), 1, 3);
			}
		}).start();*/

        finish();
    }


    /**
     * 音量数据等相关工作
     */
    private void initVolume() {
        audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // 获取最大音量
        maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 初始化音量大概为最大音量的1/2
        curVolume = maxVolume / 2;
        // 每次调整的音量大概为最大音量的1/8
        stepVolume = maxVolume / 8;
    }

    /**
     * 调整音量
     */
    private void adjustVolume() {
        audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume,
                AudioManager.FLAG_PLAY_SOUND);
    }

    /**
     * 减小音量
     * */
    public void onClickDownVolume() {
        curVolume -= stepVolume;
        if (curVolume <= 0) {
            curVolume = 0;
            Toast.makeText(this, "已到最小音量", Toast.LENGTH_SHORT).show();
            return;
        }
        adjustVolume();
    }

    /**
     * 增加音量
     * */
    public void onClickUpVolume() {
        curVolume += stepVolume;
        if (curVolume >= maxVolume) {
            curVolume = maxVolume;
            Toast.makeText(this, "已到最大音量", Toast.LENGTH_SHORT).show();
            return;
        }
        adjustVolume();
    }

    // 快退
    public void onClickRewind() {
        int pos = volMediaPlayer.getCurrentPosition();
        if (pos > intervalTime) {
            pos -= (intervalTime * 1000); // milliseconds
            volMediaPlayer.seekTo(pos);
        }
    }

    Timer timer;
    TimerTask task;

    /*private void startTask(){
        if(timer==null){
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    PlayModel playModel = VolManager.getInstance().getPlayUrl("qcctv1");
                    LogUtil.e("MainActivity--->onPostExecute--->PlayModel::" + playModel);

                    Message msg = handler.obtainMessage();
                    if (playModel.getStatus() == 0){
                        msg.what = TIMER_REPEAT;

                    }else if (playModel.getStatus() == 1){
                        msg.what = TIMER_PAY;
                    }else {
                        msg.what = TIMER_ERROR;
                    }
                    msg.obj = playModel.getPlayUrl();
                    handler.sendMessage(msg);
                }
            };
            timer.schedule(task,40*1000,40*1000);
        }
    }*/

//    private void cancelTask(){
//        if (timer!=null){
//            task.cancel();
//            timer.cancel();
//            task = null;
//            timer = null;
//        }
//    }

    private void doPrepare(final String cid){
        if(isInit){
            bufferDialog.show();
            //cancelTask();
           /* new Thread(new Runnable() {
                @Override
                public void run() {
                    PlayModel playModel = VolManager.getInstance().getPlayUrl(cid);
                    LogUtil.e("MainActivity--->onPostExecute--->PlayModel::" + playModel);

                    Message msg = handler.obtainMessage();
                    if (playModel.getStatus() == 0){
                        msg.what = TIMER_PLAY;

                    }else if (playModel.getStatus() == 1){
                        msg.what = TIMER_PAY;
                    }else {
                        msg.what = TIMER_ERROR;
                    }
                    msg.obj = playModel.getPlayUrl();
                    handler.sendMessage(msg);
                    //startTask();
                }
            }).start();*/
            String playUrl = VolManager.getInstance().getPlayUrl(cid);
           // String playUrl = "http://cdn.voole.com:3580/uid$190952191/stamp$1506418298/keyid$67141632/auth$d6e9318e951ee52c577e61a246dc52e6/f3c6e280c8f6becda0fecd0cc55b5612.m3u8?ext=v:1.4,btime:0,etime:0;admt:0,lg:246e4227_874550040:pln:0:mid:1904643:amid:0;oid:100817,f:1,p:0,code:12614,eid:100895,m:1904643,aid:-1&bke=cdnbk.voole.com&type=get_m3u8&host=cdn.voole.com:3580&port=3528&is3d=0&proto=5&isp2p=0&hid=001a34ac5735";
           // String playUrl = "http://cdn.voole.com:3580/uid$190952191/stamp$1506420448/keyid$67141632/auth$cb661eac857305d0d411db0839bae27d/c142948cb6e5f9280fdbfd7048611861.m3u8?ext=v:1.4,btime:0,etime:0;admt:0,lg:246e4227_1819104040:pln:0:mid:92074277:amid:0;oid:100817,f:1,p:0,code:37074,eid:100895,m:92074277,aid:-1&bke=cdnbk.voole.com&type=get_m3u8&host=cdn.voole.com:3580&port=3528&is3d=0&proto=5&isp2p=0&hid=001a34ac5735";
            volMediaPlayer.reset();
            volMediaPlayer.prepareTrdUrl(playUrl);

        }else{
            isStart = false;
            Toast.makeText(getApplicationContext(), "还没有初化，请先初始化", Toast.LENGTH_SHORT).show();
        }

    }


    private void showError(String msg){
        isStart = false;
        new AlertDialog.Builder(MainActivity.this).setTitle("获取播放串出错")
                .setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void initBufferView()
    {
        bufferDialog = new ProgressDialog(this);
        bufferDialog.setProgressStyle(R.style.dialog);// 设置进度条的形式为圆形转动的进度条
        bufferDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        bufferDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        bufferDialog.setIcon(R.mipmap.ic_launcher);//
        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
        bufferDialog.setTitle("播放提示");
        bufferDialog.setMessage("播放过程中出现缓冲");
    }

    private static final int UPDATE_TIME = 50000;
    private static final int TIMER_PLAY = 50002;
    private static final int TIMER_PAY = 50003;
    private static final int TIMER_ERROR = 50004;
    private static final int TIMER_REPEAT = 50005;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TIME:
                    if(tvTime!=null){
                        tvTime.setText(timeStr);
                    }
                    break;
                case TIMER_PLAY:
                    String url = (String) msg.obj;
                    volMediaPlayer.reset();
                    volMediaPlayer.prepareTrdUrl(url);
                    break;
                case TIMER_PAY:
                    String text = (String) msg.obj;
                    Bitmap bitmap = QRCodeUtil.createImage(text, changeWidth(504), changeHeight(504));
                    String uid = "123455";
                    showPurchase(uid,bitmap);
                    break;
                case TIMER_ERROR:
                    bufferDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"未知错误，请进行重试",Toast.LENGTH_LONG);
                    break;
                case TIMER_REPEAT:
                    LogUtil.i("TIMER_REPEAT");
                    String url1 = (String) msg.obj;
                    try {
                        volMediaPlayer.setDataSource(url1);
                    } catch (Exception e) {
                        LogUtil.i("TIMER_REPEAT--->"+e);
                    }
                    break;
                default:
                    break;
            }
        };
    };

    private void initPurchaseView(Context context){
        purchaseView = new PurchaseView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        purchaseView.setLayoutParams(params);
        purchaseView.setVisibility(View.GONE);
        purchaseView.setRefreshListener(new PurchaseView.RefreshListener() {

            @Override
            public void doRefresh(PurchaseView view) {
                view.hide();
                doPrepare("qcctv1");
            }
        });
        rootView.addView(purchaseView);
    }

    private void showPurchase(String uid,Bitmap bitmap){
        if(purchaseView.getVisibility()!=View.VISIBLE){
            purchaseView.setBitmap(bitmap);
            purchaseView.setUid(uid);
            purchaseView.setVisibility(View.VISIBLE);
            purchaseView.getFocus();
        }
    }

    private int changeWidth(int width) {
        return DisplayManager.GetInstance().changeWidthSize(width);
    }

    private int changeHeight(int height) {
        return DisplayManager.GetInstance().changeHeightSize(height);
    }

}
