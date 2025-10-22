package clientPackage;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 8000);
            System.out.println(" Connecté au serveur !");
            Scanner input = new Scanner(System.in);

            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            System.out.print(" Entrez votre opération : ");
            String operation = input.nextLine();

            if (!operation.matches("\\d+(\\.\\d+)?\\s*[+\\-*/]\\s*\\d+(\\.\\d+)?")) {
                System.out.println(" Format invalide ! Exemple correct : 12 + 5");
                s.close();
                return;
            }

            out.println(operation);
            String resultat = in.readLine();
            System.out.println(" Réponse " + resultat);

            s.close();
            input.close();

        } catch (IOException e) {
            System.out.println(" Erreur client : " + e.getMessage());
        }
    }
}
