package br.com.whatsappandroid.cursoandroid.whatsapp.model;

/**
 * Created by carlos.bruno on 20/06/2017.
 */

public class Conversa {

    private String idUsuario;
    private String nome;
    private String mensagem;

    public Conversa() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
