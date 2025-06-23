// Carro.java
package br.inatel.model;

public class Carro {
    private int idCarro;
    private int idMarca;
    private String nome;

    public Carro() {}

    public Carro(int idMarca, String nome) {
        this.idMarca = idMarca;
        this.nome    = nome;
    }
    public int getIdCarro() { return idCarro; }
    public void setIdCarro(int idCarro) { this.idCarro = idCarro; }
    public int getIdMarca() { return idMarca; }
    public void setIdMarca(int idMarca) { this.idMarca = idMarca; }
    public String getNome()  { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return String.format("Carro{id=%d, idMarca=%d, nome='%s'}", idCarro, idMarca, nome);
    }
}
