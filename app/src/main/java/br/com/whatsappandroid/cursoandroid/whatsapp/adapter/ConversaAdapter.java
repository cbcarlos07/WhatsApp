package br.com.whatsappandroid.cursoandroid.whatsapp.adapter;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Conversa;

/**
 * Created by carlos.bruno on 20/06/2017.
 */


public class ConversaAdapter extends ArrayAdapter<Conversa> {
    private Context context;
    private ArrayList<Conversa> conversas;

    public ConversaAdapter(Context c, ArrayList<Conversa> objects) {
        super(c, 0, objects);
        this.context = c;
        this.conversas = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if( conversas != null ){

            //inicializa objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( context.LAYOUT_INFLATER_SERVICE );

            //Monta view a partir do XML
            view = inflater.inflate( R.layout.lista_conversa, parent, false );

            //Recupera elemento para exibição
            TextView nome = (TextView) view.findViewById( R.id.tv_titulo );
            TextView ultimaMensagem = (TextView) view.findViewById( R.id.tv_subtitulo );

            Conversa conversa = conversas.get( position );
            nome.setText( conversa.getNome() );
            ultimaMensagem.setText( conversa.getMensagem() );


        }

        return view;

    }
}
