package sound.algorithms;

import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import sound.MusicPlayer;
import sound.Song;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;

public class LowPassFilterAlgorithm implements ISoundAlgorithm {
    private static final int times = Double.SIZE / Byte.SIZE;
    private double lowPass;

    public LowPassFilterAlgorithm(double lowPass) {
        this.lowPass = lowPass;
    }

    @Override
    public Song transformateSong(Song song) {
        var data = songToDoubleArray(song);
        data = fourierLowPassFilter(data, song.getSong().getFormat().getFrameRate());
        var byteResult = toByteArray(data);
        return new Song(byteResult, song.getSong().getFormat(), "Low Pass " + song.getSongName());
    }

    private double[] fourierLowPassFilter(double[] data, double frequency) {
        var minimumDegreeOfTwo = 1;
        while (minimumDegreeOfTwo < data.length) {
            minimumDegreeOfTwo *= 2;
        }

        var padded = new double[minimumDegreeOfTwo];
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
            keepPoints[i] = frequencyDomain[i] < lowPass ? 2 : 0;
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

    private double[] songToDoubleArray(Song song) {
        try {
            var byteArray = song.getSong().readAllBytes();
            song.rewind();
            return toDoubleArray(byteArray);
        } catch (IOException e) {
            // TODO: handle this exception
            e.printStackTrace();
        }
        return null;
    }

    private double[] toDoubleArray(byte[] byteArray) {
        var doubles = new double[byteArray.length / times];
        for (var i = 0; i < doubles.length; ++i) {
            doubles[i] = ByteBuffer.wrap(byteArray, i * times, times).getDouble();
        }
        return doubles;
    }

    private byte[] toByteArray(double[] doubleArray) {
        var bytes = new byte[doubleArray.length * times];
        for (var i = 0; i < doubleArray.length; ++i) {
            ByteBuffer.wrap(bytes, i * times, times).putDouble(doubleArray[i]);
        }
        return bytes;
    }

    public static void main(String[] args) throws MalformedURLException{
        var song = new Song(new File("C:\\Users\\aleks\\Desktop\\Study\\Java\\Robots\\resources\\songs\\Frank Ocean - Chanel.wav").toURL());
        var newSong = new LowPassFilterAlgorithm(25000).transformateSong(song);
        var player = new MusicPlayer(new Song[] {newSong});
        player.play();
        while (true) {
        }
    }
}
