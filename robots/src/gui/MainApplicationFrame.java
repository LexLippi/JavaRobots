package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

import log.Logger;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final ResourceBundle bundle;
    
    public MainApplicationFrame(ResourceBundle bundle) {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        this.bundle = bundle;
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);
        setContentPane(desktopPane);
        
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow(bundle);
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                handleClosing();
            }
        });
    }

    private void handleClosing() {
        int answer = showWarningMessage();
        if (answer == JOptionPane.YES_OPTION) {
            Logger.info(bundle.getString("getQuit"));
            System.exit(0);
        }
        else{
            Logger.info(bundle.getString("getNotQuit"));
        }
    }

    private int showWarningMessage() {
        String[] buttonLabels = new String[] {bundle.getString("accept"), bundle.getString("not accept")};
        String defaultOption = buttonLabels[0];
        Icon icon = null;

        return JOptionPane.showOptionDialog(this,
                bundle.getString("getWantExit"),
                bundle.getString("getWarning"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                icon,
                buttonLabels,
                defaultOption);
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource(), bundle);
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug(bundle.getString("protocolKey"));
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        var mainMenu = createMenu(bundle.getString("menuKey"), KeyEvent.VK_M, "Главное меню");
        {
            mainMenu.add(createMenuItem(bundle.getString("getExit"),
                    (event) -> {
                        handleClosing();
                    }
            ));
        }

        var lookAndFeelMenu = createMenu(bundle.getString("modeKey"), KeyEvent.VK_V,
                "Управление режимом отображения приложения");
        {
            lookAndFeelMenu.add(createMenuItem(bundle.getString("systemModeKey"),
                    (event) -> {
                        setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        this.invalidate();
                    }
            ));
        }

        {
            lookAndFeelMenu.add(createMenuItem(bundle.getString("universalModeKey"),
                    (event) -> {
                        setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                        this.invalidate();
                    }
            ));
        }

        var testMenu = createMenu(bundle.getString("testsKey"), KeyEvent.VK_T, "Тестовые команды");
        {
            testMenu.add(createMenuItem(bundle.getString("getMessageKey"),
                    (event) -> {
                    Logger.debug(bundle.getString("messageKey"));
                    }
            ));
        }

        menuBar.add(mainMenu);
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        return menuBar;
    }

    private JMenu createMenu(String menuName, int eventNumber, String description) {
        JMenu menu = new JMenu(menuName);
        menu.setMnemonic(eventNumber);
        menu.getAccessibleContext().setAccessibleDescription(description);
        return menu;
    }

    private JMenuItem createMenuItem(String itemName, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(itemName, KeyEvent.VK_S);
        menuItem.addActionListener(actionListener);
        return menuItem;
    }

    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}

