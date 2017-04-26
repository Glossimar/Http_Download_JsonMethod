package com.bignerdranch.android.imageloadingwan;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LENOVO on 2017/4/11.
 */

public class HttpMethod {
    private int jsonArrayLen;

    public int getJsonArrayLen() {
        return jsonArrayLen;
    }

    public void setJsonArrayLen(int jsonArrayLen) {
        this.jsonArrayLen = jsonArrayLen;
    }

    public void httpRequest(final String urlAddress, final CallBackListener callBackListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(urlAddress);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(16000);
                    connection.setReadTimeout(16000);

                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder responce = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        responce.append(line);
                    }

                    if (callBackListener != null) {
                        callBackListener.onFinish(responce.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBackListener != null) {
                        callBackListener.onError(e);

                    }
                }
            }
        }).start();
    }

    public Object paresJsonObject(String jsonData,String objectName){
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONObject(jsonData);
            Object object=jsonObject.get(objectName);
            Log.d("HTTPMETHOD_JSONOBJECT", "paresJsonObject: "+object);
            return object;
        }catch (Exception e){
            e.printStackTrace();
            Log.d("HTTPMETHOD_JSONARRAY", "paresJsonArray: object对象解析失败" );
            return null;
        }
    }

    public Object [] paresJsonArray(String jsonData,String arrayName,String values){
        JSONArray jsonArray=null;
        JSONObject jsonObject=null;
        Object[] objectValues;

        try {
            jsonObject=new JSONObject(jsonData);
            jsonArray=jsonObject.getJSONArray(arrayName);
            objectValues=new Object[jsonArray.length()];
            setJsonArrayLen(jsonArray.length());
            for (int i=0;i<jsonArray.length();i++){
                JSONObject js=jsonArray.getJSONObject(i);
                objectValues[i]=js.get(values);
                Log.d("HTTPMETHOD_JSONARRAY", "paresJsonArray: "+objectValues[i]);
            }
            return objectValues;
        }catch (Exception e){
            e.printStackTrace();
            Log.d("HTTPMETHOD_JSONARRAY", "paresJsonArray: Array数组解析失败" );
            return null;
        }
    }
}
