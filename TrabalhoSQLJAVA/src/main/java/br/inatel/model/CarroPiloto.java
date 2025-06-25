// src/br/inatel/model/CarroPiloto.java
package br.inatel.model;

public class CarroPiloto {
    private final String modelo;
    private final String pilotoNome;

    public CarroPiloto(String modelo, String pilotoNome) {
        this.modelo = modelo;
        this.pilotoNome = pilotoNome;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPilotoNome() {
        return pilotoNome;
    }

    @Override
    public String toString() {
        return modelo + " → " + pilotoNome;
    }
}