package com.example.desarr.seguridad.acts;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.desarr.seguridad.server.Autenticar;
import com.example.desarr.seguridad.server.CallOperation;
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
import java.io.OutputStream;
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
    private EditText txtRoute;
    private EditText responseTextView = null;

    private Button btnSend;
    private Button btnPost;
    private Button btnPostHandler;
    private Button btnRouteLogin;
    private Button btnRouteOperation;

    private Handler uiUpdater = null;

    private static final int REQUEST_CODE_SHOW_RESPONSE_TEXT = 1;
    private static final String KEY_RESPONSE_TEXT = "KEY_RESPONSE_TEXT";
    private static final String REQUEST_METHOD_POST = "POST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);

        initComp();

        btnSend.requestFocus();
        btnSend.setFocusableInTouchMode(true);
        btnSend.requestFocusFromTouch();
    }

    public void ejecutar(View v) {
        SharedPreferences preferencias = getSharedPreferences("datos",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("login", "http://www.cgepm.gov.ar:8888/cgepm/web/movil/testPost");
        editor.commit();
        finish();
    }

    protected void initPreferences(){
        SharedPreferences prefe =
            getSharedPreferences("datos",Context.MODE_PRIVATE);
        prefe.getString("mail","");
    }

    protected void initComp() {

        //<editor-fold defaultstate="collapsed" desc="Carga Componentes">
        txtParam = (EditText) findViewById(R.id.txtParams);
        btnSend = (Button) findViewById(R.id.btnSend);
        txtResponse = (EditText) findViewById(R.id.txtResponse);
        txtUrl = (EditText) findViewById(R.id.txtUrl);
        txtEncode = (EditText) findViewById(R.id.txtEncode);
        btnPost = (Button) findViewById(R.id.btnPost);
        txtPost = (EditText) findViewById(R.id.txtResponsePost);
        txtRoute = (EditText) findViewById(R.id.txtRoute);
        btnPostHandler = (Button) findViewById(R.id.btnPostHandler);
        btnRouteLogin = (Button) findViewById(R.id.btnRouteLogin);
        btnRouteOperation = (Button) findViewById(R.id.btnRouteOperation);
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
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Handlers">
        /*uiUpdater = new Handler()
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
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="btnSend">
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /**
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
            **/
            String serverResponse = null;
            try {
                serverResponse = new Autenticar("dato").execute().get();
            } catch (Exception ex) {
                System.out.println("error");
            }
            txtResponse.setText("");
            txtResponse.setText("ok"+serverResponse);
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
            txtPost.setText("");
            txtPost.setText("ok"+serverResponse);
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="btnPostHandler">
        btnPostHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String reqUrl =  "http://www.cgepm.gov.ar:8888/cgepm/web/movil/getPost";
            startSendHttpRequestThread(reqUrl);
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="btnLogin">
        btnRouteLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverResponse = null;
                int oper = 1; /* Tipo de operacion Login */
                String pathRoute = "p_autenticar-user_123-pass_456-data_ppppp";
                String encodeData =
                    SecurityKey.codificarParametros(pathRoute);
                try {
                    serverResponse = new CallOperation(oper, encodeData).execute().get();
                } catch (Exception ex) {
                    System.out.println("error");
                }
                txtRoute.setText("");
                txtRoute.setText("ok  "+serverResponse);
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="btnOperation">
        btnRouteOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverResponse = null;
                int oper = 2; /* Tipo de operacion Login */
                String pathRoute =
                    "p_verAgenteDocumento-documento_26529520-typeOut_0";
                String encodeData =
                        SecurityKey.codificarParametros(pathRoute);
                try {
                    serverResponse = new CallOperation(oper, encodeData).execute().get();
                } catch (Exception ex) {
                    System.out.println("error");
                }
                txtRoute.setText("");
                txtRoute.setText("ok  "+serverResponse);
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
                String url = null;
                URL urlObj = null;
                HttpURLConnection httpConn = null;
                InputStream in = null;
                OutputStream out = null;
                InputStreamReader isReader = null;
                DataOutputStream doStream = null;
                BufferedReader breader = null;
                StringBuffer readTextBuf = new StringBuffer();
                StringBuilder sbParams = new StringBuilder();
                StringBuilder result = null;
                String paramsString = null;
                String line = null;
                String deviceInfo = InfoControl.getData();
                HashMap<String, String> params = null;
                String valor;

                params = new HashMap<String,String>();
                params.put("username","getPost"+Fechas.getMovilDate());
                params.put("documento","26529520");
                params.put("device","444"+deviceInfo.length());

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
                try {
                    url = reqUrl;
                    urlObj = new URL(url);
                    httpConn = (HttpURLConnection)urlObj.openConnection();
                    httpConn.setDoOutput(true);
                    httpConn.setRequestMethod(REQUEST_METHOD_POST);
                    httpConn.setConnectTimeout(10000);
                    httpConn.setReadTimeout(10000);
                    httpConn.connect();

                    paramsString = sbParams.toString();
                    doStream = new DataOutputStream(httpConn.getOutputStream());
                    doStream.writeBytes(paramsString);
                    doStream.flush();

                    in = httpConn.getErrorStream();
                    if (in == null) {
                        in = httpConn.getInputStream();
                    }
                    isReader = new InputStreamReader(in);

                    breader = new BufferedReader(isReader);

                    line = breader.readLine();
                    while(line != null)
                    {
                       readTextBuf.append(line);
                       line = breader.readLine();
                    }

                    Message message;
                    Bundle bundle;
                    // Send message to main thread to update response text in TextView after read all.
                    message = new Message();
                    // Set message type.
                    message.what = REQUEST_CODE_SHOW_RESPONSE_TEXT;
                    // Create a bundle object.
                    bundle = new Bundle();
                    // Put response text in the bundle with the special key.
                    bundle.putString(KEY_RESPONSE_TEXT, "ok"+readTextBuf.toString());
                    // Set bundle data in message.
                    message.setData(bundle);
                    // Send message to main thread Handler to process.
                    uiUpdater.sendMessage(message);
                }catch(Exception ex){
                    Log.e("", ex.getMessage(), ex);
                }finally {
                    try {

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
