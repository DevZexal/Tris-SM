package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerMain {

	public static void main(String[] args) {
		final int PORT = 5500;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
        	
            ListaClient listaClient = new ListaClient();        
            System.out.println("Server Tris-SM Aperto sulla porta " + PORT);

            while (true) {
            	
            	Socket nuovoClient = serverSocket.accept();
            	listaClient.addClient(nuovoClient);
            	
            	Thread connessioneThread = new Thread(new ThreadConnessione(nuovoClient, listaClient));
                connessioneThread.start();
                
            }

        } catch (IOException e) {
            System.out.println("Errore di connessione");
        }
	}

}
