package cmpt213.A4.model;
import java.util.*;
/**
 * A class for creating the cells that make up the game board. Contains getters and setters to retrieve and update
 * information for the corresponding cell such as if it's part of fill, it's location, etc.
 */
public class Cell {
    private int currentNumber;
    private boolean isMiddle;
    private boolean isFill;
    private int rowIndex;
    private int columnIndex;

    public int getRowIndex() {
        return  rowIndex;
    }
    public int getColumnIndex() {
        return columnIndex;
    }
    public int getCurrentNumber() {
        return currentNumber;
    }
    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public boolean isMiddle() {
        return isMiddle;
    }

    public boolean isFill() {
        return isFill;
    }
    public void setFill(boolean isFill) {
        this.isFill = isFill;
    }
    public void setMiddle(boolean middle) {
        isMiddle = middle;
    }

    //create cell with a random number 0-15
    public Cell(int rowIndex, int columnIndex, int maxbound) {
        Random random = new Random();
        this.currentNumber = random.nextInt(maxbound);
        this.isMiddle = false;
        this.isFill = false;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }
}
