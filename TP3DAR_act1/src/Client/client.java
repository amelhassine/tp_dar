package Client;

import Shared.Operation;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            System.out.println((String) in.readObject());

            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.print("Entrer le 1er nombre (ou 'exit' pour quitter) : ");
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("exit")) break;

                double a = Double.parseDouble(input);
                System.out.print("Entrer le 2ème nombre : ");
                double b = Double.parseDouble(sc.nextLine());
                System.out.print("Entrer l’opération (+, -, *, /) : ");
                char op = sc.nextLine().charAt(0);

                Operation operation = new Operation(a, b, op);
                out.writeObject(operation);
                out.flush();

                String response = (String) in.readObject();
                System.out.println("Serveur → " + response);
            }

        } catch (Exception e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }
}
