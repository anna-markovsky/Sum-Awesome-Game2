package cmpt213.A4.model;
import cmpt213.A4.model.FillConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public int playerHealth = 600;
    private int fillStrength = 0;
    private Player player = new Player();
    private List<Opponent> opponents;
    private GameBoard board = new GameBoard();
    private FillConditions fillConditions = new FillConditions();
    private List<PlayerAttackObserver> observers = new ArrayList<PlayerAttackObserver>();

    public Game() {

    }

    public FillConditions getFillConditions() {
        return fillConditions;
    }
    public void generateOpponents() {
        opponents = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            opponents.add(new Opponent(200, 30));
        }
    }

    public List<Opponent> getOpponents() {
        return opponents;
    }

    public Cell getCellState(int row, int col) {
        return board.getCell(row, col);
    }
    public void updateFill(Cell cell) {
        cell.setFill(true);
        fillConditions.setNumFills(fillConditions.getNumFills() + 1);
        fillConditions.addCellValue(cell.getCurrentNumber());
        fillStrength += cell.getCurrentNumber();
    }
    public void updateFillTime(double durationSeconds) {
        fillConditions.setSecondsTaken(durationSeconds);
    }
    public void updateMiddleCell(Cell cell) {
        board.replaceMiddleCell(cell);
    }
    public void updateMatchingCellPosition(Cell cell) {
        board.replaceMatchingCell(cell);
    }
    //function for player move
    //first check if there are any matching cells
    //if 1 matching cell, add current cell to fill, and replace middle with matching cell
    //and replace matching cell with random cell.
    //if there is >1 matching cell, do same steps but randomly select matching cell out of all matches
    //then check if the whole board is part of the fill if yes attack
    // if sum is wrong, then make the opponent attack
    public Cell getMatchingCell(int userInput) {
        Cell middleCell = board.getMiddleCellState();
        int middleValue = middleCell.getCurrentNumber();
        List<Cell> matchingCells = new ArrayList<>();
        for(int row = 0; row < board.NUM_ROWS; row++){
            for(int col = 0; col < board.NUM_COLS; col++) {
                Cell curCell = getCellState(row, col);
                int cellValue = curCell.getCurrentNumber();
                if(curCell != middleCell) {
                    int difference = userInput - middleValue;
                    if (difference == cellValue && !curCell.isFill()) {
                        System.out.println("Found match");

                        return curCell;
                    }
                    if(difference == cellValue && curCell.isFill()) {
                        matchingCells.add(curCell);
                    }
                }
            }
        }
        if(!matchingCells.isEmpty()) {
           Random random = new Random();
           int index = random.nextInt(matchingCells.size());
           System.out.println("index = "+ index);
           return matchingCells.get(index);
        }
        throw new IllegalArgumentException();
    }

    public void replaceBoardIfFilled() {
        if (board.isWholeBoardFill()) {
            this.board = new GameBoard();
        }
    }
    public boolean playerReadyForAttack() {
        if (board.isWholeBoardFill()) {
            System.out.println("Player is attacking opponent with " + fillStrength + "damage applied");
            notifyObservers();
            return true;
        }
        return false;
    }
    public void attackOpponent() {
        System.out.println("Player is attacking opponent with " + fillStrength + " damage applied");
        notifyObservers();
    }

    public boolean hasUserWon() {
        //TODO Create this method
        return false;
    }
    public boolean hasOpponentWon() {
        return player.didPlayerLose();
    }

    /*
     * Functions to support being observable.
     * ------------------------------------------------------
     */
    public void addObserver(PlayerAttackObserver observer) {
        observers.add(observer);
    }
    private void notifyObservers() {
        for (PlayerAttackObserver observer : observers) {
            observer.stateChanged();
        }
    }

}