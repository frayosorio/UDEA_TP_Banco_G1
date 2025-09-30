package servicios;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelos.Credito;
import modelos.Cuenta;
import modelos.TipoTransaccion;
import modelos.Transaccion;

public class TransaccionServicio {

    private static final int SALDO_PREDETERMINADO = 0;

    private static List<Transaccion> transacciones = new ArrayList<>();
    private static String[] encabezadosTransacciones = new String[] { "Cuenta", "Tipo", "Valor", "Saldo", "Estado" };

    public static void agregar(Cuenta cuenta, TipoTransaccion tipo, double valor) {
        double saldo = SALDO_PREDETERMINADO;
        boolean aceptada = false;
        switch (tipo) {
            case CONSIGNACION_PAGO:
                if (cuenta instanceof Credito) {
                    Credito credito = (Credito) cuenta;
                    aceptada = credito.pagar(valor);
                    saldo = credito.getValorPrestado() - credito.getSaldo();
                } else {
                    aceptada = cuenta.consignar(valor);
                    saldo = cuenta.getSaldo();
                }
                break;
            case RETIRO:
                aceptada = cuenta.retirar(valor);
                if (cuenta instanceof Credito) {
                    Credito credito = (Credito) cuenta;
                    saldo = credito.getValorPrestado() - credito.getValorRetirado();
                } else {
                    saldo = cuenta.getSaldo();
                }
                break;
        }
        Transaccion transaccion = new Transaccion(cuenta,
                tipo.toString(),
                valor, saldo, !aceptada);
        transacciones.add(transaccion);
    }

    public static void mostrar(JTable tblTransacciones) {
        String[][] datos = null;
        if (transacciones.size() > 0) {
            datos = new String[transacciones.size()][encabezadosTransacciones.length];
            int fila = 0;
            DecimalFormat df = new DecimalFormat("#,##0.00");
            for (Transaccion t : transacciones) {
                datos[fila][0] = t.getCuenta().toString();
                datos[fila][1] = t.getTipo();
                datos[fila][2] = df.format(t.getValor());
                datos[fila][3] = df.format(t.getSaldo());
                datos[fila][4] = t.isRechazada() ? "Rechazada" : "Aceptada";
                fila++;
            }
        }
        DefaultTableModel dtm = new DefaultTableModel(datos, encabezadosTransacciones);
        tblTransacciones.setModel(dtm);
    }

}
