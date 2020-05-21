package gui;

import game.RobotModel;
import game.RobotState;
import game.TargetModel;
import sound.MusicPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class GameVisualizer extends JPanel implements Serializable, Observer
{
    private TargetModel targetModel;
    private RobotModel robotModel;
    private transient MusicPlayer musicPlayer;
    protected ClosingHandler closingHandler;

    public GameVisualizer(RobotModel robotModel, TargetModel targetModel)
    {
        this.targetModel = targetModel;
        this.robotModel = robotModel;
        this.robotModel.setFieldSize(getWidth(), getHeight());
        closingHandler = new ClosingHandler();
        var url = getClass().getResource("/robotSounds/RobotMoving.wav");
        musicPlayer = new MusicPlayer((new URL[] {url}));
        targetModel.addObserver(this);
        robotModel.addObserver(this);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                targetModel.setTargetPosition(e.getPoint());
                if (!Objects.equals(musicPlayer.getCurrentSongName(), "RobotMoving.wav")) {
                    musicPlayer.deleteAllSongs();
                    var url = getClass().getResource("/robotSounds/RobotMoving.wav");
                    musicPlayer.addNewSongs((new URL[] {url}));
                    musicPlayer.play();
                }
            }
        });
        addComponentListener(new ResizeListener());
        setDoubleBuffered(true);
        musicPlayer.play();
        musicPlayer.setVolumeLevel(0.9f);
    }

    class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            robotModel.setFieldSize(getWidth(), getHeight());
        }
    }

    public void stopMusic(){
        musicPlayer.stop();
    }

    public void setMetadata() {
        var url = getClass().getResource("/robotSounds/RobotMoving.wav");
        musicPlayer = new MusicPlayer((new URL[] {url}));
        musicPlayer.play();
        musicPlayer.setVolumeLevel(0.9f);
        robotModel.setMetadata();
        targetModel.addObserver(this);
        robotModel.addObserver(this);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                targetModel.setTargetPosition(e.getPoint());
                musicPlayer.deleteAllSongs();
                var url = getClass().getResource("/robotSounds/RobotMoving.wav");
                musicPlayer.addNewSongs((new URL[] {url}));
                musicPlayer.play();
            }
        });
        setDoubleBuffered(true);
        addComponentListener(new ResizeListener());
        onRedrawEvent();
    }
    
    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g; 
        drawRobot(g2d, robotModel.getRobotPositionX(), robotModel.getRobotPositionY(), robotModel.getRobotDirection());
        drawTarget(g2d, targetModel.getTargetPositionX(), targetModel.getTargetPositionY());
    }
    
    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    
    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    
    private void drawRobot(Graphics2D g, int x, int y, double direction)
    {
        int robotCenterX = robotModel.getRobotPositionX();
        int robotCenterY = robotModel.getRobotPositionY();
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY); 
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
    }
    
    private void drawTarget(Graphics2D g, int x, int y)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0); 
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    @Override
    public void update(Observable observable, Object state) {
        if (state == null) {
            onRedrawEvent();
            return;
        }
        var robotState = (RobotState)state;
        switch (robotState) {
            case MOVE:
            case STAND:
                onRedrawEvent();
                break;
            case SHUTDOWN:
                if(!musicPlayer.getPaused()) {
                    musicPlayer.deleteAllSongs();
                    var url = getClass().getResource("/robotSounds/RobotStanding.wav");
                    musicPlayer.addNewSongs((new URL[]{url}));
                    musicPlayer.play();
                }
                break;
            default:
                throw new IllegalStateException("Update GameVisualizer received illegal robot state");
        }
    }
}
