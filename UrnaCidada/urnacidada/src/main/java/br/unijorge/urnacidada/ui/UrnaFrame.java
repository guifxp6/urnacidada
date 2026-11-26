package br.unijorge.urnacidada.ui;

import br.unijorge.urnacidada.model.Candidato;
import br.unijorge.urnacidada.service.UrnaService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Optional;

public class UrnaFrame extends JFrame {
    private final UrnaService service;
    private StringBuilder numeroDigitado = new StringBuilder();
    private boolean votoBranco = false;

    private JLabel lblCargo;
    private JLabel lblVisorNumero;
    private JLabel lblInfoCandidato;
    private JLabel lblInstrucao;

    public UrnaFrame() {
        this.service = new UrnaService();
        configurarJanela();
        construirInterface();
        atualizarTela();
    }

    private void configurarJanela() {
        setTitle("Urna Cidadã - Simulador Educativo (UNIJORGE)");
        setSize(820, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void construirInterface() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(230, 230, 230));

        JPanel visorPanel = new JPanel(new BorderLayout(10, 10));
        visorPanel.setBackground(Color.WHITE);
        visorPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        visorPanel.setPreferredSize(new Dimension(450, 400));

        lblCargo = new JLabel("", SwingConstants.LEFT);
        lblCargo.setFont(new Font("Arial", Font.BOLD, 18));
        lblCargo.setBorder(new EmptyBorder(10, 15, 5, 15));

        lblVisorNumero = new JLabel("", SwingConstants.LEFT);
        lblVisorNumero.setFont(new Font("Monospaced", Font.BOLD, 34));
        lblVisorNumero.setBorder(new EmptyBorder(5, 15, 5, 15));

        lblInfoCandidato = new JLabel("", SwingConstants.LEFT);
        lblInfoCandidato.setFont(new Font("Arial", Font.PLAIN, 15));
        lblInfoCandidato.setVerticalAlignment(SwingConstants.TOP);
        lblInfoCandidato.setBorder(new EmptyBorder(10, 15, 10, 15));

        lblInstrucao = new JLabel("<html><hr>Aperte a tecla:<br><b>CONFIRMA</b> para confirmar este voto<br><b>CORRIGE</b> para reiniciar este voto</html>");
        lblInstrucao.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInstrucao.setBorder(new EmptyBorder(5, 15, 10, 15));

        JPanel topoVisor = new JPanel(new GridLayout(2, 1));
        topoVisor.setBackground(Color.WHITE);
        topoVisor.add(lblCargo);
        topoVisor.add(lblVisorNumero);

        visorPanel.add(topoVisor, BorderLayout.NORTH);
        visorPanel.add(lblInfoCandidato, BorderLayout.CENTER);
        visorPanel.add(lblInstrucao, BorderLayout.SOUTH);

        JPanel direitoPanel = new JPanel(new BorderLayout(10, 10));
        direitoPanel.setBackground(new Color(40, 40, 40));
        direitoPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblJustica = new JLabel("JUSTIÇA ELEITORAL (SIMULADOR)", SwingConstants.CENTER);
        lblJustica.setForeground(Color.WHITE);
        lblJustica.setFont(new Font("Arial", Font.BOLD, 14));
        direitoPanel.add(lblJustica, BorderLayout.NORTH);

        JPanel tecladoNumerico = new JPanel(new GridLayout(4, 3, 8, 8));
        tecladoNumerico.setBackground(new Color(40, 40, 40));

        String[] teclas = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", ""};
        for (String t : teclas) {
            if (t.isEmpty()) {
                JLabel vazio = new JLabel();
                tecladoNumerico.add(vazio);
            } else {
                JButton btn = new JButton(t);
                btn.setFont(new Font("Arial", Font.BOLD, 20));
                btn.setBackground(Color.BLACK);
                btn.setForeground(Color.WHITE);
                btn.setFocusPainted(false);
                btn.addActionListener(e -> digitarNumero(t));
                tecladoNumerico.add(btn);
            }
        }
        direitoPanel.add(tecladoNumerico, BorderLayout.CENTER);

        JPanel acoesPanel = new JPanel(new GridLayout(1, 3, 8, 8));
        acoesPanel.setBackground(new Color(40, 40, 40));

        JButton btnBranco = criarBotaoAcao("BRANCO", Color.WHITE, Color.BLACK);
        btnBranco.addActionListener(e -> acaoBranco());

        JButton btnCorrige = criarBotaoAcao("CORRIGE", new Color(230, 115, 0), Color.BLACK);
        btnCorrige.addActionListener(e -> acaoCorrige());

        JButton btnConfirma = criarBotaoAcao("CONFIRMA", new Color(0, 160, 60), Color.BLACK);
        btnConfirma.addActionListener(e -> acaoConfirma());

        acoesPanel.add(btnBranco);
        acoesPanel.add(btnCorrige);
        acoesPanel.add(btnConfirma);
        direitoPanel.add(acoesPanel, BorderLayout.SOUTH);

        mainPanel.add(visorPanel, BorderLayout.CENTER);
        mainPanel.add(direitoPanel, BorderLayout.EAST);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuOficina = new JMenu("Oficina / Mesário");
        JMenuItem itemApuracao = new JMenuItem("Exibir Boletim de Urna (Apuração)");
        itemApuracao.addActionListener(e -> JOptionPane.showMessageDialog(this, service.gerarBoletimUrna()));
        JMenuItem itemNovoEleitor = new JMenuItem("Liberar para Próximo Eleitor");
        itemNovoEleitor.addActionListener(e -> {
            service.reiniciarVotacao();
            acaoCorrige();
        });
        menuOficina.add(itemNovoEleitor);
        menuOficina.add(itemApuracao);
        menuBar.add(menuOficina);
        setJMenuBar(menuBar);

        add(mainPanel);
    }

