package sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Song implements AutoCloseable{
    private Clip clip;
    private FloatControl volumeControl;
    private boolean isPlaying;
    private AudioInputStream stream;

    public Song (File file) {
        try {
            stream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(stream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clip.addLineListener(new Listener());
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.out.println("File " + file.getName() + " is not audio");
        }
    }

    public void play() {
        if (!isPlaying) {
            clip.start();
            isPlaying = true;
        }
    }

    public void rewind() {
        clip.setFramePosition(0);
    }

    public long getSongLength() {
        return clip.getMicrosecondLength() / 1000;
    }

    public void stop() {
        if (isPlaying) {
            clip.stop();
        }
    }

    public void setVolume(float newVolume) {
        if (newVolume < 0) {
            newVolume = 0;
        }
        if (newVolume > 1) {
            newVolume = 1;
        }
        var min = volumeControl.getMinimum();
        var max = volumeControl.getMaximum();
        volumeControl.setValue((max-min)*newVolume+min);
    }

    public void close() {
        try {
            clip.close();
            stream.close();
        } catch (IOException exc) {
            // pass
        }
    }

    private class Listener implements LineListener {
        public void update(LineEvent ev) {
            if (ev.getType() == LineEvent.Type.STOP) {
                isPlaying = false;
                synchronized(clip) {
                    clip.notify();
                }
            }
        }
    }
}
