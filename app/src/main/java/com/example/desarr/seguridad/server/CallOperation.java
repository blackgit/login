package com.example.desarr.seguridad.server;

import android.os.AsyncTask;

import com.example.desarr.seguridad.custom.Fechas;
import com.example.desarr.seguridad.custom.InfoControl;

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

public class CallOperation extends AsyncTask<String, String, String> {

    String processResponse;
    static String data;
    static int oper;

    public CallOperation(int oper, String data) {
        this.oper = oper;
        this.data = data;
    }
    @Override
    protected String doInBackground(String... strings) {
        switch (oper){
            case 1:
                processResponse = CallOperation.sendPostLogin();
                break;
            case 2:
                processResponse = CallOperation.sendPostOperation();
                break;
        }
        return processResponse;
    }
    @Override
    protected void onPostExecute(String success)
    {
        super.onPostExecute(processResponse);
    }

    private static String sendPostLogin(){
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
        url = "http://www.cgepm.gov.ar:8888/cgepm/web/movil/getRouteLogin/";
        params = new HashMap<String,String>();
        params.put("username","testPost"+Fechas.getMovilDate());
        params.put("param1",data);
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
            InputStream ins = httpConn.getErrorStream();
            if (ins == null) {
                ins = httpConn.getInputStream();
            }
            breader = new BufferedReader(new InputStreamReader(ins));
            while((line=breader.readLine()) != null){
                result.append(line);
            }
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

    private static String sendPostOperation(){
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
        url = "http://www.cgepm.gov.ar:8888/cgepm/web/movil/getRouteOperation/";
        params = new HashMap<String,String>();
        params.put("username","testPost"+Fechas.getMovilDate());
        params.put("param1",data);
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
            InputStream ins = httpConn.getErrorStream();
            if (ins == null) {
                ins = httpConn.getInputStream();
            }
            breader = new BufferedReader(new InputStreamReader(ins));
            while((line=breader.readLine()) != null){
                result.append(line);
            }
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
