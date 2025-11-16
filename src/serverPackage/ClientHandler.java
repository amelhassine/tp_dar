package serverPackage;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientHandler implements Runnable {
    private Socket socket;
    private AtomicInteger compteurGlobal;

    public ClientHandler(Socket socket, AtomicInteger compteurGlobal) {
        this.socket = socket;
        this.compteurGlobal = compteurGlobal;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String operation;
            while ((operation = in.readLine()) != null) {
                System.out.println("Opération reçue de " + socket.getInetAddress() + " : " + operation);
                String resultat = traiterOperation(operation);
                out.println(resultat);

                // incrémenter le compteur global
                int total = compteurGlobal.incrementAndGet();
                System.out.println("Total d'opérations traitées : " + total);
            }
        } catch (IOException e) {
            System.out.println("Client déconnecté : " + socket.getInetAddress());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }

    private String traiterOperation(String operation) {
        try {
            String[] elements = operation.trim().split(" ");
            if (elements.length != 3) {
                return "Erreur : format invalide. Exemple correct : 55 * 25";
            }

            double op1 = Double.parseDouble(elements[0]);
            String operateur = elements[1];
            double op2 = Double.parseDouble(elements[2]);

            double res;
            switch (operateur) {
                case "+": res = op1 + op2; break;
                case "-": res = op1 - op2; break;
                case "*": res = op1 * op2; break;
                case "/":
                    if (op2 == 0) return "Erreur : Division par zéro !";
                    res = op1 / op2;
                    break;
                default:
                    return "Erreur : opérateur non reconnu.";
            }

            return "Résultat = " + res;

        } catch (NumberFormatException e) {
            return "Erreur de calcul : " + e.getMessage();
        }
    }
}
