package modelos;

import java.text.DecimalFormat;

import interfaces.IPagable;
import interfaces.IRetirable;

public class CreditoRotativo extends Cuenta implements IPagable, IRetirable {

    private double cupoPrestamo;
    private double tasa;
    private int plazo;

    public CreditoRotativo(String titular, String numero, double cupoPrestamo, double tasa, int plazo) {
        super(titular, numero, cupoPrestamo);
        this.cupoPrestamo = cupoPrestamo;
        this.tasa = tasa;
        this.plazo = plazo;
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

    public double getSaldoPrestamo() {
        return cupoPrestamo - getSaldo();
    }

    @Override
    public double getCuota() {
        double t = tasa / 100;
        return getSaldoPrestamo() * Math.pow(1 + t, plazo) * t / (Math.pow(1 + t, plazo) - 1);
    }

    public boolean pagar(double cantidad) {
        System.out.println("saldo=" + getSaldo() + " cupo=" + cupoPrestamo);
        if (getSaldo() < cupoPrestamo) {
            var intereses = getSaldoPrestamo() * tasa / 100;
            var abonoCapital = cantidad - intereses;
            System.out.println("Pago existoso. Nuevo saldo disponible :" + getSaldo());
            return incrementarSaldo(abonoCapital);
        } else {
            System.out.println("No hay deuda a pagar");
            return false;
        }
    }

    @Override
    public String[] mostrarValores() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return new String[] {
                "Credito Rotativo",
                getNumero(),
                getTitular(),
                df.format(getSaldo()),
                "",
                df.format(cupoPrestamo),
                df.format(tasa),
                df.format(plazo),
                ""
        };
    }

    @Override
    public ResultadoTransaccionDto procesarTransaccion(TipoTransaccion tipo, double valor) {
        boolean aceptada = false;
        double saldo = cupoPrestamo;
        switch (tipo) {
            case CONSIGNACION_PAGO:
                aceptada = pagar(valor);
                saldo = getSaldo();
                break;
            case RETIRO:
                aceptada = retirar(valor);
                saldo = getSaldo();
                break;
        }
        return new ResultadoTransaccionDto(aceptada, saldo);
    }

}
