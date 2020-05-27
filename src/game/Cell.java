package game;

public class Cell {
    private double leftUpperPositionX;
    private double leftUpperPositionY;
    private double rightDownPositionX;
    private double rightDownPositionY;
    private FilterName filterName;

    public Cell(double leftX, double leftY, double rightX, double rightY, FilterName filterName) {
        setCellPosition(leftX, leftY, rightX, rightY);
        this.filterName = filterName;
    }

    public boolean isPointInCell(double positionX, double positionY) {
        return Double.compare(leftUpperPositionX, positionX) <= 0 &&
                Double.compare(positionX, rightDownPositionX) <= 0 &&
                Double.compare(leftUpperPositionY, positionY) <= 0 &&
                Double.compare(positionY, rightDownPositionY) <= 0;
    }

    public FilterName getFilterName() {
        return filterName;
    }

    public void setCellPosition(double leftX, double leftY, double rightX, double rightY) {
        leftUpperPositionX = leftX;
        leftUpperPositionY = leftY;
        rightDownPositionX = rightX;
        rightDownPositionY = rightY;
    }
}
