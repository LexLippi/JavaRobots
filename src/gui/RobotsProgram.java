package gui;

import log.LogWindowSource;
import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;


public class RobotsProgram
{
  private static ProgramPreferences prefs = new ProgramPreferences();
  public static void main(String[] args) {
      ResourceBundle bundle = prefs.getBundle();
      try {
        //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
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
    }

    public static void changeBundle(ResourceBundle bundle){
      prefs.setBundle(bundle);
    }

    public static void restart(ResourceBundle bundle, MainApplicationFrame mainFrame){
      changeBundle(bundle);
      mainFrame.dispose();
      SwingUtilities.invokeLater(() -> {
        MainApplicationFrame frame = new MainApplicationFrame(bundle);
        Logger.getDefaultLogSource().clear();
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
      });
    }
}
