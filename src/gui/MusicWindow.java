/*
 * Created by JFormDesigner on Thu Apr 30 00:30:04 YEKT 2020
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.event.*;

import sound.MusicPlayer;


public class MusicWindow extends JInternalFrame implements Serializable, Reopenable, Musical, Multilangual, Observer
{
    private MusicWindow musicWindow = this;
    private ResourceBundle m_bundle;
    private transient MusicPlayer musicPlayer;
    private URL[] songs;
    private boolean loopStatus;
    private boolean muteStatus = false;
    private float unmutedVolume;
    private ClosingHandler closingHandler;

    public MusicWindow(URL[] songsUrls, ResourceBundle bundle) {
        m_bundle = bundle;
        this.songs = songsUrls;
        musicPlayer = new MusicPlayer(songs);
        this.musicPlayer.addObserver(this);
        initComponents();
        closingHandler = new ClosingHandler();
        this.setTitle(m_bundle.getString("musicPlayerTitleKey"));
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                closingHandler.handleClosing(musicWindow, e, bundle, ClosingHandler.ClosingType.MUSIC);
            }
        });
    }

    public void stopMusic(){
        musicPlayer.pause();
    }

    @Override
    public void setMetadata(ResourceBundle bundle) {
        m_bundle = bundle;
        musicPlayer = new MusicPlayer(songs);
        musicPlayer.addObserver(this);
        initComponents();
        this.setTitle(m_bundle.getString("musicPlayerTitleKey"));
    }

    private void playButtonClicked(MouseEvent e) {
        musicPlayer.play();
    }

    private void pauseBttnClicked(MouseEvent e) {
        musicPlayer.pause();
     }

    private void stopBttnClicked(MouseEvent e) {
        musicPlayer.stop();
     }

    private void skipBttnClicked(MouseEvent e) {
        musicPlayer.skip();
     }


    public void changeLanguage(ResourceBundle nextBundle){
        m_bundle = nextBundle;
        this.setTitle(nextBundle.getString("musicPlayerTitleKey"));
        nowPlaying.setText(m_bundle.getString("getCurrentSong") + " " + musicPlayer.getCurrentSongName());
    }

    private void loopBttnMouseClicked(MouseEvent e) {
        musicPlayer.loop(loopStatus);
        if(loopStatus)
            loopBttn.setIcon(new ImageIcon(getClass().getResource("/playerIcons/blackbig.png")));
        else
            loopBttn.setIcon(new ImageIcon(getClass().getResource("/playerIcons/replay.png")));
        loopStatus = !loopStatus;
    }

    @Override
    public void update(Observable observable, Object o) {
        nowPlaying.setText(m_bundle.getString("getCurrentSong") + " " + musicPlayer.getCurrentSongName());
        songLength.setText(String.format("%.2f", (int)((musicPlayer.getCurrentSongLength()) / 60) + ((musicPlayer.getCurrentSongLength()) % 60) / 100).replace(',', ':'));
        currentPosition.setText(String.format("%.2f", (int)((musicPlayer.getCurrentPosition()) / 60) + ((musicPlayer.getCurrentPosition()) % 60) / 100).replace(',', ':'));
    }

    private void volumeChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        musicPlayer.setMusicVolume(source.getValue()/100.0f);
    }

    private void volumeBttnClicked(MouseEvent e) {
        if(muteStatus) {
            musicPlayer.setMusicVolume(unmutedVolume);
            volumeBttn.setIcon(new ImageIcon(getClass().getResource("/playerIcons/volume.png")));
        }
        else {
            unmutedVolume = musicPlayer.getVolumeLevel();
            volumeBttn.setIcon(new ImageIcon(getClass().getResource("/playerIcons/muted.png")));
            musicPlayer.setVolumeLevel(0.0f);
        }
        muteStatus = !muteStatus;
    }

    private void rewindBttnClicked(MouseEvent e) {
        musicPlayer.getPreviousSong();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        currentSong = new JPanel();
        nowPlaying = new JLabel();
        currentPosition = new JLabel();
        songLength = new JLabel();
        currentPositionSlider = new JSlider((int) musicPlayer.volumeLevel.getMinimum()*50,(int) musicPlayer.volumeLevel.getMaximum()*100,(int) musicPlayer.volumeLevel.getValue()*100);
        panel1 = new JPanel();
        stopBttn = new JLabel();
        playBttn = new JLabel();
        pauseBttn = new JLabel();
        skipBttn = new JLabel();
        rewindBttn = new JLabel();
        loopBttn = new JLabel();
        volumeSlider = new JSlider((int) musicPlayer.volumeLevel.getMinimum()*50,(int) musicPlayer.volumeLevel.getMaximum()*100,(int) musicPlayer.volumeLevel.getValue()*100);
        volumeBttn = new JLabel();

        //======== this ========
        setVisible(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Music Player");
        setClosable(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== currentSong ========
        {
            currentSong.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing.
            border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing. border. TitledBorder. CENTER
            , javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("D\u0069alog" ,java .awt .Font
            .BOLD ,12 ), java. awt. Color. red) ,currentSong. getBorder( )) ); currentSong. addPropertyChangeListener (
            new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062order"
            .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
            currentSong.setLayout(null);

            //---- nowPlaying ----
            nowPlaying.setText("PLAYING");
            nowPlaying.setHorizontalAlignment(SwingConstants.CENTER);
            currentSong.add(nowPlaying);
            nowPlaying.setBounds(0, 0, 455, 45);

            //---- currentPosition ----
            currentPosition.setText("0:00");
            currentPosition.setHorizontalAlignment(SwingConstants.CENTER);
            currentSong.add(currentPosition);
            currentPosition.setBounds(5, 45, 60, 40);

            //---- songLength ----
            songLength.setText("-0:00");
            songLength.setHorizontalAlignment(SwingConstants.CENTER);
            currentSong.add(songLength);
            songLength.setBounds(390, 45, 60, 40);

            //---- currentPositionSlider ----
            currentPositionSlider.setMaximum(101);
            currentPositionSlider.setValue(0);
            currentSong.add(currentPositionSlider);
            currentPositionSlider.setBounds(65, 45, 325, 40);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < currentSong.getComponentCount(); i++) {
                    Rectangle bounds = currentSong.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = currentSong.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                currentSong.setMinimumSize(preferredSize);
                currentSong.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(currentSong);
        currentSong.setBounds(0, 0, 455, 85);

        //======== panel1 ========
        {
            panel1.setLayout(null);

            //---- stopBttn ----
            stopBttn.setIcon(new ImageIcon(getClass().getResource("/playerIcons/stop32.png")));
            stopBttn.setHorizontalAlignment(SwingConstants.CENTER);
            stopBttn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            stopBttn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    stopBttnClicked(e);
                }
            });
            panel1.add(stopBttn);
            stopBttn.setBounds(242, 15, 32, 32);

            //---- playBttn ----
            playBttn.setIcon(new ImageIcon(getClass().getResource("/playerIcons/play.png")));
            playBttn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            playBttn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    playButtonClicked(e);
                }
            });
            panel1.add(playBttn);
            playBttn.setBounds(165, 0, 64, 64);

            //---- pauseBttn ----
            pauseBttn.setIcon(new ImageIcon(getClass().getResource("/playerIcons/pause.png")));
            pauseBttn.setHorizontalAlignment(SwingConstants.CENTER);
            pauseBttn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            pauseBttn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    pauseBttnClicked(e);
                }
            });
            panel1.add(pauseBttn);
            pauseBttn.setBounds(120, 15, 32, 32);

            //---- skipBttn ----
            skipBttn.setHorizontalAlignment(SwingConstants.CENTER);
            skipBttn.setIcon(new ImageIcon(getClass().getResource("/playerIcons/skip.png")));
            skipBttn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            skipBttn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    skipBttnClicked(e);
                }
            });
            panel1.add(skipBttn);
            skipBttn.setBounds(287, 15, 32, 32);

            //---- rewindBttn ----
            rewindBttn.setIcon(new ImageIcon(getClass().getResource("/playerIcons/rewind.png")));
            rewindBttn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            rewindBttn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    rewindBttnClicked(e);
                }
            });
            panel1.add(rewindBttn);
            rewindBttn.setBounds(75, 15, 32, 32);

            //---- loopBttn ----
            loopBttn.setIcon(new ImageIcon(getClass().getResource("/playerIcons/replayblack.png")));
            loopBttn.setHorizontalAlignment(SwingConstants.CENTER);
            loopBttn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            loopBttn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    loopBttnMouseClicked(e);
                }
            });
            panel1.add(loopBttn);
            loopBttn.setBounds(5, 0, 64, 64);

            //---- volumeSlider ----
            volumeSlider.setFont(volumeSlider.getFont().deriveFont(volumeSlider.getFont().getStyle() | Font.ITALIC));
            volumeSlider.setForeground(Color.white);
            volumeSlider.setMinorTickSpacing(5);
            volumeSlider.setMajorTickSpacing(10);
            volumeSlider.addChangeListener(e -> volumeChanged(e));
            panel1.add(volumeSlider);
            volumeSlider.setBounds(370, 15, 80, 32);

            //---- volumeBttn ----
            volumeBttn.setIcon(new ImageIcon(getClass().getResource("/playerIcons/volume.png")));
            volumeBttn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            volumeBttn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    volumeBttnClicked(e);
                }
            });
            panel1.add(volumeBttn);
            volumeBttn.setBounds(332, 15, 32, 32);
        }
        contentPane.add(panel1);
        panel1.setBounds(0, 85, 455, 65);

        contentPane.setPreferredSize(new Dimension(470, 185));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel currentSong;
    private JLabel nowPlaying;
    private JLabel currentPosition;
    private JLabel songLength;
    private JSlider currentPositionSlider;
    private JPanel panel1;
    private JLabel stopBttn;
    private JLabel playBttn;
    private JLabel pauseBttn;
    private JLabel skipBttn;
    private JLabel rewindBttn;
    private JLabel loopBttn;
    private JSlider volumeSlider;
    private JLabel volumeBttn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
