package com.bignerdranch.android.myapplication;

/**
 * Created by LENOVO on 2017/4/6.
 */

public interface CallBackListener {
     void onFinish(String response);
    void onError(Exception e);
}
