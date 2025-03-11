package com.pedro.model;

import org.bson.types.ObjectId;

public class Pais {

    private String nome;
    private String organizacion;
    private String[] partidos;
    private ObjectId id_presidente;

    public Pais() {
    }

    public Pais(String nome, String organizacion, String[] partidos, Presidente presidente) {
        this.nome = nome;
        this.organizacion = organizacion;
        this.partidos = partidos;
        this.id_presidente = presidente.getId();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    public String[] getPartidos() {
        return partidos;
    }

    public void setPartidos(String[] partidos) {
        this.partidos = partidos;
    }

    public ObjectId getId_presidente() {
        return id_presidente;
    }

    public void setId_presidente(ObjectId id_presidente) {
        this.id_presidente = id_presidente;
    }
}
