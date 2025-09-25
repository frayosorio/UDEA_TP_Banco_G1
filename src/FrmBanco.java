import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import modelos.*;
import servicios.CuentaServicio;
import servicios.UtilServicio;

public class FrmBanco extends JFrame {

    private String[] encabezadosTransacciones = new String[] { "Cuenta", "Tipo", "Valor", "Saldo", "Estado" };
    private String[] opcionesTransaccion = new String[] { "Consignación o Pago", "Retiro" };

    private JTable tblCuentas, tblTransacciones;
    private JPanel pnlEditarCuenta, pnlEditarTransaccion;

    private JTextField txtNumero, txtTitular, txtSaldoInicial, txtSobregiro, txtValor, txtPlazo;
    private JComboBox cmbTipoCuenta, cmbTipoTransaccion, cmbCuenta;

    private JLabel lblSobregiro, lblSaldoInicial, lblPlazo;

    private JTabbedPane tp;

    private List<Transaccion> transacciones = new ArrayList<>();

    public FrmBanco() {
        setSize(600, 400);
        setTitle("Cuentas Bancarias");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JToolBar tbBanco = new JToolBar();

        JButton btnAgregarCuenta = new JButton();
        btnAgregarCuenta.setIcon(new ImageIcon(getClass().getResource("/iconos/AgregarCuenta.png")));
        btnAgregarCuenta.setToolTipText("Agregar Cuenta");
        btnAgregarCuenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAgregarCuentaClick();
            }
        });
        tbBanco.add(btnAgregarCuenta);

        JButton btnQuitarCuenta = new JButton();
        btnQuitarCuenta.setIcon(new ImageIcon(getClass().getResource("/iconos/QuitarCuenta.png")));
        btnQuitarCuenta.setToolTipText("Quitar Cuenta");
        btnQuitarCuenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnQuitarCuentaClick();
            }
        });
        tbBanco.add(btnQuitarCuenta);

        JButton btnTransaccion = new JButton();
        btnTransaccion.setIcon(new ImageIcon(getClass().getResource("/iconos/Transaccion.png")));
        btnTransaccion.setToolTipText("Realizar Transacción");
        btnTransaccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnTransaccionClick();
            }
        });
        tbBanco.add(btnTransaccion);

        // Contenedor principal de CUENTAS con BoxLayout (vertical)
        JPanel pnlCuentas = new JPanel();
        pnlCuentas.setLayout(new BoxLayout(pnlCuentas, BoxLayout.Y_AXIS));

        // Panel 1 (oculto por defecto)
        pnlEditarCuenta = new JPanel();
        pnlEditarCuenta.setPreferredSize(new Dimension(pnlEditarCuenta.getWidth(), 100)); // Altura fija de 100px
        pnlEditarCuenta.setLayout(null);

        JLabel lblNumero = new JLabel("Número");
        lblNumero.setBounds(10, 10, 100, 25);
        pnlEditarCuenta.add(lblNumero);

        txtNumero = new JTextField();
        txtNumero.setBounds(110, 10, 100, 25);
        pnlEditarCuenta.add(txtNumero);

        JLabel lblTitular = new JLabel("Titular");
        lblTitular.setBounds(10, 40, 100, 25);
        pnlEditarCuenta.add(lblTitular);

        txtTitular = new JTextField();
        txtTitular.setBounds(110, 40, 100, 25);
        pnlEditarCuenta.add(txtTitular);

        lblSaldoInicial = new JLabel("Saldo Inicial");
        lblSaldoInicial.setBounds(10, 70, 100, 25);
        pnlEditarCuenta.add(lblSaldoInicial);

        txtSaldoInicial = new JTextField();
        txtSaldoInicial.setBounds(110, 70, 100, 25);
        pnlEditarCuenta.add(txtSaldoInicial);

        cmbTipoCuenta = new JComboBox(TipoCuenta.values());
        cmbTipoCuenta.setBounds(220, 10, 100, 25);
        pnlEditarCuenta.add(cmbTipoCuenta);

        cmbTipoCuenta.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (cmbTipoCuenta.getSelectedIndex()) {
                    case 0:
                        lblSaldoInicial.setText("Saldo Inicial");
                        lblSobregiro.setVisible(false);
                        txtSobregiro.setVisible(false);
                        lblPlazo.setVisible(false);
                        txtPlazo.setVisible(false);
                        break;
                    case 1:
                        lblSaldoInicial.setText("Saldo Inicial");
                        lblSobregiro.setText("Sobregiro");
                        lblSobregiro.setVisible(true);
                        txtSobregiro.setVisible(true);
                        lblPlazo.setVisible(false);
                        txtPlazo.setVisible(false);
                        break;
                    case 2:
                        lblSaldoInicial.setText("Valor Prestado");
                        lblSobregiro.setText("Tasa");
                        lblSobregiro.setVisible(true);
                        txtSobregiro.setVisible(true);
                        lblPlazo.setVisible(true);
                        txtPlazo.setVisible(true);
                        break;
                }
            }

        });

        lblSobregiro = new JLabel("Sobregiro");
        lblSobregiro.setBounds(220, 40, 100, 25);
        pnlEditarCuenta.add(lblSobregiro);

        txtSobregiro = new JTextField();
        txtSobregiro.setBounds(300, 40, 100, 25);
        pnlEditarCuenta.add(txtSobregiro);

        lblPlazo = new JLabel("Plazo");
        lblPlazo.setBounds(420, 40, 100, 25);
        pnlEditarCuenta.add(lblPlazo);

        txtPlazo = new JTextField();
        txtPlazo.setBounds(460, 40, 100, 25);
        pnlEditarCuenta.add(txtPlazo);

        JButton btnGuardarCuenta = new JButton("Guardar");
        btnGuardarCuenta.setBounds(220, 70, 100, 25);
        btnGuardarCuenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnGuardarCuentaClick();
            }
        });
        pnlEditarCuenta.add(btnGuardarCuenta);

        JButton btnCancelarCuenta = new JButton("Cancelar");
        btnCancelarCuenta.setBounds(320, 70, 100, 25);
        btnCancelarCuenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCancelarCuentaClick();
            }
        });
        pnlEditarCuenta.add(btnCancelarCuenta);

        pnlEditarCuenta.setVisible(false); // Se oculta al inicio

        // Panel 2 (siempre visible)
        tblCuentas = new JTable();
        JScrollPane spListaCuentas = new JScrollPane(tblCuentas);

        CuentaServicio.mostrar(tblCuentas);

        // Agregar componentes
        pnlCuentas.add(pnlEditarCuenta);
        pnlCuentas.add(spListaCuentas);

        // JScrollPane para permitir desplazamiento si es necesario
        JScrollPane spCuentas = new JScrollPane(pnlCuentas);
        spCuentas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Contenedor principal de TRANSACCIONES con BoxLayout (vertical)
        JPanel pnlTransacciones = new JPanel();
        pnlTransacciones.setLayout(new BoxLayout(pnlTransacciones, BoxLayout.Y_AXIS));

        // Panel 1 (oculto por defecto)
        pnlEditarTransaccion = new JPanel();
        pnlEditarTransaccion.setPreferredSize(new Dimension(pnlEditarTransaccion.getWidth(), 100)); // Altura fija de
                                                                                                    // 100px
        pnlEditarTransaccion.setLayout(null);

        JLabel lblCuenta = new JLabel("Cuenta");
        lblCuenta.setBounds(10, 10, 100, 25);
        pnlEditarTransaccion.add(lblCuenta);

        cmbCuenta = new JComboBox();
        cmbCuenta.setBounds(110, 10, 100, 25);
        pnlEditarTransaccion.add(cmbCuenta);

        JLabel lblTipo = new JLabel("Tipo");
        lblTipo.setBounds(10, 40, 100, 25);
        pnlEditarTransaccion.add(lblTipo);

        cmbTipoTransaccion = new JComboBox();
        cmbTipoTransaccion.setBounds(110, 40, 100, 25);
        DefaultComboBoxModel mdlTipoTransaccion = new DefaultComboBoxModel(opcionesTransaccion);
        cmbTipoTransaccion.setModel(mdlTipoTransaccion);
        pnlEditarTransaccion.add(cmbTipoTransaccion);

        JLabel lblValor = new JLabel("Valor");
        lblValor.setBounds(10, 70, 100, 25);
        pnlEditarTransaccion.add(lblValor);

        txtValor = new JTextField();
        txtValor.setBounds(110, 70, 100, 25);
        pnlEditarTransaccion.add(txtValor);

        JButton btnGuardarTransaccion = new JButton("Guardar");
        btnGuardarTransaccion.setBounds(220, 70, 100, 25);
        btnGuardarTransaccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnGuardarTransaccionClick();
            }
        });
        pnlEditarTransaccion.add(btnGuardarTransaccion);

        JButton btnCancelarTransaccion = new JButton("Cancelar");
        btnCancelarTransaccion.setBounds(320, 70, 100, 25);
        btnCancelarTransaccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCancelarTransaccionClick();
            }
        });
        pnlEditarTransaccion.add(btnCancelarTransaccion);

        pnlEditarTransaccion.setVisible(false); // Se oculta al inicio

        // Panel 2 (siempre visible)
        tblTransacciones = new JTable();
        JScrollPane spListaTransacciones = new JScrollPane(tblTransacciones);

        DefaultTableModel dtm = new DefaultTableModel(null, encabezadosTransacciones);
        tblTransacciones.setModel(dtm);

        // Agregar componentes
        pnlTransacciones.add(pnlEditarTransaccion);
        pnlTransacciones.add(spListaTransacciones);

        // JScrollPane para permitir desplazamiento si es necesario
        JScrollPane spTransacciones = new JScrollPane(pnlTransacciones);
        spTransacciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        tp = new JTabbedPane();
        tp.addTab("Cuentas", spCuentas);
        tp.addTab("Transacciones", spTransacciones);

        getContentPane().add(tbBanco, BorderLayout.NORTH);
        getContentPane().add(tp, BorderLayout.CENTER);

        cmbTipoCuenta.setSelectedIndex(0);
    }

    private void btnAgregarCuentaClick() {
        pnlEditarCuenta.setVisible(true);
        tp.setSelectedIndex(0);

    }

    private void btnQuitarCuentaClick() {
        int posicion = tblCuentas.getSelectedRow();
        if (posicion >= 0) {
            if (CuentaServicio.quitar(posicion)) {
                cmbCuenta.removeItemAt(posicion);
                CuentaServicio.mostrar(tblCuentas);
            } else {
                JOptionPane.showMessageDialog(null, "no se pudo retirar la cuenta");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar la cuenta");
        }
    }

    private void btnGuardarCuentaClick() {
        pnlEditarCuenta.setVisible(false);

        try {
            String titular = txtTitular.getText();
            String numero = txtNumero.getText();
            double saldoInicial = UtilServicio.leerReal(txtSaldoInicial.getText());
            double sobregiro = UtilServicio.leerReal(txtSobregiro.getText());
            int plazo = UtilServicio.leerEntero(txtPlazo.getText());

            if (!titular.isEmpty() && !numero.isEmpty() && saldoInicial >= 0 && sobregiro >= 0 && plazo >= 0) {
                Cuenta c = CuentaServicio.agregar((TipoCuenta) cmbTipoCuenta.getSelectedItem(),
                        titular, numero, saldoInicial, sobregiro, plazo);
                cmbCuenta.addItem(c.toString());
                CuentaServicio.mostrar(tblCuentas);
            } else {
                JOptionPane.showMessageDialog(null, "Datos no validos");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void btnCancelarCuentaClick() {
        pnlEditarCuenta.setVisible(false);

    }

    private void btnTransaccionClick() {
        pnlEditarTransaccion.setVisible(true);
        tp.setSelectedIndex(1);

    }

    private void btnGuardarTransaccionClick() {
        /*
         * pnlEditarTransaccion.setVisible(false);
         * double valor = Double.parseDouble(txtValor.getText());
         * if (cmbTipoTransaccion.getSelectedIndex() >= 0 &&
         * cmbCuenta.getSelectedIndex() >= 0 && valor > 0) {
         * Cuenta cuenta = cuentas.get(cmbCuenta.getSelectedIndex());
         * double saldo = 0;
         * boolean aceptada = false;
         * switch (cmbTipoTransaccion.getSelectedIndex()) {
         * case 0:
         * if (cuenta instanceof Credito) {
         * Credito credito = (Credito) cuenta;
         * aceptada = credito.pagar(valor);
         * saldo = credito.getValorPrestado() - credito.getSaldo();
         * } else {
         * aceptada = cuenta.consignar(valor);
         * saldo = cuenta.getSaldo();
         * }
         * break;
         * case 1:
         * aceptada = cuenta.retirar(valor);
         * if (cuenta instanceof Credito) {
         * Credito credito = (Credito) cuenta;
         * saldo = credito.getValorPrestado() - credito.getValorRetirado();
         * } else {
         * saldo = cuenta.getSaldo();
         * }
         * break;
         * }
         * Transaccion transaccion = new Transaccion(cuenta,
         * opcionesTransaccion[cmbTipoTransaccion.getSelectedIndex()],
         * valor, saldo, !aceptada);
         * transacciones.add(transaccion);
         * mostrarTransacciones();
         * } else {
         * JOptionPane.showMessageDialog(null,
         * "Debe seleccionar la cuenta y el tipo de transacción, asi como un valor positivo de la transacción"
         * );
         * }
         */
    }

    private void btnCancelarTransaccionClick() {
        pnlEditarTransaccion.setVisible(false);

    }

    private void mostrarTransacciones() {
        String[][] datos = new String[transacciones.size()][encabezadosTransacciones.length];
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
        DefaultTableModel dtm = new DefaultTableModel(datos, encabezadosTransacciones);
        tblTransacciones.setModel(dtm);
    }

}
