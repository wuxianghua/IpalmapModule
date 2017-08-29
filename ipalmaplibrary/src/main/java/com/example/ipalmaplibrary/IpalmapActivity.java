package com.example.ipalmaplibrary;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by stone on 2017/7/12.
 */

public class IpalmapActivity extends AppCompatActivity {
    private PWebView webView;
    private LinearLayout ivBack;
    private static String MAP_URL = "map_url";
    public static void navigatorThis(Context that, String mapUrl) {
        Intent intent = new Intent(that, IpalmapActivity.class);
        intent.putExtra(MAP_URL, mapUrl);
        that.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipalmap_content_main);
        webView =(PWebView) findViewById(R.id.ll_webview);
        ivBack = (LinearLayout) findViewById(R.id.imageBack);
        String url = getIntent().getStringExtra(MAP_URL);
        if (url == null) {
            webView.loadURL("http://bi.palmap.cn/native-kangmei/");
        }
        webView.loadURL(url);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            },1);
        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
