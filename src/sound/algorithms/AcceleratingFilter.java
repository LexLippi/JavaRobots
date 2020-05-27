package sound.algorithms;

import sound.Song;
import javax.sound.sampled.AudioFormat;
import java.io.Serializable;

public class AcceleratingFilter extends SoundFilter implements Serializable {
    private int accelerator;

    public AcceleratingFilter(int accelerator) {
        this.accelerator = accelerator;
    }

    @Override
    byte[] getFilteredData(byte[] data) {
        var result = new byte[data.length / accelerator];
        for (var i = 0; i < result.length; i += sampleSize) {
            setSample(result, i, getSample(data, accelerator * i));
        }
        return result;
    }

    @Override
    Song getSongFromBytes(byte[] bytes, AudioFormat format, String songName) {
        return new Song(bytes, format, "Accelerating " + songName);
    }

//    public static void main(String[] args) throws MalformedURLException, InterruptedException {
//        var filename = "C:\\Users\\aleks\\Desktop\\Study\\Java\\Robots\\resources\\songs\\TestSample.wav";
//        var song = new Song(new File(filename).toURL());
//        var filter = new AcceleratingFilter();
//        var newSong = filter.transformateSong(song);
//        var player = new MusicPlayer(new Song[] {newSong});
//        player.play();
//        Thread.sleep(50000);
//    }
}
