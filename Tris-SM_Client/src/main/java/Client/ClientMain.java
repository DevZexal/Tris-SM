package Client;

import java.io.IOException;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        final String IP = "127.0.0.1";
        final int PORT = 5500;

        try {
            Socket clientSocket = new Socket(IP, PORT);
            System.out.println("Connesso al server Tris-SM!");

            Thread invio = new Thread(new ThreadInvio(clientSocket));
            Thread ricevi = new Thread(new ThreadRicevi(clientSocket));

            // Impostiamo ricevi come prioritario per vedere subito i messaggi di benvenuto
            ricevi.start();
            invio.start();

        } catch (IOException e) {
            System.out.println("Impossibile connettersi al server su " + IP + ":" + PORT);
        }
    }
}