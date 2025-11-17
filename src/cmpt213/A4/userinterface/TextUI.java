package cmpt213.A4.userinterface;

import cmpt213.A4.model.*;

import java.util.List;
import java.util.Scanner;

public class TextUI {
    private static final String NOTFILL_SYMBOL = " ";
    private static final String FILL_SYMBOL = "_";
    private static final int ROW_LENGTH = 3;
    private final Game game;

    public TextUI(Game game) {
        this.game = game;
    }

    public void playGame() {
        displayWelcome();
        displayBoard(false);
        long startTime = System.nanoTime();

        while (gameRunning()) {
            doPlayerTurn();
            if (game.playerReadyForAttack()) {
                //isAttackDone = true;
                //doOpponentTurn();
                long endTime = System.nanoTime();
                long elapsedTimeNanos = endTime - startTime;
                double durationSeconds = (double) elapsedTimeNanos / 1_000_000_000.0;
                game.updateFillTime(durationSeconds);
                System.out.println("Method execution time: " + durationSeconds + " seconds");
                game.attackOpponent();
                game.resetGameConditions();
            }
        displayHealthOpponents();
        displayBoard(false);
        displayPlayerInfo();

        doWonOrLost();
        }
    }

    private boolean gameRunning() {
        return !game.hasUserWon() && !game.hasOpponentWon();
    }

    private void displayWelcome() {
        System.out.println("------------------------");
        System.out.println("Welcome to Sum-Awesome!");
        System.out.println("by Anna Markovsky");
        System.out.println("------------------------");
        System.out.println();
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

        //System.out.println("[ " + player.getPlayerHealth() + " ]       Fill Strength: " + game.fillStrength);
    }
    //TODO fix the column format
    public void displayBoard(boolean revealBoard) {
        System.out.println();
        //System.out.println("Game Board:");
        // Print rows:
        for (int row = 0; row < GameBoard.NUM_ROWS; row++) {
            String output = "";
            for (int col = 0; col < GameBoard.NUM_COLS; col++) {
                Cell cell = game.getCellState(row, col);
                int symbol = cell.getCurrentNumber();
                if(cell.isFill()){
                    String format = FILL_SYMBOL  + symbol + FILL_SYMBOL;
                    output += String.format("%-7s", format);
                    //output +=  FILL_SYMBOL  + symbol + FILL_SYMBOL + "   ";
                }
                else {
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
            String input = scanner.nextLine().trim();
            switch (input.toLowerCase()) {
                case "gear":
                    System.out.println("Player Gear Inventory: ");
                    // TODO handle gear
                    break;
                case "cheat":
                    System.out.println("Cheat activated (TODO)");
                    // TODO handle cheat
                    break;
                case "stats":
                    System.out.println("Player Stats: ");
                    // TODO handle stats
                    break;
                case "new":
                    System.out.println("Starting new game...");
                    // TODO handle new
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
    private void doPlayerTurn() {
        int sum = getPlayerMove();
        try {
            Cell matchingCell = game.getMatchingCell(sum);
            game.updateFill(matchingCell);

            game.updateMiddleCell(matchingCell);
            game.updateMatchingCellPosition(matchingCell);

            System.out.print("Player Turn was successful. ");
        }
        catch(IllegalArgumentException e) {
            System.out.println("Invalid sum. Opponent will attack.");
        }
    }
    private void doWonOrLost() {
        if (game.hasUserWon()) {
            System.out.println("Congratulations! You won!");
        } else if (game.hasOpponentWon()) {
            System.out.println("I'm sorry, you have no health left! They win!");
        } else {
            assert false;
        }
        displayBoard(true);
    }

}
