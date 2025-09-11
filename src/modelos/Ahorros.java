package modelos;

public class Ahorros extends Cuenta {
    private final double TASA_INTERES = 0.02;

    public Ahorros(String titular, String numero, double saldo) {
        super(titular, numero, saldo);
    }

    @Override
    public boolean retirar(double cantidad) {
        if (getSaldo() >= cantidad) {
            setSaldo(getSaldo() - cantidad);
            System.out.println("Retiro existoso. Nuevo saldo:" + getSaldo());
            return true;
        } else {
            System.out.println("Fondos insuficientes para este retiro");
            return false;
        }
    }

    public void abonarIntereses() {
        setSaldo(getSaldo() * (1 + TASA_INTERES));
        System.out.println("Intereses mensuales abonados. Nuevo saldo:" + getSaldo());
    }

}
