package com.example.desarr.seguridad.acts;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.desarr.seguridad.R;
import com.example.desarr.seguridad.custom.Fechas;
import com.example.desarr.seguridad.custom.InfoControl;
import com.example.desarr.seguridad.custom.SecurityKey;
import com.example.desarr.seguridad.custom.ThisApp;
import com.example.desarr.seguridad.manager.LinkCall;
import com.example.desarr.seguridad.manager.LinkCallParams;
import com.example.desarr.seguridad.server.ServerCall;
import com.example.desarr.seguridad.server.ServerRoute;
import com.example.desarr.seguridad.server.ServerRouteLocal;

public class SaludoActivity extends AppCompatActivity {

    private EditText txtStringToCode;
    private EditText txtStringEncoded;
    private TextView txtLogin;
    private TextView txtResponse;
    private TextView txtSaludo;
    private TextView lblResultado;
    private TextView lblParametros;
    private TextView txtDataDev;
    private Button btnEncode;
    private Button btnSend;
    private Button btnSendParametros;
    private Button btnLogin;
    private Button btnGetDataDev;
    private Button btnPostLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saludo);

        btnEncode = (Button) findViewById(R.id.btnEncode);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSendParametros = (Button) findViewById(R.id.btnSendParametros);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnGetDataDev = (Button) findViewById(R.id.btnGetDataDev);
        btnPostLocal = (Button) findViewById(R.id.btnPostLocal);

        lblResultado = (TextView) findViewById(R.id.lblResultado);
        lblParametros = (TextView) findViewById(R.id.lblParametros);

        txtSaludo = (TextView) findViewById(R.id.txtSaludo);
        txtStringToCode = (EditText) findViewById(R.id.txtToCode);
        txtStringEncoded = (EditText) findViewById(R.id.txtEncoded);
        txtLogin = (TextView) findViewById(R.id.txtDataToLogin);
        txtResponse = (TextView) findViewById(R.id.txtResponse);
        txtDataDev = (TextView) findViewById(R.id.txtDataDev);

        Bundle bundle = this.getIntent().getExtras();
        String uno = bundle.getString("USER");
        String dos = bundle.getString("PASS");

        //Code
        final String codeUser, codePass;

        //Mensaje a mostrar
        txtSaludo.setText(uno + " :: " + dos);

        //Proceso
        codeUser = SecurityKey.codificarParametros(uno);
        codePass = SecurityKey.codificarParametros(dos);

        //Eventos botón
        btnEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtStringEncoded.setText("");
                txtStringEncoded.setText(SecurityKey.codificarParametros(txtStringToCode.getText().toString()));
            }
        });
        //Controller seguridadmovil/procesarParametrosDecode/xxxxx
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LinkCall(lblResultado, txtStringEncoded.getText().toString()).execute();
            }
        });
        //Controller seguridadmovil/ver/xxxxx
        btnSendParametros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LinkCallParams(lblParametros, Fechas.getMovilDate()).execute();
            }
        });
        //Controller seguridadmovil/getRoute/xxxxx
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "email";
                String password = "password";
                String data = null;
                String sr = null;
                try {
                    data = InfoControl.getDataDev();
                } catch (Exception ex) {
                    System.out.println("error");
                }
                try {
                    String encodeData = txtLogin.getText().toString();
                    encodeData = SecurityKey.codificarParametros(encodeData + data);
                    sr = new ServerRoute("emailx", "passwordx", encodeData).execute().get();
                    /*
                    txtLogin.setText(encodeData);
                    sr = new ServerCall("emailx","passwordx",encodeData).execute().get();
                    */
                } catch (Exception ex) {
                    System.out.println("error");
                }
                txtDataDev.setText(InfoControl.getData());
                txtResponse.setText(Fechas.getMovilHour() + " :: " + sr);
            }
        });
        //Controller seguridadmovil/getRoute/xxxxx
        btnPostLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "email";
                String password = "password";
                String data = null;
                String sr = null;
                try {
                    data = InfoControl.getDataDev();
                } catch (Exception ex) {
                    System.out.println("error");
                }
                try {
                    String encodeData = txtLogin.getText().toString();
                    encodeData = SecurityKey.codificarParametros(encodeData + data);
                    sr = new ServerRouteLocal("emailx", "passwordx", encodeData).execute().get();

                } catch (Exception ex) {
                    System.out.println("error");
                }
                txtDataDev.setText("Local::: "+InfoControl.getData());
                txtResponse.setText("Local::: "+Fechas.getMovilHour() + " :: " + sr);
            }
        });
        //DataDevice
        btnGetDataDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDataDev.setText("");
            }
        });
    }
}
