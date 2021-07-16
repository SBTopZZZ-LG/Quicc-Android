package com.sbtopzzz.quicc.HelperClasses;

import android.app.Activity;

import androidx.annotation.NonNull;

public class ThreadSwitch {
    private final Activity context;

    public ThreadSwitch(@NonNull Activity context) {
        this.context = context;
    }

    public void runOnUiThread(@NonNull Run run, Object...args) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                run.onFinish(run.run(args));
            }
        });
    }
    public void runOnNewThread(@NonNull Run run, Object...args) {
        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                run.onFinish(run.run(args));
            }
        });
        newThread.start();
    }

    public interface Run {
        int run(Object[] args);
        void onFinish(int FINISH_CODE);
    }
}
