package br.unijorge.urnacidada.service;

import br.unijorge.urnacidada.model.*;
import br.unijorge.urnacidada.repository.FirebaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrnaService {
    private final List<Candidato> candidatos = new ArrayList<>();
    private final FirebaseRepository repository;
    private int etapaAtual = 1; // 1 = Vereador (5 dígitos), 2 = Prefeito (2 dígitos), 3 = FIM

    public UrnaService() {
        this.repository = new FirebaseRepository();
        carregarCandidatosFicticios();
    }

    private void carregarCandidatosFicticios() {
        candidatos.add(new CandidatoProporcional(10101, "Ada Lovelace", "Partido da Lógica (PLOG)", "Inclusão Digital nas Escolas"));
        candidatos.add(new CandidatoProporcional(20202, "Alan Turing", "Partido da Ciência (PCIE)", "Laboratórios de Robótica"));
        candidatos.add(new CandidatoProporcional(30303, "Grace Hopper", "Partido da Inovação (PINO)", "Internet Gratuita nos Bairros"));

        candidatos.add(new CandidatoMajoritario(10, "Clarice Lispector", "Partido da Literatura (PLIT)", "Machado de Assis"));
        candidatos.add(new CandidatoMajoritario(20, "Santos Dumont", "Partido da Engenharia (PENG)", "Marie Curie"));
    }

    public int getEtapaAtual() {
        return etapaAtual;
    }

    public int getQuantidadeDigitosEtapa() {
        return (etapaAtual == 1) ? 5 : 2;
    }

    public String getTituloCargoAtual() {
        return (etapaAtual == 1) ? "VEREADOR(A) (5 dígitos)" : "PREFEITO(A) (2 dígitos)";
    }

    public Optional<Candidato> buscarCandidato(String numeroStr) {
        if (numeroStr.isEmpty()) return Optional.empty();
        int num = Integer.parseInt(numeroStr);
        return candidatos.stream()
                .filter(c -> c.getNumero() == num)
                .filter(c -> (etapaAtual == 1 && c instanceof CandidatoProporcional) ||
                             (etapaAtual == 2 && c instanceof CandidatoMajoritario))
                .findFirst();
    }

    public void registrarVoto(String numeroDigitado, boolean isBranco) {
        String cargo = (etapaAtual == 1) ? "VEREADOR" : "PREFEITO";

        if (isBranco) {
            repository.salvarVoto(new Voto(cargo, "BRANCO", "BRANCO"));
        } else {
            Optional<Candidato> candidatoOpt = buscarCandidato(numeroDigitado);
            if (candidatoOpt.isPresent()) {
                candidatoOpt.get().adicionarVoto();
                repository.salvarVoto(new Voto(cargo, numeroDigitado, "NOMINAL"));
            } else {
                repository.salvarVoto(new Voto(cargo, numeroDigitado, "NULO"));
            }
        }
        etapaAtual++;
    }

    public void reiniciarVotacao() {
        this.etapaAtual = 1;
    }

    public String gerarBoletimUrna() {
        StringBuilder sb = new StringBuilder("=== BOLETIM DE URNA (APURAÇÃO) ===\n\n");
        for (Candidato c : candidatos) {
            sb.append(c.getCargo()).append(" | ")
              .append(c.getNumero()).append(" - ")
              .append(c.getNome()).append(": ")
              .append(c.getVotos()).append(" voto(s)\n");
        }
        return sb.toString();
    }
}