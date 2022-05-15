package com.example.androidempresax.db;

import java.io.Serializable;

public class Time implements Serializable {

    private int idTime;
    private string descricao;

    public int getIdTime() {
        return idTime;
    }

    public void setIdTime(int idTime) {
        this.idTime = idTime;
    }

    public string getDescricao() {
        return descricao;
    }

    public void setDescricao(string descricao) {
        this.descricao = descricao;
    }

    public times() {
    }

    public times(int idTime, string descricao) {
        this.idTime = idTime;
        this.descricao = descricao;
    }
}