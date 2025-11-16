package Client;

import java.net.*;
import java.util.Scanner;

public class ClientChatUDP {

 public static void main(String[] args) {
     try {
         Scanner sc = new Scanner(System.in);

         System.out.print("Nom d'utilisateur : ");
         String username = sc.nextLine();

         DatagramSocket socket = new DatagramSocket();
         InetAddress serverIP = InetAddress.getByName("localhost");
         int serverPort = 1234;

         Thread receiveThread = new Thread(() -> {
             try {
                 byte[] buffer = new byte[1024];
                 while (true) {
                     DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                     socket.receive(packet);
                     String msg = new String(packet.getData(), 0, packet.getLength());
                     System.out.println("\n " + msg);
                     System.out.print("Message : ");
                 }
             } catch (Exception e) {
             }
         });

         receiveThread.start();

         while (true) {
             System.out.print("Message : ");
             String msg = sc.nextLine();
             String finalMessage = "[" + username + "] : " + msg;

             DatagramPacket packet = new DatagramPacket(
                     finalMessage.getBytes(),
                     finalMessage.length(),
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
