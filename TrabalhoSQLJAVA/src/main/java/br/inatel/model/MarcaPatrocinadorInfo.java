// src/main/java/br/inatel/model/MarcaPatrocinadorInfo.java
package br.inatel.model;

public class MarcaPatrocinadorInfo {
    private String marcaNome;
    private String patrocinadorNome;

    // Construtor
    public MarcaPatrocinadorInfo(String marcaNome, String patrocinadorNome) {
        this.marcaNome = marcaNome;
        this.patrocinadorNome = patrocinadorNome;
    }

    // Getters
    public String getMarcaNome() {
        return marcaNome;
    }

    public String getPatrocinadorNome() {
        return patrocinadorNome;
    }

    // Override toString() para facilitar a impressão
    @Override
    public String toString() {
        return marcaNome + " → " + patrocinadorNome;
    }
}
