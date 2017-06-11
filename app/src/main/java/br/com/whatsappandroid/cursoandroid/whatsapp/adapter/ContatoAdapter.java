package br.com.whatsappandroid.cursoandroid.whatsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Contato;

/**
 * Created by carlos.bruno on 11/06/2017.
 */
public class ContatoAdapter extends ArrayAdapter<Contato> {

    private ArrayList<Contato> contatos;
    private Context context;
    public ContatoAdapter(Context c, ArrayList<Contato> objects) {
        super(c, 0, objects);
        this.contatos = objects;
        this.context = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     //   return super.getView(position, convertView, parent);
        View view = null;

        //Verifica se a lista está vazia
        if( contatos !=  null ){
            //inicializar objetos para a montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( context.LAYOUT_INFLATER_SERVICE );

            //Monta a nossa view a partir do xml
            view = inflater.inflate(R.layout.lista_contato, parent, false );

            //recupera elemento para exibição
            TextView nomeContato = (TextView) view.findViewById( R.id.tv_nome );
            TextView emailContato = (TextView) view.findViewById( R.id.tv_email );

            Contato contato = contatos.get( position );
            nomeContato.setText( contato.getNome() );
            emailContato.setText( contato.getEmail() );
        }

        return view;

    }
}
