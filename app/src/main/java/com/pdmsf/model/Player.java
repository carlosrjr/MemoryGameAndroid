package com.pdmsf.model;

public class Player {
    private String nome;
    private String score;
    private int errors;

    public Player() {
        this.nome = "";
        this.score = "0";
        this.errors = 0;
    }

    public Player(String nome, String score, int errors) {
        this.nome = nome;
        this.score = score;
        this.errors = errors;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getErrors() { return errors; }
    public void setErrors(int errors) { this.errors = errors; }
}
