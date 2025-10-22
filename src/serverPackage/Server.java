package serverPackage;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            System.out.println(" Serveur en attente de connexion...");
            ServerSocket serveur = new ServerSocket(8000);
            Socket socket = serveur.accept();
            System.out.println(" Client connecté !");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String operation = in.readLine();
            System.out.println(" Opération reçue : " + operation);

            String resultat;

            try {
                String[] elements = operation.trim().split(" ");
                if (elements.length != 3) {
                    resultat = "Erreur : format invalide. Exemple correct : 55 * 25";
                } else {
                    double op1 = Double.parseDouble(elements[0]);
                    String operateur = elements[1];
                    double op2 = Double.parseDouble(elements[2]);

                    double res = 0;
                    switch (operateur) {
                        case "+": res = op1 + op2; break;
                        case "-": res = op1 - op2; break;
                        case "*": res = op1 * op2; break;
                        case "/":
                            if (op2 != 0)
                                res = op1 / op2;
                            else {
                                resultat = "Erreur : Division par zéro !";
                                out.println(resultat);
                                socket.close();
                                serveur.close();
                                return;
                            }
                            break;
                        default:
                            resultat = "Erreur : opérateur non reconnu.";
                            out.println(resultat);
                            socket.close();
                            serveur.close();
                            return;
                    }

                    resultat = "Résultat = " + res;
                }

            } catch (Exception e) {
                resultat = "Erreur de calcul : " + e.getMessage();
            }

            out.println(resultat);
            System.out.println(" Résultat envoyé au client : " + resultat);

            socket.close();
            serveur.close();

        } catch (IOException e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}
