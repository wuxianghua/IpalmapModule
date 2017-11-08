package com.example.ipalmaplibrary;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * Created by stone on 2017/7/12.
 */

public class IpalmapActivity extends AppCompatActivity implements SensorEventListener {
    private PWebView webView;
    private ImageView ivBack;
    private ProgressBar progressBar;
    private static String MAP_URL = "map_url";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mField;
    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];
    public static void navigatorThis(Context that, String mapUrl) {
        Intent intent = new Intent(that, IpalmapActivity.class);
        intent.putExtra(MAP_URL, mapUrl);
        that.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.ipalmap_content_main);
        webView =(PWebView) findViewById(R.id.ll_webview);
        ivBack = (ImageView) findViewById(R.id.imageBack);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        String url = getIntent().getStringExtra(MAP_URL);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        registerListener();
        if (url == null) {
            webView.loadURL("http://parking.ipalmap.com/public/lifang/pro/#/mapView?_k=gq6d61");
            //webView.loadURL("http://10.0.10.13:8080/");
            //webView.loadURL("http://bi.palmap.cn/native-guangdong/#/");
        }
        webView.setWebViewPageLoadListener(new PWebView.WebViewPageLoadListener() {
            @Override
            public void pageStart() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void pageFinished() {
                progressBar.setVisibility(View.GONE);
            }
        });
        webView.loadURL(url);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void registerListener() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValues = event.values;
        }else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
        }else {
            magneticFieldValues = event.values;
        }
        calculateOrientation();
    }
    int degree;
    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues,
                magneticFieldValues);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);
        values[0] = values[0] < 0 ? 360+values[0]:values[0];
        degree = (int) values[0];
        webView.setOrientationDegree(degree);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }
}
