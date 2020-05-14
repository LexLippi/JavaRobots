package gui;

import game.RobotModel;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.io.Closeable;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class DistanceWindow  extends JInternalFrame implements Serializable, Observer, Reopenable, Multilangual {
    private RobotModel robotModel;
    private JTable robotToTargetDistance;
    private ClosingHandler closingHandler;

    public DistanceWindow(RobotModel robotModel, ResourceBundle bundle) {
        setVisible(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setClosable(true);
        JInternalFrame window = this;
        this.robotModel = robotModel;
        this.robotModel.addObserver(this);
        closingHandler = new ClosingHandler();
        var columnsHeader = new String[] {bundle.getString("getTitleRobotName"),
                bundle.getString("getTitleRobotDistance")};
        var robotData = new Object[][] {{bundle.getString("getRobotName"), robotModel.getDistanceToTarget()}};
        var panel = new JPanel(new BorderLayout());
        this.setTitle(bundle.getString("getTitleRobotDistance"));
        robotToTargetDistance = new JTable(robotData, columnsHeader);
        panel.add(new JScrollPane(robotToTargetDistance), BorderLayout.CENTER);
        getContentPane().add(panel);
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                closingHandler.handleClosing(window, e, bundle, ClosingHandler.ClosingType.GAME );
            }
        });
        pack();
    }

    @Override
    public void update(Observable observable, Object o) {
        robotToTargetDistance.setValueAt(robotModel.getDistanceToTarget(), 0, 1);
    }

    public void changeLanguage(ResourceBundle nextBundle){
    setTitle(nextBundle.getString("getTitleRobotDistance"));
    robotToTargetDistance.getColumnModel().getColumn(0).setHeaderValue(nextBundle.getString("getTitleRobotName"));
    robotToTargetDistance.getColumnModel().getColumn(1).setHeaderValue(nextBundle.getString("getTitleRobotDistance"));
    robotToTargetDistance.getModel().setValueAt(nextBundle.getString("getRobotName"), 0,0);
    }

    @Override
    public void setMetadata(ResourceBundle bundle) {
        robotModel.setMetadata();
        robotModel.addObserver(this);
    }
}
