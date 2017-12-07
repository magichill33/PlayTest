package com.gntv.sdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vol.sdk.utils.DisplayManager;
import com.vol.sdk.utils.LogUtil;


@SuppressWarnings("ResourceType")
public class PurchaseView extends RelativeLayout {
	//private TextView tv_uid = null;
	//private TextView tv_uid_desc = null;
	private ImageView iv_code = null;
	private TextView tipText = null;
	private Button button = null;
	private Context ctx = null;
	private RefreshListener refreshListener = null;
	
	public PurchaseView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context){
		setFocusable(true);
		setFocusableInTouchMode(true);
		setClickable(true);
		
		ctx = context;
		//setGravity(Gravity.CENTER);
		setBackgroundResource(R.mipmap.bg_purchase);
		
		RelativeLayout rlContainer = new RelativeLayout(context);
		rlContainer.setId(1000);
		rlContainer.setFocusable(true);
		rlContainer.setFocusableInTouchMode(true);
		rlContainer.setClickable(true);
		LayoutParams rlParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
		rlParams.addRule(ALIGN_PARENT_RIGHT);
		rlParams.setMargins(0, changeHeight(30), changeWidth(40), 0);
		rlContainer.setLayoutParams(rlParams);
		addView(rlContainer);
		
		iv_code = new ImageView(context);
		LayoutParams ivParams = new LayoutParams(changeWidth(504),changeHeight(504));
		ivParams.addRule(CENTER_HORIZONTAL);
		iv_code.setLayoutParams(ivParams);
		iv_code.setId(1001);
		rlContainer.addView(iv_code);
		
/*		tv_uid_desc = new TextView(context);
		tv_uid_desc.setId(1000001);
		LayoutParams descParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		//uidParams.addRule(LEFT_OF, 1000);
		descParams.addRule(ALIGN_PARENT_BOTTOM);
		descParams.setMargins(changeWidth(80), 0, 0, changeHeight(30));
		tv_uid_desc.setLayoutParams(descParams);
		tv_uid_desc.setTextSize(changeTextSize(80));
		tv_uid_desc.setText("您的用户ID是:");
		tv_uid_desc.setTextColor(getColor(R.color.white)); 
		TextPaint tp = tv_uid_desc.getPaint(); 
		tp.setFakeBoldText(true); 
		addView(tv_uid_desc);
		
		tv_uid = new TextView(context);
		LayoutParams uidParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		uidParams.addRule(RIGHT_OF,1000001);
		uidParams.addRule(ALIGN_BASELINE,1000001);
		tv_uid.setLayoutParams(uidParams);
		tv_uid.setTextSize(changeTextSize(80));
		tv_uid.setText("您的用户ID是:");
		tv_uid.setTextColor(getColor(R.color.red)); 
		TextPaint tp1 = tv_uid.getPaint(); 
		tp1.setFakeBoldText(true); 
		addView(tv_uid);*/
		
		tipText = new TextView(context);
		LayoutParams tipParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		tipParams.addRule(BELOW, 1001);
		tipParams.addRule(CENTER_HORIZONTAL);
		tipParams.topMargin = changeHeight(10);
		tipText.setLayoutParams(tipParams);
		tipText.setId(1002);
		tipText.setTextSize(changeTextSize(48));
		tipText.setText("[请使用微信扫描二维码]");
		tipText.setTextColor(getColor(R.color.white)); 
		rlContainer.addView(tipText);
		
		button = new Button(context);
		LayoutParams btnParams = new LayoutParams(changeWidth(200),changeHeight(80));
		btnParams.addRule(ALIGN_PARENT_BOTTOM);
		btnParams.addRule(CENTER_HORIZONTAL);
		btnParams.setMargins(0, 0, changeWidth(160), changeHeight(30));
		//button.setBackgroundResource(R.drawable.cs_button);
		button.setLayoutParams(btnParams);
		button.setId(1003);
		button.setTextSize(changeTextSize(30));
		button.setTextColor(getColor(R.color.white));
		button.setGravity(Gravity.CENTER);
		button.setText("已完成支付");
		rlContainer.addView(button);
		
	}
	
	@Override
	protected void onFocusChanged(boolean gainFocus, int direction,
			Rect previouslyFocusedRect) {
		LogUtil.i("PurchaseView--->onFocusChanged--->"+gainFocus);
		if(gainFocus){
			button.setBackgroundResource(R.mipmap.cs_menu_button_bg_focus);
		}else{
			button.setBackgroundResource(R.mipmap.cs_menu_button_bg);
		}
		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
	}
	
	public void setBitmap(Bitmap bitmap){
		iv_code.setBackgroundDrawable(new BitmapDrawable(ctx.getResources(),bitmap));
	}
	
	public void setUid(String uid){
		//tv_uid.setText(uid);
	}
	
	private int changeWidth(int width){
		return DisplayManager.GetInstance().changeWidthSize(width);
	}
	
	private int changeHeight(int height){
		return DisplayManager.GetInstance().changeHeightSize(height);
	}
	
	private int changeTextSize(int size){
		return DisplayManager.GetInstance().changeTextSize(size);
	}
	
	protected int getColor(int id) {
		return ctx.getResources().getColor(id);
	}
	
	public void hide(){
		setVisibility(GONE);
	}
	
	public void setRefreshListener(RefreshListener refreshListener) {
		this.refreshListener = refreshListener;
	}

	public interface RefreshListener{
		void doRefresh(PurchaseView view);
	}
	
	public void getFocus(){
		requestFocus();
		//button.requestFocus();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		LogUtil.i("PurchaseView--------->onKeyDown----->"+keyCode);
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
		case KeyEvent.KEYCODE_ESCAPE:
			break;

		default:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		LogUtil.i("PurchaseView--------->onKeyUp----->"+keyCode);
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
		case KeyEvent.KEYCODE_ESCAPE:
			break;
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_DPAD_CENTER:
			if(refreshListener!=null){
				refreshListener.doRefresh(PurchaseView.this);
			}
			break;
		default:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
