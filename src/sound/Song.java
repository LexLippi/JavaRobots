package sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Song{
    private AudioInputStream stream;
    private float lengthInSeconds;
    private URL url;

    public Song (URL url) {
        this.url = url;
        rewind();
        System.out.println(url);
        System.out.println(url.getFile());
        lengthInSeconds = stream.getFrameLength() / stream.getFormat().getFrameRate();
    }

    public AudioInputStream getSong() {
        return stream;
    }

    public String getSongName() {
        var partsFilename = url.getFile().split("/");
        return partsFilename[partsFilename.length - 1].replace("%20", " ");
    }

    public void rewind() {
        try {
            stream = AudioSystem.getAudioInputStream(url);
        } catch (IOException | UnsupportedAudioFileException e) {
            System.out.println("File " + url.getFile() + " is not audio");
        }
    }

    public float getSongLengthInSeconds() {
        return lengthInSeconds;
    }
}

