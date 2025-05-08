package package1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PanelPartida extends JPanel implements ActionListener {
    private JTextArea areaDeTexto; // Área para mostrar mensajes
    private JScrollPane scrollTexto;
    private JButton botonAtacar, botonDefender, botonAyuda, botonSiguienteTurno, botonAgregarEquipo, botonIniciarJuego;
    private JTextField campoEquipo; // Campo para ingresar el nombre del equipo
    private JComboBox<String> listaEquipos; // Desplegable para seleccionar equipos
    private List<String> equipos; // Lista de equipos
    private int indiceActual = 0; // Índice del equipo actual
    private int rondaActual = 1; // Número de ronda
    private Partida partida; // Instancia de la clase Partida

    public PanelPartida() {
        equipos = new ArrayList<>();
        partida = new Partida(); // Crear instancia de Partida

        setLayout(new BorderLayout());

        // Área de texto para mostrar mensajes del juego
        areaDeTexto = new JTextArea(20, 50);
        areaDeTexto.setEditable(false);
        scrollTexto = new JScrollPane(areaDeTexto);

        // Botones para las acciones
        botonAtacar = new JButton("Atacar");
        botonDefender = new JButton("Defender");
        botonAyuda = new JButton("Pedir Ayuda");
        botonSiguienteTurno = new JButton("Siguiente Turno");
        botonAgregarEquipo = new JButton("Agregar Equipo");
        botonIniciarJuego = new JButton("Iniciar Juego");

        // Campo de texto y desplegable
        campoEquipo = new JTextField(20);
        listaEquipos = new JComboBox<>();

        // Panel para botones y campos de texto
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Nombre del Equipo:"));
        inputPanel.add(campoEquipo);
        inputPanel.add(botonAgregarEquipo);
        inputPanel.add(botonIniciarJuego);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(listaEquipos);
        buttonPanel.add(botonAtacar);
        buttonPanel.add(botonDefender);
        buttonPanel.add(botonAyuda);
        buttonPanel.add(botonSiguienteTurno);

        // Agregar componentes al panel principal
        add(scrollTexto, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Agregar listeners a los botones
        botonAtacar.addActionListener(this);
        botonDefender.addActionListener(this);
        botonAyuda.addActionListener(this);
        botonSiguienteTurno.addActionListener(this);
        botonAgregarEquipo.addActionListener(this);
        botonIniciarJuego.addActionListener(this);

        // Mensaje inicial
        mostrarMensaje("Bienvenido a COLD WAR. Por favor, agrega equipos para comenzar.");
    }

    /**
     * Muestra un mensaje en el área de texto.
     */
    private void mostrarMensaje(String mensaje) {
        areaDeTexto.append(mensaje + "\n");
        areaDeTexto.setCaretPosition(areaDeTexto.getDocument().getLength());
    }

    /**
     * Actualiza el turno al siguiente equipo o inicia una nueva ronda.
     */
    private void actualizarTurno() {
        if (equipos.isEmpty()) {
            mostrarMensaje("No hay equipos. Por favor, agrega equipos para continuar.");
            return;
        }

        if (indiceActual < equipos.size()) {
            String equipoActual = equipos.get(indiceActual);
            mostrarMensaje("Turno de " + equipoActual + ". ¿Qué acción deseas realizar?");
            listaEquipos.setSelectedItem(equipoActual);
        } else {
            mostrarMensaje("Fin de la ronda " + rondaActual + ". Iniciando la siguiente ronda...");
            indiceActual = 0;
            rondaActual++;
            mostrarMensaje("Ronda " + rondaActual + " comenzada.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonAgregarEquipo) {
            String nombreEquipo = campoEquipo.getText().trim();
            if (!nombreEquipo.isEmpty()) {
                equipos.add(nombreEquipo);
                listaEquipos.addItem(nombreEquipo);
                mostrarMensaje("Equipo " + nombreEquipo + " agregado.");
                campoEquipo.setText("");
            } else {
                mostrarMensaje("Por favor, ingresa un nombre válido para el equipo.");
            }
        } else if (e.getSource() == botonIniciarJuego) {
            if (equipos.isEmpty()) {
                mostrarMensaje("No puedes iniciar el juego sin equipos. Agrega al menos un equipo.");
            } else {
                mostrarMensaje("El juego ha comenzado. ¡Buena suerte!");
                partida.iniciarPartida(equipos.size(), new MenuSwingAdapter(this)); // Usar un adaptador para conectar con Partida
                actualizarTurno();
            }
        } else if (e.getSource() == botonAtacar) {
            if (!equipos.isEmpty() && indiceActual < equipos.size()) {
                String equipoActual = equipos.get(indiceActual);
                mostrarMensaje(equipoActual + " ha decidido atacar.");
                // Aquí podrías llamar al método atacar de Partida
            } else {
                mostrarMensaje("No hay turno activo. Por favor, inicia un turno.");
            }
        } else if (e.getSource() == botonDefender) {
            if (!equipos.isEmpty() && indiceActual < equipos.size()) {
                String equipoActual = equipos.get(indiceActual);
                mostrarMensaje(equipoActual + " ha decidido defender.");
                // Aquí podrías llamar al método defender de Partida
            } else {
                mostrarMensaje("No hay turno activo. Por favor, inicia un turno.");
            }
        } else if (e.getSource() == botonAyuda) {
            if (!equipos.isEmpty() && indiceActual < equipos.size()) {
                String equipoActual = equipos.get(indiceActual);
                mostrarMensaje(equipoActual + " ha pedido ayuda.");
                // Aquí podrías llamar al método pedir ayuda de Partida
            } else {
                mostrarMensaje("No hay turno activo. Por favor, inicia un turno.");
            }
        } else if (e.getSource() == botonSiguienteTurno) {
            if (!equipos.isEmpty()) {
                indiceActual++;
                actualizarTurno();
            } else {
                mostrarMensaje("No hay equipos. Por favor, agrega equipos para continuar.");
            }
        }
    }
}
