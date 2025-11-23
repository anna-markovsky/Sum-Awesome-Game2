package cmpt213.A4.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Game {
    public int fillStrength = 0;
    private int turnsUntilAttack;
    private int MIN_TURNS = 3;
    private int MAX_TURNS = 5;
    public int NUM_OPPONENTS = 3;
    public int DEFAULT_MAX_BOUND = 16;
    public int OPPONENT_HEALTH_DEFAULT = 50;
    public int OPPONENT_HEALTH_LOW = 50;
    public int OPPONENT_HEALTH_HIGH = 200;
    private int OPPONENT_DAMAGE = 50;
    private Player player = new Player();
    private List<Opponent> opponents;
    private GameBoard board = new GameBoard(DEFAULT_MAX_BOUND);
    private FillConditions fillConditions = new FillConditions();
    private RingManager ringManager = new RingManager();
    private List<PlayerAttackObserver> observers = new ArrayList<PlayerAttackObserver>();
    private List<PlayerMoveObserver> moveObservers = new ArrayList<PlayerMoveObserver>();
    private List<MatchCompleteObserver> matchObservers = new ArrayList<>();
    private final WeaponManager weaponManager;
    public int currentMaxBound;


    public Game() {
        generateOpponents();
        setTurnsUntilAttack();

        ringManager.equipRing(ringManager.getAllRings().get(0));
        ringManager.equipRing(ringManager.getAllRings().get(0));
        ringManager.equipRing(ringManager.getAllRings().get(0));
        this.weaponManager = new WeaponManager(this);
        this.currentMaxBound = DEFAULT_MAX_BOUND;

    }

    public void equipRing(int ringIndex){
        ringManager.equipRing(ringManager.getAllRings().get(ringIndex));
    }

    public List<Ring> getAllRingsList(){
        return ringManager.getAllRings();
    }

    public List<Weapon> getAllWeaponsList() {
        return weaponManager.getAllWeapons();
    }

    public Player getPlayer() {
        return player;
    }

    public FillConditions getFillConditions() {
        return fillConditions;
    }

    public void generateOpponents() {
        opponents = new ArrayList<>();
        for (int i = 0; i < NUM_OPPONENTS; i++) {
            opponents.add(new Opponent(OPPONENT_HEALTH_DEFAULT, OPPONENT_DAMAGE));
        }
    }
    public void updateHealthOpponents(int health) {
         List<Opponent> opponents = getOpponents();
         for (int i = 0; i < NUM_OPPONENTS; i++) {
             Opponent currentOpponent = opponents.get(i);
             currentOpponent.setHealth(health);
         }
     }
    public void setTurnsUntilAttack() {
        Random random = new Random();
        int turnsUntilAttack = random.nextInt(MAX_TURNS - MIN_TURNS + 1) + MIN_TURNS;
        this.turnsUntilAttack = turnsUntilAttack;
    }


    public List<Opponent> getOpponents() {
        return opponents;
    }
    public List<Opponent> getAliveOpponents() {
        return opponents.stream()
                .filter(o -> o.getHealth() > 0)
                .collect(Collectors.toList());
    }

    public Cell getCellState(int row, int col) {
        return board.getCell(row, col);
    }

    public void updateFill(Cell cell) {
        cell.setFill(true);
        player.addNumFills();
        fillConditions.addCellValue(cell.getCurrentNumber());
        fillConditions.setLastSelectedColIndex(cell.getColumnIndex());
        fillStrength += cell.getCurrentNumber();
        turnsUntilAttack -= 1;
        notifyMoveObservers();
    }

    public Opponent selectRandomOpponent(){
        List<Opponent> opponents = getAliveOpponents();

        Random random = new Random();
        int index = random.nextInt(opponents.size());
        return opponents.get(index);
    }

    public boolean opponentReadyForAttack() {
        return (turnsUntilAttack == 0);
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

    public void setCurrentMaxBound(int maxBound) {
        this.currentMaxBound = maxBound;
    }
    public void updateMaxBoundCustom(int maxbound) {
        setCurrentMaxBound(maxbound);
        board.setMaxBound(maxbound);
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
           return matchingCells.get(index);
        }
        throw new IllegalArgumentException();
    }

    public void resetGameConditions(boolean isMatchComplete) {
        if(isMatchComplete) {
            this.currentMaxBound = DEFAULT_MAX_BOUND;

        }
        //this.currentMaxBound = DEFAULT_MAX_BOUND;
        board = new GameBoard(currentMaxBound);
        fillStrength = 0;
        fillConditions = new FillConditions();
        setTurnsUntilAttack();
    }

    public void startNewMatch() {
        resetGameConditions(true);
        generateOpponents();
        player.resetPlayerHealth();
    }

    public boolean playerReadyForAttack() {
        if (board.isWholeBoardFill()) {
            notifyObservers();
            return true;
        }
        return false;
    }

    public void attackPlayer() {
        //notifyObservers();
        Opponent opponent = selectRandomOpponent();
        player.decreaseHealth(opponent.getDamage());
        setTurnsUntilAttack();
        notifyMoveObservers();

       // notifyObservers();

    }

    public List<String> getActivationMsgs(){
        return ringManager.getActivationMsgs();
    }

    public void attackOpponent() {
        fillStrength = ringManager.calculateTotalMultiplier(fillStrength);

        Weapon equippedWeapon = player.getWeapon();
        boolean activated = equippedWeapon.getWeaponCondition().isActive(fillConditions);

        if (activated) {
            System.out.println(equippedWeapon.getWeaponName() + " effect activated!");
            applyWeaponDamage(equippedWeapon);

        } else {
            System.out.println(equippedWeapon.getWeaponName() + " effect did NOT activate.");
        }
        List<Double> percentDamageOpponents = equippedWeapon.getPercentDamageOpponents();
        //first attack selected opponent based on index
        int opponentIndex = fillConditions.getLastSelectedColIndex();
        Opponent selectedOpponent = opponents.get(opponentIndex);
        selectedOpponent.takeDamage(fillStrength);
        player.addDamageDealt(fillStrength);
        //extracted(percentDamageOpponents);
        //player.dropWeapon();
       notifyMoveObservers();

        //notifyObservers();
    }

    private void applyWeaponDamage(Weapon equippedWeapon) {
        List<Double> percentDamageOpponents = equippedWeapon.getPercentDamageOpponents();

        for (int i = 0; i < percentDamageOpponents.size(); i++){
            Opponent currentOpponent = opponents.get(i);
            double weaponDamage = currentOpponent.getHealth() * percentDamageOpponents.get(i);
            currentOpponent.takeDamage((int) weaponDamage);
            player.addDamageDealt((int) weaponDamage);

        }
    }

    public void updateMatchStatus(boolean matchStatus) {
        notifyMatchObservers(matchStatus);
    }

    public boolean hasUserWon() {
        int numOpponentsDead = 0;
        for (int i = 0; i < opponents.size(); i++) {
            if (opponents.get(i).getHealth() <= 0) {
                numOpponentsDead++;
            }
        }
        if (numOpponentsDead == NUM_OPPONENTS){
            updateMatchStatus(true);
            System.out.println("you win, would you like to equip ?");



            return true;
        }
        return false;
    }

    public boolean hasOpponentWon() {
        if(player.didPlayerLose()) {
            updateMatchStatus(false);
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

    private void notifyMatchObservers(boolean matchWon) {
        for (MatchCompleteObserver observer : matchObservers) {
            observer.stateChanged(matchWon);
        }
    }
}