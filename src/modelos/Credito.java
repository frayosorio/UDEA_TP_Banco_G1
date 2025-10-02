package modelos;

import java.text.DecimalFormat;

public class Credito extends Cuenta {

    private double valorPrestado;
    private double tasa;
    private int plazo;
    private double valorRetirado;

    public Credito(String titular, String numero, double valorPrestado, double tasa, int plazo) {
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

    public boolean pagar(double cantidad) {
        if (getSaldo() < valorPrestado) {
            var intereses = (valorPrestado - getSaldo()) * tasa / 100;
            var abonoCapital = cantidad - intereses;
            return consignar(abonoCapital);
        } else {
            System.out.println("Ya la deuda está pagada");
            return false;
        }
    }

    public double getValorPrestado() {
        return valorPrestado;
    }

    public double getTasa() {
        return tasa;
    }



    public double getValorRetirado() {
        return valorRetirado;
    }

    public int getPlazo() {
        return plazo;
    }

    @Override
    public String[] mostrarValores() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return new String[] {
                "Ahorros",
                getNumero(),
                getTitular(),
                df.format(getSaldo()),
                "", 
                df.format(valorPrestado),
                df.format(tasa),
                df.format(plazo),
                df.format(getCuota())
        }; 
    }

}
