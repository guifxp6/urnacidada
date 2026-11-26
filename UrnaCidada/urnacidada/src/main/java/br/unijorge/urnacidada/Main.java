package br.unijorge.urnacidada;

import br.unijorge.urnacidada.ui.UrnaFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UrnaFrame urna = new UrnaFrame();
            urna.setVisible(true);
        });
    }
}