package com.example.desarr.seguridad.server;

import android.os.AsyncTask;

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

public class Autenticar extends AsyncTask<String, String, String> {

    String processResponse;
    static String data;
    public Autenticar(String data) {
        this.data = data;
    }
    @Override
    protected String doInBackground(String... strings) {
        processResponse = Autenticar.sendPost();
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
        //url = "http://www.cgepm.gov.ar:8888/cgepm/web/movil/testPost";
        //url = "http://www.cgepm.gov.ar:8888/cgepm/web/movil/getRoutelogin";
        url = "http://www.cgepm.gov.ar:8888/cgepm/web/movil/seguridadmovil/ver/";
        params = new HashMap<String,String>();
        params.put("username","testPost"+Fechas.getMovilDate());
        //params.put("param1",data);
        params.put("param1","201039bA614NDWdnIHwxAqUPibb612oomaWrQXKYzNa_620GsGxoapGuCZpXCuPvgNYat618TPjvTMmuLnBiAGSIRB-d616uOkBqYseTykddTyQ56613puiKqIzDbAGXb_4617UovphCNjMmjAAonbQss611fVyTpVRSWmNpa611FGEcunhIWTO3-615oKstbxjTpqhWHdD12610kTofCzHIOfr_615IyxjDMNCriMWwSFse612StdIdStiaTle-u611AQmBuVYBDKgar611JVdUONGIzYgic620UMwODQUDHvsoJmguPAeTnt616XDkePvrYLYNoZewHte613eQgTRUswIftkvau615QTxuOKEUQtkwdgfp_620eIIXalKtuADLrNgVxIKy");
/*        params.put("param1","2010399x416bhCGQkVFxBeExxUr78420ZAHVXAYiTVRydRbiinOJa" +
                "_414rStFzqxqoCtFyYat419KPeNKfCeFdudosFsDry" +
                "-d414wiXwFeHXSAikAZ56420nWEoZxUMlYTlbdsDngfr" +
                "_4419HwBEBwdEiRRyytxfTEhss412jHYcBzzMintypa416" +
                "PuXlAifgVyQuStti3" +
                "-420VOicqizLmHYomxvshztF12420UWiymCrnXjMKwMpRUVDrr" +
                "_412ocPQMmWGfDdxse417YzyjOIIUdlkiNYrYG" +
                "-u418uSLjgdSrisSJSnevrlar412XBPXZYDrmrKpic419" +
                "epMtjbcGGzJEjEyZVtXnt410lNhnJmcrszte419tcgHtSWycbEKqyNIIRQau" +
                "414EEJSUmLycvdzhdp" +
                "_418ODKCoKHurwOBNIdRAC");*/
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
