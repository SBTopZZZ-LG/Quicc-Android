package com.sbtopzzz.quicc.HelperClasses;

import android.app.Activity;

import java.util.Timer;
import java.util.TimerTask;

public class Wait {
    /**
     * Executes the runner AFTER specified milliseconds.
     * @param milliseconds Duration in milliseconds to wait (Ex: '2000' for 2 seconds)
     * @param runner Runner to execute after sleeping
     */
    public static void sleep(long milliseconds, Runnable runner) {
        Timer myTimer = new Timer ();
        TimerTask myTask = new TimerTask () {
            @Override
            public void run () {
                runner.run();
            }
        };
        myTimer.schedule(myTask, milliseconds);
    }

    /**
     * Executes the runner AFTER specified milliseconds on the specified activity UI thread.
     * @param activity Activity on which the runner is based
     * @param milliseconds Duration in milliseconds to wait (Ex: '2000' for 2 seconds)
     * @param runner Runner to execute after sleeping
     */
    public static void sleep(Activity activity, long milliseconds, Runnable runner) {
        Timer myTimer = new Timer ();
        TimerTask myTask = new TimerTask () {
            @Override
            public void run () {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runner.run();
                    }
                });
            }
        };
        myTimer.schedule(myTask, milliseconds);
    }
}
