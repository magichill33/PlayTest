package com.vol.sdk.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;

public class BitmapUtil {

	public static Bitmap decodeSampledBitmapFromResource(final Resources res, final int resId,
			final BitmapCallback callback) {
		final int reqWidth = 200;
		final int reqHeight = 200;
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				callback.bitmapLoaded((Bitmap) msg.obj);
			}
		};
		new Thread(new Runnable() {
			public void run() {
				final BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeResource(res, resId, options);
				options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
				options.inJustDecodeBounds = false;
				Bitmap bitmap = BitmapFactory.decodeResource(res, resId, options);
				Message message = Message.obtain();
				message.obj = (Bitmap) bitmap;
				handler.sendMessage(message);
			}
		}).start();
		return null;
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	public interface BitmapCallback {
		public void bitmapLoaded(Bitmap bitmap);
	}

	public static Bitmap readBitmap(Context context, int resId) {
		return readBitmap(context, resId, Config.ARGB_8888);
	}

	public static Bitmap readBitmap(Context context, int resId, Config config) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = config;
		// options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, options);
	}

	public static Bitmap readBitmapOriginal(Context context, int resId) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.ARGB_8888;
		options.inPurgeable = true;
		options.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, options);
	}

	public static void releaseBitmap(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
	}

	public static boolean isReleased(Bitmap bitmap) {
		return (bitmap == null || bitmap.isRecycled());
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
}
