package Server;


public class Game {
	
    private char[][] board;
    private char currentPlayer;
    private boolean gameActive;
    private String winner;

    public Game() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) board[i][j] = '-';
        }
        currentPlayer = 'X';
        gameActive = true;
        winner = null;
    }
    
    public synchronized boolean makeMove(int row, int col) {
        
    }

    private void switchPlayer() {
        if(currentPlayer == 'X') {
        	currentPlayer = 'O';
        }
        else
        	currentPlayer = 'X';
    }

    private boolean checkWin(int r, int c) {
        
    }

    private boolean isBoardFull() {
        
    }
    
    public void printBoard() {
    	
    }
    
    

    // Getters per il Server
    public char getCurrentPlayer() { return currentPlayer; }
    public boolean isGameActive() { return gameActive; }
    public String getWinner() { return winner; }
    public char[][] getBoard() { return board; }
    
}
