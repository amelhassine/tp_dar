package Client;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import Shared.Operation;
public class client {
	public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connecté au serveur.");

            Scanner sc = new Scanner(System.in);
            System.out.print("Entrez le premier nombre : ");
            double op1 = sc.nextDouble();
            System.out.print("Entrez l’opérateur (+, -, *, /) : ");
            char op = sc.next().charAt(0);
            System.out.print("Entrez le deuxième nombre : ");
            double op2 = sc.nextDouble();
            Operation operation = new Operation(op1, op, op2);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(operation);
            System.out.println("Objet envoyé : " + operation);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            double resultat = (double) in.readObject();
            System.out.println("Résultat reçu du serveur : " + resultat);

            sc.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	

}
