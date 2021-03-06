package com.example.desarr.seguridad.server;

import android.os.AsyncTask;
import android.util.Log;

import com.example.desarr.seguridad.custom.Fechas;
import com.example.desarr.seguridad.custom.InfoControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class ServerPostConnection extends AsyncTask<String, String, String> {

    String processResponse;

    public ServerPostConnection() {
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

    private static String sendPost(){
        String url = null;
        URL urlObj = null;
        HttpURLConnection httpConn = null;
        InputStream in =  null;
        OutputStream out = null;
        InputStreamReader isReader = null;
        DataOutputStream doStream = null;
        BufferedReader breader = null;
        StringBuffer readTextBuf = new StringBuffer();
        StringBuilder sbParams = new StringBuilder();
        StringBuilder result = new StringBuilder();
        String paramsString = null;
        String line = null;
        String deviceInfo = InfoControl.getData();
        HashMap<String, String> params = null;
        String valor;

        params = new HashMap<String,String>();
        params.put("username","testPost"+Fechas.getMovilDate());
        params.put("documento","26529520");
        params.put("device","555"+deviceInfo.length());

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
            ex.printStackTrace();
        }
        try {
            url = "http://www.cgepm.gov.ar:8888/cgepm/web/movil/testPost";

            urlObj = new URL(url);
            httpConn = (HttpURLConnection)urlObj.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setReadTimeout(15000 /* milliseconds */);
            httpConn.setConnectTimeout(15000 /* milliseconds */);
            httpConn.connect();
            paramsString = sbParams.toString();
            doStream = new DataOutputStream(httpConn.getOutputStream());
            doStream.writeBytes(paramsString);
            doStream.flush();
            doStream.close();
            //int rep = httpConn.getResponseCode();
            InputStream ins = httpConn.getErrorStream();
            if (ins == null) {
                ins = httpConn.getInputStream();
            }
            breader = new BufferedReader(new InputStreamReader(ins));
            //result.append(rep);
            while((line=breader.readLine()) != null){
                result.append(line);
            }
            /**/
            try{
            JSONObject jobj =  new JSONObject(result.toString());
            result.delete(0,result.length());
            if (jobj != null) {
                try {
                    JSONArray jarray = jobj.getJSONArray("vector");
                    result.append("\n");
                    result.append(jarray.getJSONObject(0).getString("nombres"));
                    result.append("\n");
                    result.append(jarray.getJSONObject(0).getString("apellidos"));
                    result.append("\n");
                    result.append(jarray.getJSONObject(0).getString("documento"));
                    result.append("\n");
                    result.append(jarray.getJSONObject(0).getString("cuit"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }catch(Exception ex){}
            /**/
        } catch(IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                if (doStream != null) {
                    doStream.close();
                    doStream = null;
                }
                if (breader != null) {
                    breader.close();
                    breader = null;
                }
                if (isReader != null) {
                    isReader.close();
                    isReader = null;
                }
                if (httpConn != null) {
                    httpConn.disconnect();
                    httpConn = null;
                }
            } catch (IOException ex) {

            }
        }
        if (result != null)
            valor = result.toString();
        else
            valor = "no data333";
        return valor;
    }
}
