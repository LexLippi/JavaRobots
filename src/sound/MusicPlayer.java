package sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MusicPlayer {
    private ConcurrentLinkedQueue<Song> songs = new ConcurrentLinkedQueue<>();
    private FloatControl volumeLevel;
    private Clip clip;
    private final Listener lineListener = new Listener();

    public MusicPlayer(File[] files){
        for (var file: files) {
            songs.add(new Song(file));
        }
        createNewClip();
    }

    public void setVolumeLevel(float volume) {
        if (volume < 0) {
            volume = 0;
        }
        if (volume > 1) {
            volume = 1;
        }
        var min = volumeLevel.getMinimum();
        var max = volumeLevel.getMaximum();
        volumeLevel.setValue((max-min)*volume+min);
    }

    public float getVolumeLevel() {
        float volume = volumeLevel.getValue();
        float min = volumeLevel.getMinimum();
        float max = volumeLevel.getMaximum();
        return (volume-min)/(max-min);
    }

    public float getCurrentSongLength() {
        return songs.peek().getSongLengthInSeconds();
    }

    public void pause() {
        clip.stop();
    }

    public void play() {
        clip.start();
    }

    public void skip() {
        clip.stop();
        updateCurrentSong();
        play();
    }

    public void updateCurrentSong() {
        clip.close();
        var song = songs.remove();
        var volume = getVolumeLevel();
        song.rewind();
        createNewClip();
        setVolumeLevel(volume);
        songs.add(song);
    }

    private void createNewClip() {
        try {
            clip = AudioSystem.getClip();
            clip.open(songs.peek().getSong());
            clip.addLineListener(lineListener);
            volumeLevel = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    private class Listener implements LineListener {
        public void update(LineEvent ev) {
            if (ev.getType() == LineEvent.Type.STOP) {
                updateCurrentSong();
                play();
            }
        }
    }
}
