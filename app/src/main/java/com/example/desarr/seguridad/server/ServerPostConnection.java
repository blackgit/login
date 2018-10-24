package com.example.desarr.seguridad.server;

import android.os.AsyncTask;
import android.util.Log;

import com.example.desarr.seguridad.custom.InfoControl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerPostConnection extends AsyncTask<String, String, String> {

    String processResponse;

    public ServerPostConnection() {
    }

    private static String sendPost(){

        String url = null;
        URL urlObj = null;
        HttpURLConnection conn = null;
        String paramsString = null;
        DataOutputStream wr = null;
        InputStream in = null;
        BufferedReader reader = null;
        StringBuilder result = null;
        String line = null;
        String deviceInfo = InfoControl.getData();
        HashMap<String, String> params = null;
        StringBuilder sbParams = new StringBuilder();
        params = new HashMap<String,String>();
        params.put("username",deviceInfo);

        try{
            int i = 0;
            for (String key : params.keySet()) {
                try {
                    if (i != 0){
                        sbParams.append("&");
                    }
                    sbParams.append(key)
                        .append("=")
                            .append(URLEncoder
                                .encode(params
                                    .get(key), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }catch(IllegalStateException ex){
            Log.w("IOException", ex.toString());
        }
        
        try {
            url = "http://www.cgepm.gov.ar:8888/sf/web/movil/testPost";
            urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.connect();
            paramsString = sbParams.toString();
            wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(paramsString);
            wr.flush();
            wr.close();
        } catch (Exception e) {
            Log.e("HTTP", "Error in http connection " + e.toString());
        }
        
        try {
            in = new BufferedInputStream(conn.getInputStream());
            reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("test", "result from server: " + result.toString());
        }catch(Exception ex){
            Log.w("Error", ex.toString());
        }finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        
        return result.toString();
    }

    @Override
    protected String doInBackground(String... strings) {
        processResponse = ServerPostConnection.sendPost();
        return processResponse;
    }
    @Override
    protected void onPostExecute(String success) {
        super.onPostExecute(processResponse);
    }

}
