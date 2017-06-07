package br.com.whatsappandroid.cursoandroid.whatsapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;

public class LoginActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
    }

    public void abrirCadastroUsuario(View view){
        Intent intent = new Intent(LoginActivity2.this, CadastroUsuarioActivity.class);
        startActivity( intent );
    }
}
