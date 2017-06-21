package br.com.whatsappandroid.cursoandroid.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by carlos.bruno on 06/06/2017.
 */
public class Preferencias {
    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "whatsapp.preferencias";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;


    private final String CHAVE_TELEFONE = "telefone";
    private final String CHAVE_TOKEN = "token";
    private final String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";
    private final String CHAVE_NOME = "nomeUsuarioLogado";


    public Preferencias (Context contextoParametro){

        contexto = contextoParametro;
        //MODE = 0 apenas o app tera acesso
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();

    }

  /*  public void salvarUsuarioPreferencias( String nome, String telefone, String token ){

        editor.putString(CHAVE_NOME, nome);
        //editor.putString(CHAVE_TELEFONE, telefone);
        //editor.putString(CHAVE_TOKEN, token);
        editor.commit();
    }*/

    public void salvarDados( String identificadorUsuario, String  nomeUsuario  ){

        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        editor.putString(CHAVE_NOME, nomeUsuario);
        editor.commit();
    }

    /*public HashMap<String, String> getDadosUsuario(){
        HashMap<String, String> dadosUsuario =  new HashMap<>();

        dadosUsuario.put(CHAVE_NOME, preferences.getString( CHAVE_NOME, null));
        dadosUsuario.put(CHAVE_TELEFONE, preferences.getString(CHAVE_TELEFONE, null));
        dadosUsuario.put(CHAVE_TOKEN, preferences.getString(CHAVE_TOKEN, null));

        return dadosUsuario;
    }*/

    public String getIdentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }
    public String getNome(){
        return preferences.getString(CHAVE_NOME, null);
    }
}
