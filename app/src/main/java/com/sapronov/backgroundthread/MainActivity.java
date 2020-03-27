package com.sapronov.backgroundthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startThread(View view){
//       TestThread thread=new TestThread(10);
//       thread.start();
        TestRunnable runnable=new TestRunnable(10);
        new Thread(runnable).start();
    }

    public void stopThread(View view){
        Toast.makeText(this,"Stopped",Toast.LENGTH_SHORT).show();
    }
}
