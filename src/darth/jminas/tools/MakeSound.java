package darth.jminas.tools;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MakeSound extends Thread {

    private final String clipPath;
    private final boolean soundDisabled;

    public MakeSound(String clipPath, boolean soundDisabled) {
        this.clipPath = clipPath;
        this.soundDisabled = soundDisabled;
    }

    @Override
    public void run() {
        if (soundDisabled) return;
        try {
            final Clip sonido = AudioSystem.getClip();
            URL pathBoom = getClass().getResource(clipPath);
            sonido.open(AudioSystem.getAudioInputStream(pathBoom));
            sonido.start();
            sonido.loop(2);
            while (sonido.getMicrosecondPosition() <= sonido.getMicrosecondLength());
            sonido.stop();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | NullPointerException e) {
            new ErrorReporter().CreateLog("Error message: " + e.getMessage() + "\n" + e.getLocalizedMessage());
        }
    }
}
