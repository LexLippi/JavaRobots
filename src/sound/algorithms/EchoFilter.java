package sound.algorithms;

import sound.Song;
import javax.sound.sampled.AudioFormat;
import java.io.Serializable;

public class EchoFilter extends SoundFilter implements Serializable {
    private short[] delayBuffer;
    private int delayBufferPos;
    private float decay;

    public EchoFilter(int numDelaySamples, float decay) {
        delayBuffer = new short[numDelaySamples];
        this.decay = decay;
    }

    @Override
    protected byte[] getFilteredData(byte[] data) {
        var result = new byte[data.length];
        for (var i = 0; i < data.length; i += sampleSize) {
            var oldSample = getSample(data, i);
            var newSample = (short) (oldSample + decay * delayBuffer[delayBufferPos]);
            setSample(result, i, newSample);
            delayBuffer[delayBufferPos] = newSample;
            delayBufferPos++;
            if (delayBufferPos == delayBuffer.length) {
                delayBufferPos = 0;
            }
        }
        return result;
    }

    @Override
    protected Song getSongFromBytes(byte[] bytes, AudioFormat format, String songName) {
        return new Song(bytes, format, "Echo " + songName);
    }
}
