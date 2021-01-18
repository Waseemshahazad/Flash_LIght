package com.wasemdevloper.wallpaper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {
     private CameraManager cameramanager;
     private String mCameraId;
     private ToggleButton ivOnOff;
     private Boolean isTorchOn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivOnOff = findViewById(R.id.ivonof);
        final RelativeLayout bg = findViewById(R.id.bg);
        isTorchOn = false;

        Boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!isFlashAvailable) {
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
            alert.setTitle(getString(R.string.app_name));
            alert.setMessage(getString(R.string.msg_error));
            alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.lbl_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
            return;
        }
        cameramanager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = cameramanager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        ivOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTorchOn) {
                    try {
                        turnOfLight();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    isTorchOn = false;
                } else {
                    try {
                        turnOnLight();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    isTorchOn = true;
                }
            }

            private void turnOnLight() throws CameraAccessException {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameramanager.setTorchMode(mCameraId, true);
//                    Toast.makeText(MainActivity.this, "On Light", Toast.LENGTH_SHORT).show();
                    bg.setBackgroundColor(Color.GREEN);
                }
            }

            private void turnOfLight() throws CameraAccessException {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameramanager.setTorchMode(mCameraId, false);
//                   Toast.makeText(MainActivity.this, "Off Light", Toast.LENGTH_SHORT).show();
                  bg.setBackgroundColor(Color.WHITE);
                }
            }
        });

    }}

