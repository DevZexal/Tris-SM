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

        // Partita già finita
        if (!gameActive) {
            return false;
        }

        // Coordinate fuori dalla griglia
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return false;
        }

        // Casella già occupata
        if (board[row][col] != '-') {
            return false;
        }

        // Effettua la mossa
        board[row][col] = currentPlayer;

        // Controllo vittoria
        if (checkWin(row, col)) {
            gameActive = false;
            winner = String.valueOf(currentPlayer);
            return true;
        }

        // Controllo pareggio
        if (isBoardFull()) {
            gameActive = false;
            winner = "DRAW";
            return true;
        }

        // Cambia giocatore
        switchPlayer();
        return true;
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
