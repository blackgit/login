package com.example.desarr.seguridad;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText txtUser;
    private EditText txtPass;
    private Button btnAceptar;
    private Menu aMenuH, aMenuA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Referencia a los controles de la interfaz
        txtUser = (EditText)findViewById(R.id.txtUser);
        txtPass = (EditText) findViewById(R.id.txtPass);
        btnAceptar = (Button)findViewById(R.id.btnAceptar);
        aMenuH = findViewById(R.id.hola);
        aMenuA = findViewById(R.id.adios);

        Bundle bundle = this.getIntent().getExtras();

        String uno = bundle.getString("USER");
        String dos = bundle.getString("PASS");
        String tres = bundle.getString("SEC");
        txtUser.setText(uno+"---"+dos);
        txtPass.setText(tres);

        //Evento click del botón
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Intent
            Intent intent =
                new Intent(MainActivity.this, SaludoActivity.class);
            //Información a pasar entre actividades
            Bundle b = new Bundle();
            b.putString("USER", txtUser.getText().toString());
            b.putString("PASS", txtPass.getText().toString());

            //Información al intent
            intent.putExtras(b);
            //Nueva actividad
            startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onMenuItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.adios:
                txtUser.setText("adios");
                return true;
            case R.id.hola:
                txtUser.setText("hola");
                return true;
        }
        return super.onMenuItemSelected(item.getItemId(),item);
    }
}
