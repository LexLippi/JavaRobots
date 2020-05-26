package sound.algorithms;

import sound.Song;
import javax.sound.sampled.AudioFormat;

public class EchoFilter extends SoundFilter {
    private short[] delayBuffer;
    private int delayBufferPos;
    private float decay;

    public EchoFilter(int numDelaySamples, float decay) {
        delayBuffer = new short[numDelaySamples];
        this.decay = decay;
    }

    @Override
    protected void filter(byte[] data) {
        for (var i = 0; i < data.length - 1; i += 2) {
            var oldSample = getSample(data, i);
            var newSample = (short) (oldSample + decay * delayBuffer[delayBufferPos]);
            setSample(data, i, newSample);
            delayBuffer[delayBufferPos] = newSample;
            delayBufferPos++;
            if (delayBufferPos == delayBuffer.length) {
                delayBufferPos = 0;
            }
        }
    }

    @Override
    protected Song getSongFromBytes(byte[] bytes, AudioFormat format, String songName) {
        return new Song(bytes, format, "Echo " + songName);
    }
}
