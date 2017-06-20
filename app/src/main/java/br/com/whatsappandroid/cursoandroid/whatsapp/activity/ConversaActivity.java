package br.com.whatsappandroid.cursoandroid.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.adapter.MensagemAdapter;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Base64Custom;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Preferencias;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Conversa;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Mensagem;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Mensagem2;


public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editMensagem;
    private ImageButton btMensagem;
    private DatabaseReference firebase;
    private ListView listView;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private ValueEventListener valueEventListenerMensagem;

    //dados do destinatario
    private String nomeUsuarioDestinatario;
    private String idUsuarioDestinatario;


    //dados do remetente
    private String idUsuarioRemetente;
    private String nomeUsuarioRemetente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = (Toolbar) findViewById(R.id.tb_conversa);
        editMensagem = (EditText) findViewById(R.id.edit_mensagem);
        btMensagem = (ImageButton) findViewById(R.id.bt_enviar);
        listView = (ListView) findViewById(R.id.lv_conversas);

        // dados do usuário logado
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioRemetente = preferencias.getIdentificador();
        nomeUsuarioRemetente = preferencias.getNome();


        Bundle extra = getIntent().getExtras();

        if( extra != null ){
            nomeUsuarioDestinatario = extra.getString("nome");
            String emailDestinatario = extra.getString("email");
            idUsuarioDestinatario = Base64Custom.codificarBase64( emailDestinatario );
        }


        //Configura toolbar
        toolbar.setTitle( nomeUsuarioDestinatario );
        toolbar.setNavigationIcon( R.drawable.ic_action_arrow_left );
        setSupportActionBar( toolbar );

        //monta a listview e adapter
         mensagens = new ArrayList<>();
        /* adapter = new ArrayAdapter(
                  ConversaActivity.this,
                  android.R.layout.simple_list_item_1,
                  mensagens
         );*/
        adapter = new MensagemAdapter(ConversaActivity.this,mensagens);
        listView.setAdapter( adapter );

        //recuperar  mensagens do firebase
        firebase = ConfiguracaoFirebase.getFirebase()
                   .child( "mensagens" )
                   .child( idUsuarioRemetente )
                   .child( idUsuarioDestinatario ) ;

        // Cria listener para mensagens
        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Limpa mensagens
                mensagens.clear();

                //Recupera mensagens
                for ( DataSnapshot dados: dataSnapshot.getChildren() ){
                    Mensagem mensagem = dados.getValue( Mensagem.class );
                    mensagens.add( mensagem );

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        firebase.addValueEventListener( valueEventListenerMensagem );

        //Envia mensagem
        btMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoMensagem = editMensagem.getText().toString();

                if( textoMensagem.isEmpty() ){
                    Toast.makeText(ConversaActivity.this, "Digite uma mensagem para anviar!", Toast.LENGTH_LONG).show();
                }else{

                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario( idUsuarioRemetente ); //usuario que está enviando a mensagem
                    mensagem.setMensagem( textoMensagem );

                    //salvamos mensagem para o remente
                    boolean retornoMensagemRemetente =  salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);
                    if( !retornoMensagemRemetente ){
                        Toast.makeText(
                                ConversaActivity.this,
                                "Problema ao salvar mensagem, tente novamente",
                                Toast.LENGTH_SHORT
                        ).show();
                    }else{

                        //salvamos mensagem para o destinatario
                        boolean retornoMensagemDestinatario = salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente, mensagem);
                        if( !retornoMensagemDestinatario ){
                            Toast.makeText(
                                    ConversaActivity.this,
                                    "Problema ao salvar mensagem para o destinatário, tente novamente",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    //salvamos mensagem para o remente
                    Conversa conversa = new Conversa();
                    conversa.setIdUsuario( idUsuarioDestinatario );
                    conversa.setNome( nomeUsuarioDestinatario );
                    conversa.setMensagem( textoMensagem );
                    boolean retornoConversaRemetente = salvarConversa( idUsuarioRemetente, idUsuarioDestinatario, conversa );
                    if( !retornoConversaRemetente ){
                        Toast.makeText(
                                ConversaActivity.this,
                                "Problema ao salvar conversa, tente novamente",
                                Toast.LENGTH_SHORT
                        ).show();
                    }else{

                        //salvamos mensagem para o destinatario
                        Conversa conversa1 = new Conversa();
                        conversa1.setIdUsuario( idUsuarioRemetente );
                        conversa1.setNome( nomeUsuarioRemetente );
                        conversa1.setMensagem( textoMensagem );

                        boolean retornoConversaDestinatario = salvarConversa( idUsuarioDestinatario, idUsuarioRemetente, conversa );
                        if( !retornoConversaDestinatario ){
                            Toast.makeText(
                                    ConversaActivity.this,
                                    "Problema ao salvar conversa para o destinatário, tente novamente",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                    }



                    editMensagem.setText("");
                    /*
                    + mensagens
                        + carlos@gmail.com
                           + bruno@gmai.com
                             + mensagem
                             + mensagem
                           + goncalveso@gmai.com
                             +mensagem
                     */

                }

            }
        });

    }

    private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem){
        try{

            firebase = ConfiguracaoFirebase.getFirebase().child("mensagens");

            firebase.child( idRemetente )
                    .child( idDestinatario )
                    .push() //gera o identificador unico
                    .setValue( mensagem );

            return true;
        }catch ( Exception e ){
            e.printStackTrace();
            return false;
        }
    }

    private boolean salvarConversa( String idRemetente, String idDestinatario, Conversa conversa ){
        try{

            firebase = ConfiguracaoFirebase.getFirebase().child( "conversas" );
            firebase.child( idRemetente )
                    .child( idDestinatario)
                    .setValue( conversa );
            return true;

        }catch ( Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener( valueEventListenerMensagem );
    }
}
