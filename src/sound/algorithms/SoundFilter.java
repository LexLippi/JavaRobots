package sound.algorithms;

import sound.Song;
import javax.sound.sampled.AudioFormat;

abstract class SoundFilter implements ISoundFilter {
    abstract void filter(byte[] data);

    abstract Song getSongFromBytes(byte[] bytes, AudioFormat format, String songName);

    @Override
    public Song transformateSong(Song song) {
        var data = song.getSongData();
        filter(data);
        return getSongFromBytes(data, song.getSong().getFormat(), song.getSongName());
    }

    protected static short getSample(byte[] buffer, int position) {
        return (short) (((buffer[position + 1] & 0xff) << 8) | (buffer[position] & 0xff));
    }

    protected static void setSample(byte[] buffer, int position, short sample) {
        buffer[position] = (byte) (sample & 0xff);
        buffer[position + 1] = (byte) ((sample >> 8) & 0xff);
    }

    protected int getMinimumDegreeOfTwoMoreThanNumber(int number) {
        var minimumDegreeOfTwo = 1;
        while (minimumDegreeOfTwo < number) {
            minimumDegreeOfTwo *= 2;
        }
        return minimumDegreeOfTwo;
    }

}
