package gui;

import log.Logger;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class GameWindow extends JInternalFrame
{
    private final GameVisualizer m_visualizer;
    public GameWindow(ResourceBundle bundle)
    {
        super(bundle.getString("gameFieldKey"), true, true, true, true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                handleClosing(e);
            }
        });
    }

    private void handleClosing(InternalFrameEvent e) {
        int answer = showWarningMessage();
        if (answer == JOptionPane.YES_OPTION) {
            Logger.info("Close game");
            this.dispose();
        }
        else{
            Logger.info("Don't close game");
        }
    }

    private int showWarningMessage() {
        String[] buttonLabels = new String[] {"Yes", "No"};
        String defaultOption = buttonLabels[0];
        Icon icon = null;

        return JOptionPane.showOptionDialog(this,
                "Do you really want to exit?",
                "Question",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon,
                buttonLabels,
                defaultOption);
    }
}
