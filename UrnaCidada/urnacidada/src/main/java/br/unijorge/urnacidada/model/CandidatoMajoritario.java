package br.unijorge.urnacidada.model;

public class CandidatoMajoritario extends Candidato {
    private String nomeVice;

    public CandidatoMajoritario(int numero, String nome, String partido, String nomeVice) {
        super(numero, nome, partido);
        this.nomeVice = nomeVice;
    }

    @Override
    public String getCargo() {
        return "PREFEITO(A)";
    }

    @Override
    public String getDescricaoCompleta() {
        return "<html><b>Nome:</b> " + getNome() + "<br><b>Partido:</b> " + getPartido() +
               "<br><b>Vice:</b> " + nomeVice + "</html>";
    }
}