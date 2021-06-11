package com.example.pedesocorro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Cadastro extends AppCompatActivity {
    EditText edtNomeUsuario, edtTelefoneContatoConfianca, edtContatoConfianca;
    TextView  txtViewTestProg;
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inicio da API que vai gravar os dados
        SharedPreferences pref = getApplicationContext().getSharedPreferences("PEDE_SOCORRO_DATA", MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        edtNomeUsuario = findViewById(R.id.InputNome);
        edtContatoConfianca = findViewById(R.id.inputCtt);
        edtTelefoneContatoConfianca = findViewById(R.id.inputTel);
        txtViewTestProg = findViewById(R.id.txtViewTeste);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Button btnSalvarProg = findViewById(R.id.btnSalvar);

        btnSalvarProg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeUsuario = edtNomeUsuario.getText().toString();
                String contatoConfianca = edtContatoConfianca.getText().toString();
                String telefoneContatoConfianca = edtTelefoneContatoConfianca.getText().toString();

                //grava dados na API
                edt.putString("name", nomeUsuario);
                edt.putString("nomeContatoConfianca", contatoConfianca);
                edt.putString("numeroTelefoneContatoConfianca", telefoneContatoConfianca);

                // salva os dados gravados na API
                edt.commit();

                //recupera os dados da API
                String nomeDB=pref.getString("name", null);
                String nomeContatoConfiancaDB=pref.getString("nomeContatoConfianca", null);
                String numeroTelefoneContatoConfiancaDB=pref.getString("numeroTelefoneContatoConfianca", null);

                txtViewTestProg.setText("Usuário: " + nomeDB +  "\nContato de confiança: " + nomeContatoConfiancaDB + "\nTelefone do Contato: " + numeroTelefoneContatoConfiancaDB);

            }
        });

    }


}