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
    	
    	char p = currentPlayer;
    	
    	// --Controllo per riga
        if (board[r][0] == p && board[r][1] == p && board[r][2] == p) {
        	return true;
        }
        
        // --Controllo per colonna
        if (board[0][c] == p && board[1][c] == p && board[2][c] == p) {
        	return true;
        }
        
        // --Controllo della diagonale principale
        if (r == c && board[0][0] == p && board[1][1] == p && board[2][2] == p) {
        	return true;
        }
        // --Controllo della diagonale secondaria
        if(r + c == 2 && board[2][0] == p && board [1][1] == p && board[0][2] == p) {
        	return true;
        }
    	
        return false;
    }

    private boolean isBoardFull() {
 
        for (int i = 0; i < 3; i++) {
        	for (int j = 0; j < 3; j++) {
        		if (board[i][j]== '-') {
        			return false;
        		}
        	}
        }
        return true;
    }
    
    public void printBoard() {
    	for(int i = 0; i < 3; i++) {
    		for (int j = 0; j < 3; j++){
    			System.out.print(board[i][j]+ " ");
    		}
    		System.out.println();
    	}
    }
    
    

    // Getters per il Server
    public char getCurrentPlayer() { return currentPlayer; }
    public boolean isGameActive() { return gameActive; }
    public String getWinner() { return winner; }
    public char[][] getBoard() { return board; }
    
}
