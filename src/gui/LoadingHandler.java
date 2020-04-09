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
                        break;
                    }
                    case "GameWindow" : {
                        var gameWindow = (GameWindow) frame;
                        gameWindow.setMetadata(bundle);
                        break;
                    }
                    default: {
                        throw new IllegalStateException();
                    }
                }
                mainFrame.addWindow(frame);
            }
            Logger.info("Load a save");
        }
        else{
            LogWindow logWindow = mainFrame.createLogWindow();
            mainFrame.addWindow(logWindow);

            GameWindow gameWindow = new GameWindow(bundle);
            gameWindow.setSize(400,  400);
            mainFrame.addWindow(gameWindow);
            Logger.info("Don't load a save");
        }
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
