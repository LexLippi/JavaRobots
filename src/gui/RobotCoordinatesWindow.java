package gui;

import game.RobotModel;
import game.RobotState;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class RobotCoordinatesWindow extends JInternalFrame implements Serializable, Observer, Reopenable {
    private RobotModel robotModel;
    private JTable robotCoordinates;

    public RobotCoordinatesWindow(RobotModel robotModel, ResourceBundle bundle) {
        setVisible(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setClosable(true);
        var columnsHeader = new String[] {bundle.getString("getTitleRobotName"),
                bundle.getString("getTitleRobotHorizontalCoordinate"),
                bundle.getString("getTitleRobotVerticalCoordinate")};
        var robotData = new Object[][] {{bundle.getString("getRobotName"),
                robotModel.getRobotPositionX(), robotModel.getRobotPositionY()}};
        var panel = new JPanel(new BorderLayout());
        setTitle(bundle.getString("getTitleRobotCoordinates"));
        robotCoordinates = new JTable(robotData, columnsHeader);
        panel.add(new JScrollPane(robotCoordinates), BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        this.robotModel = robotModel;
        this.robotModel.addObserver(this);
    }

    public void changeLanguage(ResourceBundle nextBundle){
        setTitle(nextBundle.getString("getTitleRobotCoordinates"));
        robotCoordinates.getColumnModel().getColumn(0).setHeaderValue(nextBundle.getString("getTitleRobotName"));
        robotCoordinates.getColumnModel().getColumn(1).setHeaderValue(nextBundle.getString("getTitleRobotHorizontalCoordinate"));
        robotCoordinates.getColumnModel().getColumn(2).setHeaderValue(nextBundle.getString("getTitleRobotVerticalCoordinate"));
        robotCoordinates.getModel().setValueAt(nextBundle.getString("getRobotName"), 0,0);
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
