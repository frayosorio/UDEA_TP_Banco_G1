package servicios;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelos.Ahorros;
import modelos.Corriente;
import modelos.Credito;
import modelos.Cuenta;
import modelos.TipoCuenta;

public class CuentaServicio {

    private static List<Cuenta> cuentas = new ArrayList<>();

    private static String[] encabezadosCuentas = new String[] { "Tipo", "Número", "Titular", "Saldo",
            "Sobregiro", "Valor Prestado", "Tasa", "Plazo", "Cuota" };

    public static void mostrar(JTable tblCuentas) {
        String[][] datos = null;
        if (cuentas.size() > 0) {
            datos = new String[cuentas.size()][encabezadosCuentas.length];

            int fila = 0;
            for (Cuenta c : cuentas) {
                if (c != null) {
                    datos[fila][0] = c instanceof Ahorros ? "Ahorros"
                            : c instanceof Corriente ? "Corriente" : "Crédito";
                    datos[fila][1] = c.getNumero();
                    datos[fila][2] = c.getTitular();

                    DecimalFormat df = new DecimalFormat("#,##0.00");

                    datos[fila][3] = df.format(c.getSaldo());
                    datos[fila][4] = c instanceof Corriente ? df.format(((Corriente) c).getSobregiro()) : "";
                    datos[fila][5] = c instanceof Credito ? df.format(((Credito) c).getValorPrestado()) : "";
                    datos[fila][6] = c instanceof Credito ? df.format(((Credito) c).getTasa()) : "";
                    datos[fila][7] = c instanceof Credito ? df.format(((Credito) c).getPlazo()) : "";
                    datos[fila][8] = c instanceof Credito ? df.format(((Credito) c).getCuota()) : "";
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
