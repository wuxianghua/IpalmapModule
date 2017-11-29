package com.example.stone.ipalmapmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ipalmaplibrary.IpalmapActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mapUrl = "http://10.0.10.53:8081/#/";
                //IpalmapActivity.navigatorThis(MainActivity.this,mapUrl);
                IpalmapActivity.navigatorThis(MainActivity.this,mapUrl);
            }
        });
    }
}
