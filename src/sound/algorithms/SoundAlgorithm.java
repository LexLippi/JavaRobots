package sound.algorithms;

import sound.Song;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.nio.ByteBuffer;

abstract class SoundAlgorithm implements ISoundAlgorithm {
    private static final int times = Double.SIZE / Byte.SIZE;

    abstract double[] getAlgorithmResult(double[] data, double frequency);

    abstract Song getSongFromBytes(byte[] bytes, AudioFormat format, String songName);

    @Override
    public Song transformateSong(Song song) {
        var data = songToDoubleArray(song);
        data = getAlgorithmResult(data, song.getSong().getFormat().getFrameRate());
        var byteResult = toByteArray(data);
        return getSongFromBytes(byteResult, song.getSong().getFormat(), song.getSongName());
    }

    protected double[] songToDoubleArray(Song song) {
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

    protected double[] toDoubleArray(byte[] byteArray) {
        var doubles = new double[byteArray.length / times];
        for (var i = 0; i < doubles.length; ++i) {
            doubles[i] = ByteBuffer.wrap(byteArray, i * times, times).getDouble();
        }
        return doubles;
    }

    protected byte[] toByteArray(double[] doubleArray) {
        var bytes = new byte[doubleArray.length * times];
        for (var i = 0; i < doubleArray.length; ++i) {
            ByteBuffer.wrap(bytes, i * times, times).putDouble(doubleArray[i]);
        }
        return bytes;
    }

    protected int getMinimumDegreeOfTwoMoreThanNumber(int number) {
        var minimumDegreeOfTwo = 1;
        while (minimumDegreeOfTwo < number) {
            minimumDegreeOfTwo *= 2;
        }
        return minimumDegreeOfTwo;
    }

}
