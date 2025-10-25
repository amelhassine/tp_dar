package ClientProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class clientprocess implements Runnable {
    private Socket socket;
    private int clientId;

    public clientprocess(Socket socket, int clientId) {
        this.socket = socket;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true))
        {
            out.println("WELCOME! clientNumber:" + clientId + " adresse IP:" + socket.getRemoteSocketAddress());

            String line;
            while ((line = in.readLine()) != null) {
                System.out.printf("Reçu du client %d: %s%n", clientId, line);
                if ("exit".equalsIgnoreCase(line.trim())) {
                    out.println("BYE");
                    break;
                }
                out.println("SERVER_ECHO (client " + clientId + "): " + line);
            }
        } catch (IOException e) {
            System.err.println("Erreur avec client " + clientId + ": " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Connexion avec client " + clientId + " fermée.");
            } catch (IOException ignore) {}
        }
    }
}
