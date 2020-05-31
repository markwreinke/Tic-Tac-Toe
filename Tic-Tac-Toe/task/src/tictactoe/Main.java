package tictactoe;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[][] board = createBoard();

        displayBoard(board);

        int xCo = -1;
        int yCo = -1;

        String xCoString;
        String yCoString;

        boolean xTurn = true;

        /*System.out.println("Test! " + determineState(board));
        System.out.println("Test! " + checkColumns(board));
        System.out.println("Test! " + checkDiagonals(board));
        System.out.println("Test! " + checkRows(board)); */

        while(determineState(board).equals("Game not finished")) {

            boolean validCoordinatesGiven = true;

            do {
               // try {
                validCoordinatesGiven = true;
                    System.out.print("Enter the coordinates: ");

                    xCoString = scanner.next();
                    yCoString = scanner.next();

                    if (!xCoString.matches("\\d") || !yCoString.matches("\\d"))  {
                        validCoordinatesGiven = false;
                        System.out.println("\nYou should enter numbers!");
                    } else {
                        xCo = Integer.parseInt(xCoString);
                        yCo = Integer.parseInt(yCoString);
                    }

                    if((xCo < 1 || xCo > 3 || yCo < 1 || yCo > 3) && validCoordinatesGiven) {
                        validCoordinatesGiven = false;
                        System.out.println("Coordinates should be from 1 to 3!");
                    }
                    else if (!spotIsFree(board, xCo, yCo)) {
                        validCoordinatesGiven = false;
                        System.out.println("This cell is occupied! Choose another one!");
                    }

                //} catch (NoSuchElementException e) {
                   // System.out.println("\nYou should enter numbers!");
               // }
            } while (!validCoordinatesGiven);

            if (xTurn) {
                board[transformYCo(yCo) - 1][xCo - 1] = 'X';
                xTurn = false;
            } else {
                board[transformYCo(yCo) - 1][xCo - 1] = 'O';
                xTurn = true;
            }

            displayBoard(board);

        }

        System.out.println(determineState(board));
    }

    public static int transformYCo(int yCo) {
        switch(yCo) {
            case 1: return 3;
            case 2: return 2;
            case 3: return 1;
            default: return -1;
        }
    }

    public static boolean spotIsFree(char[][] board, int xCo, int yCo) {
        if(board[transformYCo(yCo) - 1][xCo - 1] == 'X' || board[transformYCo(yCo) - 1][xCo - 1] == 'O') {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isComplete(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ' || board[i][j] == '_') {
                    return false;
                }
            }
        }
        return true;
    }
    public static String determineState(char[][] board) {
        char rows = checkRows(board);
        char columns = checkColumns(board);
        char diagonals = checkDiagonals(board);

        if (rows == 'B' || columns == 'B' || !validRatios(board)) {
            return "Impossible";
        } else if (rows == 'X' || columns == 'X' || diagonals == 'X') {
            return "X wins";
        } else if (rows == 'O' || columns == 'O' || diagonals == 'O') {
            return "O wins";
        } else if(isComplete(board)) {
            return "Draw";
        } else {
            return "Game not finished";
        }
    }

    public static boolean validRatios(char[][] board) {
        int numX = 0;
        int numY = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'X') {
                    numX++;
                } else if (board[i][j] == 'O') {
                    numY++;
                }
            }
        }

        if(Math.abs(numX - numY) > 1) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * Check the diagonals of the given board for three Xs or Os in a row
     * @param board
     * @return char
     *  'X' for a diagonal of X
     *  'O' for a column of O
     *  'N' for no diagonals
     */
    public static char checkDiagonals(char[][] board) {
        boolean xDiagonal = false;
        boolean oDiagonal = false;

        if((board[0][0] == board[1][1] && board[1][1] == board[2][2]) || (board[0][2] == board[1][1] && board[1][1] == board[2][0])) {
            if(board[1][1] == 'X') {
                xDiagonal = true;
            } else if(board[1][1] == 'O') {
                oDiagonal = true;
            }
        }

        if (xDiagonal) {
            return 'X';
        } else if (oDiagonal) {
            return 'O';
        } else {
            return 'N';
        }
    }


    /**
     * Check the columns of the given board for three Xs or Os in a column
     * @param board
     * @return char
     *  'B' for column of X and column of O
     *  'X' for a column of X
     *  'O' for a column of O
     *  'N' for no columns
     */
    public static char checkColumns(char[][] board) {
        int xcount = 0;
        int ocount = 0;

        boolean xColumn = false;
        boolean oColumn = false;

        for (int j = 0; j < board.length; j++) {
            for (int i = 0; i < board.length; i++) {
                if (board[i][j] == 'X') {
                    xcount++;
                } else if (board[i][j] == 'O') {
                    ocount++;
                }
            }
            if (xcount == 3) {
                xColumn = true;
            } else if (ocount == 3) {
                oColumn = true;
            }

            xcount = 0;
            ocount = 0;
        }

        if (xColumn && oColumn) {
            return 'B';
        } else if (xColumn) {
            return 'X';
        } else if (oColumn) {
            return 'O';
        } else {
            return 'N';
        }

    }


    /**
     * Check the rows of the given board for three Xs or Os in a row
     * @param board
     * @return char
     *  'B' for row of X and row of O
     *  'X' for a row of X
     *  'O' for a row of O
     *  'N' for no rows
     */
    public static char checkRows(char[][] board) {
        int xcount = 0;
        int ocount = 0;

        boolean xRow = false;
        boolean oRow = false;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'X') {
                    xcount++;
                } else if (board[i][j] == 'O') {
                    ocount++;
                }
            }
            if (xcount == 3) {
                xRow = true;
            } else if (ocount == 3) {
                oRow = true;
            }

            xcount = 0;
            ocount = 0;
        }

        if (xRow && oRow) {
            return 'B';
        } else if (xRow) {
            return 'X';
        } else if (oRow) {
            return 'O';
        } else {
            return 'N';
        }
    }

    public static char[][] createBoard() {
        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        return board;
    }

    public static void displayBoard(char[][] board) {
        System.out.println("---------");

        for(int i = 0; i < board.length; i++) {
            System.out.print("| ");

            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("|");

        }
        System.out.println("---------");
    }
}
