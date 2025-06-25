package br.inatel.model;

public class PilotoEquipamento {
    private int idPiloto;
    private int idEquipamento;

    // Construtor
    public PilotoEquipamento(int idPiloto, int idEquipamento) {
        this.idPiloto = idPiloto;
        this.idEquipamento = idEquipamento;
    }

    // Getters e Setters
    public int getIdPiloto() {
        return idPiloto;
    }

    public void setIdPiloto(int idPiloto) {
        this.idPiloto = idPiloto;
    }

    public int getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(int idEquipamento) {
        this.idEquipamento = idEquipamento;
    }
}