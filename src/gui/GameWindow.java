package gui;

import javax.swing.border.*;
import game.RobotModel;
import game.TargetModel;
import sound.algorithms.FilterMode;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.io.Serializable;
import java.util.ResourceBundle;

public class GameWindow extends JInternalFrame implements Serializable, Reopenable, Multilangual
{
    private GameWindow window = this;
    private final GameVisualizer m_visualizer;
    public GameWindow(ResourceBundle bundle, RobotModel robotModel, TargetModel targetModel, FilterMode filterMode)
    {
        super(bundle.getString("gameFieldKey"), true, true, true, true);
        m_visualizer = new GameVisualizer(robotModel, targetModel, filterMode);
        m_visualizer.grabFocus();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        initComponents();
        createNewClosingHandler(bundle);
        pack();
    }
    @Override
    public void dispose(){
        stopMusic();
        super.dispose();
    }

    public void setMetadata(ResourceBundle bundle) {
        m_visualizer.setMetadata();
        createNewClosingHandler(bundle);
    }

    private void stopMusic(){
        m_visualizer.stopMusic();
    }

    public void changeLanguage(ResourceBundle bundle){
        stopMusic();
        this.setTitle(bundle.getString("gameFieldKey"));
    }

    private void createNewClosingHandler(ResourceBundle bundle) {
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                m_visualizer.closingHandler.handleClosing(window, e, bundle, ClosingHandler.ClosingType.GAME);
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        panel1 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();
        panel2 = new JPanel();
        panel8 = new JPanel();
        panel9 = new JPanel();
        panel3 = new JPanel();
        panel6 = new JPanel();
        panel7 = new JPanel();
        m_visualizer.setLayout(new GridLayout(3, 3));

        //======== this ========
        setVisible(true);

        //======== panel1 ========
        {
            panel1.setBackground(new Color(153, 255, 255));
            panel1.setBorder(new MatteBorder(2, 2, 1, 1, Color.black));
            panel1.setLayout(null);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel1.getComponentCount(); i++) {
                    Rectangle bounds = panel1.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel1.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel1.setMinimumSize(preferredSize);
                panel1.setPreferredSize(preferredSize);
            }
        }
        m_visualizer.add(panel1);

        //======== panel4 ========
        {
            panel4.setBackground(new Color(153, 204, 255));
            panel4.setBorder(new MatteBorder(2, 1, 1, 1, Color.darkGray));
            panel4.setLayout(null);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel4.getComponentCount(); i++) {
                    Rectangle bounds = panel4.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel4.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel4.setMinimumSize(preferredSize);
                panel4.setPreferredSize(preferredSize);
            }
        }
        m_visualizer.add(panel4);

        //======== panel5 ========
        {
            panel5.setBackground(new Color(153, 153, 255));
            panel5.setBorder(new MatteBorder(2, 1, 1, 2, Color.darkGray));
            panel5.setLayout(null);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel5.getComponentCount(); i++) {
                    Rectangle bounds = panel5.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel5.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel5.setMinimumSize(preferredSize);
                panel5.setPreferredSize(preferredSize);
            }
        }
        m_visualizer.add(panel5);

        //======== panel2 ========
        {
            panel2.setBackground(new Color(255, 153, 255));
            panel2.setBorder(new MatteBorder(1, 2, 1, 1, Color.darkGray));
            panel2.setLayout(null);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel2.getComponentCount(); i++) {
                    Rectangle bounds = panel2.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel2.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel2.setMinimumSize(preferredSize);
                panel2.setPreferredSize(preferredSize);
            }
        }
        m_visualizer.add(panel2);

        //======== panel8 ========
        {
            panel8.setBackground(Color.white);
            panel8.setBorder(new LineBorder(Color.darkGray, 1, true));
            panel8.setLayout(null);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel8.getComponentCount(); i++) {
                    Rectangle bounds = panel8.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel8.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel8.setMinimumSize(preferredSize);
                panel8.setPreferredSize(preferredSize);
            }
        }
        m_visualizer.add(panel8);

        //======== panel9 ========
        {
            panel9.setBackground(new Color(255, 153, 153));
            panel9.setBorder(new MatteBorder(1, 1, 1, 2, Color.darkGray));
            panel9.setLayout(null);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel9.getComponentCount(); i++) {
                    Rectangle bounds = panel9.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel9.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel9.setMinimumSize(preferredSize);
                panel9.setPreferredSize(preferredSize);
            }
        }
        m_visualizer.add(panel9);

        //======== panel3 ========
        {
            panel3.setBackground(new Color(204, 255, 153));
            panel3.setBorder(new MatteBorder(1, 2, 2, 1, Color.darkGray));
            panel3.setLayout(null);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel3.getComponentCount(); i++) {
                    Rectangle bounds = panel3.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel3.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel3.setMinimumSize(preferredSize);
                panel3.setPreferredSize(preferredSize);
            }
        }
        m_visualizer.add(panel3);

        //======== panel6 ========
        {
            panel6.setBackground(new Color(153, 255, 153));
            panel6.setBorder(new MatteBorder(1, 1, 2, 1, Color.darkGray));
            panel6.setLayout(null);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel6.getComponentCount(); i++) {
                    Rectangle bounds = panel6.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel6.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel6.setMinimumSize(preferredSize);
                panel6.setPreferredSize(preferredSize);
            }
        }
        m_visualizer.add(panel6);

        //======== panel7 ========
        {
            panel7.setBackground(new Color(102, 255, 153));
            panel7.setBorder(new MatteBorder(1, 1, 2, 2, Color.darkGray));
            panel7.setLayout(null);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel7.getComponentCount(); i++) {
                    Rectangle bounds = panel7.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel7.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel7.setMinimumSize(preferredSize);
                panel7.setPreferredSize(preferredSize);
            }
        }
        m_visualizer.add(panel7);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel panel1;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel2;
    private JPanel panel8;
    private JPanel panel9;
    private JPanel panel3;
    private JPanel panel6;
    private JPanel panel7;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
