import java.io.PrintStream;

public class Solver {

    private int[][] inputs;
    private final int SIZE = 9;
    private final int EMPTY = 0;


    public Solver(int[][] inputs) {
        this.inputs = inputs;
    }



    public int[][] returnResult() {
        if (solve()) { // the sudoku is solved, return the result
            return inputs;
        } else { // the sudoku is unsolvable
            return null;
        }
    }



    // check if the exist the same number in the same row
    private boolean checkRow(int row, int number) {
        for (int i = 0; i < SIZE; i++)
            if (inputs[row][i] == number)
                return false;

        return true;
    }


    // check if there exist the same number in the same column
    private boolean checkCol(int col, int number) {
        for (int i = 0; i < SIZE; i++)
            if (inputs[i][col] == number)
                return false;

        return true;
    }



    // check if there exist the same number in the 3x3 box
    private boolean checkBox(int row, int col, int number) {
        int x = (row / (SIZE / 3)) * (SIZE / 3);
        int y = (col / (SIZE / 3)) * (SIZE / 3);

        for (int i = x; i < x + 3; i++)
            for (int j = y; j < y + 3; j++)
                if (inputs[i][j] == number)
                    return false;

        return true;
    }


    // check if the number satisfies all the requirements
    private boolean isValid(int row, int col, int number) {
        return checkRow(row, number)  &&  checkCol(col, number)  &&  checkBox(row, col, number);
    }



    // Use a recursive BackTracking algorithm to solve the sudoku.
    public boolean solve() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                // we only perform change on empty cell
                if (inputs[row][col] == EMPTY) {
                    // the possible numbers for an empty cell is from 1 to 9
                    for (int number = 1; number <= SIZE; number++) {
                        // check if the number satisfies the requirements
                        if (isValid(row, col, number)) {
                            inputs[row][col] = number;

                            // start back tracing
                            if (solve()) {
                                return true;
                            } else {
                                inputs[row][col] = EMPTY;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true; // sudoku solved
    }
}
