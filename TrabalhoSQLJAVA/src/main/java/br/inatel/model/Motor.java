package br.inatel.model;

public class Motor {
    private int idMotor;
    private double cilindrada;
    private boolean aspirado;
    private double durabilidade;

    // Construtor
    public Motor(double cilindrada, boolean aspirado, double durabilidade) {
        this.cilindrada = cilindrada;
        this.aspirado = aspirado;
        this.durabilidade = durabilidade;
    }

    // Getters e Setters
    public int getIdMotor() {
        return idMotor;
    }

    public void setIdMotor(int idMotor) {
        this.idMotor = idMotor;
    }

    public double getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(double cilindrada) {
        this.cilindrada = cilindrada;
    }

    public boolean isAspirado() {
        return aspirado;
    }

    public void setAspirado(boolean aspirado) {
        this.aspirado = aspirado;
    }

    public double getDurabilidade() {
        return durabilidade;
    }

    public void setDurabilidade(double durabilidade) {
        this.durabilidade = durabilidade;
    }
}