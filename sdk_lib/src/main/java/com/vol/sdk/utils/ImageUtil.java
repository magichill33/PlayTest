package com.vol.sdk.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {
	/** ��ResolveInfo�л�ȡBitmapͼ�� */
	public static Bitmap getIconBitmap(Context context, ResolveInfo resolveInfo) {
		PackageManager pm = context.getPackageManager();
		Drawable icon = resolveInfo.activityInfo.loadIcon(pm);
		BitmapDrawable bitmapDrawable = (BitmapDrawable) icon;
		Bitmap iconBitmap = bitmapDrawable.getBitmap();
		iconBitmap = ImageUtil.roundCorner(iconBitmap, 10);
		return iconBitmap;
	}
	
	public static Bitmap getResourceBitmap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// ��ȡ��ԴͼƬ
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
	
	public static Bitmap getResourceBitmap(Resources res, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// ��ȡ��ԴͼƬ
//		InputStream is = context.getResources().openRawResource(resId);
//		return BitmapFactory.decodeStream(is, null, opt);
		return BitmapFactory.decodeResource(res, resId);
	}
	
	/** ΪͼƬ����Բ�� */
	public static Bitmap roundCorner(Bitmap source, int rate) {
		int width = source.getWidth();
		int height = source.getHeight();

		Bitmap roundCornerImage = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(roundCornerImage);

		Rect rect = new Rect(0, 0, width, height);
		RectF rectF = new RectF(rect);
		Paint paint = new Paint();

		paint.setAntiAlias(true);
		canvas.drawRoundRect(rectF, rate, rate, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(source, rect, rect, paint);

		source = null;
		return roundCornerImage;
	}

	/** ΪͼƬ���ϱ߿� */
	public static Bitmap border(Bitmap source, Bitmap border, int padding) {
		int width = source.getWidth() + padding * 2;
		int height = source.getHeight() + padding * 2;

		border = ImageUtil.resize(border, width, height);

		Bitmap bitmapWithBorder = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmapWithBorder);
		canvas.drawBitmap(border, 0, 0, null);
		canvas.drawBitmap(source, padding, padding, null);

		source = null;
		return bitmapWithBorder;
	}
	
	/** ΪͼƬ�����ɫ */
	public static Bitmap fillPixels(Bitmap bitmap, int fillColor) {
		// ��ͼƬ��Ҫ�����߻�ԭ ��ɫ����ʼ�� ��ʼ �� �յ�
    	int mBitmapWidth = bitmap.getWidth();
    	int mBitmapHeight = bitmap.getHeight();
		Bitmap targetBitmap = bitmap.copy(Config.ARGB_8888, true);
		
		for (int i = 0; i < mBitmapHeight; i++) {
			for (int j = 0; j < mBitmapWidth; j++) {
				// ����Ҫ������ɫֵ�����
				// ����˵��һ�� ���color ��ȫ͸�� ����ȫ�� ����ֵΪ 0
				// getPixel()����͸��ͨ�� getPixel32()�Ŵ�͸������ ����ȫ͸����0x00000000
				// ��͸����ɫ��0xFF000000 ������͸�����־Ͷ���0��
				int color = targetBitmap.getPixel(j, i);
				if (color != 0) {
					targetBitmap.setPixel(j, i, fillColor);
				}
			}
		}
		
		return targetBitmap;
	}

	/** ΪͼƬ���ϵ�Ӱ */
	public static Bitmap reflection(Bitmap source, int reflectionGap,
			int reflectionHeight) {
		int width = source.getWidth();
		int height = source.getHeight();

		// ���ԭͼ����һ����Ȳ��䣬�߶�Ϊԭͼ1/4�ĵ�Ӱλͼ
		Matrix matrix = new Matrix(); // �����任����
		matrix.preScale(1, -1); // ָ������ı任����ΪX�᲻�䣬Y���෴
		Bitmap reflectionImage = Bitmap.createBitmap(source, 0, 
				height - reflectionHeight, width, reflectionHeight, matrix, false);

		// ����һ�������ԭͼ��ȣ��߶�Ϊԭͼ+��Ӱͼ�߶ȵĿ�λͼ
		int reflectionTop = height + reflectionGap;
		int transformHeight = reflectionTop + reflectionHeight;
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				transformHeight, Config.ARGB_8888);

		// �����洴����λͼ��ʼ����������
		Canvas canvas = new Canvas(bitmapWithReflection);

		// ��ԭʼͼƬ�͵�ӰͼƬ�ֱ𻭵�������
		canvas.drawBitmap(source, 0, 0, null);
		canvas.drawBitmap(reflectionImage, 0, reflectionTop, null);
		reflectionImage = null;
		source = null;
		
		// ���������࣬���������ΪͼƬ��Ӹ��ָ������Ч
		Paint paint = new Paint();

		// ȡ����Ч��
		paint.setAntiAlias(false);

		// ΪͼƬ�ĵ�Ӱ��ӽ���Ч��
		/*
		 * ��һ�����������ʾ����Ч�����ʼ��꣬ǰ����X��꣬������Y��� �����ĸ������ʾ����Ч��Ľ�����꣬ǰ����X��꣬������Y���
		 * ����������ʾ���俪ʼ����ɫ������������ʾ����������ɫ ���һ�������ʾ���������
		 */
		paint.setShader(new LinearGradient(0, reflectionTop, 0,
				transformHeight, 0x10ffffff, 0x00ffffff, TileMode.MIRROR));

		// ���û��ʵ��ڸ�Ч����򻭱ʻ���ȥ�����ݻ��ڸ�סԭͼ��
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

		// ʹ�û����򻭲��ϻ���Ч��
		canvas.drawRect(0, height, width, transformHeight, paint);

		return bitmapWithReflection;
	}

	/** ��������ͼƬ��С */
	public static Bitmap resize(Bitmap source, int resizeWidth, int resizeHeight) {
		int sourceWidth = source.getWidth();
		int sourceHeight = source.getHeight();

		float widthScale = (float) resizeWidth / (float) sourceWidth;
		float heightScale = (float) resizeHeight / (float) sourceHeight;

		Matrix matrix = new Matrix();
		matrix.postScale(widthScale, heightScale);
		Bitmap result = Bitmap.createBitmap(source, 0, 0, sourceWidth,
				sourceHeight, matrix, true);

		source = null;
		return result;
	}

	/** ����ͼƬ��ƫת�Ƕ� */
	/*public static Bitmap skew (Bitmap source, int skewX, int skewY) {
	
	matrix.preTranslate(tmpBit.getWidth() >> 1, tmpBit.getHeight() >> 1);
        matrix.preSkew(skewX, skewY);
        // matrix.postSkew(skewX, skewY);
        // ֱ��setSkew()����ǰ�洦���rotate()��translate()�ȵȶ�����Ч��
        // matrix.setSkew(skewX, skewY);
        // 2.ͨ����������ͼ��(��ֱ��������Canvas)
        Log.d(��ANDROID_LAB��, ��width=�� + tmpBit.getWidth() + �� height=�� + tmpBit.getHeight());
        Bitmap newBit = null;
        try {
            // �������ת�����ͼ�����п��ܲ�����0����ʱ���׳�IllegalArgumentException
            newBit = Bitmap.createBitmap(tmpBit, 0, 0, tmpBit.getWidth(), tmpBit.getHeight(), matrix, true);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
        if (newBit != null) {
            imgResult.setImageBitmap(newBit);
        }
    }*/
	

	/** ��ͼƬ���浽ָ����Ŀ¼ */
	public static void saveImage(Bitmap bitmap, String path, String fileName) {
		//��Bitmapת����PNG��ʽ��byte����
		ByteArrayOutputStream bas = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, bas);
		
		//ɾ���Ѵ��ڵľ��ļ�
		String filePath = path + "/" + fileName;
		File oldFile = new File(filePath);
		if (oldFile.exists()) {
			oldFile.delete();
		}
		
		//���ļ��в����ڣ��򴴽��µ��ļ���
		File fileDir = new File(path);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		
		//��byte����д���ļ�
		FileOutputStream out = null;
		try {			
			out = new FileOutputStream(filePath);
			out.write(bas.toByteArray(), 0, bas.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/** ��ͼƬ��ָ����Ŀ¼��ȡ���� */
	public static Bitmap loadImage(Context context, String path, String fileName) {
		InputStream in = null;
		try {
			in = new FileInputStream(path + "/" + fileName);
			return BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/** ��ͼƬ��ָ����Ŀ¼��ȡ���� */
	public static Bitmap loadImage(String fileName) {
		InputStream in = null;
		try {
			in = new FileInputStream(fileName);
			return BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
