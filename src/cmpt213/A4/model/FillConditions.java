package cmpt213.A4.model;

import java.util.*;

public class FillConditions {
    private List<Integer> selectedCellValues = new ArrayList<>();
    private double secondsTaken;
    //private int numFills;
    private boolean isAscending;
    private boolean isDescending;
    private int lastSelectedColIndex;

    public FillConditions(){
        //this.selectedCellValues = new int[0];
        this.secondsTaken = 0.0;
        //this.numFills = 0;
        this.isAscending = false;
        this.isDescending = false;
    }

    public double getSecondsTaken() {

        return secondsTaken;
    }
    public void setSecondsTaken(double secondsTaken) {
        this.secondsTaken = secondsTaken;
    }


    public int getLastSelectedColIndex() {
        return lastSelectedColIndex;
    }
    public void setLastSelectedColIndex(int lastSelectedColIndex) {
        this.lastSelectedColIndex = lastSelectedColIndex;
    }
    public void addCellValue(int cellNumber) {
       selectedCellValues.add(cellNumber);
    }

    public int calculateNumFills() {
        if(selectedCellValues.isEmpty()) {
            return 0;
        }
        return selectedCellValues.size();

    }
    public boolean checkNumFills(int minNumberFills) {
        if(calculateNumFills() >= minNumberFills) {
            return true;
        }
        return false;
    }
    public boolean checkTime(int minSeconds) {
        if(getSecondsTaken() <= minSeconds && getSecondsTaken() > 0) {
            return true;
        }
        return false;
    }

    public boolean checkAddedCellsAscending() {
        if(selectedCellValues.isEmpty()) {
            return false;
        }
        int minValue = selectedCellValues.get(0);
        boolean allAscending = true;
        for (int i = 0; i < selectedCellValues.size(); i++) {
            if (selectedCellValues.get(i) < minValue) {
                return false;
            }
            minValue = selectedCellValues.get(i);
        }
        return true;
    }
    public boolean checkAddedCellsDescending() {
        if(selectedCellValues.isEmpty()) {
            return false;
        }
        int maxValue = selectedCellValues.get(0);
        boolean allDescending = true;
        for (int i = 0; i < selectedCellValues.size(); i++) {
            if (selectedCellValues.get(i) > maxValue) {
                return false;
            }
            maxValue = selectedCellValues.get(i);
        }
        return true;
    }

}
