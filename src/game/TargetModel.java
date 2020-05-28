package game;

import java.awt.*;
import java.io.Serializable;
import java.util.Observable;

public class TargetModel extends Observable implements Serializable {
    private volatile int m_targetPositionX = 205;
    private volatile int m_targetPositionY = 205;

    public void setTargetPosition(Point p)
    {
        m_targetPositionX = p.x;
        m_targetPositionY = p.y;
        notifyObservers();
        setChanged();
    }

    public int getTargetPositionX() {
        return m_targetPositionX;
    }

    public int getTargetPositionY() {
        return m_targetPositionY;
    }
}
