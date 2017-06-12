package br.com.whatsappandroid.cursoandroid.whatsapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.whatsappandroid.cursoandroid.whatsapp.activity.ConversaActivity2;
import br.com.whatsappandroid.cursoandroid.whatsapp.adapter.ContatoAdapter;
import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.activity.ConversaActivity;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Preferencias;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Contato;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {
    private ListView listView;
    private ArrayAdapter adapter;
    //private ArrayList<String> contatos;
    private ArrayList<Contato> contatos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerContatos;

    public ContatosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        //só será inicializado quando for inicializado
        firebase.addValueEventListener( valueEventListenerContatos );
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener( valueEventListenerContatos );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        //instancias objetos
        contatos = new ArrayList<>();
       /* contatos.add("Mariana Silva");
        contatos.add("Letícia Almeida");
        contatos.add("José Renato");*/
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        listView = (ListView) view.findViewById( R.id.lv_contatos );
        /*adapter =  new ArrayAdapter(
                getActivity(),
                R.layout.lista_contato,
                contatos
        );*/
        adapter = new ContatoAdapter(getActivity(), contatos);
        listView.setAdapter( adapter );



        //Recuperar contatos do firebase
        Preferencias preferencias = new Preferencias( getActivity() );
        String identificadorUsuarioLogado = preferencias.getIdentificador();
        firebase = ConfiguracaoFirebase.getFirebase()
                   .child("contatos")
                   .child( identificadorUsuarioLogado );

        //Listener para recuperar contatos
        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpar Lista
                contatos.clear();

                //Listar contatos
                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    Contato contato = dados.getValue( Contato.class );
                    //contatos.add( contato.getNome() );
                    contatos.add( contato );
                }

                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
//        firebase.addValueEventListener( valueEventListenerContatos );
       /* firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpar Lista
                contatos.clear();

                //Listar contatos
                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    Contato contato = dados.getValue( Contato.class );
                    contatos.add( contato.getNome() );
                }

                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                //recupera dados a serem passados
                Contato contato = contatos.get( position );

                //Enviando dados para conversa activity
                intent.putExtra("nome", contato.getNome());
                intent.putExtra("email", contato.getEmail());
                startActivity( intent );

            }
        });

        return view;
    }

}
