package com.example.zcp.recyclerviewdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Window;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 获取图片URL转为Bitmap对象
 *
 * @author tanruimin
 */
public class Datu extends AppCompatActivity {

    /**
     * 自定义的ImageView控制，可对图片进行多点触控缩放和拖动
     */
    private ZoomImageView zoomImageView;

    private Bitmap bm;

    /**
     * 待展示的图片
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_datu);
        zoomImageView = (ZoomImageView) findViewById(R.id.zoom_image_view);
        // 取出图片路径，并解析成Bitmap对象，然后在ZoomImageView中显示
        String imagePath = getIntent().getStringExtra("image_path");
        getBitmap(imagePath);
        zoomImageView.setImageBitmap(bm);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 记得将Bitmap对象回收掉
        if (bm != null) {
            bm.recycle();
        }
    }
    public Bitmap getBitmap(String url) {
        bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }
}