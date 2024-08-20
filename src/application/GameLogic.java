package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic {
	Random random = new Random();
	double randomDouble;
	int randomInt;
	private final char AI = 'X';
	private final char PLAYER = 'O';
    
	char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
	
	public static char[][] mainBoard = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
	public static int [] lastPlayedSpot = {0, 0};
	
	void playerMove(int row, int col) {
    	if (board[row][col] == ' ') board[row][col] = PLAYER;
    }
    void aiMove() {
    	randomDouble = random.nextDouble() * 100;
    	if (randomDouble >= UserInterfaceController.getCurrentDifficulty()) {
    		randomInt = random.nextInt(getAvailableMoves().size());
    		int[] randomMove = getAvailableMoves().get(randomInt);
    		board[randomMove[0]][randomMove[1]] = AI;
    		System.out.println("AI random move");
    	} else {
    		int[] bestMove = minimax(6, AI);
    		board[bestMove[1]][bestMove[2]] = AI;
    		System.out.println("AI best move");
    	}
    }
    
    static void resetGame() {
    	mainBoard = new char[][] {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
    	lastPlayedSpot = new int[] {0, 0};
    }
	
    private int[] minimax(int depth, char turn) {
        List<int[]> availableMoves = getAvailableMoves();

        int bestScore = (turn == AI) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if (availableMoves.isEmpty() || depth == 0 || isWinning(PLAYER) || isWinning(AI)) {
            bestScore = evaluate();
        } else {
            for (int[] move : availableMoves) {
                board[move[0]][move[1]] = turn;
                if (turn == AI) {
                    currentScore = minimax(depth - 1, PLAYER)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {
                    currentScore = minimax(depth - 1, AI)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                board[move[0]][move[1]] = ' ';
            }
        }
        return new int[]{bestScore, bestRow, bestCol};
    }
	
    List<int[]> getAvailableMoves() {
        List<int[]> availableMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    availableMoves.add(new int[]{i, j});
                }
            }
        }
        return availableMoves;
    }
    
    List<int[]> getAvailableMovesMain() {
        List<int[]> availableMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mainBoard[i][j] == ' ') {
                    availableMoves.add(new int[]{i, j});
                }
            }
        }
        return availableMoves;
    }
	
    private int evaluate() {
        int score = 0;
        if (isWinning(AI)) {
            score = 10;
        } else if (isWinning(PLAYER)) {
            score = -10;
        }
        return score;
    }
	
    boolean isWinning(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }
    
    boolean isWinningMain(char player) {
        for (int i = 0; i < 3; i++) {
            if (mainBoard[i][0] == player && mainBoard[i][1] == player && mainBoard[i][2] == player) {
                return true;
            }
            if (mainBoard[0][i] == player && mainBoard[1][i] == player && mainBoard[2][i] == player) {
                return true;
            }
        }
        if (mainBoard[0][0] == player && mainBoard[1][1] == player && mainBoard[2][2] == player) {
            return true;
        }
        if (mainBoard[0][2] == player && mainBoard[1][1] == player && mainBoard[2][0] == player) {
            return true;
        }
        return false;
    }
    
    boolean isGameOver() {
        if (isWinning(PLAYER)) {
            System.out.println("You win!");
            return true;
        }
        if (isWinning(AI)) {
            System.out.println("AI wins!");
            return true;
        }
        if (getAvailableMoves().isEmpty()) {
            System.out.println("It's a draw!");
            return true;
        }
        return false;
    }
    boolean isGameOverMain() {
        if (isWinningMain(PLAYER)) {
            System.out.println("You win!");
            return true;
        }
        if (isWinningMain(AI)) {
            System.out.println("AI wins!");
            return true;
        }
        if (getAvailableMovesMain().isEmpty()) {
            System.out.println("It's a draw!");
            return true;
        }
        return false;
    }
}
