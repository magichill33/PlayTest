package com.vol.sdk.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.view.View;

@SuppressLint("NewApi")
public class AnimUtil {
	/**
	 * 
	 * @param left	左视图
	 * @param right	右视图
	 * @param value 移动距离，正向右，负向左
	 */
	public static void setAnim(final View left, final View right, float value) {
		setAnim(left, right, null, value, 2000) ;
	}
	/**
	 * 
	 * @param left 	左视图
	 * @param right 右视图
	 * @param value 移动距离，正向右，负向左
	 * @param duration 移动时间
	 */
	public static void setAnim(final View left, final View right, float value, long duration) {
		setAnim(left, right, null, value, duration) ;
	}

	/**
	 * @param left	左视图
	 * @param right	右视图
	 * @param cur	中间视图
	 * @param value	移动距离，正向右，负向左
	 */
	public static void setAnim(final View left, final View right, final View cur, float value) {
		setAnim(left, right, cur, value, 2000) ;
	}
	
	/**
	 * @param left	左视图
	 * @param right	右视图
	 * @param cur	中间视图
	 * @param value	移动距离，正向右，负向左
	 * @param duration	移动时间，默认2000，单位毫秒
	 */
	public static void setAnim( final View left, final View right, final View cur, float value ,long duration) {
		AnimatorSet animatorSet = new AnimatorSet();
		ValueAnimator valueAnimator = ValueAnimator.ofFloat(value);
		valueAnimator.setDuration(duration);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				left.setTranslationX((Float) animation.getAnimatedValue());
			}
		});
		
		if (cur == null) {
			animatorSet.play(valueAnimator).with(
					ObjectAnimator.ofFloat(right, "TranslationX", value)
					.setDuration(duration));
		} else {
			animatorSet.play(valueAnimator).with(
					ObjectAnimator.ofFloat(cur, "TranslationX", value)
					.setDuration(duration)).with(
					ObjectAnimator.ofFloat(right, "TranslationX", value)
					.setDuration(duration)) ;
		}
		animatorSet.start();
	}
	
	public static void setAnim(View view,float v1,float v2,int duration,final AnimEndEvent event){
		ObjectAnimator objAnimator = ObjectAnimator.ofFloat(view, "TranslationX", v1,v2);
		objAnimator.setDuration(duration);
		objAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				if(event!=null){
					event.onAnimEnd();
				}
			}
		});
		objAnimator.start();
	}
	
	public static void setAnim( final View one, final View two, final View three,final View four, float value ,long duration) {
		AnimatorSet animatorSet = new AnimatorSet();
		ObjectAnimator objAnimator = ObjectAnimator.ofFloat(one,"TranslationX", value);
		objAnimator.setDuration(duration);
/*		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				left.setTranslationX((Float) animation.getAnimatedValue());
			}
		});*/
		
		/*if (cur == null) {
			animatorSet.play(valueAnimator).with(
					ObjectAnimator.ofFloat(right, "TranslationX", value)
					.setDuration(duration));
		} else {
			animatorSet.play(valueAnimator).with(
					ObjectAnimator.ofFloat(cur, "TranslationX", value)
					.setDuration(duration)).with(
					ObjectAnimator.ofFloat(right, "TranslationX", value)
					.setDuration(duration)) ;
		}*/
		animatorSet.play(objAnimator).with(
				ObjectAnimator.ofFloat(two, "TranslationX", value).setDuration(duration))
				.with(ObjectAnimator.ofFloat(three, "TranslationX", value).setDuration(duration))
				.with(ObjectAnimator.ofFloat(four, "TranslationX", value).setDuration(duration));
		animatorSet.start();
	}
	
	public static void fadeIn(View view){
		//AnimatorSet animatorSet = new AnimatorSet();
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f,1f);
		objectAnimator.setDuration(500);
		objectAnimator.start();
	}
	
	public static void fadeOut(View view){
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f,0f);
		objectAnimator.setDuration(500);
		objectAnimator.start();
	}

	public static interface AnimEndEvent{
		void onAnimEnd();
	}
}
