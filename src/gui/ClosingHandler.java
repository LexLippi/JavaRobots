package gui;

import log.Logger;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import java.io.Serializable;
import java.util.ResourceBundle;

public class ClosingHandler implements Serializable {
    public enum ClosingType{
        GAME, LOG
    }

    private enum Context{
        EXIT, CLOSE
    }

    public void handleClosing(ResourceBundle bundle) {
        int answer = showWarningMessage(bundle, Context.EXIT);
        if (answer == JOptionPane.YES_OPTION) {
            Logger.info("getQuit");
            System.exit(0);
        }
        else{
            Logger.info("getNotQuit");
        }
    }

    public void handleClosing(JInternalFrame window, InternalFrameEvent e, ResourceBundle bundle, ClosingType type) {
        int answer = showWarningMessage(bundle, Context.CLOSE);
        String yesMessage = "";
        String noMessage = "";
        switch (type) {
            case GAME:
                yesMessage = "closeGameWindow";
                noMessage = "notCloseGameWindow";
                break;
            case LOG:
                yesMessage = "closeLogWindow";
                noMessage = "notCloseLogWindow";
                break;
        }
        if (answer == JOptionPane.YES_OPTION) {
            Logger.info(yesMessage);
            window.dispose();
        }
        else{
            Logger.info(noMessage);
        }
    }

    private int showWarningMessage(ResourceBundle bundle, Context context) {
        String[] buttonLabels = new String[] {bundle.getString("accept"), bundle.getString("not accept")};
        String defaultOption = buttonLabels[0];
        String message = "Warning";
        Icon icon = null;
        switch (context){
            case EXIT:
                message = "getWantExit";
                break;
            case CLOSE:
                message = "getWantCloseWindow";
                break;
        }
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
