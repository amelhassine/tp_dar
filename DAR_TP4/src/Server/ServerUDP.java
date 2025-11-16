package Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class ServerUDP {
 public static void main(String[] args) {
     try {
         InetSocketAddress serverAddress = new InetSocketAddress("0.0.0.0", 1234);
         DatagramSocket socket = new DatagramSocket(serverAddress);

         System.out.println(" Serveur UDP en écoute sur le port 1234");

         byte[] buffer = new byte[1024];

         while (true) {
             DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
             socket.receive(packet);

             String msg = new String(packet.getData(), 0, packet.getLength());

             System.out.println(" Message reçu depuis " +
                     packet.getAddress().getHostAddress() +
                     ":" + packet.getPort() +
                     " → " + msg);
         }
     } catch (Exception e) {
         e.printStackTrace();
     }
 }
}
