package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ThreadInvio implements Runnable {

    private Scanner sc;
    private PrintWriter out;
    private volatile boolean puoInviare = true; // TRUE all'inizio

    public ThreadInvio(Socket socket) throws IOException {
        sc = new Scanner(System.in);
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void abilitaInvio() {
        puoInviare = true;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (sc.hasNextLine()) {
                    String message = sc.nextLine();

                    if (puoInviare) {
                        out.println(message);
                        puoInviare = false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Errore nell'invio.");
        }
    }
}
