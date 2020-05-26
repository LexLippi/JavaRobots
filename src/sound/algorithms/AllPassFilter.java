package sound.algorithms;

import sound.MusicPlayer;
import sound.Song;

import javax.sound.sampled.AudioFormat;
import java.io.File;
import java.net.MalformedURLException;

public class AllPassFilter extends SoundFilter{
    private float gainValue;
    private float previousSample;
    private float filteredPreviousSample;

    public AllPassFilter(float gainValue) {
        this.gainValue = gainValue;
    }

    @Override
    void filter(byte[] data) {
        for (var i = 0; i < data.length; i += 2) {
            var sample = getSample(data, i);
            filteredPreviousSample = (gainValue * (filteredPreviousSample - sample)) + previousSample;
            previousSample = sample;
            setSample(data, i, (short) filteredPreviousSample);
        }
    }

    @Override
    Song getSongFromBytes(byte[] bytes, AudioFormat format, String songName) {
        return new Song(bytes, format, "All pass " + songName);
    }
}
