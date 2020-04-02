package gui;

import java.awt.Frame;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RobotsProgram
{
    public static void main(String[] args) {
      var locale = new Locale("ru", "RUS");
      var bundle = ResourceBundle.getBundle("gui.Bundles.Bundle", locale);
      try {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        UIManager.put("InternalFrameTitlePane.closeButtonText",
                bundle.getString("frameTitlePane.closeButtonText"));
        UIManager.put("InternalFrameTitlePane.minimizeButtonText",
                bundle.getString("frameTitlePane.minimizeButtonText"));
        UIManager.put("InternalFrameTitlePane.restoreButtonText",
                bundle.getString("frameTitlePane.restoreButtonText"));
        UIManager.put("InternalFrameTitlePane.maximizeButtonText",
                bundle.getString("frameTitlePane.maximizeButtonText"));
        UIManager.put("InternalFrameTitlePane.moveButtonText",
                bundle.getString("frameTitlePane.moveButtonText"));
        UIManager.put("InternalFrameTitlePane.sizeButtonText",
                bundle.getString("frameTitlePane.sizeButtonText"));
      } catch (Exception e) {
        e.printStackTrace();
      }

      SwingUtilities.invokeLater(() -> {
        MainApplicationFrame frame = new MainApplicationFrame(bundle);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
      });
    }}
