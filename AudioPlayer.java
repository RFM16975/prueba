package package1;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {

	public static void ReproducirAudio() {

	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("audio.wav"));
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	
}
}
