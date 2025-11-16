package Server;

//ServerChatUDP.java
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class ServerChatUDP {

 public static void main(String[] args) {
     try {
         DatagramSocket socket = new DatagramSocket(1234);
         System.out.println(" Serveur de chat UDP lancé...");

         byte[] buffer = new byte[1024];
         Set<SocketAddress> clients = new HashSet<>();

         while (true) {
             DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
             socket.receive(packet);

             SocketAddress clientAddr = packet.getSocketAddress();
             clients.add(clientAddr); 

             String msg = new String(packet.getData(), 0, packet.getLength());
             System.out.println(" " + clientAddr.toString() + " → " + msg);

             for (SocketAddress addr : clients) {
                 if (!addr.equals(clientAddr)) {
                     DatagramPacket sendPacket =
                             new DatagramPacket(msg.getBytes(), msg.length(), addr);
                     socket.send(sendPacket);
                 }
             }
         }

     } catch (Exception e) {
         e.printStackTrace();
     }
 }
}
