package br.com.whatsappandroid.cursoandroid.whatsapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Base64Custom;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Preferencias;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Usuario;

public class LoginActivity2 extends AppCompatActivity {
    private EditText email;
    private EditText senha;
    private Button   botaoLogar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        verificarUsuarioLogado();

        email = (EditText) findViewById(R.id.edit_login_email);
        senha = (EditText) findViewById(R.id.edit_login_senha);
        botaoLogar = (Button) findViewById(R.id.bt_logar);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setEmail( email.getText().toString() );
                usuario.setSenha( senha.getText().toString() );
                validarLogin();
            }
        });


    }
    private void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if( autenticacao.getCurrentUser() != null ){

            abriTelaPrincipal();

        }
    }
    private void validarLogin(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if( task.isSuccessful() ){

                    Preferencias preferencias = new Preferencias( LoginActivity2.this );
                    String identificadoUsuarioLogado = Base64Custom.codificarBase64( usuario.getEmail() );
                    preferencias.salvarDados( identificadoUsuarioLogado );

                    abriTelaPrincipal();
                    Toast.makeText(LoginActivity2.this, "Sucesso ao fazer login", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(LoginActivity2.this, "Erro ao fazer login", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void abriTelaPrincipal(){
        Intent intent = new Intent(LoginActivity2.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void abrirCadastroUsuario(View view){
        Intent intent = new Intent(LoginActivity2.this, CadastroUsuarioActivity.class);
        startActivity( intent );
    }
}
