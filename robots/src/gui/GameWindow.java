package gui;

import log.Logger;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class GameWindow extends JInternalFrame
{
    private GameWindow window = this;
    private final GameVisualizer m_visualizer;
    private ClosingHandler closingHandler = new ClosingHandler();
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
                closingHandler.handleClosing(window, e, bundle,1 );
            }
        });
    }
}
