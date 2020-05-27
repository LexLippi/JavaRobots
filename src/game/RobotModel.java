package game;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class RobotModel extends Observable implements Serializable, Observer {
    private transient Timer m_timer = createTimer();
    private volatile double m_fieldWidth = 100f;
    private volatile double m_fieldHeight = 100f;
    private volatile TargetModel targetModel;
    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    private volatile double m_robotDirection = 0;
    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;
    private double distanceToTarget;
    private final Cell[][] field = new Cell[3][3];
    private Cell currentCell;
    private GameType gameType;

    private static Timer createTimer()
    {
        return new Timer("events generator", true);
    }

    public RobotModel(TargetModel targetModel, GameType gameType) {
        this.gameType = gameType;
        this.targetModel = targetModel;
        this.targetModel.addObserver(this);
        distanceToTarget = distance(targetModel.getTargetPositionX(), targetModel.getTargetPositionY(),
                m_robotPositionX, m_robotPositionY);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 20);
    }

    public void setMetadata() {
        this.targetModel.addObserver(this);
        m_timer = createTimer();
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 20);
    }

    public void setFieldSize(float fieldWidth, float fieldHeight) {
        m_fieldWidth = fieldWidth;
        m_fieldHeight = fieldHeight;
    }

    public int getRobotPositionX() {
        return round(m_robotPositionX);
    }

    public int getRobotPositionY() {
        return round(m_robotPositionY);
    }

    public double getRobotDirection() {
        return m_robotDirection;
    }

    private void updateDistanceToTarget(double distance) {
        distanceToTarget = distance >= 0.5 ? distance : 0;
    }

    public double getDistanceToTarget() {
        return distanceToTarget;
    }

    protected void onModelUpdateEvent()
    {
        updateDistanceToTarget(distance(targetModel.getTargetPositionX(), targetModel.getTargetPositionY(),
                m_robotPositionX, m_robotPositionY));
        if (Double.compare(distanceToTarget, 0) == 0) {
            notifyWithGameType(GameType.POINT);
            return;
        }
        var angleToTarget = angleTo(m_robotPositionX, m_robotPositionY,
                targetModel.getTargetPositionX(), targetModel.getTargetPositionY());
        var angularVelocity = angleToTarget > m_robotDirection ? maxAngularVelocity :
                angleToTarget < m_robotDirection ? -maxAngularVelocity : 0;
        moveRobot(maxVelocity, angularVelocity, 10);
        notifyWithGameType(GameType.BLOCKS);
    }

    private void notifyWithGameType(GameType gameType) {
        if (this.gameType == gameType) {
            notifyIfChangeCell();
        }
        else {
            notifyObservers();
            setChanged();
        }
    }

    private void notifyIfChangeCell() {
        var cell = checkInWhichCellRobotLocates();
        if (cell != currentCell) {
            currentCell = cell;
            notifyObservers(cell.getFilterName());
            setChanged();
        }
    }

    private Cell checkInWhichCellRobotLocates() {
        for (Cell[] cells : field) {
            for (Cell cell : cells) {
                if (cell.isPointInCell(m_robotPositionX, m_robotPositionY)) {
                    return cell;
                }
            }
        }
        return currentCell;
    }

    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection  + angularVelocity * duration) -
                        Math.sin(m_robotDirection));
        if (!Double.isFinite(newX))
        {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }
        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection  + angularVelocity * duration) -
                        Math.cos(m_robotDirection));
        if (!Double.isFinite(newY))
        {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }
        m_robotPositionX = changeLimits(newX, 0, m_fieldWidth);
        m_robotPositionY = changeLimits(newY, 0, m_fieldHeight);
        m_robotDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
    }

    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private static double changeLimits(double value, double min, double max)
    {
        if (value < min)
            return max;
        if (value > max)
            return min;
        return value;
    }

    private static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }

    private static int round(double value)
    {
        return (int)(value + 0.5);
    }

    private static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    @Override
    public void update(Observable observable, Object o) {
        updateDistanceToTarget(distance(targetModel.getTargetPositionX(), targetModel.getTargetPositionY(),
                m_robotPositionX, m_robotPositionY));
    }
}
