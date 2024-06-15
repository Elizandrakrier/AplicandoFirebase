package com.example.aprendendosobrefriebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aprendendosobrefriebase.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import classes.User;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private EditText edtName, edtIdade;
    private Button btnLoginCadastro;
    private DatabaseReference myRef;
    private User user;
    private TextView txtNome, txtIdade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        user = new User();

        edtName = (EditText) findViewById(R.id.edt_name);
        edtIdade = (EditText) findViewById(R.id.edt_idade);
        btnLoginCadastro = (Button) findViewById(R.id.btn_login_cadastro);

        btnLoginCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.setValue(edtName.getText().toString());
                cadastrarMeusDados(edtName.getText().toString(), edtIdade.getText().toString());
            }
        });

        txtNome = (TextView) findViewById(R.id.txt_name);
        txtIdade = (TextView) findViewById(R.id.txt_idade);

        preencherDadosUser();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_disconnect) {
            // Handle the disconnect action
            mAuth.signOut();
            voltarTelaLogin();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void voltarTelaLogin() {
        Toast.makeText(this, "Usuario desconectado"
                ,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginAcitivity.class);
        startActivity(intent);
    }

    public void cadastrarMeusDados(String nome, String idade) {
        String key = myRef.child("Usuário").push().getKey();
        user.setNome(nome);
        user.setIdade(idade);

        myRef.child("Usuário").child(key).setValue(user);

        Toast.makeText(this, "Dados inseridos com sucesso"
                , Toast.LENGTH_SHORT).show();
    }

    public void preencherDadosUser() {
        myRef.child("Usuário").addValueEventListener
                (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);

                    txtNome.setText(user.getNome());
                    txtIdade.setText(user.getIdade());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}





