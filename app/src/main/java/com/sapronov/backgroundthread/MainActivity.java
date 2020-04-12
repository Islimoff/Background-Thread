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
import java.lang.ref.WeakReference;
import java.net.URL;

import static android.os.AsyncTask.execute;

public class MainActivity extends AppCompatActivity {

    private TestRunnable runnable;
    private boolean isActive = false;
    private TextView textView;
    private ImageView imageView;
    private LoadImage loadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadImage = null;
    }

    public void startThread(View view) {
        LoadImage loadImage = (LoadImage) new LoadImage(this)
                .execute("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT1BrG_WY1G3LRs0y56amxaLmJ03JF23w77uEOPH6tngcVe-kj6&usqp=CAU");
        if (!isActive) {
            runnable = new TestRunnable(10, textView, imageView);
            new Thread(runnable).start();
            isActive = true;
        }
    }

    public void stopThread(View view) {
        Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
        runnable.finish();
        isActive = false;
    }

    static class LoadImage extends AsyncTask<String, Void, Bitmap> {

        private WeakReference<MainActivity> weakReference;

        public LoadImage(MainActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            MainActivity activity = weakReference.get();
            if (activity == null || activity.isFinishing()) {
                return null;
            }
            String url = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
                e.printStackTrace();
            }
            Bitmap result = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
            return result;
        }

        protected void onPostExecute(Bitmap result) {
            MainActivity activity = weakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.imageView.setImageBitmap(result);
        }
    }
}
