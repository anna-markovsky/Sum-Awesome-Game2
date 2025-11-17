package cmpt213.A4.model;
import cmpt213.A4.model.FillConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Game {
    public int playerHealth = 600;
    public int fillStrength = 0;
    private Player player = new Player();
    private List<Opponent> opponents;
    private GameBoard board = new GameBoard();
    private FillConditions fillConditions = new FillConditions();
    private List<PlayerAttackObserver> observers = new ArrayList<PlayerAttackObserver>();
    private List<PlayerMoveObserver> moveObservers = new ArrayList<PlayerMoveObserver>();
    private List<MatchCompleteObserver> matchObservers = new ArrayList<>();
    public Game() {
        generateOpponents();

        //player.equipWeapon(new NullWeapon());
        //this.opponents = generateOpponents();
    }
    public Player getPlayer() {
        return player;
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
        fillConditions.setLastSelectedColIndex(cell.getColumnIndex());
        fillStrength += cell.getCurrentNumber();
        notifyMoveObservers();
    }

    public int getFillStrength(){
        return fillStrength;
    }

    public int getPlayerHealth(){
        return playerHealth;
    }

    public void updateFillTime(double durationSeconds) {

        System.out.println("DEBUG: updateFillTime called, seconds = " + durationSeconds);
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

    public void resetGameConditions() {
        if (board.isWholeBoardFill()) {
            board = new GameBoard();
            fillStrength = 0;
            fillConditions = new FillConditions();
        }
        fillStrength = 0;
    }



    public void startNewMatch() {
        resetGameConditions();
        generateOpponents();
        player.resetPlayerHealth();
    }
    public boolean playerReadyForAttack() {
        if (board.isWholeBoardFill()) {
            //notifyObservers();
            return true;
        }
        return false;
    }


    public void attackOpponent() {
        notifyObservers();
        Weapon equippedWeapon = player.getWeapon();
        List<Double> damages = equippedWeapon.getDamageOpponents();
        //first attack selected opponent based on index
        int opponentIndex = fillConditions.getLastSelectedColIndex();
        Opponent selectedOpponent = opponents.get(opponentIndex);
        selectedOpponent.takeDamage(fillStrength);
        System.out.println("opponent " + opponentIndex + " has " + selectedOpponent.getHealth() + " health");
        System.out.println("damages size" + damages.size());
        for (int i = 0;i < damages.size();i++){
            Opponent currentOpponent = opponents.get(i);
            //      currentOpponent.takeDamage(fillStrength);
            double weaponDamage = currentOpponent.getHealth() * damages.get(i);
            currentOpponent.takeDamage((int) weaponDamage);
            System.out.println("player attacks opponent " + i + " with " + weaponDamage);

        }
        System.out.println("Player is attacking opponent with " + equippedWeapon.getWeaponName());
        //opponents.get(col).takeDamage(fillStrength);
        //notifyObservers();
    }


    public boolean hasUserWon() {
        int numOpponentsDead = 0;
        for (int i = 0; i < opponents.size(); i++) {
            if (opponents.get(i).getHealth() <= 0) {
                numOpponentsDead++;
            }
        }
        if (numOpponentsDead == 3){
            notifyMatchObservers();
            return true;
        }
        return false;
    }

    public boolean hasOpponentWon() {
        if(player.didPlayerLose()) {
            notifyMatchObservers();
            return true;
        }
        return false;
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
            observer.attackStateChanged();
        }
    }

    public void addMoveObserver(PlayerMoveObserver observer) {
        moveObservers.add(observer);
    }
    private void notifyMoveObservers() {
        for (PlayerMoveObserver observer : moveObservers) {
            observer.moveStateChanged();
        }
    }
    public void addMatchObserver(MatchCompleteObserver observer) {
        matchObservers.add(observer);
    }
    private void notifyMatchObservers() {
        for (MatchCompleteObserver observer : matchObservers) {
            observer.stateChanged();
        }
    }


}