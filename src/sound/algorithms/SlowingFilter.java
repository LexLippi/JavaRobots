package sound.algorithms;

import sound.Song;
import javax.sound.sampled.AudioFormat;
import java.io.Serializable;

public class SlowingFilter extends SoundFilter implements Serializable {
    public int moderator;

    public SlowingFilter(int moderator) {
        this.moderator = moderator;
    }

    @Override
    byte[] getFilteredData(byte[] data) {
        var result = new byte[data.length * moderator];
        for (var i = 0; i < result.length; i += sampleSize * moderator) {
            setSample(result, i, getSample(data, i / moderator));
        }
        return result;
    }

    @Override
    Song getSongFromBytes(byte[] bytes, AudioFormat format, String songName) {
        return new Song(bytes, format, "Slowing " + songName);
    }
}
