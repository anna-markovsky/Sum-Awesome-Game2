package cmpt213.A4.model;

import java.util.*;

public class FillConditions {
    private List<Integer> selectedCellValues = new ArrayList<>();
    private double secondsTaken;
    private int numFills;
    private boolean isAscending;
    private boolean isDescending;

    public FillConditions(){
        //this.selectedCellValues = new int[0];
        this.secondsTaken = 0.0;
        this.numFills = 0;

        this.isAscending = false;
        this.isDescending = false;
    }

    public double getSecondsTaken() {

        return secondsTaken;
    }
    public void setSecondsTaken(double secondsTaken) {

        this.secondsTaken = secondsTaken;
    }

    public int getNumFills() {

        return numFills;
    }
    public void setNumFills(int numFills) {

        this.numFills = numFills;
    }
    public void addCellValue(int cellNumber) {
       selectedCellValues.add(cellNumber);
    }
    public boolean checkAddedCellsAscending() {
        int minValue = selectedCellValues.get(0);
        boolean allAscending = true;
        for (int i = 0; i < selectedCellValues.size(); i++) {
            if (selectedCellValues.get(i) < minValue) {
                //allAscending = false;
                return false;
                //break;
            }
            minValue = selectedCellValues.get(i);
        }
        return true;
        //this.isAscending = allAscending;
    }
    public boolean checkAddedCellsDescending() {
        int maxValue = selectedCellValues.get(0);
        boolean allDescending = true;
        for (int i = 0; i < selectedCellValues.size(); i++) {
            if (selectedCellValues.get(i) > maxValue) {
                return false;
            }
            maxValue = selectedCellValues.get(i);
        }

        //this.isAscending = allDescending;
        return true;
    }
    public boolean isAscending() {

        return isAscending;
    }

    public boolean isDescending() {

        return isDescending;
    }
}
