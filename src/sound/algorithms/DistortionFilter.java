package sound.algorithms;

import sound.MusicPlayer;
import sound.Song;

import javax.sound.sampled.AudioFormat;
import java.io.File;
import java.net.MalformedURLException;

public class DistortionFilter extends SoundFilter {
    @Override
    void filter(byte[] data) {
        var multiplier = 1.0 / 0x7fff;
        var th = 1.0/3.0;
        for (var i = 0; i < data.length; i += 2) {
            var resultSample = 0.0;
            var inputSample = getSample(data, i);
            var normalizeInputSample = multiplier * inputSample;
            var absoluteNormalizeInputSample = Math.abs(inputSample);
            if (absoluteNormalizeInputSample < th) {
                resultSample = inputSample * 2 * multiplier;
            }
            else if (absoluteNormalizeInputSample < 2 * th) {
                var coeff = 2 - absoluteNormalizeInputSample * 3;
                var offset = (3 - coeff * coeff) / 3;
                resultSample = Math.signum(normalizeInputSample) * offset;
            }
            else if (absoluteNormalizeInputSample >= 2 * th) {
                resultSample = Math.signum(normalizeInputSample);
            }
            setSample(data, i, (short) (resultSample/multiplier));
        }
    }

    @Override
    Song getSongFromBytes(byte[] bytes, AudioFormat format, String songName) {
        return new Song(bytes, format, "Distortion " + songName);
    }
}
