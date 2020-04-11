package com.sapronov.backgroundthread;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

import static android.os.AsyncTask.execute;

public class MainActivity extends AppCompatActivity {

    private TestRunnable runnable;
    private boolean isActive = false;
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);
        imageView=findViewById(R.id.imageView);
    }

    public void startThread(View view) {
        new loadImage((ImageView) findViewById(R.id.imageView))
                .execute("https://avatars.mds.yandex.net/get-zen_doc/235990/pub_5cf83147b854e100b048c13b_5cf84fe0051e5a00aef89f91/scale_1200");
        if (!isActive) {
            runnable = new TestRunnable(10,textView,imageView);
            new Thread(runnable).start();
            isActive = true;
        }
    }

    public void stopThread(View view) {
        Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
        runnable.finish();
        isActive = false;
    }


    static class loadImage extends AsyncTask<String,Void, Bitmap>{

        ImageView imageView;

        public loadImage(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
                e.printStackTrace();
            }
            Bitmap result=Bitmap.createScaledBitmap(bitmap,200,200,false);
            return result;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
