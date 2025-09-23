package modelos;

public class Transaccion {
    private Cuenta cuenta;
    private String tipo;
    private double valor;
    private double saldo;
    private boolean rechazada;

    public Transaccion(Cuenta cuenta, String tipo, double valor, double saldo, boolean rechazada) {
        this.cuenta = cuenta;
        this.tipo = tipo;
        this.valor = valor;
        this.saldo = saldo;
        this.rechazada = rechazada;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean isRechazada() {
        return rechazada;
    }

}
