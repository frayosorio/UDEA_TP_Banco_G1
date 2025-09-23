package modelos;

public abstract class Cuenta {

    private String titular;
    private String numero;
    private double saldo;

    public Cuenta(String titular, String numero, double saldo) {
        this.titular = titular;
        this.numero = numero;
        this.saldo = saldo;
    }

    public String getTitular() {
        return titular;
    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    protected void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean consignar(double cantidad) {
        if (cantidad > 0) {
            this.saldo += cantidad;
            return true;
        }
        return false;
    }

    public abstract boolean retirar(double cantidad);

    @Override
    public String toString() {
        return numero + "  " + titular;
    }

}
