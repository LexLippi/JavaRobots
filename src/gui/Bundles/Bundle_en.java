package gui.Bundles;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class Bundle_en extends ListResourceBundle implements Serializable{
    private Object[][] contents = {
            {"modeKey", "Display mode"},
            {"systemModeKey", "System diagram"},
            {"universalModeKey", "Universal diagram"},
            {"testsKey", "Tests"},
            {"menuKey", "Menu"},
            {"getExit", "Exit"},
            {"getMessageKey", "Message in log"},
            {"messageKey", "New string"},
            {"protocolKey", "Protocol works"},
            {"gameFieldKey", "Game field"},
            {"logTitleKey", "Work protocol"},
            {"frameTitlePane.closeButtonText", "Close"},
            {"frameTitlePane.minimizeButtonText", "Minimize"},
            {"frameTitlePane.restoreButtonText", "Restore"},
            {"frameTitlePane.maximizeButtonText", "Maximize"},
            {"frameTitlePane.moveButtonText", "Move"},
            {"frameTitlePane.sizeButtonText", "Size"},
            {"getQuit", "Quit"},
            {"getNotQuit", "Don't quit"},
            {"accept", "Yes"},
            {"not accept", "No"},
            {"getWarning", "Warning"},
            {"getWantExit",  "Do you really want to exit?"},
            {"getWantCloseWindow", "Do you really want to close this window?"},
            {"getLanguage", "Language"},
            {"getRussian", "Russian"},
            {"getEnglish", "English"},
            {"getNotification", "Notification"},
            {"getSaveMessage", "You have a save. Do you want to load it?"},
            {"exit", "Forced exit"},
            {"loadMessage", "Load a save"},
            {"notLoadMessage", "Don't load a save"},
            {"closeGameWindow", "Close game window"},
            {"closeLogWindow", "Close log window"},
            {"notCloseGameWindow", "Don't close game window"},
            {"notCloseLogWindow", "Don't close log window"},
            {"getCurrentSong", "Now playing"},
            {"musicPlayerTitleKey", "Music player"},
            {"closeMusicWindow", "Close music window"},
            {"notCloseMusicWindow", "Don't close music window"},
            {"getTitleRobotName", "Robot name"},
            {"getTitleRobotHorizontalCoordinate", "Horizontal coordinate"},
            {"getTitleRobotVerticalCoordinate", "Vertical coordinate"},
            {"getRobotName", "Pig Peter"},
            {"getTitleRobotDistance", "Distance to target"},
            {"getTitleRobotCoordinates", "Robot Coordinates"},
            {"closeDistanceWindow", "Close distance window"},
            {"notCloseDistanceWindow", "Don't close distance window"},
            {"closeCoordinatesWindow", "Close coordinates window"},
            {"notCloseCoordinatesWindow", "Don't close coordinates window"}

    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
