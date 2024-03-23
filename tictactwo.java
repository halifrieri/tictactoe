package tictactwo;

import java.util.Scanner;

public class TicTacTwo {

    static String player1 = "x";
    static String player2 = "O";
    static String currentPlayer = player1;
    static String name1;
    static String name2;
    static int gameType;
public static void main(String[] args) {
    String[][] board = new String[3][3];
    Scanner input = new Scanner(System.in);

      System.out.println("Player 1, what is your name?");
        name1 = input.nextLine();
        System.out.println("Player 2, what is your name?");
        name2 = input.nextLine();

    if(!name1.equals("AiBot1")&&!name2.equals("AiBot2"))
    {gameType = 1;}
    if(name1.equals("AiBot1"))
    {gameType = 2; name1 = "AiBot";}
    if(name2.equals("AiBot2"))
       {gameType = 2; name2 = "AiBot";}
    if(name1.equals("AiBot1")&&name2.equals("AiBot2"))
    {gameType = 3;}

    fillboard(board);
    printboard(board);

    while (true) {
        takeTurn(board, currentPlayer, gameType);
        printboard(board);

        String winner = checkWinner(board);
        if (winner != null) {
            System.out.println(winner + " wins!");
            break;
        } else if (tiegame(board)) {
            System.out.println("It's a tie!");
            break;
        }
        switchPlayer();
    }
}

    public static void fillboard(String[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = "*";
    }

    public static void printboard(String[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }System.out.println();
    }

   public static void takeTurn(String[][] board, String player, int gameType) {
    if (gameType == 1) {
        Scanner input = new Scanner(System.in);
        int row, col;
        while (true) {
            System.out.println(currentPlayer + ", select a row (0-2):");
            row = input.nextInt();
            System.out.println(currentPlayer + ", select a column (0-2):");
            col = input.nextInt();

            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col].equals("*")) {
                board[row][col] = player;
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    } else if ((gameType == 2 && (name1.equals("AiBot") && currentPlayer.equals(player1) || name2.equals("AiBot") && currentPlayer.equals(player2)))
               || gameType == 3) {
        AiBot(board, currentPlayer);
    } else {
        // This branch is for Human in a Human vs AiBot game
        Scanner input = new Scanner(System.in);
        int row, col;
        while (true) {
            System.out.println(currentPlayer + ", select a row (0-2):");
            row = input.nextInt();
            System.out.println(currentPlayer + ", select a column (0-2):");
            col = input.nextInt();

            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col].equals("*")) {
                board[row][col] = player;
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }
}

    public static String checkWinner(String[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].equals("*")) {
                return board[i][0];
            }
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) && !board[0][i].equals("*")) {
                return board[0][i];
            }
        }
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].equals("*")) {
            return board[0][0];
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].equals("*")) {
            return board[0][2];
        }
        return null;
    }

    public static boolean tiegame(String[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("*")) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void switchPlayer() {
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
    }

    public static int minimax(String[][] board, String player) {
        String winner = checkWinner(board);
        String opponent = (player.equals(player1)) ? player2 : player1;

        if (winner != null && winner.equals(player2)) {
            return +1;
        } else if (winner != null && winner.equals(player1)) {
            return -1;
        } else if (tiegame(board)) {
            return 0;
        }

        int best;
        if (player.equals(player2)) {
            best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].equals("*")) {
                        board[i][j] = player;
                        best = Math.max(best, minimax(board, opponent));
                        board[i][j] = "*";
                    }
                }
            }
        } else {
            best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].equals("*")) {
                        board[i][j] = player;
                        best = Math.min(best, minimax(board, opponent));
                        board[i][j] = "*";
                    }
                }
            }
        }
        return best;
    }

    public static void AiBot(String[][] board, String player) {
        int bestScore = (player.equals(player1) ? Integer.MAX_VALUE : Integer.MIN_VALUE);
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("*")) {
                    board[i][j] = player;
                    int currentScore = minimax(board, player.equals(player1) ? player2 : player1);
                    board[i][j] = "*";
                    if (player.equals(player1) && currentScore < bestScore) {
                        bestScore = currentScore;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    } else if (player.equals(player2) && currentScore > bestScore) {
                        bestScore = currentScore;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        board[bestMove[0]][bestMove[1]] = player;
    }
}