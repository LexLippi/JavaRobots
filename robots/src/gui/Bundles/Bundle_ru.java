package gui.Bundles;

import java.util.ListResourceBundle;

public class Bundle_ru extends ListResourceBundle {
    private Object[][] contents = {
            {"modeKey", "Режим отображения"},
            {"systemModeKey", "Системная схема"},
            {"universalModeKey", "Универсальная схема"},
            {"testsKey", "Тесты"},
            {"getMessageKey", "Сообщение в лог"},
            {"messageKey", "Новая строка"},
            {"protocolKey", "Протокол работает"},
            {"gameFieldKey", "Игровое поле"},
            {"logTitleKey", "Протокол работы"},
            {"frameTitlePane.closeButtonText", "Закрыть"},
            {"frameTitlePane.minimizeButtonText", "Уменьшить"},
            {"frameTitlePane.restoreButtonText", "Восстановить"},
            {"frameTitlePane.maximizeButtonText", "Увеличить"},
            {"frameTitlePane.moveButtonText", "Переместить"},
            {"frameTitlePane.sizeButtonText", "Изменить размер"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
