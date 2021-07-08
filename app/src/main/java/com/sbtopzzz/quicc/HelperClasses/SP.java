package com.sbtopzzz.quicc.HelperClasses;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SP {
    private static SharedPreferences sharedPref = null;

    /**
     * IMPORTANT: Should be Initialized before performing push and pull operations.
     * @param baseContext MainActivity context, or any other underlying context that is not destroyed
     */
    public static void Initialize(@NonNull Activity baseContext) {
        sharedPref = baseContext.getPreferences(Context.MODE_PRIVATE);
    }

    public static void push(@NonNull String key, @Nullable String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Nullable
    public static String pull(@NonNull String key) {
        return sharedPref.getString(key, null);
    }
}
