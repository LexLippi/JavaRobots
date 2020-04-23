package gui;

import log.Logger;

import javax.swing.*;
import java.util.ResourceBundle;

public class LoadingHandler {
    public void handleLoading(MainApplicationFrame mainFrame, JInternalFrame[] frames, ResourceBundle bundle) {
        int answer = showSaveMessage(bundle);
        if (answer == JOptionPane.YES_OPTION) {
            for (var frame: frames) {
                switch (frame.getClass().getSimpleName()) {
                    case "LogWindow" : {
                        var logWindow = (LogWindow) frame;
                        logWindow.setMetadata(Logger.getDefaultLogSource(), bundle);
                        mainFrame.logWindow = logWindow;
                        break;
                    }
                    case "GameWindow" : {
                        var gameWindow = (GameWindow) frame;
                        gameWindow.setMetadata(bundle);
                        mainFrame.gameWindow = gameWindow;
                        break;
                    }
                    default: {
                        throw new IllegalStateException();
                    }
                }
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
