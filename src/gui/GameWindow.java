package gui;

import game.RobotModel;
import game.TargetModel;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.io.Serializable;
import java.util.ResourceBundle;

public class GameWindow extends JInternalFrame implements Serializable, Musical, Reopenable
{
    private GameWindow window = this;
    private final GameVisualizer m_visualizer;
    public GameWindow(ResourceBundle bundle, RobotModel robotModel, TargetModel targetModel)
    {
        super(bundle.getString("gameFieldKey"), true, true, true, true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        m_visualizer = new GameVisualizer(robotModel, targetModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                m_visualizer.closingHandler.handleClosing(window, e, bundle, ClosingHandler.ClosingType.GAME );
            }
        });
        pack();
    }

    public void setMetadata(ResourceBundle bundle) {
        m_visualizer.setMetadata();
        createNewClosingHandler(bundle);
    }

    public void stopMusic(){
        m_visualizer.stopMusic();
    }

    public void changeLanguage(ResourceBundle bundle){
        this.setTitle(bundle.getString("gameFieldKey"));
    }

    private void createNewClosingHandler(ResourceBundle bundle) {
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                m_visualizer.closingHandler.handleClosing(window, e, bundle, ClosingHandler.ClosingType.GAME);
            }
        });
    }
}
