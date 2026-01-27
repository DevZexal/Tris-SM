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
            inviaMessaggio("-------------");
            inviaMessaggio("TRIS-SM");
            inviaMessaggio("-------------");
            inviaMessaggio("Scegli la modalita' di gioco");
            inviaMessaggio("[0] Multi-player(PvP) [1] Single-Player(PvC)");

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
            System.out.println("Connessione persa");
        }
    }

    private void gestisciPartitaPvP() throws IOException {

        while (game.isGameActive()) {

            if (game.getCurrentPlayer() == mioSegno) {

                inviaMessaggio("PRINT_BOARD: " + game.getBoardString());
                inviaMessaggio("YOUR_TURN");

                String mossa = in.readLine();
                String[] coordinate = mossa.split(" ");
                int r = Integer.parseInt(coordinate[0]);
                int c = Integer.parseInt(coordinate[1]);

                if (game.makeMove(r, c)) {

                    avversario.inviaMessaggio("OPPONENT_MOVED " + r + " " + c);

                    if (!game.isGameActive()) {
                        String winner = game.getWinner();

                        if (String.valueOf(mioSegno).equals(winner)) {
                            inviaMessaggio("GAME_OVER Hai vinto!");
                            avversario.inviaMessaggio("GAME_OVER Hai perso!");
                        } else if ("DRAW".equals(winner)) {
                            inviaMessaggio("GAME_OVER Pareggio");
                            avversario.inviaMessaggio("GAME_OVER Pareggio");
                        }
                    }

                } else {
                    inviaMessaggio("Mossa non valida");
                }
            }
        }
    }

    private void gestisciPartitaPvC() throws IOException {
        inviaMessaggio("Modalità PvC non ancora implementata");
    }
}
