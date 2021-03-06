package com.mispeliculas.pruebalogin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etUsuario, etContraseña;
    TextView tvMensaje;
    Button btIniciar, btRegistrar;
    TextView neuron;
    private FirebaseAuth mAuth;
    //id pruebalogin-9578d fire base

    int REQUEST_CODE = 200;



    //probando comentario para git coomit mas push

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = findViewById(R.id.etUsuario);
        etContraseña = findViewById(R.id.etContraseña);
        tvMensaje = findViewById(R.id.tvMensaje);
        btIniciar = findViewById(R.id.btIniciar);
        btRegistrar = findViewById(R.id.btRegistrar);
        neuron = findViewById(R.id.neuron);

        VerificarPermisos();

        neuron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://fundacionneuron.org/";

                Uri link = Uri.parse(url);
                Intent i = new Intent(Intent.ACTION_VIEW, link);
                startActivity(i);
            }
        });



    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void VerificarPermisos() {

       int permisoSms = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
       int permisoAlmacenamiento = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permisoSms == PackageManager.PERMISSION_GRANTED && permisoAlmacenamiento == PackageManager.PERMISSION_GRANTED){
            //Mensaje permisos aceptados

                Toast.makeText(this, "Permisos aceptado", Toast.LENGTH_SHORT).show();

        }else{
            requestPermissions(new String[]{Manifest.permission.SEND_SMS,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE );
        }
    }


    public void Iniciar(View view){

        String usuario = etUsuario.getText().toString();
        String contraseña = etContraseña.getText().toString();
        mAuth = FirebaseAuth.getInstance();

        if(usuario.isEmpty() && contraseña.isEmpty()){
            tvMensaje.setText("Rellene todos los campos");

        }else if(usuario.isEmpty()){
            tvMensaje.setText("El campo usuario esta vacio");
        }else if(contraseña.isEmpty()){
            tvMensaje.setText("El campo contraseña esta vacio");
        }
        else{
            mAuth.signInWithEmailAndPassword(usuario, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        Intent home = new Intent(MainActivity.this, HomeUsuario.class);
                        startActivity(home);
                    }else{
                        tvMensaje.setText("campos erroneos");
                        Toast.makeText(MainActivity.this, "No se pudo inicar sesion, verifique correo y contraseña", Toast.LENGTH_SHORT).show();

                    }


                }
            });

        }


    }

   /* @Override
    protected void onStart() {
        FirebaseUser user = mAuth.getCurrentUser();

        if(user!=null){
            Intent home = new Intent(MainActivity.this, HomeUsuario.class);
            startActivity(home);
        }

        super.onStart();
    }

    */
    public void Registrar(View view){

        Intent registrar = new Intent(this, Registrarse.class);
        startActivity(registrar);

    }


}