package com.example.aprendendosobrefriebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginAcitivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);
        mAuth = FirebaseAuth.getInstance();

        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EfetuarLogin(edtEmail.getText().toString()
                        , edtPassword.getText().toString());
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            UsuarioLogado();
        }
    }

    public void UsuarioLogado() {
        Intent intent = new  Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Usuario logado", Toast.LENGTH_SHORT).show();
    }

    public void EfetuarLogin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "singInWithEmail:sucess");
                            UsuarioLogado();
                        } else {
                            Log.v("TAG", "singInWithEmail:failure");
                            Toast.makeText(LoginAcitivity.this
                                    , "singInWithEmail:failure"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
