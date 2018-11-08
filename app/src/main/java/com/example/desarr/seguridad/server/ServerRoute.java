package com.example.desarr.seguridad.server;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ServerRoute extends AsyncTask<String, String, String> {

    private String mEmail = null;
    private String mPassword = null;
    private final String mData;
    String loginProcess = null;

    /**
     * Constructor
     * @param email
     * @param password
     * @param data
     */
    public ServerRoute(String email, String password, String data) {
        mEmail = email;
        mPassword = password;
        mData = data;
    }
    /**
     * Constructor
     * @param data
     */
    public ServerRoute(String data) {
        mData = data;
    }
    @Override
    protected String doInBackground(String... params) {
        loginProcess = ServerRoute.loginMethod(mData);
        return loginProcess;
    }
    @Override
    protected void onPostExecute(String success) {
        super.onPostExecute(loginProcess);
        //protected void onPostExecute(final Boolean success) {

        //mAuthTask = null;
        //showProgress(false);

        //if (success) {
        /**/
        /*
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Bundle b = new Bundle();
        System.out.println("=================" + serverResponse);
        b.putString("USER", mEmail);
        b.putString("PASS", mPassword);
        b.putString("SEC", loginProcess[0]);
        b.putString("ID", loginProcess[1]);
        intent.putExtras(b);
        startActivity(intent);
        */
        /**/
        //} else {
        //    mPasswordView.setError(getString(R.string.error_incorrect_password));
        //    mPasswordView.requestFocus();
        //}
    }
    @Override
    protected void onCancelled() {
        //mAuthTask = null;
        //showProgress(false);
    }

    public static String loginMethod(String param) {
        String serverResponse = null;
        int statusCode;
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader reader = null;
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL("http://www.cgepm.gov.ar:8888/cgepm/web/movil/getRoute/"+param);
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
}
