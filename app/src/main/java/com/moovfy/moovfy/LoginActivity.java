package com.moovfy.moovfy;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.shape.TriangleEdgeTreatment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button log;
    Button regist;
    Button forg;
    EditText user_t;
    EditText pas_t;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        showLocationPermission();

        log = findViewById(R.id.login);
        regist = findViewById(R.id.register);
        forg = findViewById(R.id.forgot);


        user_t = findViewById(R.id.email);
        pas_t = findViewById(R.id.password);

        progressDialog = new ProgressDialog(this);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user_t.getText().toString().trim();
                String pas = pas_t.getText().toString().trim();
                login(email,pas);
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(),Register.class);
                startActivity(register);
            }
        });

        forg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reset = new Intent(getApplicationContext(),PasswordReset.class);
                startActivity(reset);
            }
        });




    }
    public void showLocationPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(LoginActivity.this, "No se tiene permiso para obtener la ubicación", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
        }
        /*else {
            Toast.makeText(LoginActivity.this, "Se tiene permiso!", Toast.LENGTH_SHORT).show();
        }*/
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 225:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                }else {

                }
                return;
        }

    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private void login(String email, String pas) {

        if (email.equals("")) {
            Toast.makeText(this,"Introduzca el email", Toast.LENGTH_LONG).show();
            return;
        }
        if (pas.equals("")) {
            Toast.makeText(this,"Introduzca el password", Toast.LENGTH_LONG).show();
            return;
        }
        if (!validarEmail(email)) {
            Toast.makeText(this, "Error en el formato del email", Toast.LENGTH_LONG).show();
            return;
        }


        mAuth.signInWithEmailAndPassword(email,pas)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (!user.isEmailVerified()) {
                                Toast.makeText(LoginActivity.this,"Correo electrónico no verificado", Toast.LENGTH_LONG).show();
                                return;
                            }
                            progressDialog.setMessage("Iniciando sessión...");
                            progressDialog.show();
                            getSharedPreferences("PREFERENCE",MODE_PRIVATE).edit().putString("user_uid",user.getUid()).commit();
                            Intent main = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(main);
                        } else {
                            Log.w( "signInUserWithEmail", task.getException());
                            Toast.makeText(LoginActivity.this, "No se pudo iniciar sesión", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });


    }

}