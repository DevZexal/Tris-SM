package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadRicevi implements Runnable {
    private BufferedReader in;
    private Socket socket;

    public ThreadRicevi(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String messaggio;
            while ((messaggio = in.readLine()) != null) {
                
                if (messaggio.startsWith("PRINT_BOARD: ")) {
                    // Estrae la stringa della board (es. "X-O---X--")
                    String boardData = messaggio.replace("PRINT_BOARD: ", "");
                    printFormattedBoard(boardData);
                } 
                else if (messaggio.startsWith("GAME_OVER")) {
                    System.out.println("\n***************************");
                    System.out.println("      " + messaggio);
                    System.out.println("***************************");
                    break; // Termina il ciclo
                } 
                else {
                    // Messaggi generici (es. "YOUR_TURN", "Mossa ok", "TRIS-SM")
                    System.out.println("\n[SERVER]: " + messaggio);
                }
            }
            System.out.println("\nConnessione chiusa.");
            socket.close();
            System.exit(0); // Chiude l'applicazione al termine della partita

        } catch (IOException e) {
            System.out.println("Errore di ricezione o chiusura socket.");
        }
    }

    // Trasforma la stringa "X-O---X--" in una griglia 3x3 visiva
    private void printFormattedBoard(String data) {
        System.out.println("\n  SCACCHIERA:");
        System.out.println("    0 1 2"); // Indici colonne
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " | "); // Indice riga
            for (int j = 0; j < 3; j++) {
                System.out.print(data.charAt(i * 3 + j) + " ");
            }
            System.out.println("|");
        }
    }
}