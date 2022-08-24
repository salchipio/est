package nakpark;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class _ventana extends javax.swing.JFrame {
    
    Color b = new Color(255, 255, 255);
    Color l = new Color(161, 252, 159);
    Color o = new Color(252, 159, 159);
    Color color_letra = new Color(0, 0, 0);
    JButton[] esp;
    JButton sp;
    GridLayout gr = new GridLayout(0, 5);
    Font letrat = new Font("Arial", Font.BOLD, 14);
    Font letra = new Font("Monospaced", Font.BOLD, 14);
    JScrollPane a;
    _db d = new _db();
    _val v = new _val();
    estacionamiento e = new estacionamiento();
    detalle_estacionamiento de = new detalle_estacionamiento();
    producto p = new producto();
    espacio ar = new espacio();
    usuario usua = new usuario();
    
    public _ventana() {
        a = new JScrollPane();
        a.setBounds(460, 0, 479, 441);
        a.setBorder(BorderFactory.createLineBorder(Color.white));
        a.getVerticalScrollBar().setUnitIncrement(16);
        a.setVisible(false);
        initComponents();
        listar_espacios();
        datos_estacionamiento();
        combo_productos();
        capacidad();
        principal();
        boton_espacio();
        xprincipal.setEnabledAt(2, false);
        xprincipal.setEnabledAt(3, false);
        px.setVisible(false);
        cc.setVisible(false);
        setLocationRelativeTo(null);
        setSize(950, 550);
    }
    
    public void capacidad() {
        int o = d.datos_capacidad();
        int c = d.capacidad();
        int l = c - o;
        etot.setText(String.valueOf(c));
        ltot.setText(String.valueOf(l));
        otot.setText(String.valueOf(o));
    }
    
    public void datos_salida() {
        String placa = placax.getText();
        String[] datos = new String[6];
        datos = d.datos_salida(placa);
        System.out.println("xDD " + datos[0]);
        pl.setText(datos[0]);
        hen.setText(datos[2]);
        hsa.setText(datos[3]);
        prod.setText(datos[1]);
        canth.setText(datos[4]);
        pag.setText(datos[5]);
    }
    
    public void vuelto() {
        double v = Double.parseDouble(pag.getText().replace(',', '.'));
        int p = Integer.parseInt(pagax.getText());
        double c = p - v;
        cx.setText(String.valueOf(c));
    }
    
    public void boton_espacio() {
        boolean a = d.asignarespacio();
        if (a == true) {
            esx.setVisible(true);
        } else {
            esx.setVisible(false);
        }
    }
    
    public void ingresar(String placa, String producto, int espacio) {
        e.ingreso(placa, producto, espacio);
        limpiar();
    }
    
    public void ingreso_normal() {
        String placa = placax.getText();
        String producto = tipox.getSelectedItem().toString();
        e.ingreso_se(placa, producto);
        principal();
    }
    
    public void retirar() {
        String placa = placax.getText();
        e.salida(placa);
        principal();
    }
    
    public void retirar_normal() {
        String placa = placax.getText();
        e.salida_normal(placa);
        principal();
    }
    
    public void retirar_espacio(int espacio) {
        d.retirar_espacio(espacio);
    }
    
    public JPanel listar_espacios() {
        Font letra = new Font("Monospaced", Font.BOLD, 12);
        int capacidad = d.capacidad();
        
        JPanel espacios = new JPanel();
        
        espacios.setBackground(b);
        espacios.setLayout(null);
        
        int contador = 1;
        esp = new JButton[capacidad];
        for (int i = 0; i < capacidad; i++) {
            esp[i] = new JButton();
            esp[i].setFont(letra);
            esp[i].setText("" + contador);
            esp[i].setContentAreaFilled(false);
            esp[i].setForeground(color_letra);
            esp[i].setOpaque(true);
            esp[i].setBorder(BorderFactory.createLineBorder(b));
            esp[i].setBackground(l);
            accion_espacio a = new accion_espacio();
            esp[i].addActionListener(a);
            espacios.add(esp[i]);
            espacios.setLayout(gr);
            contador++;
        }
        a.setViewportView(espacios);
        a.setVisible(true);
        espaci.setVisible(false);
        espacios.setVisible(true);
        d.capacidad_actualizada(capacidad, esp);
        return espacios;
    }
    
    public void datos_estacionamiento() {
        de = d.datos_estacionamiento();
        razonx.setText(de.getRazon());
        rucx.setText(de.getRuc());
        dirx.setText(de.getDireccion());
        celx.setText(de.getCelular());
        igvx.setText(String.valueOf(de.getIgv()));
        comx.setText(de.getComentario());
        capx.setText(String.valueOf(de.getCapacidad()));
        est.setText(de.getRazon());
        aes.setSelected(de.isAsignarcasilleros());
    }
    
    public void modificar_datos_estacionamiento() {
        String razon = razonx.getText();
        String ruc = rucx.getText();
        String direccion = dirx.getText();
        String cel = celx.getText();
        String comentario = comx.getText();
        double igv = Double.parseDouble(igvx.getText());
        int cap = Integer.parseInt(capx.getText());
        boolean Asignarcasilleros = aes.isSelected();
        de.modificar_detalle(razon, ruc, direccion, cel, comentario, igv, cap, Asignarcasilleros);
        JOptionPane.showMessageDialog(null, "Guardado!");
        datos_estacionamiento();
        capacidad();
        principal();
    }
    
    public void limpìar_producto() {
        nomx.setText(" ");
        tarx.setText(" ");
        horx.setText(" ");
        sobx.setText(" ");
        tolx.setText(" ");
    }
    
    public void registrar_producto() {
        String nombre = nomx.getText();
        double tarifa = Double.parseDouble(tarx.getText());
        double canthoras = Double.parseDouble(horx.getText());
        double tar_sobreestadia = Double.parseDouble(sobx.getText());
        double tolerancia = Double.parseDouble(tolx.getText());
        p.registrar_producto(nombre, tarifa, canthoras, tar_sobreestadia, tolerancia);
        JOptionPane.showMessageDialog(null, "Producto registrado");
        limpìar_producto();
    }
    
    public void modificar_producto() {
        String prod = tipp.getSelectedItem().toString();
        double tarifa = Double.parseDouble(te.getText());
        double canthoras = Double.parseDouble(he.getText());
        double tar_sobreestadia = Double.parseDouble(se.getText());
        double tolerancia = Double.parseDouble(toe.getText());
        p.modificar_producto(prod, tarifa, canthoras, tar_sobreestadia, tolerancia);
        JOptionPane.showMessageDialog(null, "Producto modificado");
    }
    
    public void eliminar_producto() {
        String prod = tipel.getSelectedItem().toString();
        p.eliminar_producto(prod);
        JOptionPane.showMessageDialog(null, "Producto Eliminado");
    }
    
    public void mostrar_producto() {
        try {
            if (tipp.getSelectedItem().toString().equals(null)) {
                te.setText(" ");
                he.setText(" ");
                se.setText(" ");
                toe.setText(" ");
            } else {
                String n = tipp.getSelectedItem().toString();
                p = d.datos_producto(n);
                String a = String.valueOf(p.getTarifa());
                String b = String.valueOf(p.getHoras());
                String c = String.valueOf(p.getTarsobreestadia());
                String d = String.valueOf(p.getTolerancia());
                te.setText(a);
                he.setText(b);
                se.setText(c);
                toe.setText(d);
            }
        } catch (Exception ex) {
            System.out.println(" " + ex.getMessage());
        }
    }
    
    public void listar_vehiculos() {
        String tipo = tipex.getSelectedItem().toString();
        String placa = plac.getText();
        String tipoVehiculo = "";
        String estado = "";
        String fecha = "";
        String fechax = "";
        String f = "fuera";
        String e = "en";
        if (tipex.getSelectedItem().equals(tipo)) {
            if (tipex.getSelectedItem().equals(tipo)) {
                tipoVehiculo = tipo;
            }
            if (tipex.getSelectedItem().equals("Todos")) {
                tipoVehiculo = "";
            }
        } else if (tipex.getSelectedItem().equals(tipo)) {
            tipoVehiculo = tipo;
        } else if (tipex.getSelectedItem().equals("Todos")) {
            tipoVehiculo = "";
        }
        if (estadex.getSelectedItem().equals(f)) {
            estado = "fuera";
        }
        if (estadex.getSelectedItem().equals(e)) {
            estado = "en";
        }
        if (dix.getDate() != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dix.getDate();
            fecha = dateFormat.format(date);
        }
        System.out.println(fecha);
        System.out.println(fechax);
        listax.setModel(d.listar_vehiculos(tipoVehiculo, estado, fecha, placa));
        listax.getColumnModel().getColumn(3).setPreferredWidth(120);
        listax.getColumnModel().getColumn(4).setPreferredWidth(120);
        
    }
    
    public void buscar_porfecha() {
        String fecha = "";
        String fechax = "";
        if (diax.getDate() != null && diex.getDate() != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd");
            Date date = diax.getDate();
            Date datex = diex.getDate();
            fecha = dateFormat.format(date);
            fechax = dateFormats.format(datex);
            System.out.println(fecha);
            System.out.println(fechax);
        }
        listax.setModel(d.listar_vehiculos_fecha(fecha, fechax));
        listax.getColumnModel().getColumn(3).setPreferredWidth(120);
        listax.getColumnModel().getColumn(4).setPreferredWidth(120);
    }
    
    public void reportar_ingresos() {
        String tipo = tipex.getSelectedItem().toString();
        String placa = plac.getText();
        String tipoVehiculo = "";
        String estado = "";
        String fecha = "";
        String fechax = "";
        String f = "fuera";
        String e = "en";
        if (tipex.getSelectedItem().equals(tipo)) {
            if (tipex.getSelectedItem().equals(tipo)) {
                tipoVehiculo = tipo;
            }
            if (tipex.getSelectedItem().equals("Todos")) {
                tipoVehiculo = "";
            }
        } else if (tipex.getSelectedItem().equals(tipo)) {
            tipoVehiculo = tipo;
        } else if (tipex.getSelectedItem().equals("Todos")) {
            tipoVehiculo = "";
        }
        if (estadex.getSelectedItem().equals(f)) {
            estado = "fuera";
        }
        if (estadex.getSelectedItem().equals(e)) {
            estado = "en";
        }
        if (dix.getDate() != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dix.getDate();
            fecha = dateFormat.format(date);
            System.out.println(fecha);
        }
        ingresox.setText(" s/. " + d.reportar_ingresos(estado, tipoVehiculo, placa, fecha));
    }
    
    public void principal() {
        espaci.setVisible(true);
        a.setVisible(false);
        limpiar();
        capacidad();
        px.setVisible(false);
    }
    
    public void limpiar_lista() {
        dix.setCalendar(null);
        plac.setText("");
        estadex.setSelectedIndex(0);
        tipex.setSelectedIndex(0);
    }
    
    public void limpiar_fechas() {
        diax.setCalendar(null);
        diex.setCalendar(null);
    }
    
    public void limpiar() {
        placax.setText("");
        tipox.setSelectedIndex(0);
        pl.setText("");
        hen.setText("");
        hsa.setText("");
        prod.setText("");
        canth.setText("");
        pag.setText("");
        pagax.setText("");
        cx.setText("");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        dateUtil1 = new com.toedter.calendar.DateUtil();
        testDateEvaluator1 = new com.toedter.calendar.demo.TestDateEvaluator();
        jPanel1 = new javax.swing.JPanel();
        xprincipal = new javax.swing.JTabbedPane();
        xd = new javax.swing.JPanel();
        placax = new javax.swing.JTextField();
        tipox = new javax.swing.JComboBox<>();
        rSLabelFecha1 = new rojeru_san.RSLabelFecha();
        rSLabelHora1 = new rojeru_san.RSLabelHora();
        jButton12 = new javax.swing.JButton();
        esx = new javax.swing.JButton();
        espaci = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        etot = new javax.swing.JTextField();
        ltot = new javax.swing.JTextField();
        otot = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        pl = new javax.swing.JTextField();
        hen = new javax.swing.JTextField();
        hsa = new javax.swing.JTextField();
        canth = new javax.swing.JTextField();
        pag = new javax.swing.JTextField();
        pagax = new javax.swing.JTextField();
        cx = new javax.swing.JTextField();
        camx = new javax.swing.JButton();
        jLabel49 = new javax.swing.JLabel();
        prod = new javax.swing.JTextField();
        retx = new javax.swing.JButton();
        px = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        xdx = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listax = new javax.swing.JTable();
        estadex = new javax.swing.JComboBox<>();
        tipex = new javax.swing.JComboBox<>();
        plac = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        diax = new com.toedter.calendar.JDateChooser();
        diex = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        ingresox = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        Reset = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        dix = new com.toedter.calendar.JDateChooser();
        buscarx = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        xdxx = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        razonx = new javax.swing.JTextField();
        rucx = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        dirx = new javax.swing.JTextField();
        celx = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        igvx = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        comx = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        capx = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        aes = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        dxx = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        tolx = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        sobx = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        horx = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        tarx = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        nomx = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        tipel = new javax.swing.JComboBox<>();
        jButton11 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        toe = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        tipp = new javax.swing.JComboBox<>();
        te = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        he = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        se = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        est = new javax.swing.JLabel();
        usu = new javax.swing.JTextField();
        lu = new javax.swing.JLabel();
        lc = new javax.swing.JLabel();
        bv = new javax.swing.JButton();
        pass = new javax.swing.JPasswordField();
        cc = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        xprincipal.add(xd, 0);
        xprincipal.add(xdx, 1);
        xprincipal.add(xdxx, 2);
        xprincipal.add(dxx, 3);
        xprincipal.setFont(letrat);
        xprincipal.setBackground(new java.awt.Color(255, 255, 255));
        System.out.print("xd "+ xprincipal.getTabCount());

        xd.setBackground(new java.awt.Color(255, 255, 255));
        xd.add(a);

        placax.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        placax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placaxActionPerformed(evt);
            }
        });
        placax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                placaxKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                placaxKeyTyped(evt);
            }
        });

        tipox.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tipox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoxActionPerformed(evt);
            }
        });

        rSLabelFecha1.setForeground(new java.awt.Color(0, 0, 0));
        rSLabelFecha1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N

        rSLabelHora1.setForeground(new java.awt.Color(0, 0, 0));
        rSLabelHora1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salida.png"))); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        esx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/outdoor-parking.png"))); // NOI18N
        esx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                esxActionPerformed(evt);
            }
        });

        espaci.setBackground(new java.awt.Color(255, 255, 255));

        jLabel24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel24.setText("Total");

        jLabel25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel25.setText("Libres");

        jLabel26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel26.setText("Ocupados");

        etot.setEditable(false);
        etot.setBackground(new java.awt.Color(231, 232, 247));
        etot.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        ltot.setEditable(false);
        ltot.setBackground(new java.awt.Color(231, 232, 247));
        ltot.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        otot.setEditable(false);
        otot.setBackground(new java.awt.Color(231, 232, 247));
        otot.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel27.setText("Placa");

        jLabel28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel28.setText("Hora entrada");

        jLabel44.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel44.setText("Hora salida");

        jLabel45.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel45.setText("Cantidad horas");

        jLabel46.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel46.setText("Valor a Pagar");

        jLabel47.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel47.setText("Paga");

        jLabel48.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel48.setText("Cambio");

        pl.setEditable(false);
        pl.setBackground(new java.awt.Color(253, 239, 239));
        pl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        hen.setEditable(false);
        hen.setBackground(new java.awt.Color(253, 239, 239));
        hen.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        hsa.setEditable(false);
        hsa.setBackground(new java.awt.Color(253, 239, 239));
        hsa.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        canth.setEditable(false);
        canth.setBackground(new java.awt.Color(253, 239, 239));
        canth.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        pag.setEditable(false);
        pag.setBackground(new java.awt.Color(253, 239, 239));
        pag.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        pagax.setBackground(new java.awt.Color(225, 253, 253));
        pagax.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        pagax.setForeground(new java.awt.Color(0, 0, 51));

        cx.setEditable(false);
        cx.setBackground(new java.awt.Color(228, 219, 243));
        cx.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        camx.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        camx.setText("Cambio");
        camx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                camxActionPerformed(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel49.setText("Producto");

        prod.setEditable(false);
        prod.setBackground(new java.awt.Color(253, 239, 239));
        prod.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        retx.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        retx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/parking-barrier.png"))); // NOI18N
        retx.setText("Retirar");
        retx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel45)
                        .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel47)
                    .addComponent(jLabel46))
                .addGap(25, 25, 25)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(retx)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(hen, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                        .addComponent(hsa)
                        .addComponent(pl, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pag, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                        .addComponent(canth))
                    .addComponent(prod, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cx, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                            .addComponent(pagax, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(camx, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(hen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(hsa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(canth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(pag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(pagax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(camx))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(retx)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout espaciLayout = new javax.swing.GroupLayout(espaci);
        espaci.setLayout(espaciLayout);
        espaciLayout.setHorizontalGroup(
            espaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(espaciLayout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
            .addGroup(espaciLayout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addGroup(espaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(espaciLayout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(otot, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(espaciLayout.createSequentialGroup()
                        .addGroup(espaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addGap(44, 44, 44)
                        .addGroup(espaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ltot, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(etot, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        espaciLayout.setVerticalGroup(
            espaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(espaciLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(espaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(etot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(espaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ltot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(espaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(otot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        px.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        px.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/parking-barrierx.png"))); // NOI18N
        px.setText("Ingresar");
        px.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pxActionPerformed(evt);
            }
        });

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/parking.png"))); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout xdLayout = new javax.swing.GroupLayout(xd);
        xd.setLayout(xdLayout);
        xdLayout.setHorizontalGroup(
            xdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(xdLayout.createSequentialGroup()
                .addGroup(xdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(xdLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(xdLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(xdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(placax, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(xdLayout.createSequentialGroup()
                                .addComponent(tipox, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))))
                    .addGroup(xdLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(rSLabelFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rSLabelHora1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(xdLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(esx, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(xdLayout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addComponent(px)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                .addComponent(espaci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        xdLayout.setVerticalGroup(
            xdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(xdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(xdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(xdLayout.createSequentialGroup()
                        .addGroup(xdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(esx, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(xdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rSLabelHora1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rSLabelFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51)
                        .addComponent(placax, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tipox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(px)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addComponent(espaci, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        xprincipal.addTab("            Estacionamiento           ", xd);

        xdx.setBackground(new java.awt.Color(255, 255, 255));

        listax.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        listax.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        listax.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Placa", "Producto", "Espacio", "Hora entrada", "Hora salida", "Pago"
            }
        ));
        listax.setRowHeight(21);
        listax.setSelectionBackground(new java.awt.Color(204, 255, 255));
        listax.setSelectionForeground(new java.awt.Color(4, 4, 4));
        jScrollPane1.setViewportView(listax);
        if (listax.getColumnModel().getColumnCount() > 0) {
            listax.getColumnModel().getColumn(3).setMinWidth(120);
            listax.getColumnModel().getColumn(4).setMinWidth(120);
        }

        estadex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "en", "fuera" }));

        plac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                placKeyTyped(evt);
            }
        });

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("hasta");

        jLabel2.setText("Placa");

        jButton2.setText("Reportar Ingresos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        ingresox.setEditable(false);
        ingresox.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ingresox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 0)));

        jButton3.setText("Exportar Excel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        Reset.setText("Reset");
        Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salida.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel5.setText("Desde");

        buscarx.setText("Buscar");
        buscarx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarxActionPerformed(evt);
            }
        });

        jButton4.setText("Reset");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout xdxLayout = new javax.swing.GroupLayout(xdx);
        xdx.setLayout(xdxLayout);
        xdxLayout.setHorizontalGroup(
            xdxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, xdxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(xdxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(xdxLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ingresox, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, xdxLayout.createSequentialGroup()
                        .addGroup(xdxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(xdxLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, xdxLayout.createSequentialGroup()
                                .addComponent(estadex, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tipex, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(xdxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, xdxLayout.createSequentialGroup()
                                        .addComponent(plac, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                                        .addComponent(dix, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(Reset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(87, 87, 87)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(diax, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(diex, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(xdxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(buscarx, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(47, 47, 47))))
        );
        xdxLayout.setVerticalGroup(
            xdxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(xdxLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(xdxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(xdxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(estadex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tipex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(xdxLayout.createSequentialGroup()
                        .addGroup(xdxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Reset)
                            .addComponent(jButton4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(xdxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(xdxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(plac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5))
                            .addComponent(dix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, xdxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(diex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buscarx)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(diax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(xdxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ingresox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        xprincipal.addTab("                         Lista                        ", xdx);

        xdxx.setBackground(new java.awt.Color(255, 255, 255));
        xdxx.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("Razon social");

        razonx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                razonxKeyTyped(evt);
            }
        });

        rucx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rucxKeyTyped(evt);
            }
        });

        jLabel15.setText("RUC");

        jLabel16.setText("Direccion");

        dirx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dirxActionPerformed(evt);
            }
        });
        dirx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dirxKeyTyped(evt);
            }
        });

        celx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                celxKeyTyped(evt);
            }
        });

        jLabel17.setText("Celular");

        jLabel18.setText("IGV");

        igvx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                igvxKeyTyped(evt);
            }
        });

        comx.setColumns(20);
        comx.setLineWrap(true);
        comx.setRows(5);
        comx.setWrapStyleWord(true);
        comx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comxKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(comx);

        jLabel19.setText("Comentario");

        jButton5.setText("Guardar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel4.setText("Capacidad");

        capx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                capxKeyTyped(evt);
            }
        });

        jLabel20.setText("espacios");

        aes.setBackground(new java.awt.Color(255, 255, 255));
        aes.setText("Asignar espacios");
        aes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aesActionPerformed(evt);
            }
        });

        jLabel9.setText("desasignar o asignar espacios.");

        jLabel8.setText("- Retirar todos los vehiculos antes de");

        jLabel10.setText("Recomendacion:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15)
                                .addComponent(jLabel16))
                            .addGap(33, 33, 33))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(18, 18, 18)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19))
                        .addGap(20, 20, 20)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(celx, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(igvx, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(razonx)
                                .addComponent(rucx)
                                .addComponent(dirx, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(capx, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20)
                                .addGap(61, 61, 61))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addComponent(aes)
                                .addGap(88, 88, 88))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(14, 14, 14))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(razonx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(rucx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(dirx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(aes)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(capx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(celx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(igvx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salida.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salida.png"))); // NOI18N
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout xdxxLayout = new javax.swing.GroupLayout(xdxx);
        xdxx.setLayout(xdxxLayout);
        xdxxLayout.setHorizontalGroup(
            xdxxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(xdxxLayout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(179, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, xdxxLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(xdxxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, xdxxLayout.createSequentialGroup()
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, xdxxLayout.createSequentialGroup()
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );
        xdxxLayout.setVerticalGroup(
            xdxxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(xdxxLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(147, 147, 147)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        xprincipal.addTab("                  Parametros                ", xdxx);

        dxx.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton9.setText("Registrar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel35.setText("horas");

        tolx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tolxKeyTyped(evt);
            }
        });

        jLabel33.setText("Tolerancia");

        jLabel32.setText("Tarifa de sobreestadia");

        sobx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sobxKeyTyped(evt);
            }
        });

        jLabel36.setText("por hora");

        jLabel31.setText("horas");

        horx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                horxKeyTyped(evt);
            }
        });

        jLabel34.setText("por");

        tarx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tarxKeyTyped(evt);
            }
        });

        jLabel30.setText("Tarifa");

        jLabel29.setText("Nombre");

        nomx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nomxKeyTyped(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(219, 255, 246));
        jTextField2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextField2.setText("Registro de producto");

        jLabel21.setText("Tolerancia entre 0 y 1    Ej: 0.25 = 15 min");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton9)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel33)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tolx, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel35))))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel32)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel30)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tarx, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel34)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(sobx, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel36))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(horx, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel31))))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nomx, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(nomx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(tarx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(horx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(sobx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addGap(9, 9, 9)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tolx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel35))
                .addGap(21, 21, 21)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tipel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipelActionPerformed(evt);
            }
        });

        jButton11.setText("Eliminar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(219, 255, 246));
        jTextField4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextField4.setText("Eliminar Producto");
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel22.setText("Retire todos los vehiculos que tienen");

        jLabel23.setText("el producto, antes de eliminarlo.");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(tipel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton11))
                    .addComponent(jLabel22)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton10.setText("Cambiar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        toe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                toeKeyTyped(evt);
            }
        });

        jLabel42.setText("Tolerancia");

        jLabel43.setText("horas");

        tipp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tippActionPerformed(evt);
            }
        });

        te.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                teKeyTyped(evt);
            }
        });

        jLabel37.setText("Tarifa");

        jLabel41.setText("Tarifa de sobreestadia");

        jLabel38.setText("por");

        he.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                heKeyTyped(evt);
            }
        });

        jLabel39.setText("horas");

        jLabel40.setText("por hora");

        se.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                seKeyTyped(evt);
            }
        });

        jTextField3.setEditable(false);
        jTextField3.setBackground(new java.awt.Color(219, 255, 246));
        jTextField3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextField3.setText("Modificar Producto");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel41)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel37)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(te, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel38))
                                    .addComponent(jLabel42))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(he, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel39))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(se, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel40))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(toe, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel43))))
                            .addComponent(tipp, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addComponent(jButton10)
                                .addGap(59, 59, 59))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tipp, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(te, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(he, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(se, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(jLabel42))
                .addGap(18, 18, 18)
                .addComponent(jButton10)
                .addGap(19, 19, 19))
        );

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salida.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dxxLayout = new javax.swing.GroupLayout(dxx);
        dxx.setLayout(dxxLayout);
        dxxLayout.setHorizontalGroup(
            dxxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dxxLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dxxLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        dxxLayout.setVerticalGroup(
            dxxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dxxLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(dxxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dxxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        xprincipal.addTab("                     Productos                    ", dxx);

        est.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        est.setForeground(new java.awt.Color(0, 51, 51));

        lu.setText("Usuario");

        lc.setText("Contraseña");

        bv.setText("Ingresar");
        bv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bvActionPerformed(evt);
            }
        });

        cc.setText("Cerrar Sesion");
        cc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ccActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(xprincipal)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(est, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usu, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cc)
                .addGap(46, 46, 46))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(est, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lu)
                            .addComponent(usu)
                            .addComponent(lc)
                            .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bv)
                            .addComponent(cc))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xprincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dirxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dirxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dirxActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        reportar_ingresos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        listar_vehiculos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tipoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoxActionPerformed
        boolean a = d.asignarespacio();
        try {
            if (a == true) {
                if (tipox.getSelectedItem().equals(" ")) {
                    principal();
                } else {
                    listar_espacios();
                }
            } else {
                boton_ingreso();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_tipoxActionPerformed

    private void placaxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placaxKeyTyped
        v.placakey(evt, placax);
    }//GEN-LAST:event_placaxKeyTyped

    private void placaxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placaxKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            datos_salida();
        }
    }//GEN-LAST:event_placaxKeyPressed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if (!nomx.getText().equals("") && !tarx.getText().equals("") && !horx.getText().equals("") && !sobx.getText().equals("") && !tolx.getText().equals("")) {
            registrar_producto();
            tipox.removeAllItems();
            tipp.removeAllItems();
            tipel.removeAllItems();
            tipex.removeAllItems();
            combo_productos();
        } else {
            JOptionPane.showMessageDialog(null, "use todos los campos");
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (!te.getText().equals("") && !he.getText().equals("") && !se.getText().equals("") && !toe.getText().equals("")) {
            try {
                modificar_producto();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "No hay producto a modificar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "use todos los campos");
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (!razonx.getText().equals("") && !rucx.getText().equals("") && !dirx.getText().equals("") && !celx.getText().equals("") && !comx.getText().equals("") && !igvx.getText().equals("")) {
            modificar_datos_estacionamiento();
            boton_espacio();
        } else {
            JOptionPane.showMessageDialog(null, "use todos los campos");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        try {
            eliminar_producto();
            tipox.removeAllItems();
            tipp.removeAllItems();
            tipel.removeAllItems();
            tipex.removeAllItems();
            combo_productos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay producto a eliminar");
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void tipelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipelActionPerformed

    }//GEN-LAST:event_tipelActionPerformed

    private void tippActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tippActionPerformed
        mostrar_producto();
    }//GEN-LAST:event_tippActionPerformed

    private void ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetActionPerformed
        limpiar_lista();
    }//GEN-LAST:event_ResetActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void esxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_esxActionPerformed
        listar_espacios();
    }//GEN-LAST:event_esxActionPerformed

    private void placaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_placaxActionPerformed

    private void camxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_camxActionPerformed
        vuelto();
    }//GEN-LAST:event_camxActionPerformed

    private void aesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aesActionPerformed

    private void pxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pxActionPerformed
        int a = Integer.parseInt(ltot.getText());
        String b = placax.getText();
        if (placax.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Faltan datos.");
        } else {
            if (a == 0) {
                JOptionPane.showMessageDialog(null, "Estacionamiento lleno");
            } else {
                if (d.verificar_placa(b) == true) {
                    JOptionPane.showMessageDialog(null, "ERROR: El vehiculo " + b + " se encuentra dentro del estacionamiento.");
                    principal();
                } else {
                    ingreso_normal();
                    principal();
                }
            }
        }
    }//GEN-LAST:event_pxActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        principal();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void bvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bvActionPerformed
        validar();
        usu.setText("");
        pass.setText("");
    }//GEN-LAST:event_bvActionPerformed

    private void ccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ccActionPerformed
        regresar();
    }//GEN-LAST:event_ccActionPerformed

    private void placKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placKeyTyped
        v.placakey(evt, plac);
    }//GEN-LAST:event_placKeyTyped

    private void razonxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_razonxKeyTyped
        v.textokey(evt, razonx);
    }//GEN-LAST:event_razonxKeyTyped

    private void rucxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rucxKeyTyped
        v.numerooncekey(evt, rucx);
    }//GEN-LAST:event_rucxKeyTyped

    private void celxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_celxKeyTyped
        v.numeronuevekey(evt, celx);
    }//GEN-LAST:event_celxKeyTyped

    private void dirxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dirxKeyTyped
        v.textokey(evt, dirx);
    }//GEN-LAST:event_dirxKeyTyped

    private void igvxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_igvxKeyTyped
        v.numerocuatrokey(evt, igvx);
    }//GEN-LAST:event_igvxKeyTyped

    private void comxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comxKeyTyped
        v.textolargokey(evt, comx);
    }//GEN-LAST:event_comxKeyTyped

    private void capxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_capxKeyTyped
        v.numerooncekey(evt, capx);
    }//GEN-LAST:event_capxKeyTyped

    private void nomxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nomxKeyTyped
        v.textokeynom(evt, nomx);
    }//GEN-LAST:event_nomxKeyTyped

    private void tarxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tarxKeyTyped
        v.numerooncekey(evt, tarx);
    }//GEN-LAST:event_tarxKeyTyped

    private void horxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_horxKeyTyped
        v.numerosietekey(evt, horx);
    }//GEN-LAST:event_horxKeyTyped

    private void sobxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sobxKeyTyped
        v.numerooncekey(evt, sobx);
    }//GEN-LAST:event_sobxKeyTyped

    private void tolxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tolxKeyTyped
        v.numerocuatrokey(evt, tolx);
    }//GEN-LAST:event_tolxKeyTyped

    private void teKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_teKeyTyped
        v.numerooncekey(evt, te);
    }//GEN-LAST:event_teKeyTyped

    private void heKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_heKeyTyped
        v.numerosietekey(evt, he);
    }//GEN-LAST:event_heKeyTyped

    private void seKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_seKeyTyped
        v.numerooncekey(evt, se);
    }//GEN-LAST:event_seKeyTyped

    private void toeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_toeKeyTyped
        v.numerocuatrokey(evt, toe);
    }//GEN-LAST:event_toeKeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            d.exportar_excel(listax);
        } catch (IOException ex) {
            Logger.getLogger(_ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void buscarxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarxActionPerformed
        buscar_porfecha();
    }//GEN-LAST:event_buscarxActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        limpiar_fechas();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void retxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retxActionPerformed
        boolean a = d.asignarespacio();
        if (a == true) {
            retirar();
        } else {
            retirar_normal();
        }
    }//GEN-LAST:event_retxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(_ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(_ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(_ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(_ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new _ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Reset;
    private javax.swing.JCheckBox aes;
    private javax.swing.JButton buscarx;
    private javax.swing.JButton bv;
    private javax.swing.JButton camx;
    private javax.swing.JTextField canth;
    private javax.swing.JTextField capx;
    private javax.swing.JButton cc;
    private javax.swing.JTextField celx;
    private javax.swing.JTextArea comx;
    private javax.swing.JTextField cx;
    private com.toedter.calendar.DateUtil dateUtil1;
    private com.toedter.calendar.JDateChooser diax;
    private com.toedter.calendar.JDateChooser diex;
    private javax.swing.JTextField dirx;
    private com.toedter.calendar.JDateChooser dix;
    private javax.swing.JPanel dxx;
    private javax.swing.JPanel espaci;
    private javax.swing.JLabel est;
    private javax.swing.JComboBox<String> estadex;
    private javax.swing.JButton esx;
    private javax.swing.JTextField etot;
    private javax.swing.JTextField he;
    private javax.swing.JTextField hen;
    private javax.swing.JTextField horx;
    private javax.swing.JTextField hsa;
    private javax.swing.JTextField igvx;
    private javax.swing.JTextField ingresox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel lc;
    private javax.swing.JTable listax;
    private javax.swing.JTextField ltot;
    private javax.swing.JLabel lu;
    private javax.swing.JTextField nomx;
    private javax.swing.JTextField otot;
    private javax.swing.JTextField pag;
    private javax.swing.JTextField pagax;
    private javax.swing.JPasswordField pass;
    private javax.swing.JTextField pl;
    private javax.swing.JTextField plac;
    private javax.swing.JTextField placax;
    private javax.swing.JTextField prod;
    private javax.swing.JButton px;
    private rojeru_san.RSLabelFecha rSLabelFecha1;
    private rojeru_san.RSLabelHora rSLabelHora1;
    private javax.swing.JTextField razonx;
    private javax.swing.JButton retx;
    private javax.swing.JTextField rucx;
    private javax.swing.JTextField se;
    private javax.swing.JTextField sobx;
    private javax.swing.JTextField tarx;
    private javax.swing.JTextField te;
    private com.toedter.calendar.demo.TestDateEvaluator testDateEvaluator1;
    private javax.swing.JComboBox<String> tipel;
    private javax.swing.JComboBox<String> tipex;
    private javax.swing.JComboBox<String> tipox;
    private javax.swing.JComboBox<String> tipp;
    private javax.swing.JTextField toe;
    private javax.swing.JTextField tolx;
    private javax.swing.JTextField usu;
    private javax.swing.JPanel xd;
    private javax.swing.JPanel xdx;
    private javax.swing.JPanel xdxx;
    private javax.swing.JTabbedPane xprincipal;
    // End of variables declaration//GEN-END:variables

    /*public void obtener_datos_producto() {
        String nombre = tipox.getSelectedItem().toString();
        p = d.datos_producto(nombre);
        String a = String.valueOf(p.getTarifa());
        String b = String.valueOf(p.getHoras());
        String c = String.valueOf(p.getTarsobreestadia());
        String d = String.valueOf(p.getTolerancia());
        tar.setText("  " + a);
        hor.setText("  " + b);
        sob.setText("  " + c);
        tol.setText("  " + d);
    }*/
    public void boton_ingreso() {
        px.setVisible(true);
    }
    
    public void validar() {
        boolean in;
        String usuario = usu.getText();
        String contra = new String(pass.getPassword());
        in = d.validar(usua, usuario, contra);
        if (in == true) {
            lu.setVisible(false);
            usu.setVisible(false);
            lc.setVisible(false);
            pass.setVisible(false);
            bv.setVisible(false);
            cc.setVisible(true);
            xprincipal.setEnabledAt(2, true);
            xprincipal.setEnabledAt(3, true);
            JOptionPane.showMessageDialog(null, "Bienvenido " + usuario);
        }
    }
    
    public void regresar() {
        lu.setVisible(false);
        usu.setVisible(true);
        lc.setVisible(true);
        pass.setVisible(true);
        bv.setVisible(true);
        cc.setVisible(false);
        xprincipal.setEnabledAt(2, false);
        xprincipal.setEnabledAt(3, false);
        JOptionPane.showMessageDialog(null, "Sesion Cerrada");
    }
    
    public void combo_productos() {
        tipox.addItem(" ");
        tipex.addItem("Todos");
        List<producto> lista = d.listar_producto();
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            tipox.addItem(nombre);
            tipp.addItem(nombre);
            tipex.addItem(nombre);
            tipel.addItem(nombre);
        }
    }
    
    public class accion_espacio implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            int capacidad = d.capacidad();
            int cap = Integer.parseInt(ltot.getText());
            
            for (int i = 0; i < capacidad; i++) {
                if (e.getSource().equals(esp[i])) {
                    if (esp[i].isEnabled()) {
                        String placa = placax.getText();
                        String producto = tipox.getSelectedItem().toString();
                        String numeroletra = esp[i].getText();
                        int espacio = Integer.parseInt(numeroletra);
                        System.out.println("Elejiste el slot " + espacio + " de los " + esp.length + " Slots.");
                        if (esp[i].getBackground().equals(l)) {
                            if (placa.equals("")) {
                                JOptionPane.showMessageDialog(null, "Faltan datos.");
                                //principal();
                            } else {
                                if (cap == 0) {
                                    JOptionPane.showMessageDialog(null, "Estacionamiento lleno");
                                } else {
                                    if (d.verificar_placa(placa) == true) {
                                        JOptionPane.showMessageDialog(null, "ERROR: El vehiculo " + placa + " se encuentra dentro del estacionamiento.");
                                        //principal();
                                    } else {
                                        ingresar(placa, producto, espacio);
                                        esp[i].setBackground(o);
                                        //principal();
                                    }
                                }
                            }
                        } else if (esp[i].getBackground().equals(o)) {
                            retirar_espacio(espacio);
                            esp[i].setBackground(l);
                            principal();
                        }
                    }
                }
            }
        }
    }
}
