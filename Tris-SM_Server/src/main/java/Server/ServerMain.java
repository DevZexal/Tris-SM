package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerMain {

	public static void main(String[] args) {
		final int PORT = 5500;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server avviato sulla porta " + PORT);

            while (true) {
            	
                
            }

        } catch (IOException e) {
            System.out.println("Errore server");
        }
	}

}
