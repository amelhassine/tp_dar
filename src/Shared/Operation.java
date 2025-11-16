package Shared;
import java.io.Serializable;

public class Operation implements Serializable {
    private double a;
    private double b;
    private char operateur;

    public Operation(double a, double b, char operateur) {
        this.a = a;
        this.b = b;
        this.operateur = operateur;
    }

    public double getA() { return a; }
    public double getB() { return b; }
    public char getOperateur() { return operateur; }

    @Override
    public String toString() {
        return a + " " + operateur + " " + b;
    }
}
