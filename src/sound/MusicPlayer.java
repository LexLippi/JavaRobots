package sound;


import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

public class MusicPlayer extends Observable {
    private ConcurrentLinkedDeque<Song> songs = new ConcurrentLinkedDeque<>();
    public FloatControl volumeLevel;
    private Clip clip;
    private final Listener lineListener = new Listener();
    private long clipTimePosition = 0;
    private String currentSongName;
    private final Timer timer = new Timer("events generator", true);;
    private Boolean isPaused = false;

    public MusicPlayer(URL[] urls) {
        for (var url: urls) {
            songs.add(new Song(url));
        }
        currentSongName = songs.peek().getSongName();
        createNewClip();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                changeTime();
            }
        }, 0, 20);
    }

    public MusicPlayer(Song[] songs) {
        this.songs.addAll(Arrays.asList(songs));
        currentSongName = this.songs.peek().getSongName();
        createNewClip();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                changeTime();
            }
        }, 0, 20);
    }

    private void changeTime() {
        notifyObservers();
        setChanged();
    }

    public void deleteAllSongs() {
        clip.close();
        songs.clear();
        currentSongName = null;
    }

    public Song pollFirstSong() {
        return songs.poll();
    }

    public Song getPeekSong() {
        return songs.peek();
    }

    public void addNewSong(Song song) {
        songs.add(song);
        createNewClip();
    }

    public void addNewSongs(URL[] urls) {
        for (var url: urls) {
            songs.add(new Song(url));
        }
        currentSongName = songs.peek().getSongName();
        createNewClip();
    }

    public void setClipTimePosition(int position){
        clip.setMicrosecondPosition(TimeUnit.SECONDS.toMicros(position));
    }


    public void setMusicVolume(float volume){
        volumeLevel.setValue(volume);
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

    public Boolean getPaused() {
        return isPaused;
    }

    public float getVolumeLevel() {
        float volume = volumeLevel.getValue();
        float min = volumeLevel.getMinimum();
        float max = volumeLevel.getMaximum();
        return (volume-min)/(max-min);
    }

    public float getCurrentPosition(){
        clipTimePosition = TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondPosition());
        return clipTimePosition;
    }

    public float getCurrentSongLength() {
        return songs.peek().getSongLengthInSeconds();
    }

    public void getPreviousSong() {
        isPaused = true;
        setPreviousSongToPeek();
        play();
    }

    public void stop() {
        isPaused = true;
        clip.setFramePosition(0);
        clip.stop();
    }

    public void pause() {
        isPaused = true;
        clipTimePosition = clip.getMicrosecondPosition();
        clip.stop();
    }

    public void play() {
        isPaused = false;
        setCurrentSong(songs.peek().getSongName());
        clip.setMicrosecondPosition(clipTimePosition);
        clip.start();
    }

    public void loop(boolean status){
        if(status)
            clip.loop(clip.LOOP_CONTINUOUSLY);
        else
            clip.loop(0);
    }

    public void rewind(){

    }

    public void skip() {
        isPaused = false;
        clip.stop();
    }

    public void setPreviousSongToPeek() {
        var volume = getVolumeLevel();
        clip.close();
        var prevSong = songs.removeLast();
        prevSong.rewind();
        songs.addFirst(prevSong);
        createNewClip();
        setVolumeLevel(volume);
    }

    public void setNextSongToPeek() {
        var volume = getVolumeLevel();
        clip.close();
        var song = songs.removeFirst();
        song.rewind();
        songs.addLast(song);
        createNewClip();
        setVolumeLevel(volume);
    }

    public void setCurrentSong(String songName){
        currentSongName = songName;
        notifyObservers();
        setChanged();
    }

    public String getCurrentSongName(){
        return currentSongName;
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
            if (ev.getType() == LineEvent.Type.STOP && !isPaused) {
                setNextSongToPeek();
                play();
            }
        }
    }
}
