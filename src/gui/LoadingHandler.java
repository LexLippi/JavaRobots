package gui;

import game.RobotModel;
import game.TargetModel;
import log.Logger;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingHandler {
    public void handleLoading(MainApplicationFrame mainFrame, JInternalFrame[] frames, ResourceBundle bundle) {
        int answer = showSaveMessage(bundle);
        if (answer == JOptionPane.YES_OPTION) {
            for (var frame: frames) {
                ((Reopenable)frame).setMetadata(bundle);
                mainFrame.addWindow(frame);
            }
            Logger.info("loadMessage");
        }
        else{
            makeNewWindows(mainFrame, bundle);
        }
    }

    public void makeNewWindows(MainApplicationFrame mainFrame, ResourceBundle bundle){
        var targetModel = new TargetModel();
        var robotModel = new RobotModel(targetModel);
        mainFrame.logWindow = mainFrame.createLogWindow();
        mainFrame.addWindow(mainFrame.logWindow);
        mainFrame.gameWindow = new GameWindow(bundle, robotModel, targetModel);
        mainFrame.gameWindow.setBounds(220, 10, 400,400);
        mainFrame.addWindow(mainFrame.gameWindow);
        mainFrame.robotCoordinatesWindow = new RobotCoordinatesWindow(robotModel, bundle);
        mainFrame.robotCoordinatesWindow.setBounds(620,210,500, 100);
        mainFrame.addWindow(mainFrame.robotCoordinatesWindow);
        mainFrame.distanceWindow = new DistanceWindow(robotModel, bundle);
        mainFrame.distanceWindow.setBounds(620,310,500, 100);
        mainFrame.addWindow(mainFrame.distanceWindow);
        mainFrame.musicWindow = new MusicWindow(new URL[] {
                getClass().getResource("/songs/Frank Ocean - Chanel.wav"),
                getClass().getResource("/songs/The Soul Machine - Twitchie Feet.wav"),
        }, bundle);
        mainFrame.musicWindow.setBounds(620,10,500,200);
        mainFrame.addWindow(mainFrame.musicWindow);
        Logger.info("notLoadMessage");
    }

    private int showSaveMessage(ResourceBundle bundle) {
        String[] buttonLabels = new String[] {bundle.getString("accept"), bundle.getString("not accept")};
        String defaultOption = buttonLabels[0];
        Icon icon = null;
        return JOptionPane.showOptionDialog(null,
                bundle.getString("getSaveMessage"),
                bundle.getString("getNotification"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon,
                buttonLabels,
                defaultOption);

    }
}
