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

    protected boolean incrementarSaldo(double cantidad) {
        if (cantidad <= 0) {
             System.out.println("Cantidad no valida");
            return false;
        }
        this.saldo += cantidad;
        return true;
    }

    // metodos abstractos
    public abstract String[] mostrarValores();
    public abstract ResultadoTransaccionDto procesarTransaccion(TipoTransaccion tipo, double valor);

    @Override
    public String toString() {
        return numero + "  " + titular;
    }

}
