package com.sbtopzzz.quicc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.User;
import com.sbtopzzz.quicc.Activities.UserHomeActivity;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.Activities.UserLoginActivity;
import com.sbtopzzz.quicc.HelperClasses.SP;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SP.Initialize(MainActivity.this);

        String loginToken = SP.pull("userLoginToken");
        String email = SP.pull("userLoginEmail");

        if (loginToken != null && email != null)
            Funcs.userSignIn(email, new Funcs.UserSignInResult() {
                @Override
                public void onSuccess(@Nullable String loginToken, @NonNull User user) {
                    CurrentUser.user = user;

                    startActivity(new Intent(MainActivity.this, UserHomeActivity.class));
                }

                @Override
                public void onWarning(String errorText) {
                    Toast.makeText(MainActivity.this, "Old sign in failed, Warning: " + errorText, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(@NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, "Old sign in failed, Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        else
            startActivity(new Intent(MainActivity.this, UserLoginActivity.class));
    }
}