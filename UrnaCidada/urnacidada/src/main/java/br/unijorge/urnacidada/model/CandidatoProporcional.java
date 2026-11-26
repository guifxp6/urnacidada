package br.unijorge.urnacidada.model;

public class CandidatoProporcional extends Candidato {
    private String areaAtuacao;

    public CandidatoProporcional(int numero, String nome, String partido, String areaAtuacao) {
        super(numero, nome, partido);
        this.areaAtuacao = areaAtuacao;
    }

    @Override
    public String getCargo() {
        return "VEREADOR(A)";
    }

    @Override
    public String getDescricaoCompleta() {
        return "<html><b>Nome:</b> " + getNome() + "<br><b>Partido:</b> " + getPartido() +
               "<br><b>Proposta:</b> " + areaAtuacao + "</html>";
    }
}