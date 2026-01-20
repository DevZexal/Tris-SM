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
    private boolean mioTurno;

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
        this.mioTurno = (segno == 'X'); // Inizia sempre X
        inviaMessaggio("Partita Iniziata! Sei il giocatore " + segno);
    }

    public void run() {
        try {
            // --- FASE PROTOCOLLO INIZIALE ---
            inviaMessaggio("TRIS-SM");
            inviaMessaggio("Scegli la modalita' di gioco");
            inviaMessaggio("[0] Multi-player(PvP) [1] Single-Player(PvC)");
            
            String scelta = in.readLine();

            if ("0".equals(scelta)) {
                listaClient.cercaPartita(this);
                gestisciPartitaPvP();
            } else {
                gestisciPartitaPvC();
            }

        } catch (IOException e) {
            System.out.println("Connessione persa");
        }
    }

    private void gestisciPartitaPvP() throws IOException {
        
    }

    private void gestisciPartitaPvC() throws IOException {
        
    }
}