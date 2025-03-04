package com.pedro.model;

import org.bson.types.ObjectId;

public class Presidente {

    private ObjectId id;
    private String nome;
    private int idade;
    private String partido;

    public Presidente() {
    }

    public Presidente(String nome, int idade, String partido) {
        this.id = new ObjectId();
        this.nome = nome;
        this.idade = idade;
        this.partido = partido;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }
}
