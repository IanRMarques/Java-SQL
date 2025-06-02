package br.inatel.model;

public class EquipamentoSeguranca {
    private int idEquipamento;
    private String capacete;
    private String macacao;
    private String bota;
    private String luva;

    // Construtor
    public EquipamentoSeguranca(String capacete, String macacao, String bota, String luva) {
        this.capacete = capacete;
        this.macacao = macacao;
        this.bota = bota;
        this.luva = luva;
    }

    // Getters e Setters
    public int getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(int idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public String getCapacete() {
        return capacete;
    }

    public void setCapacete(String capacete) {
        this.capacete = capacete;
    }

    public String getMacacao() {
        return macacao;
    }

    public void setMacacao(String macacao) {
        this.macacao = macacao;
    }

    public String getBota() {
        return bota;
    }

    public void setBota(String bota) {
        this.bota = bota;
    }

    public String getLuva() {
        return luva;
    }

    public void setLuva(String luva) {
        this.luva = luva;
    }
}