package gui;

import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ResourceBundle;
import java.util.Locale;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private ResourceBundle bundle;
    private ClosingHandler closingHandler = new ClosingHandler();
    private LoadingHandler loadingHandler = new LoadingHandler();
    public GameWindow gameWindow;
    public LogWindow logWindow;
    public MusicWindow musicWindow;
    public RobotCoordinatesWindow robotCoordinatesWindow;
    public DistanceWindow distanceWindow;


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
        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveWindowStates();
                closingHandler.handleClosing(bundle);
            }
            public void windowOpened(WindowEvent e) {
                File file = new File("window.dat");
                if (file.exists()){
                    loadingHandler.handleLoading(MainApplicationFrame.this, frames, bundle);
                }
                else{
                    loadingHandler.makeNewWindows(MainApplicationFrame.this, bundle);
                }

            }
        });
    }

    private enum Language{
        RUS, ENG
    }

    private void changeLanguage(Language language){
        Locale ru = new Locale("ru", "RU");
        Locale en = new Locale("en", "EN");
        Locale nextLocale = null;
        switch (language){
            case RUS:
                nextLocale = ru;
                break;
            case ENG:
                nextLocale = en;
                break;
        }
        if (nextLocale != null){
            ResourceBundle prevBundle = this.bundle;
            ResourceBundle nextBundle = ResourceBundle.getBundle("Bundles.Bundle", nextLocale);
            if (!prevBundle.equals(nextBundle)){
                logWindow.changeLanguage(nextBundle);
                gameWindow.changeLanguage(nextBundle);
                musicWindow.changeLanguage(nextBundle);
                robotCoordinatesWindow.changeLanguage(nextBundle);
                distanceWindow.changeLanguage(nextBundle);
                saveWindowStates();
                RobotsProgram.restart(nextBundle, this);
            }
        }
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
        Logger.debug("protocolKey");
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
                        Logger.info("exit");
                        closingHandler.handleClosing(bundle);
                    }
            ));
            var languageMenu = createMenu(bundle.getString("getLanguage"), KeyEvent.VK_L, "Смена языка");
            {
                languageMenu.add(createMenuItem(bundle.getString("getRussian"),
                        (event) -> {
                            changeLanguage(Language.RUS);
                        }
                        ));

                languageMenu.add(createMenuItem(bundle.getString("getEnglish"),
                        (event) -> {
                            changeLanguage(Language.ENG);
                        }
                ));
            }
            mainMenu.add(languageMenu);

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
                    Logger.debug("messageKey");
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

