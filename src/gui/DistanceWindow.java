package gui;

import game.RobotModel;
import game.RobotState;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class DistanceWindow  extends JInternalFrame implements Observer, Reopenable {
    private RobotModel robotModel;
    private JTable robotToTargetDistance;

    public DistanceWindow(RobotModel robotModel, ResourceBundle bundle) {
        var columnsHeader = new String[] {bundle.getString("getTitleRobotName"),
                bundle.getString("getTitleRobotDistance")};
        var robotData = new Object[][] {{bundle.getString("getRobotName"), robotModel.getDistanceToTarget()}};
        var panel = new JPanel(new BorderLayout());
        robotToTargetDistance = new JTable(robotData, columnsHeader);
        panel.add(new JScrollPane(robotToTargetDistance), BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        this.robotModel = robotModel;
        this.robotModel.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        var robotState = (RobotState)o;
        switch (robotState) {
            case MOVE:
            case SHUTDOWN:
                robotToTargetDistance.setValueAt(robotModel.getDistanceToTarget(), 0, 1);
                break;
            case STAND:
                break;
            default:
                throw new IllegalStateException("Update GameVisualizer received illegal robot state");
        }
    }

    public void changeLanguage(ResourceBundle nextBundle){

    }

    @Override
    public void setMetadata(ResourceBundle bundle) {
        robotModel.setMetadata();
        robotModel.addObserver(this);
    }
}
