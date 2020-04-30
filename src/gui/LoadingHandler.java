package gui;

import log.Logger;

import javax.swing.*;
import java.io.File;
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
        mainFrame.logWindow = mainFrame.createLogWindow();
        mainFrame.addWindow(mainFrame.logWindow);
        mainFrame.gameWindow = new GameWindow(bundle);
        mainFrame.gameWindow.setSize(400,  400);
        mainFrame.addWindow(mainFrame.gameWindow);
        mainFrame.musicWindow = new MusicWindow(new File("src/songs"), bundle);
        mainFrame.musicWindow.setSize(400,150);
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
