package sound.algorithms;

import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import sound.Song;
import javax.sound.sampled.AudioFormat;

public class HighPassFilterAlgorithm extends SoundAlgorithm {
    private double highPass;

    public HighPassFilterAlgorithm(double highPass) {
        this.highPass = highPass;
    }

    @Override
    double[] getAlgorithmResult(double[] data, double frequency) {
        var padded = new double[getMinimumDegreeOfTwoMoreThanNumber(data.length)];
        System.arraycopy(data, 0, padded, 0, data.length);

        var transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        var fourierTransform = transformer.transform(padded, TransformType.FORWARD);

        var frequencyDomain = new double[fourierTransform.length];
        for (var i = 0; i < frequencyDomain.length; ++i) {
            frequencyDomain[i] = frequency * i / (double) fourierTransform.length;
        }

        var keepPoints = new double[frequencyDomain.length];
        keepPoints[0] = 1;
        for (var i = 1; i < frequencyDomain.length; ++i) {
            keepPoints[i] = frequencyDomain[i] > highPass ? 2 : 0;
        }

        for (var i = 0; i < fourierTransform.length; ++i) {
            fourierTransform[i].multiply(keepPoints[i]);
        }

        var reverseFourier = transformer.transform(fourierTransform, TransformType.INVERSE);

        var result = new double[data.length];
        for (var i = 0; i < result.length; ++i) {
            result[i] = reverseFourier[i].getReal();
        }
        return result;
    }

    @Override
    Song getSongFromBytes(byte[] bytes, AudioFormat format, String songName) {
        return new Song(bytes, format, "High Pass " +  songName);
    }
}
