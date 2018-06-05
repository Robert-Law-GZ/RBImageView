package com.robert.transform;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by robert on 2017/7/28.
 */

public class GlideRoundTransform extends BitmapTransformation {
    private static float radius = 0f;
    private Paint borderPaint;

    private float borderWidth = 0;
    private int borderColor = Color.TRANSPARENT;

    public GlideRoundTransform() {
        this(4,0, Color.TRANSPARENT);
    }

    public GlideRoundTransform(int dp,float borderWidth,int borderColor) {
        super();
        this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        this.borderWidth=borderWidth;
        this.borderColor=borderColor;

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        borderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);

        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);

        float dst=borderWidth/2;
        rectF = new RectF(dst, dst, source.getWidth()-dst, source.getHeight()-dst);
        canvas.drawRoundRect(rectF, radius-dst, radius-dst, borderPaint);

        return result;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}