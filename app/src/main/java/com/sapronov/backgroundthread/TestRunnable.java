package com.sapronov.backgroundthread;

import android.util.Log;

public class TestRunnable implements Runnable {

    private int times;
    private volatile boolean stopThread = false;

    public TestRunnable(int times) {
        this.times = times;
    }

    public void finish() {
        stopThread = true;
    }


    @Override
    public void run() {
        int count = 0;
        while (count != 10) {
            if (stopThread) return;
            Log.d("TAG", "startThread" + count);
            count++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
