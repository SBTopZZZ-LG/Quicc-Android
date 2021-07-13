package com.sbtopzzz.quicc.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.Event;
import com.sbtopzzz.quicc.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class UserViewQRCodeActivity extends AppCompatActivity {
    private String eventUid;

    private TextView tvEventName;
    private ImageView ivQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_qrcode);
        Initialize();
        GetDetails();
    }

    private void GetDetails() {
        Funcs.eventGet(eventUid, new Funcs.EventGetResult() {
            @Override
            public void onSuccess(@NonNull Event event) {
                tvEventName.setText(event.getTitle());

                QRGEncoder qrgEncoder = new QRGEncoder(eventUid, null, QRGContents.Type.TEXT, 250);
                // Getting QR-Code as Bitmap
                Bitmap qr = qrgEncoder.getBitmap();
                // Setting Bitmap to ImageView
                ivQR.setImageBitmap(qr);
            }

            @Override
            public void onWarning(String errorText) {
                Toast.makeText(UserViewQRCodeActivity.this, "Warning: " + errorText, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                Toast.makeText(UserViewQRCodeActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void Initialize() {
        eventUid = getIntent().getExtras().getString("eventUid");

        tvEventName = findViewById(R.id.tvEventName);
        ivQR = findViewById(R.id.ivQR);
    }
}