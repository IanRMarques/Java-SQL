// src/main/java/br/inatel/model/CarroPilotoInfo.java
package br.inatel.model;

public class CarroPilotoInfo {
    private String carroNome;
    private String pilotoNome;

    // Construtor
    public CarroPilotoInfo(String carroNome, String pilotoNome) {
        this.carroNome = carroNome;
        this.pilotoNome = pilotoNome;
    }

    // Getters
    public String getCarroNome() {
        return carroNome;
    }

    public String getPilotoNome() {
        return pilotoNome;
    }

    // Override toString() para facilitar a impressão
    @Override
    public String toString() {
        return carroNome + " → " + pilotoNome;
    }
}
