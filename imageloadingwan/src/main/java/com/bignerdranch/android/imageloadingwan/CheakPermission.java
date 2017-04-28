package com.bignerdranch.android.imageloadingwan;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by LENOVO on 2017/4/28.
 */

public class CheakPermission extends AppCompatActivity{
    public static final int UNABLE_START = 1;
    public static final int UNABLE_USE = 2;

    private static Activity activity;
    public static void cheakActivityPermission(Activity activity, String permission, int processingMethod) {
        if (activity == null) {
            throw new RuntimeException("未传入有效的Activity");
        } else {
            CheakPermission.activity = activity;
        }
        if (permission == null) {
            Toast.makeText(activity, "未传入相应权限", Toast.LENGTH_SHORT).show();
            throw new RuntimeException("相应权限传入失败");
        }

        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, processingMethod);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(CheakPermission.activity, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(activity, "该程序的部分功能将无法使用", Toast.LENGTH_SHORT);
                }
                break;
            default:
                break;
        }
    }
}
