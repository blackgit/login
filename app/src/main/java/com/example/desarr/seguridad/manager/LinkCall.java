package com.example.desarr.seguridad.manager;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class LinkCall extends AsyncTask<String,Void,String> {

    TextView aView;
    String code;

    public LinkCall(TextView aView, String code){
        this.aView = aView;
        this.code = code;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        StringBuilder sb=null;
        BufferedReader reader=null;
        String serverResponse=null;
        URL url;
        HttpURLConnection connection = null;
        int statusCode = 0;
        try {
            //urls = "http://10.14.10.88/cgepm/web/app.php/movil/seguridadmovil/decodificar/"+code;
            //url = new URL("http://cgepm.gov.ar/sage/androidxyx/android_traer_servicios.asp?documento=28554317");
            url = new URL( "http://www.cgepm.gov.ar:8888/sf/web/movil/seguridadmovil/decodificar/"+code);
            connection = (HttpURLConnection) url.openConnection();
            System.out.println(connection.getURL().toString());
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.connect();
            statusCode = connection.getResponseCode();
        } catch (Exception e) {
            e.getMessage();
        }
        try{
            if (statusCode == 200) {
                sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            }
            connection.disconnect();
            if (sb!=null)
                serverResponse=sb.toString();
                //serverResponse="df";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return serverResponse;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //All your UI operation can be performed here
        System.out.println(s);
        this.aView.setText(s);
    }
}
