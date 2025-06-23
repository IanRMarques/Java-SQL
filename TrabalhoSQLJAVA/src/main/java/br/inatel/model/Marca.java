// Marca.java
package br.inatel.model;

public class Marca {
    private int idMarca;
    private String nome;

    public Marca() {}

    public Marca(String nome) {
        this.nome = nome;
    }
    public int getIdMarca() { return idMarca; }
    public void setIdMarca(int idMarca) { this.idMarca = idMarca; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return String.format("Marca{id=%d, nome='%s'}", idMarca, nome);
    }
}
