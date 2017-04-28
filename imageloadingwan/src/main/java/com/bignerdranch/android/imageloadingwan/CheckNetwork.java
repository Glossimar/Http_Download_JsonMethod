package com.bignerdranch.android.imageloadingwan;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by LENOVO on 2017/4/28.
 */

public class CheckNetwork {
    private Activity activity;

    public CheckNetwork(Activity activity) {
        this.activity = activity;
    }

    public NetworkInfo getNectworkInfo() {
        if (activity == null) {
            throw new RuntimeException("Input activity is null");
        } else {
            Context context = activity.getApplicationContext();
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo;
            } else {
                return null;
// throw new RuntimeException("The information of network is null");
            }
        }
    }

    public boolean isNetworkConnected() {
        NetworkInfo networkInfo = getNectworkInfo();
        if (networkInfo == null){
            Toast.makeText(activity, "你已经进入了没有网络的异次元", Toast.LENGTH_SHORT).show();
            return false;
        } else if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        return false;
    }

    public boolean isWifiConnected() {
        NetworkInfo networkInfo = getNectworkInfo();
        if (isNetworkConnected() && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            Toast.makeText(activity, "网络已连接,当前为wifi状态" ,Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    public boolean isMobileConnected() {
        NetworkInfo networkInfo = getNectworkInfo();
        if (isNetworkConnected() && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            Toast.makeText(activity, "网络已连接,当前为移动数据状态" ,Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }
}

