package br.inatel.model;

public class Patrocinador {
    private int idPatrocinador;
    private String nome;
    private int fama;

    public Patrocinador() {}

    public Patrocinador(String nome, int fama) {
        this.nome = nome;
        this.fama = fama;
    }

    // Getters e Setters
    public int getIdPatrocinador() { return idPatrocinador; }
    public void setIdPatrocinador(int idPatrocinador) { this.idPatrocinador = idPatrocinador; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getFama() { return fama; }
    public void setFama(int fama) { this.fama = fama; }
}