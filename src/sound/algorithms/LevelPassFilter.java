package sound.algorithms;

import sound.Song;
import javax.sound.sampled.AudioFormat;

public class LevelPassFilter extends SoundFilter {
    private float smoothCoeff;
    private float[] smoothedCoeffForInput = new float[2];
    private float[] smoothedCoeffForOutput = new float[2];
    private float[] inputHistory = new float[2];
    private float[] outputHistory = new float[2];

    public LevelPassFilter(float frequency, int sampleRate, PassType passType, float resonance)
    {
        var tan = Math.tan(Math.PI * frequency / sampleRate);
        var filterCoeff = passType == PassType.LOWPASS ? (float)(1.0 / tan) : (float)tan;
        smoothCoeff = 1.0f / (1.0f + resonance * filterCoeff + filterCoeff * filterCoeff);
        smoothedCoeffForInput[0] = 2f * smoothCoeff;
        smoothedCoeffForInput[0] = passType == PassType.LOWPASS ? smoothedCoeffForInput[0] : -smoothedCoeffForInput[0];
        smoothedCoeffForInput[1] = smoothCoeff;
        smoothedCoeffForOutput[0] = 2.0f * (1.0f - filterCoeff * filterCoeff) * smoothCoeff;
        smoothedCoeffForOutput[1] = (1.0f - resonance * filterCoeff + filterCoeff * filterCoeff) * smoothCoeff;
    }

    @Override
    void filter(byte[] data) {
        for (var i = 0; i < data.length; i += 2) {
            var oldSample = getSample(data, i);
            var newSample = updateSample(oldSample);
            setSample(data, i, newSample);
        }
    }

    @Override
    protected Song getSongFromBytes(byte[] bytes, AudioFormat format, String songName) {
        return new Song(bytes, format, "Filter " + songName);
    }

    public enum PassType
    {
        HIGHPASS,
        LOWPASS,
    }

    public short updateSample(short newInput)
    {
        var newOutput = smoothCoeff * newInput;
        for (var i = 0; i < smoothedCoeffForInput.length; ++i) {
            newOutput += smoothedCoeffForInput[i] * inputHistory[i] - smoothedCoeffForOutput[i] * outputHistory[i];
        }
        inputHistory[1] = inputHistory[0];
        inputHistory[0] = newInput;
        outputHistory[1] = outputHistory[0];
        outputHistory[0] = newOutput;
        return (short) outputHistory[0];
    }
}
