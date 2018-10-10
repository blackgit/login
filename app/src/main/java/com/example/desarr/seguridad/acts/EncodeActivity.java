package com.example.desarr.seguridad.acts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desarr.seguridad.R;
import com.example.desarr.seguridad.custom.Fechas;
import com.example.desarr.seguridad.custom.InfoControl;
import com.example.desarr.seguridad.custom.SecurityKey;
import com.example.desarr.seguridad.server.ServerPost;
import com.example.desarr.seguridad.server.ServerRoute;

public class EncodeActivity extends AppCompatActivity {

    private EditText txtParam;
    private EditText txtResponse;
    private EditText txtUrl;
    private EditText txtEncode;
    private EditText txtPost;
    private Button btnSend;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        txtUrl.getText().append("p_autenticar-user_123-pass_456-data_");
        txtParam.getText().append(InfoControl.getData());

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

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serverResponse = null;
                try {
                    serverResponse = new ServerPost().execute().get();
                } catch (Exception ex) {
                    System.out.println("error");
                }
                txtPost.setText(serverResponse);
            }
        });
    }
}
