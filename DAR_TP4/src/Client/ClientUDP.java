package Client;

//ClientUDP.java
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientUDP {
 public static void main(String[] args) {
     try {
         Scanner sc = new Scanner(System.in);

         System.out.print("Entrez votre nom d'utilisateur : ");
         String username = sc.nextLine();

         InetAddress serverIP = InetAddress.getByName("localhost");
         int serverPort = 1234;

         DatagramSocket socket = new DatagramSocket();

         while (true) {
             System.out.print("Message : ");
             String msg = sc.nextLine();

             String fullMessage = "[" + username + "] : " + msg;

             DatagramPacket packet = new DatagramPacket(
                     fullMessage.getBytes(),
                     fullMessage.length(),
                     serverIP,
                     serverPort
             );

             socket.send(packet);
         }
     } catch (Exception e) {
         e.printStackTrace();
     }
 }
}
