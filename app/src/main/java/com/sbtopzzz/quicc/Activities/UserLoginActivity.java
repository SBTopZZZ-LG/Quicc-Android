package com.sbtopzzz.quicc.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.User;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.HelperClasses.SP;
import com.sbtopzzz.quicc.R;

public class UserLoginActivity extends AppCompatActivity {
    private EditText etEmailID, etPassword;
    private Button btnSignIn, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        Initialize();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Funcs.userSignIn(etEmailID.getText().toString(), etPassword.getText().toString(), new Funcs.UserSignInResult() {
                    @Override
                    public void onSuccess(@Nullable String loginToken, @NonNull User user) {
                        Toast.makeText(UserLoginActivity.this, "Welcome, " + user.getEmailId() + "!", Toast.LENGTH_SHORT).show();

                        SP.push("userLoginEmail", user.getEmailId());

                        if (loginToken != null)  {
                            SP.push("userLoginToken", loginToken);
                        }

                        CurrentUser.user = user;

                        startActivity(new Intent(UserLoginActivity.this, UserHomeActivity.class));
                    }

                    @Override
                    public void onWarning(String errorText) {
                        Toast.makeText(UserLoginActivity.this, "Warning: " + errorText, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Throwable t) {
                        Toast.makeText(UserLoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLoginActivity.this, UserRegisterActivity.class));
            }
        });
    }

    private void Initialize() {
        etEmailID = findViewById(R.id.etEmailID);
        etPassword = findViewById(R.id.etPassword);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}