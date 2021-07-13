package com.sbtopzzz.quicc.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.R;

public class UserScannerActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_scanner);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Funcs.eventJoin(CurrentUser.user.getEmailId(), result.getText(), new Funcs.EventJoinResult() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(UserScannerActivity.this, "You joined the event!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onWarning(String errorText) {
                                Toast.makeText(UserScannerActivity.this, "Warning: " + errorText, Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(@NonNull Throwable t) {
                                Toast.makeText(UserScannerActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}