package br.com.whatsappandroid.cursoandroid.whatsapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {
    //private DatabaseReference referenceFirebase = FirebaseDatabase.getInstance().getReference();
    private Button botaoSair;
    private FirebaseAuth autenticacao;
    private Toolbar toolbar;
    private FirebaseAuth ususarioAutenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ususarioAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("WhatsApp");
        setSupportActionBar( toolbar );
        /*botaoSair = (Button) findViewById(R.id.bt_sair);

        botaoSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.signOut();

                Intent intent = new Intent(MainActivity.this, LoginActivity2.class);
                startActivity( intent );

            }
        });*/

      //  referenceFirebase.child("pontos").setValue(100);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ){
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case  R.id.item_configuracoes:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void deslogarUsuario(){
        ususarioAutenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity2.class);
        startActivity( intent );
        finish();
    }
}
