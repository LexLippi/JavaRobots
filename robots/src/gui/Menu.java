package gui;

import log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Menu {
    private JMenu menu;
    private JMenuBar menuBar;

    public Menu() {
        menuBar = new JMenuBar();
        createMenu();
        menuBar.add(menu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    private void createMenu() {
        menu = new JMenu("Document");
        menu.setMnemonic(KeyEvent.VK_D);
        menu.add(createMenuItem("New", KeyEvent.VK_N));
        menu.add(createMenuItem("Quit", KeyEvent.VK_Q));
    }

    private JMenuItem createMenuItem(String text, int keyEvent)
    {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setMnemonic(keyEvent);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(keyEvent, ActionEvent.ALT_MASK));
        menuItem.setActionCommand(text.toLowerCase());
        // menuItem.addActionListener(this);
        return menuItem;
    }
}
