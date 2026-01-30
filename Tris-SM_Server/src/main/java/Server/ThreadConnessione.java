package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadConnessione implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private ListaClient listaClient;

    // Variabili di gioco
    private Game game;
    private ThreadConnessione avversario;
    private char mioSegno;
    private char oppSegno;

    public ThreadConnessione(Socket client, ListaClient listaClient) throws IOException {
        this.client = client;
        this.listaClient = listaClient;
        this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.out = new PrintWriter(client.getOutputStream(), true);
    }

    public void inviaMessaggio(String msg) {
        out.println(msg);
    }

    public void setAvversario(ThreadConnessione avversario, Game game, char segno) {
        this.avversario = avversario;
        this.game = game;
        this.mioSegno = segno;
        inviaMessaggio("Partita iniziata! Sei il giocatore " + segno);
    }

    @Override
    public void run() {
        try {
        	inviaMessaggio("=================================");
        	inviaMessaggio("        BENVENUTO IN TRIS-SM");
        	inviaMessaggio("=================================");
        	inviaMessaggio("GUIDA RAPIDA:");
        	inviaMessaggio("- Il gioco si svolge su una griglia 3x3");
        	inviaMessaggio("- Le righe e le colonne vanno da 0 a 2");
        	inviaMessaggio("- Per fare una mossa scrivi: r c");
        	inviaMessaggio("  Esempio: 1 2  (riga 1, colonna 2)");
        	inviaMessaggio("- Se vuoi abbandonare la partita scrivi: EXIT");
        	inviaMessaggio("");
        	inviaMessaggio("MODALITA' DI GIOCO:");
        	inviaMessaggio("[0] Multi-player (PvP)");
        	inviaMessaggio("    → Giochi contro un altro giocatore online");
        	inviaMessaggio("[1] Single-player (PvC)");
        	inviaMessaggio("    → Giochi contro il computer");
        	inviaMessaggio("");
        	inviaMessaggio("Inserisci 0 o 1 e premi INVIO");
        	inviaMessaggio("=================================");
            String scelta = in.readLine();

            if ("0".equals(scelta)) {
                listaClient.cercaPartita(this);

                // attende che il Game venga assegnato
                while (game == null) {
                    Thread.sleep(100);
                }
                
                gestisciPartitaPvP();
            } else {
                gestisciPartitaPvC();
            }

        } catch (Exception e) {
            System.out.println("Connessione persa: " + client.getRemoteSocketAddress());
        } finally {
            try {
                client.close();
            } catch (IOException ignored) {}

            listaClient.removeClient(client);
        }
    }

    private void gestisciPartitaPvP() throws IOException {

    while (game.isGameActive()) {

        if (game.getCurrentPlayer() == mioSegno) {

            inviaMessaggio("PRINT_BOARD: " + game.getBoardString());
            inviaMessaggio("YOUR_TURN " + mioSegno);

            String mossa = in.readLine();
            if (mossa == null || mossa.equalsIgnoreCase("EXIT")) {
                inviaMessaggio("Hai abbandonato la partita.");
                return;
            }

            String[] parti = mossa.trim().split(" ");
            if (parti.length != 2) {
                inviaMessaggio("Formato non valido. Usa: r c");
                continue;
            }

            int r, c;
            try {
                r = Integer.parseInt(parti[0]);
                c = Integer.parseInt(parti[1]);
            } catch (NumberFormatException e) {
                inviaMessaggio("Inserisci solo numeri da 0 a 2");
                continue;
            }

            if (!game.makeMove(r, c)) {
                inviaMessaggio("Mossa non valida. Riprova.");
            }

            if (game.makeMove(r, c)) {

                String board = game.getBoardString();
                inviaMessaggio("PRINT_BOARD: " + board);
                avversario.inviaMessaggio("PRINT_BOARD: " + board);
                
                avversario.inviaMessaggio( mioSegno + " ha giocato " + r + " " + c);

                if (!game.isGameActive()) {
                    String winner = game.getWinner();

                    if (String.valueOf(mioSegno).equals(winner)) {
                        inviaMessaggio("GAME_OVER Hai vinto!");
                        avversario.inviaMessaggio("GAME_OVER Hai perso!");
                    } else {
                        inviaMessaggio("GAME_OVER Pareggio");
                        avversario.inviaMessaggio("GAME_OVER Pareggio");
                    }
                }

            } else {
                inviaMessaggio("Mossa non valida");
            }

        } else {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {}
        }
    }
}


    private void gestisciPartitaPvC() throws IOException {

        game = new Game();
        mioSegno = 'X';       // giocatore umano
        oppSegno = 'O';       // computer

        inviaMessaggio("Partita PvC iniziata!");
        inviaMessaggio("Tu sei X, il computer e' O");

        while (game.isGameActive()) {

        	// TURNO UMANO
		    if (game.getCurrentPlayer() == mioSegno) {
		
		        inviaMessaggio("PRINT_BOARD: " + game.getBoardString());
		        inviaMessaggio("YOUR_TURN " + mioSegno);
		
		        String mossa = in.readLine();
		        if (mossa == null || mossa.equalsIgnoreCase("EXIT")) {
		            inviaMessaggio("Hai abbandonato la partita.");
		            inviaMessaggio("PRINT_BOARD: " + game.getBoardString());
		            return;
		        }
		
		        String[] parti = mossa.trim().split(" ");
		        if (parti.length != 2) {
		            inviaMessaggio("Formato non valido. Usa: r c");
		            continue;
		        }
		
		        int r, c;
		        try {
		            r = Integer.parseInt(parti[0]);
		            c = Integer.parseInt(parti[1]);
		        } catch (NumberFormatException e) {
		            inviaMessaggio("Inserisci solo numeri da 0 a 2");
		            continue;
		        }
		
		        if (!game.makeMove(r, c)) {
		            inviaMessaggio("Mossa non valida. Riprova.");
		            continue;
		        }
		
		        inviaMessaggio("PRINT_BOARD: " + game.getBoardString());
		    }
		
		    // TURNO COMPUTER
		    else {
		        int[] bestMove = trovaMossaMigliore(game);
		        game.makeMove(bestMove[0], bestMove[1]);
		        inviaMessaggio("Il computer ha giocato: " + bestMove[0] + " " + bestMove[1]);
		    }
		
		    // Controllo fine partita
		    if (!game.isGameActive()) {
		        String w = game.getWinner();
		        if ("DRAW".equals(w)) {
		            inviaMessaggio("GAME_OVER Pareggio");
		        } else if (w.equals(String.valueOf(mioSegno))) {
		            inviaMessaggio("GAME_OVER Hai vinto!");
		        } else {
		            inviaMessaggio("GAME_OVER Hai perso!");
		        }
		    }
		}

    }
    
    private int[] trovaMossaMigliore(Game game) {

        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        char[][] board = copiaBoard(game.getBoard());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (board[i][j] == '-') {
                    board[i][j] = oppSegno;

                    int score = minimax(board, false);

                    board[i][j] = '-';

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(char[][] board, boolean isMaximizing) {

        String result = controllaVittoria(board);
        if (result != null) {
            if (result.equals(String.valueOf(oppSegno))) return 10;
            if (result.equals(String.valueOf(mioSegno))) return -10;
            return 0;
        }

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = oppSegno;
                        best = Math.max(best, minimax(board, false));
                        board[i][j] = '-';
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = mioSegno;
                        best = Math.min(best, minimax(board, true));
                        board[i][j] = '-';
                    }
                }
            }
            return best;
        }
    }

    private char[][] copiaBoard(char[][] originale) {
        char[][] copia = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                copia[i][j] = originale[i][j];
        return copia;
    }

    private String controllaVittoria(char[][] b) {

        for (int i = 0; i < 3; i++) {
            if (b[i][0] != '-' && b[i][0] == b[i][1] && b[i][1] == b[i][2])
                return String.valueOf(b[i][0]);

            if (b[0][i] != '-' && b[0][i] == b[1][i] && b[1][i] == b[2][i])
                return String.valueOf(b[0][i]);
        }

        if (b[0][0] != '-' && b[0][0] == b[1][1] && b[1][1] == b[2][2])
            return String.valueOf(b[0][0]);

        if (b[0][2] != '-' && b[0][2] == b[1][1] && b[1][1] == b[2][0])
            return String.valueOf(b[0][2]);

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (b[i][j] == '-') return null;

        return "DRAW";
    }


}
