package sound;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;

public class Song{
    private AudioInputStream stream;
    private String songName;
    private float lengthInSeconds;
    private byte[] songData;
    private AudioFormat songFormat;

    public Song (URL url) {
        try {
            var stream = createStreamFromUrl(url);
            songFormat = stream.getFormat();
            songName = createSongNameFromUrl(url);
            getSamples(stream);
            rewind();
        } catch (NullPointerException e) {
            System.out.println("File " + url.getFile() + " is not audio");;
        }
    }

    public byte[] getSongData() {
        return songData;
    }

    public Song(byte[] songData, AudioFormat songFormat, String songName) {
        this.songData = songData;
        this.songName = songName;
        this.songFormat = songFormat;
        rewind();
        lengthInSeconds = stream.getFrameLength() / stream.getFormat().getFrameRate();
    }

    public AudioInputStream getSong() {
        return stream;
    }

    public String getSongName() {
        return songName;
    }

    public AudioFormat getSongFormat() {
        return songFormat;
    }

    public void rewind() {
        stream = new AudioInputStream(new ByteArrayInputStream(songData), songFormat, songData.length);
    }

    public float getSongLengthInSeconds() {
        return lengthInSeconds;
    }

    private String createSongNameFromUrl(URL url) {
        var partsFilename = url.getFile().split("/");
        return partsFilename[partsFilename.length - 1].replace("%20", " ");
    }

    private AudioInputStream createStreamFromUrl(URL url) {
        try {
            return AudioSystem.getAudioInputStream(url);
        } catch (IOException | UnsupportedAudioFileException e) {
            System.out.println("File " + url.getFile() + " is not audio");
        }
        return null;
    }

    private void getSamples(AudioInputStream audioStream) {
        var length = (int) (audioStream.getFrameLength() * songFormat.getFrameSize());
        songData = new byte[length];
        try (var is = new DataInputStream(audioStream)) {
            is.readFully(songData);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

