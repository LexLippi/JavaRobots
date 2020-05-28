package sound;

import game.FilterName;
import sound.algorithms.*;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class Equaliser implements Serializable {
    private final ConcurrentHashMap<FilterName, ISoundFilter> nameToFilter = new ConcurrentHashMap<>();
    private FilterMode filterMode;
    private transient MusicPlayer player;
    private transient Song startSong;

    public Equaliser(FilterMode filterMode, MusicPlayer player) {
        this.filterMode = filterMode;
        this.player = player;
        createNameToFilterMap();
        startSong = player.getPeekSong();
    }

    private void createNameToFilterMap() {
        nameToFilter.put(FilterName.ACCELERATING, new AcceleratingFilter(2));
        nameToFilter.put(FilterName.SLOWING, new SlowingFilter(2));
        nameToFilter.put(FilterName.ECHO, new EchoFilter(11025,.6f));
        nameToFilter.put(FilterName.ALLPASS, new AllPassFilter(.8f));
        nameToFilter.put(FilterName.DISTORTION, new DistortionFilter(1f/3f, 1f/0x7fff));
        nameToFilter.put(FilterName.FLANGER, new FlangerFilter(100, .6f));
        nameToFilter.put(FilterName.HIGHPASS,
                new LevelPassFilter(20000, LevelPassFilter.PassType.HIGHPASS, 1f));
        nameToFilter.put(FilterName.LOWPASS,
                new LevelPassFilter(20000, LevelPassFilter.PassType.LOWPASS, 1f));
    }

    public void transformateCurrentSong(FilterName filterName) {
        player.pause();
        if (!nameToFilter.containsKey(filterName)) {
            player.play();
            return;
        }
        var filter = nameToFilter.get(filterName);
        var currentSong = player.pollFirstSong();
        var song = filterMode == FilterMode.CONSISTENT ? currentSong : startSong;
        var newSong = filter.transformateSong(song);
        player.addNewSong(newSong);
        player.play();
    }

    public void setMetadata(MusicPlayer player) {
        this.player = player;
        startSong = this.player.getPeekSong();
    }
}
