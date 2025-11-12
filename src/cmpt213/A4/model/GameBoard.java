package cmpt213.A4.model;
import java.util.*;
public class GameBoard {
    private final int MIDDLE_INDEX_ROW = 1;
    private final int MIDDLE_INDEX_COL = 1;
    //private final int GAMEBOARD_SIZE = 9;
    public static final int NUM_ROWS = 3;
    public static final int NUM_COLS = 3;
    private final Cell[][] board = new Cell[NUM_ROWS][NUM_COLS];

    /*public Iterator<Cell> iterator() {
        return new Iterator<Cell>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < cells.size();
            }

            @Override
            public Cell next() {
                assert hasNext();
                return cells.get(index++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }*/
    public Cell getCell(int row, int col) {
        return board[row][col];
    }
    //initialize game board
    public GameBoard() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Cell gameCell = new Cell(row, col);
                if (row == MIDDLE_INDEX_ROW && col == MIDDLE_INDEX_COL) {
                    gameCell.setMiddle(true);
                }
                board[row][col] = gameCell;
            }
        }
    }

    public Cell getMiddleCellState(){

        return board[MIDDLE_INDEX_ROW][MIDDLE_INDEX_COL];
    }

    public void replaceMiddleCell(Cell cell) {
        cell.setFill(false);
        board[MIDDLE_INDEX_ROW][MIDDLE_INDEX_COL] = cell;

    }
    public void replaceMatchingCell(Cell cell) {
        int row = cell.getRowIndex();
        int col = cell.getColumnIndex();

        Cell newCell = new Cell(row, col);
        newCell.setFill(true);
        board[row][col] = newCell;
    }
    public boolean isWholeBoardFill(){
        for  (int row = 0; row < NUM_ROWS; row++) {
            for  (int col = 0; col < NUM_COLS; col++) {
                if(row == MIDDLE_INDEX_ROW && col == MIDDLE_INDEX_COL) {
                    continue;
                }
                if (!board[row][col].isFill()) {
                    return false;
                }
            }
        }
        return true;
    }

}
