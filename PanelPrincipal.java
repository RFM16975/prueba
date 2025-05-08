package package1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelPrincipal extends JPanel implements ActionListener {
    private Image fondo;
    private JButton botonJugar, botonReglas, botonInfo, botonSalir;
    private JFrame marcoGeneral; // Referencia al JFrame principal

    public PanelPrincipal(JFrame marcoGeneral) {
        this.marcoGeneral = marcoGeneral;

        setLayout(new FlowLayout());

        fondo = new ImageIcon("fondo.jpg").getImage();

        // Botones
        botonJugar = new JButton("Jugar");
        botonReglas = new JButton("Reglas");
        botonInfo = new JButton("Información");
        botonSalir = new JButton("Salir");

        // Listeners
        botonJugar.addActionListener(this);
        botonReglas.addActionListener(this);
        botonInfo.addActionListener(this);
        botonSalir.addActionListener(this);

        // Añadir botones al panel
        add(botonJugar);
        add(botonReglas);
        add(botonInfo);
        add(botonSalir);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonJugar) {
            // Cambiar al panel de juego
            PanelPartida panelPartida = new PanelPartida();
            marcoGeneral.setContentPane(panelPartida);
            marcoGeneral.revalidate();
        } else if (e.getSource() == botonReglas) {
            JOptionPane.showMessageDialog(this, "Aquí van las reglas del juego.");
        } else if (e.getSource() == botonInfo) {
            JOptionPane.showMessageDialog(this, "Información sobre el juego.");
        } else if (e.getSource() == botonSalir) {
            System.exit(0);
        }
    }
}