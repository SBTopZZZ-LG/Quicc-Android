package com.sbtopzzz.quicc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sbtopzzz.quicc.Activities.UserHomeActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
    }

    @Override
    protected void onStart() {
        super.onStart();

        startActivity(new Intent(MainActivity.this, UserHomeActivity.class));
    }
}