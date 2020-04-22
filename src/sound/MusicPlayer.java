package sound;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MusicPlayer {
    private ConcurrentLinkedQueue<Song> songs = new ConcurrentLinkedQueue<>();
    private Song currentSong;
    private float volumeLevel = 0.9f;

    public MusicPlayer(File[] files) {
        for (var file: files) {
            songs.add(new Song(file));
        }
        currentSong = songs.element();
    }

    public void setVolumeLevel(float volumeLevel) {
        this.volumeLevel = volumeLevel;
        currentSong.setVolume(volumeLevel);
    }

    public float getVolumeLevel() {
        return volumeLevel;
    }

    public void updateCurrentSong() {
        currentSong = songs.remove();
        songs.add(currentSong);
    }

    public long play() {
        currentSong.setVolume(volumeLevel);
        currentSong.play();
        return currentSong.getSongLength();
    }

    public void pause() {
        currentSong.stop();
    }

    public void skip() {
        currentSong.rewind();
        currentSong.stop();
        updateCurrentSong();
    }
}
