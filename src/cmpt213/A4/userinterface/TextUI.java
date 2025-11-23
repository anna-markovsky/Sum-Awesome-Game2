package cmpt213.A4.userinterface;

import cmpt213.A4.model.*;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TextUI {
    private static final String NOTFILL_SYMBOL = " ";
    private static final String FILL_SYMBOL = "_";
    private static final int ROW_LENGTH = 3;
    private Game game;
    private static List<UserInterfaceObserver> observers = new ArrayList<UserInterfaceObserver>();

    //public Cell lastSelectedCell;

    public TextUI(Game game) {
        this.game = game;
    }

    public void playGame() {
        game.startNewMatch();
        displayWelcome();
        //displayBoard(false);
        long startTime = System.nanoTime();

        while (gameRunning()) {
            displayBoardWithInfo();
            //long startTime = System.nanoTime();
            doPlayerTurn();

            if (game.playerReadyForAttack()) {
                long endTime = System.nanoTime();
                long elapsedTimeNanos = endTime - startTime;
                double durationSeconds = (double) elapsedTimeNanos / 1_000_000_000.0;
                game.updateFillTime(durationSeconds);
                System.out.println("Method execution time: " + game.getFillConditions().getSecondsTaken() + " seconds");
                game.attackOpponent();
                printRingActivationMsgs();
                game.resetGameConditions(false);
                startTime = System.nanoTime();

            }
            if (game.opponentReadyForAttack()) {
                game.attackPlayer();
            }

            doWonOrLost();
        }
    }

    private void printRingActivationMsgs(){
        List<String> msgs = game.getActivationMsgs();
        for (String msg : msgs) {
            System.out.println(msg);
        }
    }

    private void displayRingBonus(){
        System.out.println();
    }
    private boolean gameRunning() {
        return true;
        //return !game.hasUserWon() && !game.hasOpponentWon();
    }

    private void displayWelcome() {
        System.out.println("------------------------");
        System.out.println("Welcome to Sum-Awesome!");
        System.out.println("by Anna Markovsky");
        System.out.println("------------------------");
        System.out.println();
    }

    public void displayBoardWithInfo() {
        displayHealthOpponents();
        displayBoard();
        displayPlayerInfo();
    }
    public void displayHealthOpponents() {
        List<Opponent> opponents = game.getOpponents();
        opponents.stream().forEach(opponent -> {
            String format = "[" + opponent.getHealth() + "]";
            String output = String.format("%-7s", format);
            System.out.print(output);
        });
    }

    public void displayPlayerInfo() {
        Player player = game.getPlayer();
        String format = "[" + player.getPlayerHealth() + "]";
        String firstHalf = String.format("%-7s", format);

        String secondHalf = "Fill Strength: " + game.fillStrength;
        System.out.println(firstHalf + secondHalf);
    }

    public void displayBoard() {
        System.out.println();

        for (int row = 0; row < GameBoard.NUM_ROWS; row++) {
            String output = "";
            for (int col = 0; col < GameBoard.NUM_COLS; col++) {
                Cell cell = game.getCellState(row, col);
                int symbol = cell.getCurrentNumber();
                if (cell.isFill()) {
                    String format = FILL_SYMBOL + symbol + FILL_SYMBOL;
                    output += String.format("%-7s", format);
                } else {
                    String format = NOTFILL_SYMBOL + symbol + NOTFILL_SYMBOL;
                    output += String.format("%-7s", format);

                }
            }
            System.out.println(output);
            System.out.println();
        }
    }

    private int getPlayerMove() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
                    System.out.print("Enter a sum (or 'gear', 'cheat', 'stats', 'new'): ");
                    String input = scanner.nextLine().trim().toLowerCase();
                    String inputWithArgs[] = input.split("\\s+", 2);

            switch (inputWithArgs[0]) {
                        case "gear":
                            System.out.println("Player Gear Inventory: ");
                            System.out.println("Weapon: " + game.getPlayer().outputWeaponInventory());
                            break;
                case "cheat":
                    if (inputWithArgs.length == 1) {
                        System.out.println("Cheat argument not provided.");
                    } else {
                        handleCheatArguments(inputWithArgs[1]);
                    }
                    break;

                case "stats":
                    System.out.println("Player Stats: ");
                    notifyUIObservers();
                    break;
                case "new":
                    System.out.println("Starting new game...");
                    game.updateMatchStatus(false);
                    game.startNewMatch();
                    displayBoardWithInfo();
                    break;
                default:
                    try {
                        int sum = Integer.parseInt(input);
                        return sum;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid entry. Please enter a number or a valid command.");
                    }
            }
        }
    }

    private void handleCheatArguments(String args) {
        String inputWithArgs[] = args.split("\\s+");

        switch (inputWithArgs[0]) {
            case "lowhealth":
                game.updateHealthOpponents(game.OPPONENT_HEALTH_LOW_PERCENTAGE);
                break;
            case "highhealth":
                game.updateHealthOpponents(game.OPPONENT_HEALTH_HIGH_PERCENTAGE);
                break;
            case "weapons":
                selectWeaponToEquip(inputWithArgs[1]);
                break;
            case "max":
                selectMaxFillValue(inputWithArgs[1]);
        }

    }
    private void selectMaxFillValue(String args) {
        try {
            int maxbound = Integer.parseInt(args);
            if (maxbound <= 0) {
                System.out.println("The max bound must be greater than 0");
            }
            else {
                game.updateMaxBoundCustom(maxbound+1);
            }
        }catch (NumberFormatException e) {
            System.out.println("Invalid entry. Please enter a number or a valid command.");
        }

    }
    private void selectWeaponToEquip(String args) {
        try {
            int weaponNum = Integer.parseInt(args);
            List<Weapon> availableWeapons = game.getAllWeaponsList();
            if(weaponNum == 0){
                game.getPlayer().dropWeapon();
            }
            if(weaponNum > 0 && weaponNum <= availableWeapons.size()){
                Weapon selectedWeapon = availableWeapons.get(weaponNum - 1);
                System.out.println("weapon is equipped here textui");
                game.getPlayer().equipWeapon(selectedWeapon);
            }
            else {
                throw new NumberFormatException("Invalid entry. No weapon exists for provided number");
            }
        } catch (NumberFormatException e) {
        }
    }

    private void doPlayerTurn() {
        int sum = getPlayerMove();
        try {
            Cell matchingCell = game.getMatchingCell(sum);
            game.updateFill(matchingCell);

            game.updateMiddleCell(matchingCell);
            game.updateMatchingCellPosition(matchingCell);

            System.out.print("Player Turn was successful. ");
            //this.lastSelectedCell = matchingCell;
            //return matchingCell;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid sum. Opponent will attack.");
            game.attackPlayer();
        }
        //return null;
    }


    private void promptGiveRandomItem(){
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        if(random.nextInt(2) == 0){
            int weaponIndex = random.nextInt(game.getAllWeaponsList().size());
            Weapon newWeapon = game.getAllWeaponsList().get(weaponIndex);
            System.out.println("You've found " + newWeapon.getWeaponName() + "! Do you want to equip it? (y/n): ");
            String input = scanner.nextLine().toLowerCase();
            if(input.equals("y")){
                game.getPlayer().equipWeapon(newWeapon);
            }
        }else {
            int ringIndex = random.nextInt(game.getAllRingsList().size());
            Ring newRing = game.getAllRingsList().get(ringIndex);
            System.out.println("You've found " + newRing.getName() + "! Do you want to equip it? (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if(input.equals("y")){
                List<Ring> equippedRings = game.getEquippedRings();
                for(Ring ring : equippedRings){
                    System.out.println(ring.getName());
                }
                System.out.println("Which ring do you want to replace? (1-3): ");
                String x = scanner.nextLine();
                if(x.equals("1")){
                    Ring unequipRing = equippedRings.get(0);
                    game.unequipRing(unequipRing);
                    game.equipRing(ringIndex);
                }else if(x.equals("2")){
                    Ring unequipRing = equippedRings.get(1);
                    game.unequipRing(unequipRing);
                    game.equipRing(ringIndex);
                }else if(x.equals("3")){
                    Ring unequipRing = equippedRings.get(2);
                    game.unequipRing(unequipRing);
                    game.equipRing(ringIndex);
                }else{
                    System.out.println("Invalid number. No rings replaced.");
                }
            }
        }
    }

    private void doWonOrLost() {
        if (game.hasUserWon()) {
            System.out.println("Congratulations! You won!");
            promptGiveRandomItem();
            game.startNewMatch();
        } else if (game.hasOpponentWon()) {
            System.out.println("I'm sorry, you have no health left! They win!");
            game.startNewMatch();
        } else {
            assert false;
        }
    }


    /*
     * Functions to support being observable.
     * ------------------------------------------------------
     */
    public static void addUserInterfaceObserver(UserInterfaceObserver observer) {
        observers.add(observer);
    }

    private static void notifyUIObservers() {
        for (UserInterfaceObserver observer : observers) {
            observer.printRequestFromUI();
        }
    }
}
