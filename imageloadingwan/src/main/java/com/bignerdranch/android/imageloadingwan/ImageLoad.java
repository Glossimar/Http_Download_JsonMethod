package com.bignerdranch.android.imageloadingwan;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

import static android.content.ContentValues.TAG;

/**
 * Created by LENOVO on 2017/4/6.
 */

public class ImageLoad {

    public static void getImage(final Activity activity, final String imageURL, final ImageView imageView){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                BufferedReader bufferedReader;
                try{
                    URL url=new URL(imageURL);
                    connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    InputStream inputStream=connection.getInputStream();

                    Bitmap bitmap= null;
                    if (saveBitmapToSD(bitmap,imageURL,inputStream)!=null){
                        bitmap =saveBitmapToSD(bitmap,imageURL,inputStream);
                        showImage(activity,imageView,saveBitmapToSD(bitmap,imageURL,inputStream));
                        Log.d(TAG, "run: "+bitmap.getHeight()+bitmap.getWidth());
                    }
                    else {
                        Toast.makeText(activity,"图片保存失败",Toast.LENGTH_LONG).show();
                        Log.d("SAVE_BITMAP_TO_SDCARD", "run:  图片保存失败 ");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }            }
        }).start();
    }

    public static void getCircleImage(final Activity activity, final String imageURL, final CircleImageViewWan circleImageView){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                BufferedReader bufferedReader;
                try{
                    URL url=new URL(imageURL);
                    connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    final InputStream inputStream=connection.getInputStream();

                    final Bitmap[] bitmap = {null};
                    if (saveBitmapToSD(bitmap[0],imageURL,inputStream)!=null){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bitmap[0] =saveBitmapToSD(bitmap[0],imageURL,inputStream);
                                circleImageView.setImageBitmap(bitmap[0]);
                                circleImageView.init();
                                showImage(activity,circleImageView,saveBitmapToSD(bitmap[0],imageURL,inputStream));
                            }
                        });

                    }

                    else {
                        Toast.makeText(activity,"图片保存失败",Toast.LENGTH_LONG).show();
                        Log.d("SAVE_BITMAP_TO_SDCARD", "run:  图片保存失败 ");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }            }
        }).start();
    }

    private static void showImage(Activity activity, final ImageView imageView, final Bitmap bitmap){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    private static Bitmap saveBitmapToSD(Bitmap bitmap,String imageURL,InputStream inputStream){
        File f= Environment.getExternalStorageDirectory();
        String filename=f.toString()+File.separator+getMD5(imageURL)+".png";
        Log.d("saveBitmapToSD", "saveBitmapToSD: The name saved in SD card is "+filename);
        File file=new File(filename);
        FileOutputStream fos=null;
        if (file.exists()) {
            Log.d("FILEXTIS", "saveBitmapToSD: 图片已经存在");
            bitmap= BitmapFactory.decodeFile(filename);
            Log.d("BITMAPFACTORYRETURN", "saveBitmapToSD: "+BitmapFactory.decodeFile(filename));
            return bitmap;
        }else {
            Log.d("FILEDONTEXTISTS", "saveBitmapToSD: 图片不存在");
            bitmap=BitmapFactory.decodeStream(inputStream);
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    fos.flush();
                    fos.close();
                    return bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
    private static String getMD5(String url){
        String rec=null;
        try {
            MessageDigest messageDigest=MessageDigest.getInstance("MD5");
            byte [] data=messageDigest.digest(url.getBytes());
            StringBuilder stringBuilder=new StringBuilder();

            for (byte b:data){
                int ib=b& 0x0FF;
                String s=Integer.toHexString(ib);
                stringBuilder.append(s);
            }
            rec=stringBuilder.toString();
            Log.d("RUNUI+MD5", "getMD5:--------------- "+rec);
            return rec;
        }catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
    }
}