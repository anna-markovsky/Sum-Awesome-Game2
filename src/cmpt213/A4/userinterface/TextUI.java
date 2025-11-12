package cmpt213.A4.userinterface;

import cmpt213.A4.model.Cell;
import cmpt213.A4.model.Game;
import cmpt213.A4.model.GameBoard;

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
        while (gameRunning()) {
            System.out.println("here");
            doPlayerTurn();
            if(game.playerReadyForAttack()){
                //doOpponentTurn();
            }
            displayBoard(false);
            System.out.println("test");
        }
        doWonOrLost();
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

    //TODO fix the column format
    public void displayBoard(boolean revealBoard) {
        System.out.println();
        System.out.println("Game Board:");
        // Print rows:
        for (int row = 0; row < GameBoard.NUM_ROWS; row++) {
            String output = "";
            for (int col = 0; col < GameBoard.NUM_COLS; col++) {
                Cell cell = game.getCellState(row, col);
                int symbol = cell.getCurrentNumber();
                if(cell.isFill()){
                    output +=  FILL_SYMBOL  + symbol + FILL_SYMBOL + "   ";
                }
                else {
                    output += NOTFILL_SYMBOL + symbol + NOTFILL_SYMBOL + "   ";
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
