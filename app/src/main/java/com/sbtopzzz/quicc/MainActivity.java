package com.sbtopzzz.quicc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.User;
import com.sbtopzzz.quicc.Activities.UserHomeActivity;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.Activities.UserLoginActivity;
import com.sbtopzzz.quicc.HelperClasses.SP;
import com.sbtopzzz.quicc.HelperClasses.ThreadSwitch;
import com.sbtopzzz.quicc.HelperClasses.Wait;

public class MainActivity extends AppCompatActivity {
    private ImageView ivLogo;

    private boolean stopWobble = false;

    private ThreadSwitch threadSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        threadSwitch = new ThreadSwitch(this);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                        Finalize();
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                        Finalize();
                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    private void wobble(final float finalSizeWidth, final float finalSizeHeight, long duration) {
        ObjectAnimator scaleXAnim3 = ObjectAnimator.ofFloat(ivLogo, "scaleX", 0.9f * finalSizeWidth, 1.1f * finalSizeWidth);
        ObjectAnimator scaleYAnim3 = ObjectAnimator.ofFloat(ivLogo, "scaleY", 0.9f * finalSizeHeight, 1.1f * finalSizeHeight);
        scaleYAnim3.setDuration(duration);
        scaleXAnim3.setDuration(duration);

        scaleXAnim3.start();
        scaleYAnim3.start();

        scaleXAnim3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                ObjectAnimator scaleXAnim4 = ObjectAnimator.ofFloat(ivLogo, "scaleX", 1.1f * finalSizeWidth, 0.9f * finalSizeWidth);
                ObjectAnimator scaleYAnim4 = ObjectAnimator.ofFloat(ivLogo, "scaleY", 1.1f * finalSizeHeight, 0.9f * finalSizeHeight);
                scaleXAnim4.setDuration(duration);
                scaleYAnim4.setDuration(duration);

                scaleXAnim4.start();
                scaleYAnim4.start();

                scaleYAnim4.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        if (!stopWobble)
                            wobble(finalSizeWidth, finalSizeHeight, duration);
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopWobble = true;
    }

    private void Finalize() {
        ivLogo = findViewById(R.id.ivLogo);

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(ivLogo, "alpha", 0, 1);
        alphaAnimator.setDuration(3500);

        final float finalSizeWidth = ivLogo.getScaleX(), finalSizeHeight = ivLogo.getScaleY();

        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(ivLogo, "scaleX", 0, finalSizeWidth);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(ivLogo, "scaleY", 0, finalSizeHeight);
        scaleXAnim.setDuration(1000);
        scaleYAnim.setDuration(1000);

        alphaAnimator.start();
        scaleXAnim.start();
        scaleYAnim.start();

        scaleYAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                ObjectAnimator scaleXAnim2 = ObjectAnimator.ofFloat(ivLogo, "scaleX", finalSizeWidth, 0.9f * finalSizeWidth);
                ObjectAnimator scaleYAnim2 = ObjectAnimator.ofFloat(ivLogo, "scaleY", finalSizeHeight, 0.9f * finalSizeHeight);
                scaleXAnim2.setDuration(600);
                scaleYAnim2.setDuration(600);

                scaleXAnim2.start();
                scaleYAnim2.start();

                scaleYAnim2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        wobble(finalSizeWidth, finalSizeHeight, 1500);
                    }
                });
            }
        });

        Wait.sleep(MainActivity.this, 2000, new Runnable() {
            @Override
            public void run() {
                SP.Initialize(MainActivity.this);

                threadSwitch.runOnNewThread(new ThreadSwitch.Run() {
                    @Override
                    public int run(Object[] args) {
                        String loginToken = SP.pull("userLoginToken");
                        String email = SP.pull("userLoginEmail");

                        if (loginToken != null && email != null)
                            Funcs.userSignIn(email, new Funcs.UserSignInResult() {
                                @Override
                                public void onSuccess(@Nullable String loginToken, @NonNull User user) {
                                    CurrentUser.user = user;

                                    threadSwitch.runOnUiThread(new ThreadSwitch.Run() {
                                        @Override
                                        public int run(Object[] args) {
                                            startActivity(new Intent(MainActivity.this, UserHomeActivity.class));

                                            return 0;
                                        }

                                        @Override
                                        public void onFinish(int FINISH_CODE) {

                                        }
                                    });
                                }

                                @Override
                                public void onWarning(String errorText) {
                                    threadSwitch.runOnUiThread(new ThreadSwitch.Run() {
                                        @Override
                                        public int run(Object[] args) {
                                            Toast.makeText(MainActivity.this, "Old sign in failed, Warning: " + errorText, Toast.LENGTH_SHORT).show();

                                            SP.push("userLoginEmail", null);
                                            SP.push("userLoginToken", null);

                                            startActivity(new Intent(MainActivity.this, UserLoginActivity.class));

                                            return 0;
                                        }

                                        @Override
                                        public void onFinish(int FINISH_CODE) {

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(@NonNull Throwable t) {
                                    threadSwitch.runOnUiThread(new ThreadSwitch.Run() {
                                        @Override
                                        public int run(Object[] args) {
                                            Toast.makeText(MainActivity.this, "Old sign in failed, Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                                            SP.push("userLoginEmail", null);
                                            SP.push("userLoginToken", null);

                                            startActivity(new Intent(MainActivity.this, UserLoginActivity.class));

                                            return 0;
                                        }

                                        @Override
                                        public void onFinish(int FINISH_CODE) {

                                        }
                                    });
                                }
                            });
                        else
                            return 0;
                        return 1;
                    }

                    @Override
                    public void onFinish(int FINISH_CODE) {
                        if (FINISH_CODE == 0)
                            threadSwitch.runOnUiThread(new ThreadSwitch.Run() {
                                @Override
                                public int run(Object[] args) {
                                    startActivity(new Intent(MainActivity.this, UserLoginActivity.class));

                                    return 0;
                                }

                                @Override
                                public void onFinish(int FINISH_CODE) {

                                }
                            });
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}