// Patrocinador.java
package br.inatel.model;

public class Patrocinador {
    private int idPatrocinador;
    private String nome;

    public Patrocinador() {}

    public Patrocinador(String nome) {
        this.nome = nome;
    }
    public int getIdPatrocinador() { return idPatrocinador; }
    public void setIdPatrocinador(int idPatrocinador) { this.idPatrocinador = idPatrocinador; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return String.format("Patrocinador{id=%d, nome='%s'}", idPatrocinador, nome);
    }
}
