package modelos;

public class Corriente extends Cuenta {
    private double sobregiro;

    public Corriente(String titular, String numero, double saldo, double sobregiro) {
        super(titular, numero, saldo);
        this.sobregiro = sobregiro;
    }

    @Override
    public boolean retirar(double cantidad) {
        if (getSaldo() + sobregiro >= cantidad) {
            setSaldo(getSaldo() - cantidad);
            System.out.println("Retiro existoso. Nuevo saldo:" + getSaldo());
            return true;
        } else {
            System.out.println("Fondos insuficientes para este retiro");
            return false;
        }
    }

    public double getSobregiro() {
        return sobregiro;
    }

}
