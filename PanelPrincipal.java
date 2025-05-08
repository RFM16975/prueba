package package1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelPrincipal extends JPanel implements ActionListener {
    private Image fondo;
    private JButton botonJugar, botonReglas, botonInfo, botonCargar, botonSalir;
    private JTextArea areaDeTexto;
    private JScrollPane scrollTexto;

    public PanelPrincipal() {
        setLayout(new FlowLayout());

        fondo = new ImageIcon("fondo.jpg").getImage();

        // Botones y área de texto
        botonJugar = new JButton("Jugar");
        botonReglas = new JButton("Reglas");
        botonInfo = new JButton("Información");
        botonCargar = new JButton("Cargar partida");
        botonSalir = new JButton("Salir");

        areaDeTexto = new JTextArea(20, 50);
        areaDeTexto.setEditable(false);
        scrollTexto = new JScrollPane(areaDeTexto);

        // Listeners
        botonJugar.addActionListener(this);
        botonReglas.addActionListener(this);
        botonInfo.addActionListener(this);
        botonCargar.addActionListener(this);
        botonSalir.addActionListener(this);

        // Añadir componentes al panel
        add(botonJugar);
        add(botonReglas);
        add(botonInfo);
        add(botonCargar);
        add(botonSalir);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (scrollTexto.getParent() != null) {
            remove(scrollTexto);
            revalidate();
            repaint();
        }

        if (e.getSource() == botonJugar) {
            // Cambiar al panel de juego
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            Partida partida = new Partida(); // Crear instancia de partida
            topFrame.setContentPane(new PanelPartida(partida));
            topFrame.revalidate();
        }

        if (e.getSource() == botonReglas) {
            mostrarTextoDesdeArchivo("reglas.txt");
        }

        if (e.getSource() == botonInfo) {
            mostrarTextoDesdeArchivo("informacion.txt");
        }

        if (e.getSource() == botonSalir) {
            System.exit(0);
        }
    }

    private void mostrarTextoDesdeArchivo(String nombreArchivo) {
        if (scrollTexto.getParent() == null) {
            add(scrollTexto);
        }
        areaDeTexto.setText("");
        try {
            java.io.File archivo = new java.io.File(nombreArchivo);
            java.util.Scanner scanner = new java.util.Scanner(archivo);
            while (scanner.hasNextLine()) {
                areaDeTexto.append(scanner.nextLine() + "\n");
            }
            scanner.close();
        } catch (java.io.FileNotFoundException e) {
            areaDeTexto.setText("Error: Archivo no encontrado.");
            e.printStackTrace();
        }
        revalidate();
        repaint();
    }
}