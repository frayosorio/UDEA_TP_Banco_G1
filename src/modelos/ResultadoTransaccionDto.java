package modelos;

public class ResultadoTransaccionDto {

    private boolean aceptada;
    private double saldo;

    public ResultadoTransaccionDto(boolean aceptada, double saldo) {
        this.aceptada = aceptada;
        this.saldo = saldo;
    }

    public boolean isAceptada() {
        return aceptada;
    }

    public double getSaldo() {
        return saldo;
    }

}
