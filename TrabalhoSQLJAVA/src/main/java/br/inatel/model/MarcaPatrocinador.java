package br.inatel.model;

public class MarcaPatrocinador {
    private int idMarca;
    private int idPatrocinador;

    public MarcaPatrocinador() {}

    public MarcaPatrocinador(int idMarca, int idPatrocinador) {
        this.idMarca = idMarca;
        this.idPatrocinador = idPatrocinador;
    }

    // Getters e Setters
    public int getIdMarca() { return idMarca; }
    public void setIdMarca(int idMarca) { this.idMarca = idMarca; }
    public int getIdPatrocinador() { return idPatrocinador; }
    public void setIdPatrocinador(int idPatrocinador) { this.idPatrocinador = idPatrocinador; }
}