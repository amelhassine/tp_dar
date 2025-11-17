package rmiService;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ConversionImpl extends UnicastRemoteObject implements IConversion {

    public ConversionImpl() throws RemoteException {
        super();
    }

    @Override
    public double convertirMontant(double mt) throws RemoteException {
        double taux = 3.40;   // Exemple : 1 € = 3.40 DT
        return mt * taux;
    }
}
