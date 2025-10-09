package modelos;

import java.text.DecimalFormat;

import interfaces.IPagable;
import interfaces.IRetirable;

public class Credito extends Cuenta implements IPagable, IRetirable{

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

    @Override
    public double getCuota() {
        double t = tasa / 100;
        return valorPrestado * Math.pow(1 + t, plazo) * t / (Math.pow(1 + t, plazo) - 1);
    }

    @Override
    public boolean pagar(double cantidad) {
        if (getSaldo() < valorPrestado) {
            var intereses = (valorPrestado - getSaldo()) * tasa / 100;
            var abonoCapital = cantidad - intereses;
            return incrementarSaldo(abonoCapital);
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

    @Override
    public ResultadoTransaccionDto procesarTransaccion(TipoTransaccion tipo, double valor) {
        boolean aceptada = false;
        double saldo = valorPrestado;
        switch (tipo) {
            case CONSIGNACION_PAGO:
                aceptada = pagar(valor);
                saldo = valorPrestado - getSaldo();
                break;
            case RETIRO:
                aceptada = retirar(valor);
                saldo = valorPrestado - valorRetirado;
                break;
        }
        return new ResultadoTransaccionDto(aceptada, saldo);
    }

}
