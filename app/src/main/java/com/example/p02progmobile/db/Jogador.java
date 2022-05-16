package com.example.p02progmobile.db;

import java.io.Serializable;

public class Jogador implements Serializable {

    private int idJogador;
    private int idTime;
    private String nome, cpf;
    private int anoNascimento;

    public int getIdJogador() {
        return idJogador;
    }

    public int getIdTime() {
        return idTime;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setIdTime(int idTime) {
        this.idTime = idTime;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Jogador() {
    }

    public Jogador(int idJogador, int idTime, String nome, String cpf, int anoNascimento) {
        this.idJogador = idJogador;
        this.idTime = idTime;
        this.nome = nome;
        this.cpf = cpf;
        this.anoNascimento = anoNascimento;
    }

    @Override
    public String toString() {
        return "Nome: " + this.nome +" ID: " +this.idJogador  +" CPF: " + this.cpf + " Data de Nascimento: " + this.anoNascimento + " Time: " + this.idTime;
    }
}