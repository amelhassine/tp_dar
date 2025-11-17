package rmiServer;

import rmiService.ConversionImpl;
import rmiService.IConversion;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConversionServer {

    public static void main(String[] args) {
        try {
            // 1. Activer RMI Registry (sur le port 1099)
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println(">>> RMI Registry est actif sur le port 1099...");

            // 2. Créer une instance du service
            IConversion obj = new ConversionImpl();

            // 3. Publier le service dans l'annuaire
            registry.rebind("conversionService", obj);

            System.out.println(">>> Service de conversion publié avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
