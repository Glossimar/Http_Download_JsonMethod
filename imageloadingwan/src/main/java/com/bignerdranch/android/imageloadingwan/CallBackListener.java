package com.bignerdranch.android.imageloadingwan;

/**
 * Created by LENOVO on 2017/4/11.
 */

public interface CallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}
