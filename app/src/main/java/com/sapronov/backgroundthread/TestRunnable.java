package com.sapronov.backgroundthread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestRunnable implements Runnable {

    private int times;
    private volatile boolean stopThread = false;
    private TextView textView;
    private ImageView imageView;

    public TestRunnable(int times, TextView textView, ImageView imageView) {
        this.times = times;
        this.textView = textView;
        this.imageView=imageView;
    }

    public void finish() {
        stopThread = true;
    }


    @Override
    public void run() {
        int count = 0;
        final Bitmap bitmap=getBitmapFromURL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
        while (count != times) {
            if (stopThread) return;
            if (count==4){
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                            textView.setText("50%");
                    }
                });
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
            Log.d("TAG", "startThread" + count);
            count++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
