package rmiClient;

import rmiService.IConversion;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConversionClient {

    public static void main(String[] args) {
        try {
            // 1. Chercher le registre RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // 2. Récupérer la référence distante
            IConversion stub = (IConversion) registry.lookup("conversionService");

            // 3. Invoquer la méthode distante
            double euro = 500;
            double dinar = stub.convertirMontant(euro);

            System.out.println("---- Client RMI ----");
            System.out.println(euro + " € = " + dinar + " DT");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
