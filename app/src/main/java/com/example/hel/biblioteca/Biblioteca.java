package com.example.hel.biblioteca;

import java.io.Serializable;

public class Biblioteca {

    private String nome;
    private String descricao;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;

    public Biblioteca(String nome, String descricao, String path){
        this.nome = nome;
        this.descricao = descricao;
        this.path = path;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


}
