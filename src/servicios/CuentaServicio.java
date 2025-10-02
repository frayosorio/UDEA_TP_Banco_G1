package servicios;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelos.Ahorros;
import modelos.Corriente;
import modelos.Credito;
import modelos.CreditoRotativo;
import modelos.Cuenta;
import modelos.TipoCuenta;

public class CuentaServicio {

    private static List<Cuenta> cuentas = new ArrayList<>();

    private static String[] encabezadosCuentas = new String[] { "Tipo", "Número", "Titular", "Saldo",
            "Sobregiro", "Valor Prestado", "Tasa", "Plazo", "Cuota" };

    public static Cuenta getCuenta(int posicion) {
        if (posicion >= 0 && posicion < cuentas.size()) {
            return cuentas.get(posicion);
        } else {
            throw new IllegalArgumentException("Posición no existente de la cuenta");
        }
    }

    public static void mostrar(JTable tblCuentas) {
        String[][] datos = null;
        if (cuentas.size() > 0) {
            datos = new String[cuentas.size()][encabezadosCuentas.length];

            int fila = 0;
            for (Cuenta cuenta : cuentas) {
                if (cuenta != null) {
                    datos[fila] = cuenta.mostrarValores();
                }
                fila++;
            }
        }
        DefaultTableModel dtm = new DefaultTableModel(datos, encabezadosCuentas);
        tblCuentas.setModel(dtm);
    }

    public static Cuenta agregar(TipoCuenta tipo,
            String titular, String numero,
            double saldoInicial, double sobregiro, int plazo) {
        Cuenta cuenta = null;
        switch (tipo) {
            case AHORROS:
                cuenta = new Ahorros(titular, numero, saldoInicial);
                break;
            case CORRIENTE:
                cuenta = new Corriente(titular, numero,
                        saldoInicial,
                        sobregiro);
                break;
            case CREDITO:
                cuenta = new Credito(titular, numero,
                        saldoInicial,
                        sobregiro,
                        plazo);
                break;
            case CREDITO_ROTATIVO:
                cuenta = new CreditoRotativo(titular, numero,
                        saldoInicial,
                        sobregiro,
                        plazo);
                break;
        }
        if (cuenta != null) {
            cuentas.add(cuenta);
        }
        return cuenta;
    }

    public static boolean quitar(int posicion) {
        if (posicion >= 0 && posicion < cuentas.size()) {
            cuentas.remove(posicion);
            return true;
        }
        return false;
    }

}
