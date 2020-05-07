/*
 * Created by JFormDesigner on Thu Apr 30 00:30:04 YEKT 2020
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javax.swing.*;

import sound.MusicPlayer;


public class MusicWindow extends JInternalFrame implements Serializable, Reopenable, Observer
{
    private ResourceBundle m_bundle;
    private transient MusicPlayer musicPlayer;
    private URL[] songs;
    private boolean loopStatus;
    public MusicWindow(URL[] songsUrls, ResourceBundle bundle) {
        m_bundle = bundle;
        this.songs = songsUrls;
        musicPlayer = new MusicPlayer(songs);
        musicPlayer.addObserver(this);
        initComponents();
        this.setTitle(m_bundle.getString("musicPlayerTitleKey"));
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        currentSong = new JPanel();
        nowPlaying = new JLabel();
        panel1 = new JPanel();
        stopBttn = new JLabel();
        playBttn = new JLabel();
        pauseBttn = new JLabel();
        skipBttn = new JLabel();
        rewindBttn = new JLabel();
        loopBttn = new JLabel();

        //======== this ========
        setVisible(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Music Player");
        setClosable(true);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== currentSong ========
        {
            currentSong.setLayout(null);
            
            //---- nowPlaying ----
            nowPlaying.setText("PLAYING");
            nowPlaying.setHorizontalAlignment(SwingConstants.CENTER);
            currentSong.add(nowPlaying);
            nowPlaying.setBounds(5, 0, 385, 50);

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
        currentSong.setBounds(-5, 0, 395, 50);

        //======== panel1 ========
        {
            panel1.setLayout(null);

            //---- stopBttn ----
            stopBttn.setIcon(new ImageIcon(getClass().getResource("/playerIcons/stop32.png")));
            stopBttn.setHorizontalAlignment(SwingConstants.CENTER);
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
            panel1.add(rewindBttn);
            rewindBttn.setBounds(75, 15, 32, 32);

            //---- loopBttn ----
            loopBttn.setIcon(new ImageIcon(getClass().getResource("/playerIcons/replay.png")));
            loopBttn.setHorizontalAlignment(SwingConstants.CENTER);
            loopBttn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    loopBttnMouseClicked(e);
                }
            });
            panel1.add(loopBttn);
            loopBttn.setBounds(5, 0, 64, 64);
        }
        contentPane.add(panel1);
        panel1.setBounds(0, 50, 390, 65);

        contentPane.setPreferredSize(new Dimension(405, 150));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel currentSong;
    private JLabel nowPlaying;
    private JPanel panel1;
    private JLabel stopBttn;
    private JLabel playBttn;
    private JLabel pauseBttn;
    private JLabel skipBttn;
    private JLabel rewindBttn;
    private JLabel loopBttn;

    @Override
    public void update(Observable observable, Object o) {
        nowPlaying.setText(m_bundle.getString("getCurrentSong") + " " + musicPlayer.getCurrentSongName());
    }

    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
