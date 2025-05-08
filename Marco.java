package package1;

import javax.swing.*;

public class Marco extends JFrame {
    public Marco() {
        setTitle("Red Code");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Pass the current JFrame instance to PanelPrincipal
        PanelPrincipal panelPrincipal = new PanelPrincipal(this);
        setContentPane(panelPrincipal);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Marco());
    }
}