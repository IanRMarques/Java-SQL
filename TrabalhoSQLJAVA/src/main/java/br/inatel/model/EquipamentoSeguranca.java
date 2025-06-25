// EquipamentoSeguranca.java
package br.inatel.model;

public class EquipamentoSeguranca {
    private int idEquipamento;
    private String nome;

    public EquipamentoSeguranca() {}

    public EquipamentoSeguranca(String nome) {
        this.nome = nome;
    }
    public int getIdEquipamento() { return idEquipamento; }
    public void setIdEquipamento(int idEquipamento) { this.idEquipamento = idEquipamento; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return String.format("EquipamentoSeguranca{id=%d, nome='%s'}", idEquipamento, nome);
    }
}
