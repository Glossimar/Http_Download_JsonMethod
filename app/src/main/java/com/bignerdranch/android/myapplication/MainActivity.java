package com.bignerdranch.android.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int UrlName;
    private TextView textView;
    private ImageView imageView;
    public Object[] o;
    private String[] Imageurl;
    private HttpMethod method;
    private Boolean s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.texturl) ;
        imageView=(ImageView)findViewById(R.id.ima);

        method=new HttpMethod();
        method.HttpRequest("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1", new CallBackListener() {
            @Override
            public void onFinish(String response) {
                o=new Object[method.getJsonArrayLen()];
                o=method.paresJsonArray(response,"results","url");
                Imageurl=new String[method.getJsonArrayLen()];
                for (int i=0;i<o.length;i++) {
                    Imageurl[i] = o[i].toString();
                }

                s=(Boolean) method.paresJsonObject(response, "error");
                RunUI.getImage(MainActivity.this,Imageurl[1],imageView);
            }

            @Override
            public void onError(Exception e) {
                Log.d("MAINACTVITY_HTTPERROR", "onError: 网络请求出错");
            }
        });

    }

//    public void HttpConnectiom (final String urlAdd, final CallBackListener call){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpURLConnection connection=null;
//                BufferedReader reader=null;
//                try {
//                    URL url=new URL(urlAdd);
//                    connection=(HttpURLConnection)url.openConnection();
//                    connection.setRequestMethod("GET");
//                    connection.setConnectTimeout(8000);
//                    connection.setReadTimeout(8000);
//                    InputStream in=connection.getInputStream();
//                    reader=new BufferedReader(new InputStreamReader(in));
//                    StringBuilder response=new StringBuilder();
//                    String line;
//
//                    while ((line=reader.readLine())!=null) {
//                        response.append(line);
//                    }
//
//                    if (call!=null){
//                        call.onFinish(response.toString());
//                    }
//                }catch (Exception e){
//                  if (call!=null){
//                        call.onError(e);
//                    }
//                }finally {
//                    if (reader!=null){
//                        try {
//                            reader.close();
//                            Log.d("close", "run: close");
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                        if (connection!=null){
//                            connection.disconnect();
//                        }
//                    }
//                }
//            }
//        }).start();
//    }
//
//    public void showResponse(final String response){
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (response==null)
//                    Log.d("null", "run: response == null");
//                Log.d("response", "run: "+response);
//
//            }
//        });
//    }
//    public void praseJsonData(String jsonData){
//        try {
//            JSONArray jsonArray=new JSONArray(jsonData);
//            JSONObject jsonObject=new JSONObject(jsonData);
//            boolean error=jsonObject.getBoolean("error");
//            JSONArray jsonArray=jsonObject.getJSONArray("results");
//            Log.d("praseJsonData", "praseJsonData:    URL的结果为 "+error);
//
//            Imageurl=new String[jsonArray.length()];
//            for (int i=0;i<jsonArray.length();i++){
//                JSONObject jsonObject1=jsonArray.getJSONObject(i);
//                String _id=jsonObject1.getString("_id");
//                String createdAt=jsonObject1.getString("createdAt");
//                String desc=jsonObject1.getString("desc");
//                String publishedAt=jsonObject1.getString("publishedAt");
//                String source=jsonObject1.getString("source");
//                String type=jsonObject1.getString("type");
//                String url=jsonObject1.getString("url");
//                Log.d("jsonArray", "praseJsonData: "+url);
//                Imageurl[i]=url;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void getImage(Activity activity,final String imageUrl){
//
//        new  Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpURLConnection httpurlcoonection;
//                BufferedReader bufferedReader;
//                try {
//                    URL url=new URL(imageUrl);
//                    httpurlcoonection=(HttpURLConnection)url.openConnection();
//                    httpurlcoonection.setRequestMethod("GET");
//                    httpurlcoonection.setConnectTimeout(8000);
//                    httpurlcoonection.setReadTimeout(8000);
//
//                    InputStream iS=httpurlcoonection.getInputStream();
//                    Bitmap bitmap=BitmapFactory.decodeStream(iS);
//
//                    showImage(MainActivity.this,imageView,bitmap);
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    public void showImage(Activity activity,final ImageView imageView, final Bitmap bit){
//        activity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                imageView.setImageBitmap(bit);
//            }
//        });
//    }
}
