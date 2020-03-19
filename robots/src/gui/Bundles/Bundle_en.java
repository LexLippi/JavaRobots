package gui.Bundles;

import java.util.ListResourceBundle;

public class Bundle_en extends ListResourceBundle {
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
            {"frameTitlePane.sizeButtonText", "Size"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
