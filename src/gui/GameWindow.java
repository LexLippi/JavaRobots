package gui;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.io.Serializable;
import java.util.ResourceBundle;

public class GameWindow extends JInternalFrame implements Serializable
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
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                closingHandler.handleClosing(window, e, bundle, ClosingHandler.ClosingType.GAME );
            }
        });
        pack();
    }

    public void setMetadata(ResourceBundle bundle) {
        m_visualizer.setMetadata();
        createNewClosingHandler(bundle);
    }

    private void createNewClosingHandler(ResourceBundle bundle) {
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                closingHandler.handleClosing(window, e, bundle, ClosingHandler.ClosingType.GAME);
            }
        });
    }
}
