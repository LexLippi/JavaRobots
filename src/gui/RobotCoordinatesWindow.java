package gui;

import game.RobotModel;
import game.RobotState;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class RobotCoordinatesWindow extends JInternalFrame implements Observer, Reopenable {
    private RobotModel robotModel;
    private JTable robotCoordinates;

    public RobotCoordinatesWindow(RobotModel robotModel, ResourceBundle bundle) {
        var columnsHeader = new String[] {bundle.getString("getTitleRobotName"),
                bundle.getString("getTitleRobotHorizontalCoordinate"),
                bundle.getString("getTitleRobotVerticalCoordinate")};
        var robotData = new Object[][] {{bundle.getString("getRobotName"),
                robotModel.getRobotPositionX(), robotModel.getRobotPositionY()}};
        var panel = new JPanel(new BorderLayout());
        robotCoordinates = new JTable(robotData, columnsHeader);
        panel.add(new JScrollPane(robotCoordinates), BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        this.robotModel = robotModel;
        this.robotModel.addObserver(this);
    }

    public void changeLanguage(ResourceBundle nextBundle){

    }

    @Override
    public void update(Observable observable, Object o) {
        var robotState = (RobotState)o;
        switch (robotState) {
            case MOVE:
            case SHUTDOWN:
                robotCoordinates.setValueAt(robotModel.getRobotPositionX(), 0, 1);
                robotCoordinates.setValueAt(robotModel.getRobotPositionY(), 0, 2);
                break;
            case STAND:
                break;
            default:
                throw new IllegalStateException("Update GameVisualizer received illegal robot state");
        }
    }

    @Override
    public void setMetadata(ResourceBundle bundle) {
        robotModel.setMetadata();
        robotModel.addObserver(this);
    }
}
