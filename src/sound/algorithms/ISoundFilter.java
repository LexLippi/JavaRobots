package sound.algorithms;

import sound.Song;

public interface ISoundFilter {
    Song transformateSong(Song song);
}
