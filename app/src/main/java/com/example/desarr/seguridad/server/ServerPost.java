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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerPost extends AsyncTask<String, String, String> {

    String processResponse;

    public ServerPost() {
    }

    private static String sendPost(){
        
        ArrayList<NameValuePair> nameValuePairs = null;
        HttpClient httpclient = null;
        HttpPost httppost = null;
        HttpResponse response = null;
        HttpEntity entity = null;
        InputStream is = null;
        JSONObject job;
        JSONObject mensaje = null;
        String msjOut = null;
        String deviceInfo = InfoControl.getData();

        HashMap<String, String> params;
        StringBuilder sbParams = new StringBuilder();

        try{
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("username", "lastupdate"));
        }catch(IllegalStateException ex){
            Log.w("IOException", ex.toString());
        }
        
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("http://www.cgepm.gov.ar:8888/sf/web/movil/testPost");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
        } catch (Exception e) {
            Log.e("HTTP", "Error in http connection " + e.toString());
        }
        
        try {
            job = new JSONObject(inputStreamToString(response.getEntity().getContent()).toString());
            mensaje = job.getJSONObject("mensaje");
            msjOut = mensaje.getString("unidad");
        }catch(Exception ex){
            Log.w("Error", ex.toString());
        }
        
        return msjOut;
    }
    
    private static StringBuilder inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        //Guardamos la dirección en un buffer de lectura
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        //Y la leemos toda hasta el final
        try{
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        }catch(IOException ex){
            Log.w("Aviso", ex.toString());
        }

        // Devolvemos todo lo leido
        return total;
    }
    @Override
    protected String doInBackground(String... strings) {
        processResponse = ServerPost.sendPost();
        return processResponse;
    }
    @Override
    protected void onPostExecute(String success) {
        super.onPostExecute(processResponse);
    }

}
