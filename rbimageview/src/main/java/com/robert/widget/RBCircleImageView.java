package com.robert.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by robert on 17/7/26.
 */
public class RBCircleImageView extends RBImageView {

    private Paint paint;
    private Paint borderPaint;

    private int borderWidth=6;
    private int borderColor= Color.BLACK;
    private int radius=0;

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        invalidate();
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        invalidate();
    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    public RBCircleImageView(Context context) {
        super(context);
        init();
    }

    public RBCircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RBCircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
        borderPaint=new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        if (drawable!=null){
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Drawable d =new BitmapDrawable(getContext().getResources(),createCircleBitmap(bitmap));
            super.setImageDrawable(d);
        }
    }

    private Bitmap createCircleBitmap(Bitmap bitmap){
        int minDst = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Bitmap output = Bitmap.createBitmap(minDst, minDst, Bitmap.Config.ARGB_8888);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

        Canvas canvas = new Canvas(output);
        Path path=new Path();
        path.addCircle(minDst/2,minDst/2,minDst/2, Path.Direction.CW);
        canvas.clipPath(path);

        canvas.drawBitmap(bitmap,0,0,paint);

        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth*4);

        canvas.drawCircle(minDst/2, minDst/2, minDst/2-borderWidth/2, borderPaint);

        return output;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        heightMeasureSpec = widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(childWidthSize, View.MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}