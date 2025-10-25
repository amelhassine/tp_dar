package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        if (args.length >= 1) host = args[0];
        if (args.length >= 2) port = Integer.parseInt(args[1]);

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 5000);
            System.out.println("Connecté à " + host + ":" + port);

            BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            String welcome = serverIn.readLine();
            System.out.println("Serveur: " + welcome);

            String input;
            while (true) {
                System.out.print("vous -> ");
                input = userIn.readLine();
                if (input == null) break;
                serverOut.println(input);
                String response = serverIn.readLine();
                if (response == null) break;
                System.out.println("Serveur: " + response);
                if ("BYE".equals(response)) break;
            }
        } catch (IOException e) {
            System.err.println("Erreur client: " + e.getMessage());
        }
    }
}
