package com.airhockey.android;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.nfc.Tag;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.airhockey.android.util.LoggerConfig;

public class AirHockeyActivity extends AppCompatActivity {
    private GLSurfaceView mGlSurfaceView;
    private boolean renderSet = false;
    public static final String TAG = "AirHockeyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlSurfaceView = new GLSurfaceView(this);
        final ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo ci = am.getDeviceConfigurationInfo();
        boolean supportEs2 = ci.reqGlEsVersion >= 0x20000;
        if (LoggerConfig.ON) {
            Log.d(TAG, "设备支持clientVersion： " + supportEs2);
        }
        if (supportEs2) {
            mGlSurfaceView.setEGLContextClientVersion(2);
            mGlSurfaceView.setRenderer(new AirHockeyRenderer(this));
            renderSet = true;
        } else {
            return;
        }
        setContentView(mGlSurfaceView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (renderSet) {
            mGlSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (renderSet) {
            mGlSurfaceView.onResume();
        }
    }
}
