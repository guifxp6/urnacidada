package br.unijorge.urnacidada.model;

public abstract class Candidato {
    private int numero;
    private String nome;
    private String partido;
    private int votos;

    public Candidato(int numero, String nome, String partido) {
        this.numero = numero;
        this.nome = nome;
        this.partido = partido;
        this.votos = 0;
    }

    public abstract String getCargo();
    public abstract String getDescricaoCompleta();

    public void adicionarVoto() {
        this.votos++;
    }

    public int getNumero() { return numero; }
    public String getNome() { return nome; }
    public String getPartido() { return partido; }
    public int getVotos() { return votos; }
}