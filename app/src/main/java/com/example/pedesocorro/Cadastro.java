package com.example.pedesocorro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Cadastro extends AppCompatActivity {
    EditText edtNomeUsuario, edtTelefoneContatoConfianca, edtContatoConfianca;
    TextView  txtViewTestProg;
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        edtNomeUsuario = findViewById(R.id.InputNome);
        edtContatoConfianca = findViewById(R.id.inputCtt);
        edtTelefoneContatoConfianca = findViewById(R.id.inputTel);
        txtViewTestProg = findViewById(R.id.txtViewTeste);
        getSupportActionBar().hide();
        Button btnSalvarProg = findViewById(R.id.btnSalvar);

        btnSalvarProg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = edtNomeUsuario.getText().toString();
                String contatoConfianca = edtContatoConfianca.getText().toString();
                String telefoneContatoConfianca = edtTelefoneContatoConfianca.getText().toString();

                txtViewTestProg.setText("O usuário é " + nomeUsuario + "o contato de confiança é " + contatoConfianca + "O telefone é " + telefoneContatoConfianca);

            }
        });

    }


}