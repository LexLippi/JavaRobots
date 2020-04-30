package sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Song{
    private AudioInputStream stream;
    private float lengthInSeconds;
    private File file;

    public Song (File file) {
        this.file = file;
        rewind();
        lengthInSeconds = stream.getFrameLength() / stream.getFormat().getFrameRate();
    }

    public AudioInputStream getSong() {
        return stream;
    }

    public String getSongName(){
        return file.getName();
    }

    public void rewind() {
        try {
            stream = AudioSystem.getAudioInputStream(file);
        } catch (IOException | UnsupportedAudioFileException e) {
            System.out.println("File " + file.getName() + " is not audio");
        }
    }

    public float getSongLengthInSeconds() {
        return lengthInSeconds;
    }
}

