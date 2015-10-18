package com.mpoo.ruralmaps.ruralmaps.negocio;

/**
 * Created by batmovel on 18/10/2015.
 */
public class Usuario {
    private Integer _id;
    private String nome;
    private String login;
    private String senha;

    public Usuario(){}

    public Usuario (Integer id, String tarefa, String dtcriacao, String dtcompletado){
        this._id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
