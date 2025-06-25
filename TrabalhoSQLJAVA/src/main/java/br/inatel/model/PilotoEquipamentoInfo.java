// src/main/java/br/inatel/model/PilotoEquipamentoInfo.java
package br.inatel.model;

public class PilotoEquipamentoInfo {
    private String pilotoNome;
    private String equipamentoNome;

    // Construtor
    public PilotoEquipamentoInfo(String pilotoNome, String equipamentoNome) {
        this.pilotoNome = pilotoNome;
        this.equipamentoNome = equipamentoNome;
    }

    // Getters
    public String getPilotoNome() {
        return pilotoNome;
    }

    public String getEquipamentoNome() {
        return equipamentoNome;
    }

    // Override toString() para facilitar a impressão
    @Override
    public String toString() {
        return "Piloto: " + pilotoNome + ", Equipamento: " + equipamentoNome;
    }
}
