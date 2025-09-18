package modelos;

public class Credito extends Cuenta {

    private double valorPrestado;
    private double tasa;
    private double plazo;
    private double valorRetirado;

    public Credito(String titular, String numero, double valorPrestado, double tasa, double plazo) {
        super(titular, numero, 0);
        this.valorPrestado = valorPrestado;
        this.tasa = tasa;
        this.plazo = plazo;
    }

    @Override
    public boolean retirar(double cantidad) {
        if (valorRetirado < valorPrestado && cantidad <= valorPrestado - valorRetirado) {
            valorRetirado += cantidad;
            return true;
        } else {
            System.out.println("Ya fue retirado todo el capital del préstamo o la cantidad supera el disponible");
            return false;
        }
    }

    public double getCuota() {
        double t = tasa / 100;
        return valorPrestado * Math.pow(1 + t, plazo) * t / (Math.pow(1 + t, plazo) - 1);
    }

    public void pagar(double cantidad) {
        if (getSaldo() < valorPrestado) {
            var intereses = (valorPrestado - getSaldo()) * tasa;
            var abonoCapital = cantidad - intereses;
            consignar(abonoCapital);
        } else {
            System.out.println("Ya la deuda está pagada");
        }
    }

    public double getValorPrestado() {
        return valorPrestado;
    }

    public double getTasa() {
        return tasa;
    }

    public double getPlazo() {
        return plazo;
    }

    public double getValorRetirado() {
        return valorRetirado;
    }

}
