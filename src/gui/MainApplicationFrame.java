package gui;

import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.rmi.UnexpectedException;
import java.util.ResourceBundle;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final ResourceBundle bundle;
    private ClosingHandler closingHandler = new ClosingHandler();


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

        var frames = openSavedWindows();
        if (frames != null) {
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
                addWindow(frame);
            }
        }
        else {
            LogWindow logWindow = createLogWindow();
            addWindow(logWindow);

            GameWindow gameWindow = new GameWindow(bundle);
            gameWindow.setSize(400,  400);
            addWindow(gameWindow);
        }

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveWindowStates();
                closingHandler.handleClosing(bundle);
            }
        });
    }

    private JInternalFrame[] openSavedWindows() {
        var filename = "window.dat";
        try (var ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (JInternalFrame[])ois.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private void saveWindowStates() {
        var filename = "window.dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(desktopPane.getAllFrames());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
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
                        Logger.info("Forced Exit");
                        closingHandler.handleClosing(bundle);
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
                    System.out.println("here");
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

