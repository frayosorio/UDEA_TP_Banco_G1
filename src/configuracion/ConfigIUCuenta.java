package configuracion;

import java.util.Map;

import modelos.TipoCuenta;

public class ConfigIUCuenta {

    private String textoSaldo, textoSobregiro;
    private boolean mostrarSobregiro, mostrarPlazo;

    public ConfigIUCuenta(String textoSaldo, String textoSobregiro, boolean mostrarSobregiro, boolean mostrarPlazo) {
        this.textoSaldo = textoSaldo;
        this.textoSobregiro = textoSobregiro;
        this.mostrarSobregiro = mostrarSobregiro;
        this.mostrarPlazo = mostrarPlazo;
    }

    public String getTextoSaldo() {
        return textoSaldo;
    }

    public String getTextoSobregiro() {
        return textoSobregiro;
    }

    public boolean isMostrarSobregiro() {
        return mostrarSobregiro;
    }

    public boolean isMostrarPlazo() {
        return mostrarPlazo;
    }

    private static Map<TipoCuenta, ConfigIUCuenta> configuraciones = Map.of(
            TipoCuenta.AHORROS, new ConfigIUCuenta("Saldo Inicial:", null, false, false),
            TipoCuenta.CORRIENTE, new ConfigIUCuenta("Saldo Inicial:", "Sobregiro", true, false),
            TipoCuenta.CREDITO, new ConfigIUCuenta("Valor prestado:", "Tasa", true, true),
            TipoCuenta.CREDITO_ROTATIVO, new ConfigIUCuenta("Valor prestado:", "Tasa", true, true));

    public static Map<TipoCuenta, ConfigIUCuenta> getConfiguraciones() {
        return configuraciones;
    }

}
