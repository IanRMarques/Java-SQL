// src/br/inatel/model/EquipamentoPiloto.java
package br.inatel.model;

public class EquipamentoPiloto {
    private final String equipamentoNome;
    private final String pilotoNome;

    public EquipamentoPiloto(String equipamentoNome, String pilotoNome) {
        this.equipamentoNome = equipamentoNome;
        this.pilotoNome = pilotoNome;
    }

    public String getEquipamentoNome() {
        return equipamentoNome;
    }
    public String getPilotoNome() {
        return pilotoNome;
    }

    @Override
    public String toString() {
        return equipamentoNome + " → " + pilotoNome;
    }
}