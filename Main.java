package package1;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Marco marcoGeneral = new Marco(); // Assuming Marco is the main game JFrame
            Menu menu = new Menu(); // Assuming Menu is a utility class for game options
            AudioPlayer.ReproducirAudio(); // Assuming this plays background music

            Partida partida = new Partida(); // Create a new Partida (Game) instance
            PanelPartida panelPartida = new PanelPartida(partida); // Create the game UI panel

            marcoGeneral.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            marcoGeneral.add(panelPartida); // Add the PanelPartida to the JFrame
            marcoGeneral.pack();
            marcoGeneral.setVisible(true);
        });
    }
}