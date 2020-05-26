package sound.algorithms;

import sound.Song;
import javax.sound.sampled.AudioFormat;

public class FlangerFilter extends SoundFilter{
    private float wet;
    private float dry;
    private float[] flangerBuffer;

    public FlangerFilter(int sampleRate, int maxFlangerLength, float wet) {
        this.wet = wet;
        this.dry = 1 - wet;
        flangerBuffer = new float[sampleRate * maxFlangerLength];
    }

    @Override
    void filter(byte[] data) {
        for (var i = 2; i < data.length; i += 2) {
            var sample = getSample(data, i);
            flangerBuffer[(i / 2) % flangerBuffer.length] = sample;
            sample = (short) (dry * sample + flangerBuffer[(i - 1) % flangerBuffer.length] * wet);
            setSample(data, i, sample);
        }
    }

    @Override
    Song getSongFromBytes(byte[] bytes, AudioFormat format, String songName) {
        return new Song(bytes, format, "Flanger " + songName);
    }
}
