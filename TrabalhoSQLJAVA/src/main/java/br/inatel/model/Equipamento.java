// src/br/inatel/model/Equipamento.java
package br.inatel.model;

public class Equipamento {
    private int idEquipamento;
    private String nome;
    private String tipo;
    private int idPiloto;

    public Equipamento() {}

    public Equipamento(String nome, String tipo, int idPiloto) {
        this.nome = nome;
        this.tipo = tipo;
        this.idPiloto = idPiloto;
    }

    public int getIdEquipamento() {
        return idEquipamento;
    }
    public void setIdEquipamento(int idEquipamento) {
        this.idEquipamento = idEquipamento;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public int getIdPiloto() {
        return idPiloto;
    }
    public void setIdPiloto(int idPiloto) {
        this.idPiloto = idPiloto;
    }

    @Override
    public String toString() {
        return String.format(
                "Equipamento{id=%d, nome='%s', tipo='%s', idPiloto=%d}",
                idEquipamento, nome, tipo, idPiloto
        );
    }
}