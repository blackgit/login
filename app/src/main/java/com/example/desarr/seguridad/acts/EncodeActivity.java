package com.example.desarr.seguridad.acts;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desarr.seguridad.R;
import com.example.desarr.seguridad.custom.Fechas;
import com.example.desarr.seguridad.custom.InfoControl;
import com.example.desarr.seguridad.custom.SecurityKey;
import com.example.desarr.seguridad.server.ServerPostConnection;
import com.example.desarr.seguridad.server.ServerRoute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class EncodeActivity extends AppCompatActivity {

    private EditText txtParam;
    private EditText txtResponse;
    private EditText txtUrl;
    private EditText txtEncode;
    private EditText txtPost;
    private Button btnSend;
    private Button btnPost;

    private Button btnPostHandler;
    private Handler uiUpdater = null;
    private EditText responseTextView = null;
    private static final int REQUEST_CODE_SHOW_RESPONSE_TEXT = 1;
    private static final String KEY_RESPONSE_TEXT = "KEY_RESPONSE_TEXT";
    private static final String REQUEST_METHOD_POST = "POST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);

        initComp();

    }

    protected void initComp() {

        txtParam = (EditText) findViewById(R.id.txtParams);
        btnSend = (Button) findViewById(R.id.btnSend);
        txtResponse = (EditText) findViewById(R.id.txtResponse);
        txtUrl = (EditText) findViewById(R.id.txtUrl);
        txtEncode = (EditText) findViewById(R.id.txtEncode);
        btnPost = (Button) findViewById(R.id.btnPost);
        txtPost = (EditText) findViewById(R.id.txtResponsePost);
        btnPostHandler = (Button) findViewById(R.id.btnPostHandler);
        txtUrl.getText().append("p_autenticar-user_123-pass_456-data_");
        txtParam.getText().append(InfoControl.getData());
        txtPost.getText().append(InfoControl.getData());

        if(responseTextView == null)
        {
            responseTextView = (EditText)findViewById(R.id.http_url_response_text_view);
            responseTextView.getEditableText().append(Fechas.getMovilHour());
        }
        if(btnPostHandler == null)
        {
            btnPostHandler = (Button)findViewById(R.id.btnPostHandler);
        }/*
        uiUpdater = new Handler()
        {
            public void handleMessage(Message msg) {
                if(msg.what == REQUEST_CODE_SHOW_RESPONSE_TEXT)
                {
                    Bundle bundle = msg.getData();
                    if(bundle != null)
                    {
                        String responseText = bundle.getString(KEY_RESPONSE_TEXT);
                        responseTextView.getEditableText().append(responseText);
                    }
                }
            }
        };*/
        uiUpdater = new Handler()
        {
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                if(bundle != null)
                {
                    String responseText = bundle.getString(KEY_RESPONSE_TEXT);

                    responseTextView.getEditableText().append("Receive:   "+responseText);
                }
            }
        };

        //<editor-fold defaultstate="collapsed" desc="btnSend">
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String data = null;
            String url = null;
            String sr = null;
            String encodeData = null;
            data = txtParam.getText().toString();
            url = txtUrl.getText().toString();
            try {
                encodeData = SecurityKey.codificarParametros(url + data);
                //txtEncode.getText().append(encodeData);
            } catch (Exception ex) {
                System.out.println("error");
            }
            try {
                sr = new ServerRoute(encodeData).execute().get();
            } catch (Exception ex) {
                System.out.println("error");
            }
            txtResponse.setText(InfoControl.getData());
            txtResponse.setText(Fechas.getMovilHour() + " :: " + sr);
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="btnPost">
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serverResponse = null;
                try {
                    serverResponse = new ServerPostConnection().execute().get();
                } catch (Exception ex) {
                    System.out.println("error");
                }
                txtPost.setText("ok");
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="btnPostHandler">
        btnPostHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String reqUrl =  "http://www.cgepm.gov.ar:8888/sf/web/movil/getPost";
            startSendHttpRequestThread(reqUrl);
            }
        });
        //</editor-fold>
    }

    private void startSendHttpRequestThread(final String reqUrl)
    {
        Thread sendHttpRequestThread = new Thread()
        {
            @Override
            public void run() {

                HttpURLConnection httpConn = null;
                InputStream inputStream;
                InputStreamReader isReader = null;
                BufferedReader bufReader = null;
                StringBuffer readTextBuf = new StringBuffer();
                String paramsString = null;
                URL url = null;
                String line;
                /**/ //Probar
                StringBuilder sbParams = new StringBuilder();
                HashMap<String, String> params = null;
                params = new HashMap<String,String>();
                params.put("username","newPost"+Fechas.getMovilDate());
                DataOutputStream wr;
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
                /**/
                Message message;
                Bundle bundle;
                try {
                    url = new URL(reqUrl);
                    httpConn = (HttpURLConnection)url.openConnection();
                    httpConn.setRequestMethod(REQUEST_METHOD_POST);
                    httpConn.setConnectTimeout(10000);
                    httpConn.setReadTimeout(10000);
                    /**/ //Probar
                    httpConn.connect();
                    paramsString = sbParams.toString();
                    wr = new DataOutputStream(httpConn.getOutputStream());
                    wr.writeBytes(paramsString);
                    wr.flush();
                    /**/
                    //inputStream = httpConn.getInputStream();
                    //isReader = new InputStreamReader(inputStream);
                    //bufReader = new BufferedReader(isReader);
                    //line = bufReader.readLine();
                    //while(line != null)
                    //{
                    //   readTextBuf.append(line);
                    //   line = bufReader.readLine();
                    //}
                    /**/
                    String rs;
                    InputStream in = new BufferedInputStream(httpConn.getInputStream());
                    String rLine;
                    String id = null;
                    String name = null;
                    StringBuilder answer = new StringBuilder();
                    InputStreamReader isr = new InputStreamReader(in);
                    BufferedReader rd = new BufferedReader(isr);
                    try{
                        while((rLine=rd.readLine())!=null){
                            //readTextBuf.append(rLine);
                            answer.append(rLine);
                        }
                    }catch(Exception ex){}
                    rs = answer.toString();
                    JSONObject jsonObject = null;
                    JSONArray jsonArray = null;
                    try {
                        jsonObject = new JSONObject(rs);
                        jsonArray = jsonObject.getJSONArray("vector");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            id = jsonArray.getString(1);
                            name = jsonArray.getString(2);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //readTextBuf.append(":::"+id+name+":::"+answer.toString()+":::"+jsonObject.getString("vector"));
                    //readTextBuf.append(":::"+jsonObject.getString("vector"));
                    readTextBuf.append(":::"+jsonObject.getJSONArray("vector"));
                    /**/
                    // Send message to main thread to update response text in TextView after read all.
                    message = new Message();
                    // Set message type.
                    message.what = REQUEST_CODE_SHOW_RESPONSE_TEXT;
                    // Create a bundle object.
                    bundle = new Bundle();
                    // Put response text in the bundle with the special key.
                    bundle.putString(KEY_RESPONSE_TEXT, readTextBuf.toString());
                    //bundle.putString(KEY_RESPONSE_TEXT, decodeJ(readTextBuf.toString()));
                    // Set bundle data in message.
                    message.setData(bundle);
                    // Send message to main thread Handler to process.
                    uiUpdater.sendMessage(message);
                }catch(org.json.JSONException ex){
                    Log.e("", ex.getMessage(), ex);
                }catch(MalformedURLException ex){
                    Log.e("", ex.getMessage(), ex);
                }catch(IOException ex){
                    Log.e("", ex.getMessage(), ex);
                }finally {
                    try {
                        if (bufReader != null) {
                            bufReader.close();
                            bufReader = null;
                        }
                        if (isReader != null) {
                            isReader.close();
                            isReader = null;
                        }
                        if (httpConn != null) {
                            httpConn.disconnect();
                            httpConn = null;
                        }
                    }catch (IOException ex){
                        Log.e("", ex.getMessage(), ex);
                    }
                }
            }
        };
        sendHttpRequestThread.start();
    }

    private String decodeJ( String data){
        String resultOut = null;
        String oneObjectsItem;
        String oneObjectsItem1;
        JSONObject jObj = null;
        JSONArray jArr = null;
        String obj = null;
        try
        {
            jObj = new JSONObject(data);
            jArr = jObj.getJSONArray("vector");
            for (int i=0; i < jArr.length(); i++) {
                obj = jArr.getString(i);
            }
            resultOut = obj;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return resultOut;
    }
}
