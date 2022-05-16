package com.example.p02progmobile.db;

import java.io.Serializable;

public class Time implements Serializable {

    private int idTime;
    private String nome;

    public int getIdTime() {
        return idTime;
    }

    public void setIdTime(int idTime) {
        this.idTime = idTime;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Time() {
    }

    public Time(int idTime, String nome) {
        this.idTime = idTime;
        this.nome = nome;
    }

    public String toString() {
        return "Time: " + this.nome;
    }
}