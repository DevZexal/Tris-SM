package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ListaClient {
    private ArrayList<Socket> listaSockets;
    private ThreadConnessione playerWaiting = null; // Sala d'attesa per PvP

    public ListaClient() {
        listaSockets = new ArrayList<Socket>();
    }

    public synchronized void addClient(Socket client) {
        listaSockets.add(client);
    }

    // Metodo che combina i player dalla lista dei client
    public synchronized void cercaPartita(ThreadConnessione attuale) {
    	if (playerWaiting == null) {
            playerWaiting = attuale;
            attuale.inviaMessaggio("In attesa di un altro giocatore...");
        } else {
            // Trovato avversario: crea il Game e accoppiali
            Game nuovaPartita = new Game();
            ThreadConnessione p1 = playerWaiting;
            ThreadConnessione p2 = attuale;

            p1.setAvversario(p2, nuovaPartita, 'X');
            p2.setAvversario(p1, nuovaPartita, 'O');

            playerWaiting = null; // Svuota la sala d'attesa
        }
    }
}