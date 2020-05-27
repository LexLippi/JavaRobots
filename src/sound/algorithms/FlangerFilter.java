package sound.algorithms;

import sound.Song;
import javax.sound.sampled.AudioFormat;
import java.io.Serializable;

public class FlangerFilter extends SoundFilter implements Serializable {
    private float wet;
    private float dry;
    private float[] flangerBuffer;

    public FlangerFilter(int maxFlangerLength, float wet) {
        this.wet = wet;
        this.dry = 1 - wet;
        flangerBuffer = new float[sampleRate * maxFlangerLength];
    }

    @Override
    byte[] getFilteredData(byte[] data) {
        var result = new byte[data.length];
        for (var i = 2; i < data.length; i += sampleSize) {
            var sample = getSample(data, i);
            flangerBuffer[(i / 2) % flangerBuffer.length] = sample;
            sample = (short) (dry * sample + flangerBuffer[(i - 1) % flangerBuffer.length] * wet);
            setSample(result, i, sample);
        }
        return result;
    }

    @Override
    Song getSongFromBytes(byte[] bytes, AudioFormat format, String songName) {
        return new Song(bytes, format, "Flanger " + songName);
    }
}
