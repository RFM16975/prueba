package package1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;

public class PanelPrincipal extends JPanel implements ActionListener {
    Menu menu = new Menu();
    private Image fondo;
    JButton botonJugar, botonReglas, botonInfo, botonCargar, botonSalir;
    JTextArea areaDeTexto;
    JScrollPane scrollTexto;
    boolean panelCambiado = false;

    PanelPrincipal() {
        setLayout(new FlowLayout());

        fondo = new ImageIcon("fondo.jpg").getImage();

        this.botonJugar = new JButton("Jugar");
        this.botonReglas = new JButton("Reglas");
        this.botonInfo = new JButton("Información");
        this.botonCargar = new JButton("Cargar partida");
        this.botonSalir = new JButton("Salir");

        this.areaDeTexto = new JTextArea(20, 50);
        this.areaDeTexto.setEditable(false);
        this.scrollTexto = new JScrollPane(areaDeTexto);

        botonJugar.addActionListener(this);
        botonReglas.addActionListener(this);
        botonInfo.addActionListener(this);
        botonCargar.addActionListener(this);
        botonSalir.addActionListener(this);

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

        if (e.getSource() == botonJugar && !panelCambiado) {
        	 JFrame marco = (JFrame) SwingUtilities.getWindowAncestor(this);
             marco.getContentPane().removeAll();
             marco.getContentPane().add(new PanelConsola());
             marco.revalidate();
             marco.repaint();
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
            File archivo = new File(nombreArchivo);
            Scanner scanner = new Scanner(archivo);
            while (scanner.hasNextLine()) {
                areaDeTexto.append(scanner.nextLine() + "\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            areaDeTexto.setText("Error: Archivo no encontrado.");
            e.printStackTrace();
        }
        revalidate();
        repaint();
    }

    
    public void mostrar() {
        JFrame marco = (JFrame) SwingUtilities.getWindowAncestor(this);
        marco.getContentPane().removeAll();
        marco.getContentPane().add(this);
        marco.revalidate();
        marco.repaint();
    }

}

