package com.example.ipalmaplibrary;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Administrator on 2017/11/10/010.
 */

public class NetworkUtil {

    //判断是否有网络链接
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet = conn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return wifi || internet;
    }
}
