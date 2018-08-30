package com.example.desarr.seguridad;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class LinkCallParams extends AsyncTask<String,Void,String> {

    TextView aView;
    String code;

    public LinkCallParams(TextView aView, String code){
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
        StringBuffer sbff=null;

        URL url;
        HttpURLConnection connection = null;
        int statusCode = 0;
        String urls = null;
        String encodedUrl = null;
        try {
            //url = new URL("http://cgepm.gov.ar/sage/androidxyx/android_traer_servicios.asp?documento=28554317");
            //url = new URL("http://cgepm.gov.ar/sage/androidxyx/test.asp");
            url = new URL( "http://www.cgepm.gov.ar:8888/sf/web/movil/seguridadmovil/ver/"+code);
            System.out.println("CODE:    "+code);
            connection = (HttpURLConnection) url.openConnection();
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
                System.out.println("status code 200");
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
