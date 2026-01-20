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

    public synchronized void addClient(Socket c) {
        listaSockets.add(c);
    }

    // Metodo che combina i player dalla lista dei client
    public synchronized void cercaPartita(ThreadConnessione attuale) {
        
    }
}