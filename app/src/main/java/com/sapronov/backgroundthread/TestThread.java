package com.sapronov.backgroundthread;

import android.util.Log;

public class TestThread extends Thread {

    private int times;

    public TestThread(int times) {
        this.times = times;
    }

    @Override
    public void run() {
        int count = 0;
        while (count != 10) {
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
