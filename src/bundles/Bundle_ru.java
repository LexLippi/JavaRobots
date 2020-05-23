package bundles;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class Bundle_ru extends ListResourceBundle implements Serializable{
    private Object[][] contents = {
            {"modeKey", "Режим отображения"},
            {"systemModeKey", "Системная схема"},
            {"universalModeKey", "Универсальная схема"},
            {"testsKey", "Тесты"},
            {"menuKey", "Меню"},
            {"getExit", "Выход"},
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
            {"frameTitlePane.sizeButtonText", "Изменить размер"},
            {"getQuit", "Выйти"},
            {"getNotQuit", "Отмена"},
            {"accept", "Да"},
            {"not accept", "Нет"},
            {"getWarning", "Предупреждение"},
            {"getWantExit",  "Вы действительно хотите выйти?"},
            {"getWantCloseWindow", "Вы действительно хотите закрыть это окно?"},
            {"getLanguage", "Язык"},
            {"getRussian", "Русский"},
            {"getEnglish", "Английский"},
            {"getNotification", "Напоминание"},
            {"getSaveMessage", "У вас есть сохранение. Хотите его загрузить?"},
            {"exit", "Принудительный выход"},
            {"loadMessage", "Загружено сохранение"},
            {"notLoadMessage", "Не загружено сохранение"},
            {"closeGameWindow", "Закрыто окно игры"},
            {"closeLogWindow", "Закрыто окно логов"},
            {"notCloseGameWindow", "Не закрыто окно игры"},
            {"notCloseLogWindow", "Не закрыто окно логов"},
            {"getCurrentSong", "Сейчас играет"},
            {"musicPlayerTitleKey", "Музыкальный проигрыватель"},
            {"closeMusicWindow", "Закрыт музыкальный проигрыватель"},
            {"notCloseMusicWindow", "Не закрыт музыкальный проигрыватель"},
            {"getTitleRobotName", "Имя робота"},
            {"getTitleRobotHorizontalCoordinate", "Горизонтальная координата"},
            {"getTitleRobotVerticalCoordinate", "Вертикальная координата"},
            {"getRobotName", "Поросенок Пётр"},
            {"getTitleRobotDistance", "Расстояние до цели"},
            {"getTitleRobotCoordinates", "Координаты робота"},
            {"closeDistanceWindow", "Закрыто окно \"Расстояние до цели\""},
            {"notCloseDistanceWindow", "Не закрыто окно \"Расстояние до цели\""},
            {"closeCoordinatesWindow", "Закрыто окно \"Координаты робота\""},
            {"notCloseCoordinatesWindow", "Не закрыто окно \"Координаты робота\""}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