    private JButton criarBotaoAcao(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        return btn;
    }

    private void digitarNumero(String digito) {
        if (service.getEtapaAtual() > 2 || votoBranco) return;
        int maxDigitos = service.getQuantidadeDigitosEtapa();
        if (numeroDigitado.length() < maxDigitos) {
            numeroDigitado.append(digito);
            atualizarTela();
        }
    }

    private void acaoBranco() {
        if (service.getEtapaAtual() > 2) return;
        numeroDigitado.setLength(0);
        votoBranco = true;
        atualizarTela();
    }

    private void acaoCorrige() {
        if (service.getEtapaAtual() > 2) return;
        numeroDigitado.setLength(0);
        votoBranco = false;
        atualizarTela();
    }

    private void acaoConfirma() {
        if (service.getEtapaAtual() > 2) return;

        int maxDigitos = service.getQuantidadeDigitosEtapa();
        if (!votoBranco && numeroDigitado.length() < maxDigitos) {
            JOptionPane.showMessageDialog(this, "Digite todos os " + maxDigitos + " dígitos ou aperte BRANCO.");
            return;
        }

        Toolkit.getDefaultToolkit().beep();
        service.registrarVoto(numeroDigitado.toString(), votoBranco);
        numeroDigitado.setLength(0);
        votoBranco = false;
        atualizarTela();
    }

    private void atualizarTela() {
        if (service.getEtapaAtual() > 2) {
            lblCargo.setText("");
            lblVisorNumero.setText("FIM");
            lblVisorNumero.setHorizontalAlignment(SwingConstants.CENTER);
            lblInfoCandidato.setText("<html><center><br>Votação concluída e registrada com sucesso!<br><br>" +
                    "<i>Use o menu 'Oficina / Mesário' acima para liberar o próximo estudante.</i></center></html>");
            lblInstrucao.setVisible(false);
            return;
        }

        lblInstrucao.setVisible(true);
        lblVisorNumero.setHorizontalAlignment(SwingConstants.LEFT);
        lblCargo.setText("SEU VOTO PARA: " + service.getTituloCargoAtual());

        if (votoBranco) {
            lblVisorNumero.setText("");
            lblInfoCandidato.setText("<html><h2>VOTO EM BRANCO</h2></html>");
            return;
        }

        int maxDigitos = service.getQuantidadeDigitosEtapa();
        StringBuilder visorFormatado = new StringBuilder();
        for (int i = 0; i < maxDigitos; i++) {
            if (i < numeroDigitado.length()) {
                visorFormatado.append("[").append(numeroDigitado.charAt(i)).append("] ");
            } else {
                visorFormatado.append("[ ] ");
            }
        }
        lblVisorNumero.setText(visorFormatado.toString());

        if (numeroDigitado.length() == maxDigitos) {
            Optional<Candidato> cand = service.buscarCandidato(numeroDigitado.toString());
            if (cand.isPresent()) {
                lblInfoCandidato.setText(cand.get().getDescricaoCompleta());
            } else {
                lblInfoCandidato.setText("<html><b>NÚMERO ERRADO</b><br><h3>VOTO NULO</h3></html>");
            }
        } else {
            lblInfoCandidato.setText("<html><i>Digite o número do seu candidato...<br><br>" +
                    "Candidatos de teste:<br>" +
                    (service.getEtapaAtual() == 1 ? "10101 (Ada), 20202 (Alan), 30303 (Grace)" : "10 (Clarice), 20 (Dumont)") +
                    "</i></html>");
        }
    }
}