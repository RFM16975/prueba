package package1;

import javax.swing.*;

public class Marco extends JFrame {
    public Marco() {
        setTitle("Red Code");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        PanelPrincipal panelPrincipal = new PanelPrincipal();
        setContentPane(panelPrincipal);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Marco());
    }
}
