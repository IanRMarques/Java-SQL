package br.inatel.model;

public class Carro {
    private int idCarro;
    private String modelo;
    private String fabricante;
    private int ano;
    private int idPiloto;

    // Construtores
    public Carro() {}

    public Carro(String modelo, String fabricante, int ano, int idPiloto) {
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.ano = ano;
        this.idPiloto = idPiloto;
    }

    // Getters e Setters
    public int getIdCarro() { return idCarro; }
    public void setIdCarro(int idCarro) { this.idCarro = idCarro; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public int getIdPiloto() { return idPiloto; }
    public void setIdPiloto(int idPiloto) { this.idPiloto = idPiloto; }
}