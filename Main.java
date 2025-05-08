package package1;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame marcoGeneral = new JFrame("COLD WAR");
            marcoGeneral.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Mostrar PanelPrincipal al inicio
            PanelPrincipal panelPrincipal = new PanelPrincipal(marcoGeneral);
            marcoGeneral.setContentPane(panelPrincipal);
            marcoGeneral.pack();
            marcoGeneral.setVisible(true);
        });
    }
}