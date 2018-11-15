package com.moovfy.moovfy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button send_b;
    EditText email_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        mAuth = FirebaseAuth.getInstance();

        send_b =  findViewById(R.id.reset);
        email_t = findViewById(R.id.email_reset);

        send_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_t.getText().toString().trim();
                mAuth.sendPasswordResetEmail(email);
                Toast.makeText(PasswordReset.this, "Se ha enviado un correo a su cuenta. Por favor sigue los pasos que indica", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
