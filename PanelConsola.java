package package1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintStream;

public class PanelConsola extends JPanel {
    private JTextArea areaDeTexto;
    private JTextField campoEntrada;

    public PanelConsola() {
        setLayout(new BorderLayout());

        // Área de texto para la salida de la consola
        areaDeTexto = new JTextArea(20, 50);
        areaDeTexto.setEditable(false);
        JScrollPane scrollTexto = new JScrollPane(areaDeTexto);

        // Campo de texto para enviar información
        campoEntrada = new JTextField();
        campoEntrada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String entrada = campoEntrada.getText();
                System.out.println(entrada); // Enviar a la consola real
                campoEntrada.setText("");
            }
        });

        add(scrollTexto, BorderLayout.CENTER);
        add(campoEntrada, BorderLayout.SOUTH);

        // Redirigir la salida de consola a JTextArea
        redirigirSalidaConsola();
    }

    private void redirigirSalidaConsola() {
        OutputStream salida = new OutputStream() {
            @Override
            public void write(int b) {
                areaDeTexto.append(String.valueOf((char) b));
                areaDeTexto.setCaretPosition(areaDeTexto.getDocument().getLength());
            }
        };
        System.setOut(new PrintStream(salida, true));
        System.setErr(new PrintStream(salida, true)); // También redirige System.err
    }
}