// Piloto.java
package br.inatel.model;

public class Piloto {
    private int idPiloto;
    private String nome;

    public Piloto() {}

    public Piloto(String nome) {
        this.nome = nome;
    }
    public int getIdPiloto() { return idPiloto; }
    public void setIdPiloto(int idPiloto) { this.idPiloto = idPiloto; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return String.format("Piloto{id=%d, nome='%s'}", idPiloto, nome);
    }
}
