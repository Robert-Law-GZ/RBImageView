package com.robert.transform;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by robert on 2017/7/28.
 */

public class GlideCircleTransform extends BitmapTransformation {
    private final Paint borderPaint;
    private float borderWidth=0;
    private int borderColor= Color.BLACK;

    public GlideCircleTransform(float borderWidth,int borderColor) {
        super();

        this.borderWidth=borderWidth;
        this.borderColor=borderColor;

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int minDst = Math.min(source.getWidth(), source.getHeight());
        Bitmap output = Bitmap.createBitmap(minDst, minDst, Bitmap.Config.ARGB_8888);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

        Canvas canvas = new Canvas(output);
        Path path=new Path();
        path.addCircle(minDst/2,minDst/2,minDst/2, Path.Direction.CW);
        canvas.clipPath(path);

        canvas.drawBitmap(source,0,0,paint);

        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth*4);

        canvas.drawCircle(minDst/2, minDst/2, minDst/2-borderWidth/2, borderPaint);

        return output;
    }

    public String getId() {
        return getClass().getName();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}
