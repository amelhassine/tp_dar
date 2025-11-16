package ClientProcess;

import Shared.Operation;
import Server.Server;

import java.io.*;
import java.net.*;

public class ClientProcess implements Runnable {
    private Socket socket;

    public ClientProcess(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject("Bienvenue sur le serveur calculatrice !");
            out.flush();

            Object obj;
            while ((obj = in.readObject()) != null) {
                if (obj instanceof Operation) {
                    Operation op = (Operation) obj;
                    double result = calculer(op);

                    Server.incrementerCompteur();

                    out.writeObject("Résultat : " + result);
                    out.flush();
                }
            }

        } catch (EOFException e) {
            System.out.println("Client déconnecté : " + socket.getInetAddress());
        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignore) {}
        }
    }

    private double calculer(Operation op) {
        double a = op.getA();
        double b = op.getB();
        char c = op.getOperateur();
        switch (c) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return b != 0 ? a / b : Double.NaN;
            default: return Double.NaN;
        }
    }
}
