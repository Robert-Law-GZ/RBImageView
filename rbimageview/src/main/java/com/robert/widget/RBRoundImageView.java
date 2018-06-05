package com.robert.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by robert on 2017/7/27.
 */

public class RBRoundImageView extends RBImageView {
    private Paint paint;
    private Paint borderPaint;

    private float borderWidth = 4;
    private float radius = 0;
    private int borderColor = Color.BLACK;

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        this.invalidate();
    }


    public void setRadius(float radius) {
        this.radius = radius;
        this.invalidate();
    }


    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        this.invalidate();
    }

    public RBRoundImageView(Context context) {
        super(context);
    }

    public RBRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RBRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        if (drawable!=null) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap b=getRoundBitmap(bitmap,radius);
            Drawable d =new BitmapDrawable(getContext().getResources(),b);
            super.setImageDrawable(d);
        }

    }

    /**
     * 获取圆角矩形图片方法
     *
     * @param bitmap
     * @param roundPx,一般设置成14
     * @return Bitmap
     * @author caizhiming
     */
    private Bitmap getRoundBitmap(Bitmap bitmap, float roundPx) {
        paint = new Paint();
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        borderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Rect rect = new Rect(0, 0, bitmap.getWidth(),bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        paint.reset();
        RectF rectf = new RectF();
        rectf.left = borderWidth/2f;
        rectf.top = borderWidth/2f;
        rectf.right = getWidth() - borderWidth/2f;
        rectf.bottom = getHeight() - borderWidth/2f;

        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);

        canvas.drawRoundRect(rectf, radius, radius, borderPaint);
        return output;
    }

}
