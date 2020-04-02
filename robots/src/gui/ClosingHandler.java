package gui;

import log.Logger;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import java.util.ResourceBundle;

public class ClosingHandler {

    public void handleClosing(ResourceBundle bundle) {
        int answer = showWarningMessage(bundle, 1);
        if (answer == JOptionPane.YES_OPTION) {
            Logger.info(bundle.getString("getQuit"));
            System.exit(0);
        }
        else{
            Logger.info(bundle.getString("getNotQuit"));
        }
    }

    public void handleClosing(GameWindow window, InternalFrameEvent e, ResourceBundle bundle) {
        int answer = showWarningMessage(bundle, 2);
        if (answer == JOptionPane.YES_OPTION) {
            Logger.info("Close game");
            window.dispose();
        }
        else{
            Logger.info("Don't close game");
        }
    }

    public void handleClosing(LogWindow window, InternalFrameEvent e, ResourceBundle bundle) {
        int answer = showWarningMessage(bundle, 3);
        if (answer == JOptionPane.YES_OPTION) {
            Logger.info("Close log window");
            window.dispose();
        }
        else{
            Logger.info("Don't close log window");
        }
    }

    private int showWarningMessage(ResourceBundle bundle, int context) {
        String[] buttonLabels = new String[] {bundle.getString("accept"), bundle.getString("not accept")};
        String defaultOption = buttonLabels[0];
        String message;
        Icon icon = null;
        if (context==1)
            message = "getWantExit";
        else
            message = "getWantCloseWindow";
        return JOptionPane.showOptionDialog(null,
                bundle.getString(message),
                bundle.getString("getWarning"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon,
                buttonLabels,
                defaultOption);

    }
}
