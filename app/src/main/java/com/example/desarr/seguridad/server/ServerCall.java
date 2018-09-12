package com.example.desarr.seguridad.server;

import android.os.AsyncTask;

import com.example.desarr.seguridad.custom.SecurityKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ServerCall extends AsyncTask<String, String, String> {

    String serverResponse = null;
    int statusCode;
    StringBuilder sb = new StringBuilder();
    String line;
    BufferedReader reader = null;
    URL url;
    HttpURLConnection connection = null;

    private final String mEmail;
    private final String mPassword;
    private final String mData;
    String loginProcess = null;

    /**
     * Constructor
     * @param email
     * @param password
     */
    public ServerCall(String email, String password, String data) {
        mEmail = email;
        mPassword = password;
        mData = data;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            url = new URL("http://www.cgepm.gov.ar:8888/sf/web/movil/getRoute/"+mData);
            //url = new URL("http://www.cgepm.gov.ar:8888/sf/web/movil/getRoutetest/"+255555);
            //url = new URL(
            //        "http://www.cgepm.gov.ar:8888/sf/web/movil/seguridadmovil/procesarParametrosDecode/"+mData
            //);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.connect();
            statusCode = connection.getResponseCode();
            try {
                if (statusCode == 200) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                }
                connection.disconnect();
                if (sb != null)
                    serverResponse = sb.toString();
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
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return serverResponse;
    }

    @Override
    protected void onPostExecute(String success) {
        System.out.println("==============================" + serverResponse);
        super.onPostExecute(serverResponse);
    }

    @Override
    protected void onCancelled() {
        //mAuthTask = null;
        //showProgress(false);
    }

}
