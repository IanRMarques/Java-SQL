package br.inatel.model;

public class Piloto {
    private int idPiloto;
    private String nome;
    private int qpf;
    private String genero;
    private String nacionalidade;
    private boolean campeao;

    // Construtores
    public Piloto() {}

    public Piloto(String nome) {
        this.nome = nome;
    }

    public Piloto(String nome, int qpf, String genero, String nacionalidade, boolean campeao) {
        this.nome = nome;
        this.qpf = qpf;
        this.genero = genero;
        this.nacionalidade = nacionalidade;
        this.campeao = campeao;
    }

    // Getters e Setters
    public int getIdPiloto() { return idPiloto; }
    public void setIdPiloto(int idPiloto) { this.idPiloto = idPiloto; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getQpf() { return qpf; }
    public void setQpf(int qpf) { this.qpf = qpf; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }
    public boolean isCampeao() { return campeao; }
    public void setCampeao(boolean campeao) { this.campeao = campeao; }
}