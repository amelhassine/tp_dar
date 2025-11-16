package Server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import ClientProcess.ClientProcess;

public class Server {
    public static final int PORT = 5000;
    private ServerSocket serverSocket;
    private ExecutorService pool = Executors.newCachedThreadPool();

    public static AtomicInteger compteurGlobal = new AtomicInteger(0);

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur calculatrice démarré sur le port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouveau client : " + clientSocket.getInetAddress());

                pool.execute(new ClientProcess(clientSocket));
            }

        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        } finally {
            stop();
        }
    }

    public static synchronized void incrementerCompteur() {
        int val = compteurGlobal.incrementAndGet();
        System.out.println(">>> Nombre total d’opérations effectuées : " + val);
    }

    public void stop() {
        try {
            if (serverSocket != null && !serverSocket.isClosed())
                serverSocket.close();
            pool.shutdownNow();
        } catch (IOException e) {
            System.err.println("Erreur lors de l’arrêt : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
