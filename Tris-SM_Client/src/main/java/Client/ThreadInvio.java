package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ThreadInvio implements Runnable {
    private Scanner sc;
    private PrintWriter out;

    public ThreadInvio(Socket socket) throws IOException {
        sc = new Scanner(System.in);
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Legge l'input dell'utente (scelta modalità o "r c")
                if (sc.hasNextLine()) {
                    String message = sc.nextLine();
                    out.println(message);
                    
                    if (message.equalsIgnoreCase("QUIT")) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Errore nell'invio.");
        }
    }
}