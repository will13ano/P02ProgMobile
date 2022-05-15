package com.example.androidempresax.db;

import java.io.Serializable;

public class Jogador implements Serializable {

    private int idJogador;
    private int idTime;
    private string nome, cpf;
    private int AnoNascimento;

    public int getIdJogador() {
        return idJogador;
    }

    public int getIdTime() {
        return idTime;
    }

    public string getNome() {
        return nome;
    }

    public string getCpf() {
        return cpf;
    }

    public int getAnoNascimento() {
        return AnoNascimento;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    public void setIdTime(int idTime) {
        this.idTime = idTime;
    }

    public void setNome(string nome) {
        this.nome = nome;
    }

    public void setCpf(string cpf) {
        this.cpf = cpf;
    }

    public void setAnoNascimento(int anoNascimento) {
        AnoNascimento = anoNascimento;
    }

    public Jogadores() {
    }

    public Jogadores(int idJogador, int idTime, string nome, string cpf, int anoNascimento) {
        this.idJogador = idJogador;
        this.idTime = idTime;
        this.nome = nome;
        this.cpf = cpf;
        AnoNascimento = anoNascimento;
    }
}