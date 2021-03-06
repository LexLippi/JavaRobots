package sound.algorithms;

import sound.Song;
import javax.sound.sampled.AudioFormat;
import java.io.Serializable;

public class AllPassFilter extends SoundFilter implements Serializable {
    private float gainValue;
    private float previousSample;
    private float filteredPreviousSample;

    public AllPassFilter(float gainValue) {
        this.gainValue = gainValue;
    }

    @Override
    byte[] getFilteredData(byte[] data) {
        var result = new byte[data.length];
        for (var i = 0; i < data.length; i += sampleSize) {
            var sample = getSample(data, i);
            filteredPreviousSample = (gainValue * (filteredPreviousSample - sample)) + previousSample;
            previousSample = sample;
            setSample(result, i, (short) filteredPreviousSample);
        }
        return result;
    }

    @Override
    Song getSongFromBytes(byte[] bytes, AudioFormat format, String songName) {
        return new Song(bytes, format, "All pass " + songName);
    }
}
