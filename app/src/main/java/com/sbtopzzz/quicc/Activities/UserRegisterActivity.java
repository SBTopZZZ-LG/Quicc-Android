package com.sbtopzzz.quicc.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.R;

public class UserRegisterActivity extends AppCompatActivity {
    private EditText etEmailID, etPassword, etVPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        Initialize();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Funcs.userRegister(etEmailID.getText().toString(), etPassword.getText().toString(), new Funcs.UserRegisterResult() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(UserRegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onWarning(String errorText) {
                        Toast.makeText(UserRegisterActivity.this, "Warning: " + errorText, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Throwable t) {
                        Toast.makeText(UserRegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void Initialize() {
        etEmailID = findViewById(R.id.etEmailID);
        etPassword = findViewById(R.id.etPassword);
        etVPassword = findViewById(R.id.etVPassword);

        btnRegister = findViewById(R.id.btnRegister);
    }
}