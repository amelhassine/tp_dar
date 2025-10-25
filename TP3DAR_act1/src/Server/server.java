package Server;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import ClientProcess.clientprocess;

public class server {
    public static final int PORT = 5000;
    private ServerSocket serverSocket;
    private AtomicInteger clientCounter = new AtomicInteger(0);
    private ExecutorService pool = Executors.newCachedThreadPool();

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur démarré sur le port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                int clientId = clientCounter.incrementAndGet();
                SocketAddress remoteAddr = clientSocket.getRemoteSocketAddress();
                System.out.printf("Client n° %d connecté depuis %s %n", clientId, remoteAddr);
                pool.execute(new clientprocess(clientSocket, clientId));
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur: " + e.getMessage());
        } finally {
            stop();
        }
    }

    public void stop() {
        try {
            if (serverSocket != null && !serverSocket.isClosed())
                serverSocket.close();
            pool.shutdownNow();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'arrêt: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new server().start();
    }
}
