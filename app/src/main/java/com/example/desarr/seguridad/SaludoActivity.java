package com.example.desarr.seguridad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SaludoActivity extends AppCompatActivity {

    private EditText txtStringToCode;
    private EditText txtStringEncoded;
    private TextView txtSaludo;
    private TextView lblResultado;
    private TextView lblParametros;
    private Button btnEncode;
    private Button btnSend;
    private Button btnSendParametros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saludo);

        btnEncode = (Button)findViewById(R.id.btnEncode);
        btnSend = (Button)findViewById(R.id.btnSend);
        btnSendParametros = (Button)findViewById(R.id.btnSendParametros);

        lblResultado = (TextView)findViewById(R.id.lblResultado);
        lblParametros = (TextView)findViewById(R.id.lblParametros);

        txtSaludo = (TextView)findViewById(R.id.txtSaludo);
        txtStringToCode = (EditText)findViewById(R.id.txtToCode);
        txtStringEncoded = (EditText)findViewById(R.id.txtEncoded);

        Bundle bundle = this.getIntent().getExtras();
        String uno = bundle.getString("USER");
        String dos = bundle.getString("PASS");

        //Code
        final String codeUser, codePass;
        //Mensaje a mostrar
        txtSaludo.setText(uno+ " :: "+ dos);
        //Proceso
        //codeUser = SecurityKey.codificar(uno);
        //codePass = SecurityKey.codificar(dos);
        //Eventos botón
        /*btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LinkCall(lblResultado, codeUser).execute();
            }
        });*/
        btnSendParametros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            new LinkCallParams(lblParametros, "parametro fijo").execute();
            }
        });
        btnEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            txtStringEncoded.setText("");
            txtStringEncoded.setText(SecurityKey.codificarParametros(txtStringToCode.getText().toString()));
            }
        });
    }

}
