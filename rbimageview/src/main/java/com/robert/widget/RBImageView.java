package com.robert.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.robert.okhttp.OkHttpUrlLoader;
import com.robert.transform.GlideCircleTransform;
import com.robert.transform.GlideRoundTransform;

import java.io.InputStream;

/**
 * Created by robert on 2017/6/21.
 */

public class RBImageView extends AppCompatImageView {
    private String url;
    private int errorResourceId=-1;
    private int placeholderResourceId= R.drawable.com_robert_common_placeholder;
    private Context appContext;

    public void setErrorResourceId(int errorResourceId) {
        this.errorResourceId = errorResourceId;
    }

    public void setPlaceholderResourceId(int placeholderResourceId) {
        this.placeholderResourceId = placeholderResourceId;
//        setImageResource(placeholderResourceId);
    }

    public RBImageView(Context context) {
        super(context);
        initView();
    }

    public RBImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RBImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView(){
        appContext=getContext().getApplicationContext();
    }


    public void loadCircleImg(String url){
        if (this.url==null) {
            this.url=url;
            loadCircleImg(url, 0, 0);
        }else{
            if (!this.url.equalsIgnoreCase(url)){
                this.url=url;
                loadCircleImg(url, 0, 0);
            }
        }
    }

    public void loadCircleImg(String url, float borderWidth, int borderColor) {
        Glide.get(appContext).getRegistry().replace(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(borderWidth,borderColor) );

        if (errorResourceId!=-1){
            options.error(errorResourceId);
        }

        if (placeholderResourceId!=-1){
            options.placeholder(placeholderResourceId);
        }

        Glide.with(appContext).load(url).apply(options).into(this);
    }

    public void loadRoundImg(String url, int radius, float borderWidth, int borderColor) {
        Glide.get(appContext).getRegistry().replace(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransform(radius, borderWidth, borderColor));

        if (errorResourceId!=-1){
            options.error(errorResourceId);
        }

        if (placeholderResourceId!=-1){
            options.placeholder(placeholderResourceId);
        }

        if (this.url==null) {
            this.url = url;
            Glide.with(appContext).load(url).apply(options).into(this);
        }else{
            if (!this.url.equalsIgnoreCase(url)){
                this.url=url;
                Glide.with(appContext).load(url).apply(options).into(this);
            }
        }
    }

    public void load(String url) {
        Glide.get(appContext).getRegistry().replace(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory());

        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);

        if (errorResourceId!=-1){
            options.error(errorResourceId);
        }

        if (placeholderResourceId!=-1){
            options.placeholder(placeholderResourceId);
        }

        if (this.url==null) {
            this.url = url;
            Glide.with(appContext).load(url).apply(options).into(this);
        }else{
            if(!this.url.equalsIgnoreCase(url)) {
                this.url = url;
                Glide.with(appContext).load(url).apply(options).into(this);
            }
        }
    }

}
