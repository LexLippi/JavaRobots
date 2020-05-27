package sound.algorithms;

import sound.Song;
import javax.sound.sampled.AudioFormat;

public class DistortionFilter extends SoundFilter {
    private float multiplier;
    private float resonance;

    public DistortionFilter(float multiplier, float resonance) {
        this.multiplier = multiplier;
        this.resonance = resonance;
    }

    @Override
    byte[] getFilteredData(byte[] data) {
        var result = new byte[data.length];
        for (var i = 0; i < data.length; i += 2) {
            var resultSample = 0.0;
            var inputSample = getSample(data, i);
            var normalizeInputSample = multiplier * inputSample;
            var absoluteNormalizeInputSample = Math.abs(inputSample);
            if (absoluteNormalizeInputSample < resonance) {
                resultSample = inputSample * 2 * multiplier;
            }
            else if (absoluteNormalizeInputSample < 2 * resonance) {
                var coeff = 2 - absoluteNormalizeInputSample * 3;
                var offset = (3 - coeff * coeff) / 3;
                resultSample = Math.signum(normalizeInputSample) * offset;
            }
            else if (absoluteNormalizeInputSample >= 2 * resonance) {
                resultSample = Math.signum(normalizeInputSample);
            }
            setSample(result, i, (short) (resultSample/multiplier));
        }
        return result;
    }

    @Override
    Song getSongFromBytes(byte[] bytes, AudioFormat format, String songName) {
        return new Song(bytes, format, "Distortion " + songName);
    }
}
