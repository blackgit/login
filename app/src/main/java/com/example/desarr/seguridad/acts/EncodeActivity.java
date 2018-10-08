package com.example.desarr.seguridad.acts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.desarr.seguridad.R;
import com.example.desarr.seguridad.custom.Fechas;
import com.example.desarr.seguridad.custom.InfoControl;
import com.example.desarr.seguridad.custom.SecurityKey;
import com.example.desarr.seguridad.server.ServerRoute;

public class EncodeActivity extends AppCompatActivity {

    private TextView txtParam;
    private TextView txtResponse;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initComp();


    }

    protected void initComp(){
        txtParam = (TextView) findViewById(R.id.txtParam);
        btnSend = (Button) findViewById(R.id.btnSend);
        txtResponse = (TextView) findViewById(R.id.txtResponse);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "email";
                String password = "password";
                String data = null;
                String sr = null;
                String encodeData = null;
                try {
                    data = InfoControl.getDataDev();
                } catch (Exception ex) {
                    System.out.println("error");
                }
                try {
                    encodeData = txtParam.getText().toString();
                    encodeData = SecurityKey.codificarParametros(encodeData + data);
                    sr = new ServerRoute("emailx", "passwordx", encodeData).execute().get();
                } catch (Exception ex) {
                    System.out.println("error");
                }
                txtResponse.setText(InfoControl.getData());
                txtResponse.setText(Fechas.getMovilHour() + " :: " + sr);
            }
        });
    }

}
