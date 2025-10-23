package Server;
import java.io.*;
import java.net.*;
import Shared.Operation;

public class server {
	public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur démarré sur le port " + port + "...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connecté.");
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Operation op = (Operation) in.readObject();
            System.out.println("Opération reçue : " + op);

            double resultat = 0;
            switch (op.getOperateur()) {
                case '+': resultat = op.getOp1() + op.getOp2(); break;
                case '-': resultat = op.getOp1() - op.getOp2(); break;
                case '*': resultat = op.getOp1() * op.getOp2(); break;
                case '/':
                    if (op.getOp2() != 0)
                        resultat = op.getOp1() / op.getOp2();
                    else
                        System.out.println("Erreur : division par zéro !");
                    break;
                default:
                    System.out.println("Opérateur non reconnu !");
            }
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(resultat);
            System.out.println("Résultat envoyé au client : " + resultat);

            socket.close();
            System.out.println("Serveur arrêté.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	

}
