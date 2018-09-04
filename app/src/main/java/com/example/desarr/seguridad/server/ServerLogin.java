package com.example.desarr.seguridad.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ServerLogin {

    public static String loginMethod() {
        String serverResponse = null;
        int statusCode = 0;
        StringBuilder sb = null;
        BufferedReader reader = null;
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL("http://cgepm.gov.ar/sage/androidxyx/android_traer_servicios.asp?documento=28554317");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.connect();
            statusCode = connection.getResponseCode();
            try {
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
}
