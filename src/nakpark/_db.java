package nakpark;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class _db {

    Connection con;
    String dri = "com.mysql.cj.jdbc.Driver";
    String sql = "jdbc:mysql://localhost/nakpark?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String nom = "root";
    String pass = "";

    public _db() {

        try {
            Class.forName(dri);
            con = DriverManager.getConnection(sql, nom, pass);
            System.out.println("Conecto");
        } catch (Exception e) {
            System.out.println("No conecto");
        }
    }

    public boolean asignarespacio() {
        boolean ae = false;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select asignarcasillero from detalle_estacionamiento");
            if (rs.next()) {
                ae = rs.getBoolean(1);
            }
        } catch (Exception ex) {
            System.out.println("error asignarespacio: " + ex.getMessage());
        }
        return ae;
    }

    public void ingreso(espacio a) {
        try {
            PreparedStatement ps = con.prepareStatement("insert into vehiculo (placa, producto, espacio, horaentrada, estado) values(?,?,?,?,?)");
            ps.setString(1, a.getVehiculo().getPlaca());
            ps.setString(2, a.getProducto().getNombre());
            ps.setInt(3, a.getEspacio());
            ps.setString(4, a.getHoraingreso());
            ps.setString(5, a.getVehiculo().getEstado());
            ps.executeUpdate();
            System.out.println("Exito en la consulta iv!");
            System.out.println(" " + a.getVehiculo().getPlaca());
            System.out.println(" " + a.getProducto().getNombre());
            ticket_entrada(a.getVehiculo().getPlaca(), a.getProducto().getNombre(), a.getEspacio(), a.getHoraingreso());
        } catch (Exception ex) {
            System.out.println("Error en ingresar_v: " + ex.getMessage());
        }
    }

    public void ingreso_se(espacio a) {
        try {
            PreparedStatement ps = con.prepareStatement("insert into vehiculo (placa, producto, horaentrada, estado) values(?,?,?,?)");
            ps.setString(1, a.getVehiculo().getPlaca());
            ps.setString(2, a.getProducto().getNombre());
            ps.setString(3, a.getHoraingreso());
            ps.setString(4, a.getVehiculo().getEstado());
            ps.executeUpdate();
            System.out.println("Exito en la consulta iv!");
            System.out.println(" " + a.getVehiculo().getPlaca());
            System.out.println(" " + a.getProducto().getNombre());
            ticket_entrada(a.getVehiculo().getPlaca(), a.getProducto().getNombre(), a.getHoraingreso());
        } catch (Exception ex) {
            System.out.println("Error en ingresar_v: " + ex.getMessage());
        }
    }

    public void ticket_entrada(String placa, String producto, String he) {
        Font f = new Font(FontFamily.COURIER, 12.0f, Font.NORMAL, BaseColor.BLACK);
        Font fo = new Font(FontFamily.HELVETICA, 12.0f, Font.NORMAL, BaseColor.BLACK);
        Font fon = new Font(FontFamily.HELVETICA, 16.0f, Font.NORMAL, BaseColor.BLACK);
        Font fonx = new Font(FontFamily.HELVETICA, 30.0f, Font.BOLD, BaseColor.BLACK);
        Font fonxx = new Font(FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
        try {
            PreparedStatement ps = con.prepareStatement("select * from detalle_estacionamiento where id = '1'");
            PreparedStatement pst = con.prepareStatement("select * from producto where nombre = '" + producto + "'");
            ResultSet rs = ps.executeQuery();
            ResultSet res = pst.executeQuery();
            rs.next();
            res.next();
            try {
                String dest = "C:/reportes/reporte.pdf";
                Document doc = new Document(PageSize.A6);
                PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream(dest));
                doc.open();
                Barcode39 bc = new Barcode39();
                bc.setCode(placa);
                Image img = bc.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                img.scalePercent(210);
                img.setAlignment(Element.ALIGN_CENTER);
                Paragraph para = new Paragraph("Ticket de ingreso", f);
                para.setAlignment(Element.ALIGN_CENTER);
                Paragraph para1 = new Paragraph(rs.getString("razon"), fo);
                para1.setAlignment(Element.ALIGN_CENTER);
                Paragraph para2 = new Paragraph("ruc: " + rs.getString("ruc"), fo);
                para2.setAlignment(Element.ALIGN_CENTER);
                Paragraph para3 = new Paragraph(rs.getString("direccion"), fo);
                para3.setAlignment(Element.ALIGN_CENTER);
                Paragraph para4 = new Paragraph("celular: " + rs.getString("celular"), fo);
                para4.setAlignment(Element.ALIGN_CENTER);
                Paragraph para5 = new Paragraph(" ");
                para5.setAlignment(Element.ALIGN_CENTER);
                Paragraph para6 = new Paragraph(producto, fon);
                para6.setAlignment(Element.ALIGN_CENTER);
                Paragraph para7 = new Paragraph(placa, fonx);
                para7.setAlignment(Element.ALIGN_CENTER);
                Paragraph para8 = new Paragraph("Hora de entrada: " + he, fo);
                para8.setAlignment(Element.ALIGN_CENTER);
                Paragraph para9 = new Paragraph("Tarifa S/. " + res.getDouble("tarifa") + " por " + res.getDouble("horas") + " horas", fo);
                para9.setAlignment(Element.ALIGN_CENTER);
                Paragraph para10 = new Paragraph(rs.getString("comentario"), fonxx);
                para10.setAlignment(Element.ALIGN_CENTER);
                doc.add(para);
                doc.add(para1);
                doc.add(para2);
                doc.add(para3);
                doc.add(para4);
                doc.add(para5);
                doc.add(para6);
                doc.add(para7);
                doc.add(para8);
                doc.add(para9);
                doc.add(para10);
                doc.add(img);
                doc.close();
            } catch (Exception exc) {
                System.out.println("ing: " + exc.getMessage());
            }
            try {
                if ((new File("c:\\reportes/reporte.pdf")).exists()) {
                    Process p = Runtime
                            .getRuntime()
                            .exec("rundll32 url.dll,FileProtocolHandler c:\\reportes/reporte.pdf");
                    p.waitFor();
                } else {
                    System.out.println("documento no existe");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ew) {
            System.out.println(ew.toString());
        }
    }

    public void ticket_entrada(String placa, String producto, int espacio, String he) {
        Font f = new Font(FontFamily.COURIER, 12.0f, Font.NORMAL, BaseColor.BLACK);
        Font fo = new Font(FontFamily.HELVETICA, 12.0f, Font.NORMAL, BaseColor.BLACK);
        Font fon = new Font(FontFamily.HELVETICA, 16.0f, Font.NORMAL, BaseColor.BLACK);
        Font fonx = new Font(FontFamily.HELVETICA, 30.0f, Font.BOLD, BaseColor.BLACK);
        Font fonxx = new Font(FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
        try {
            PreparedStatement ps = con.prepareStatement("select * from detalle_estacionamiento where id = '1'");
            PreparedStatement pst = con.prepareStatement("select * from producto where nombre = '" + producto + "'");
            ResultSet rs = ps.executeQuery();
            ResultSet res = pst.executeQuery();
            rs.next();
            res.next();
            try {
                String dest = "C:/reportes/reporte.pdf";
                Document doc = new Document(PageSize.A6);
                PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream(dest));
                doc.open();
                Barcode39 bc = new Barcode39();
                bc.setCode(placa);
                Image img = bc.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                img.scalePercent(210);
                img.setAlignment(Element.ALIGN_CENTER);
                Paragraph para = new Paragraph("Ticket de ingreso", f);
                para.setAlignment(Element.ALIGN_CENTER);
                Paragraph para1 = new Paragraph(rs.getString("razon"), fo);
                para1.setAlignment(Element.ALIGN_CENTER);
                Paragraph para2 = new Paragraph("ruc: " + rs.getString("ruc"), fo);
                para2.setAlignment(Element.ALIGN_CENTER);
                Paragraph para3 = new Paragraph(rs.getString("direccion"), fo);
                para3.setAlignment(Element.ALIGN_CENTER);
                Paragraph para4 = new Paragraph("celular: " + rs.getString("celular"), fo);
                para4.setAlignment(Element.ALIGN_CENTER);
                Paragraph para5 = new Paragraph(" ");
                para5.setAlignment(Element.ALIGN_CENTER);
                Paragraph para6 = new Paragraph(producto, fon);
                para6.setAlignment(Element.ALIGN_CENTER);
                Paragraph para7 = new Paragraph(placa, fonx);
                para7.setAlignment(Element.ALIGN_CENTER);
                Paragraph para8 = new Paragraph("Espacio # " + espacio, fo);
                para8.setAlignment(Element.ALIGN_CENTER);
                Paragraph para9 = new Paragraph("Hora de entrada: " + he, fo);
                para9.setAlignment(Element.ALIGN_CENTER);
                Paragraph para10 = new Paragraph("Tarifa S/. " + res.getDouble("tarifa") + " por " + res.getDouble("horas") + " horas", fo);
                para10.setAlignment(Element.ALIGN_CENTER);
                Paragraph para11 = new Paragraph(rs.getString("comentario"), fonxx);
                para11.setAlignment(Element.ALIGN_CENTER);
                doc.add(para);
                doc.add(para1);
                doc.add(para2);
                doc.add(para3);
                doc.add(para4);
                doc.add(para5);
                doc.add(para6);
                doc.add(para7);
                doc.add(para8);
                doc.add(para9);
                doc.add(para10);
                doc.add(para11);
                doc.add(img);
                doc.close();
            } catch (Exception exc) {
                System.out.println("ing: " + exc.getMessage());
            }
            try {
                if ((new File("c:\\reportes/reporte.pdf")).exists()) {
                    Process p = Runtime
                            .getRuntime()
                            .exec("rundll32 url.dll,FileProtocolHandler c:\\reportes/reporte.pdf");
                    p.waitFor();
                } else {
                    System.out.println("documento no existe");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ew) {
            System.out.println(ew.toString());
        }
    }

    public void salida(String placa) {
        Double valori = 0.0;
        Double valor = 0.0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String fechas = dateFormat.format(date);
        String es = (String) fechas;
        String fechaHora = dateFormat.format(date);
        DecimalFormat formato = new DecimalFormat("#0.00");
        try {
            PreparedStatement ps = con.prepareStatement("select placa, producto, espacio, horaentrada from vehiculo where placa='" + placa + "' and estado = 'en'");
            ResultSet rs = ps.executeQuery();
            rs.first();
            String placax = rs.getString(1);
            String producto = rs.getString(2);
            int esp = rs.getInt(3);
            String hora_salida = rs.getString(4);
            System.out.println("producto: " + producto);

            PreparedStatement pst = con.prepareStatement("select tarifa, horas, sobreestadia, tolerancia from producto where nombre='" + producto + "'");
            ResultSet res = pst.executeQuery();
            res.first();

            Double tarifa = res.getDouble(1);
            Double horasbd = res.getDouble(2);
            Double sobreestadia = res.getDouble(3);
            Double tolerancia = res.getDouble(4);

            Date horasalida = dateFormat.parse(hora_salida);
            float horasacobrar = (float) (date.getTime() - horasalida.getTime()) / 3600000;
            Double cant_h = (horasbd + tolerancia);
            System.out.println("Cantidad de horas dentro: " + horasacobrar);
            System.out.println("horas + tolerancia: " + cant_h);

            if (rs.getString(2).equals(producto)) {
                if (horasacobrar < cant_h) {
                    valor = Math.ceil(horasacobrar - tolerancia) * tarifa;
                } else {
                    valor = (Math.ceil((horasacobrar - cant_h)) * sobreestadia) + tarifa; //ARREGLAR O NO TAL VEZ... SOBRE ESTADIA POR HORA
                }
            }
            ps.executeUpdate("UPDATE vehiculo SET horasalida='" + fechaHora + "',estado='fuera', valorpagado='" + valor + "' where espacio='" + esp + "' and producto='" + producto + "' and estado = 'en'");
            if (esp == 0) {
                JOptionPane.showMessageDialog(null, "Placa sin numero de espacio: quite el check de asignar espacios");
            } else {
                factura(placa, producto, tarifa, hora_salida, fechaHora, horasacobrar, valor);
            }
        } catch (Exception ex) {
            System.out.println("error al retirar: " + ex.toString());
            JOptionPane.showMessageDialog(null, "no hay datos");
        }
    }

    public void salidar_normal(String placa) {
        Double valori = 0.0;
        Double valor = 0.0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String fechas = dateFormat.format(date);
        String es = (String) fechas;
        String fechaHora = dateFormat.format(date);
        DecimalFormat formato = new DecimalFormat("#0.00");
        try {
            PreparedStatement ps = con.prepareStatement("select placa, producto, horaentrada from vehiculo where placa='" + placa + "' and estado = 'en'");
            ResultSet rs = ps.executeQuery();
            rs.first();
            String placax = rs.getString(1);
            String producto = rs.getString(2);
            String hora_salida = rs.getString(3);
            System.out.println("producto: " + producto);

            PreparedStatement pst = con.prepareStatement("select tarifa, horas, sobreestadia, tolerancia from producto where nombre='" + producto + "'");
            ResultSet res = pst.executeQuery();
            res.first();

            Double tarifa = res.getDouble(1);
            Double horasbd = res.getDouble(2);
            Double sobreestadia = res.getDouble(3);
            Double tolerancia = res.getDouble(4);

            Date horasalida = dateFormat.parse(hora_salida);
            float horasacobrar = (float) (date.getTime() - horasalida.getTime()) / 3600000;
            Double cant_h = (horasbd + tolerancia);
            System.out.println("Cantidad de horas dentro: " + horasacobrar);
            System.out.println("horas + tolerancia: " + cant_h);

            if (rs.getString(2).equals(producto)) {
                if (horasacobrar < cant_h) {
                    valor = Math.ceil(horasacobrar - tolerancia) * tarifa;
                } else {
                    valor = (Math.ceil((horasacobrar - cant_h)) * sobreestadia) + tarifa; //ARREGLAR O NO TAL VEZ... SOBRE ESTADIA POR HORA
                }
            }
            ps.executeUpdate("UPDATE vehiculo SET horasalida='" + fechaHora + "',estado='fuera', valorpagado='" + valor + "' where placa = '" + placa + "' and producto='" + producto + "' and estado = 'en'");
            factura(placa, producto, tarifa, hora_salida, fechaHora, horasacobrar, valor);
        } catch (Exception ex) {
            System.out.println("error al retirar: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "no hay datos");
        }
    }

    public String[] datos_salida(String placa) {
        String[] o = new String[6];
        Double valori = 0.0;
        Double valor = 0.0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String fechas = dateFormat.format(date);
        String es = (String) fechas;
        String fechaHora = dateFormat.format(date);
        DecimalFormat formato = new DecimalFormat("#0.00");
        try {
            PreparedStatement ps = con.prepareStatement("select placa, producto, espacio, horaentrada from vehiculo where placa='" + placa + "' and estado = 'en'");
            ResultSet rs = ps.executeQuery();
            rs.first();
            String placax = rs.getString(1);
            String producto = rs.getString(2);
            int esp = rs.getInt(3);
            String hora_salida = rs.getString(4);
            System.out.println("producto: " + producto);

            PreparedStatement pst = con.prepareStatement("select tarifa, horas, sobreestadia, tolerancia from producto where nombre='" + producto + "'");
            ResultSet res = pst.executeQuery();
            res.first();

            Double tarifa = res.getDouble(1);
            Double horasbd = res.getDouble(2);
            Double sobreestadia = res.getDouble(3);
            Double tolerancia = res.getDouble(4);

            Date horasalida = dateFormat.parse(hora_salida);
            float horasacobrar = (float) (date.getTime() - horasalida.getTime()) / 3600000;
            Double cant_h = (horasbd + tolerancia);
            System.out.println("Cantidad de horas dentro: " + horasacobrar);
            System.out.println("horas + tolerancia: " + cant_h);

            if (rs.getString(2).equals(producto)) {
                if (horasacobrar < cant_h) {
                    valor = Math.ceil(horasacobrar - tolerancia) * tarifa;
                } else {
                    valor = (Math.ceil((horasacobrar - cant_h)) * sobreestadia) + tarifa; //ARREGLAR O NO TAL VEZ... SOBRE ESTADIA POR HORA
                }
            }
            o[0] = placa;
            o[1] = producto;
            o[2] = hora_salida;
            o[3] = fechaHora;
            o[4] = String.valueOf(formato.format(horasacobrar));
            o[5] = String.valueOf(formato.format(valor));
        } catch (Exception ex) {
            System.out.println("error al retirar: " + ex.toString());
            JOptionPane.showMessageDialog(null, "no hay datos");
        }
        return o;
    }

    public void ticket_salida(String placa, String producto, Double tarifa, String horae, String hs, float cantidadhoras, double pago) {
        Font f = new Font(FontFamily.COURIER, 14.0f, Font.NORMAL, BaseColor.BLACK);
        Font fo = new Font(FontFamily.HELVETICA, 12.0f, Font.BOLD, BaseColor.BLACK);
        Font fon = new Font(FontFamily.HELVETICA, 12.0f, Font.NORMAL, BaseColor.BLACK);
        DecimalFormat formato = new DecimalFormat("#0.00");
        try {
            PreparedStatement ps = con.prepareStatement("select * from detalle_estacionamiento where id = '1'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            try {
                String dest = "C:/reportes/reporte.pdf";
                Document doc = new Document(PageSize.A6);
                PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream(dest));
                doc.open();
                Paragraph para = new Paragraph("Ticket Salida", f);
                para.setAlignment(Element.ALIGN_CENTER);
                Paragraph para1 = new Paragraph(rs.getString("razon"), fo);
                para1.setAlignment(Element.ALIGN_CENTER);
                Paragraph para2 = new Paragraph(rs.getString("direccion"), f);
                para2.setAlignment(Element.ALIGN_CENTER);
                Paragraph para3 = new Paragraph("celular: " + rs.getString("celular"), f);
                para3.setAlignment(Element.ALIGN_CENTER);
                Paragraph para4 = new Paragraph("--------------------------------------------");
                para4.setAlignment(Element.ALIGN_CENTER);
                Paragraph para5 = new Paragraph("Prodcuto: " + producto, f);
                Paragraph para6 = new Paragraph("Placa: " + placa, f);
                Paragraph para7 = new Paragraph("Hora de entrada: " + horae, f);
                Paragraph para8 = new Paragraph("Hora de salida: " + hs, f);
                Paragraph para9 = new Paragraph("Num de horas: " + formato.format(cantidadhoras), f);
                Paragraph para10 = new Paragraph("--------------------------------------------");
                para10.setAlignment(Element.ALIGN_CENTER);
                Paragraph para11 = new Paragraph("Pago: " + pago, f);
                Paragraph para12 = new Paragraph("--------------------------------------------");
                para12.setAlignment(Element.ALIGN_CENTER);
                Paragraph para13 = new Paragraph(rs.getString("comentario"), fon);
                para13.setAlignment(Element.ALIGN_CENTER);
                doc.add(para);
                doc.add(para1);
                doc.add(para3);
                doc.add(para4);
                doc.add(para5);
                doc.add(para6);
                doc.add(para7);
                doc.add(para8);
                doc.add(para9);
                doc.add(para10);
                doc.add(para11);
                doc.close();
            } catch (Exception exc) {
                System.out.println("tik_sal: " + exc);
            }
            try {
                if ((new File("c:\\reportes/reporte.pdf")).exists()) {
                    Process p = Runtime
                            .getRuntime()
                            .exec("rundll32 url.dll,FileProtocolHandler c:\\reportes/reporte.pdf");
                    p.waitFor();
                } else {
                    System.out.println("documento no existe");
                }
            } catch (Exception ex) {
                System.out.println("xd: " + ex.getMessage());
            }
        } catch (Exception ew) {
            System.out.println(ew.getMessage());
        }
    }

    public void retirar_espacio(int espacio) {
        Double valor = 0.0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String fechas = dateFormat.format(date);
        String es = (String) fechas;
        String fechaHora = dateFormat.format(date);
        DecimalFormat formato = new DecimalFormat("#0.00");
        try {
            Statement st = con.createStatement();
            Statement sta = con.createStatement();
            ResultSet rs = st.executeQuery("select horaentrada, producto, placa from vehiculo where espacio='" + espacio + "' and estado = 'en'");
            rs.first();
            String tipo = rs.getString(2);
            ResultSet res = sta.executeQuery("select tarifa, horas, sobreestadia, tolerancia from producto where nombre='" + tipo + "'");
            res.first();

            String hora_salida = rs.getString(1);
            String placa = rs.getString(3);

            Double tarifa = res.getDouble(1);
            Double horasbd = res.getDouble(2);
            Double sobreestadia = res.getDouble(3);
            Double tolerancia = res.getDouble(4);

            Date horasalida = dateFormat.parse(hora_salida);
            float horasacobrar = (float) (date.getTime() - horasalida.getTime()) / 3600000;
            Double cant_h = (horasbd + tolerancia);
            System.out.println("Cantidad de horas dentro: " + horasacobrar);
            System.out.println("horas + tolerancia: " + cant_h);

            if (rs.getString(2).equals(tipo)) {
                if (horasacobrar < cant_h) {
                    valor = Math.ceil(horasacobrar - tolerancia) * tarifa;
                } else {
                    valor = (Math.ceil((horasacobrar - cant_h)) * sobreestadia) + tarifa; //ARREGLAR O NO TAL VEZ... SOBRE ESTADIA POR HORA
                }
            }
            int pane = JOptionPane.showConfirmDialog(null, "\n\nTipo:                   " + tipo + "\n\nPlaca:                  " + placa + "\n\nHora entrada:    " + hora_salida + "\n\nHora salida:       " + fechaHora + "\n\nHoras                  " + formato.format(horasacobrar) + "\n\nCargo:               S/." + valor + "\n\n", "RETIRAR VEHICULO", JOptionPane.YES_NO_OPTION);
            if (pane == JOptionPane.YES_OPTION) {
                st.executeUpdate("UPDATE vehiculo SET horasalida='" + fechaHora + "',estado='fuera', valorpagado='" + valor + "' where espacio='" + espacio + "' and producto='" + tipo + "' and estado = 'en'");
                factura(placa, tipo, tarifa, hora_salida, fechaHora, horasacobrar, valor);
            }
        } catch (Exception ex) {
            System.out.println("error al retirar: " + ex.getMessage());
        }
    }

    ///METODO FACTURA
    public void factura(String placa, String producto, Double tarifa, String horae, String hs, float cantidadhoras, double pago) {
        Font f = new Font(FontFamily.COURIER, 12.0f, Font.NORMAL, BaseColor.BLACK);
        Font fo = new Font(FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK);
        Font fon = new Font(FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK);
        DecimalFormat formato = new DecimalFormat("#0.00");
        try {
            PreparedStatement ps = con.prepareStatement("select * from detalle_estacionamiento where id = '1'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            try {
                Double ig = rs.getDouble("igv");
                Double subt = pago - (pago * ig);
                Double igv = pago * ig;
                Double tot = subt + igv;
                String dest = "C:/reportes/reporte.pdf";
                Document doc = new Document(PageSize.A6);
                PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream(dest));
                doc.open();
                Barcode39 bc = new Barcode39();
                bc.setCode(placa);
                Image img = bc.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                img.scalePercent(110);
                img.setAlignment(Element.ALIGN_CENTER);
                Paragraph para = new Paragraph("Factura", f);
                para.setAlignment(Element.ALIGN_CENTER);
                Paragraph para1 = new Paragraph(rs.getString("razon"), fo);
                para1.setAlignment(Element.ALIGN_CENTER);
                Paragraph para2 = new Paragraph("ruc: " + rs.getString("ruc"), f);
                para2.setAlignment(Element.ALIGN_CENTER);
                Paragraph para3 = new Paragraph(rs.getString("direccion"), f);
                para3.setAlignment(Element.ALIGN_CENTER);
                Paragraph para4 = new Paragraph("celular: " + rs.getString("celular"), f);
                para4.setAlignment(Element.ALIGN_CENTER);
                Paragraph para5 = new Paragraph("--------------------------------------------");
                para5.setAlignment(Element.ALIGN_CENTER);
                Paragraph para6 = new Paragraph("Prodcuto: " + producto, f);
                Paragraph para7 = new Paragraph("Placa: " + placa, f);
                Paragraph para8 = new Paragraph("Hora de entrada: " + horae, f);
                Paragraph para9 = new Paragraph("Hora de salida: " + hs, f);
                Paragraph para10 = new Paragraph("Num de horas: " + formato.format(cantidadhoras), f);
                Paragraph para11 = new Paragraph("--------------------------------------------");
                para11.setAlignment(Element.ALIGN_CENTER);
                Paragraph para12 = new Paragraph("Subtotal: " + formato.format(subt), f);
                para12.setAlignment(Element.ALIGN_CENTER);
                Paragraph para13 = new Paragraph("IGV " + ig + "%: " + formato.format(igv), f);
                para13.setAlignment(Element.ALIGN_CENTER);
                Paragraph para14 = new Paragraph("Total: " + tot, f);
                para14.setAlignment(Element.ALIGN_CENTER);
                Paragraph para15 = new Paragraph("--------------------------------------------");
                para15.setAlignment(Element.ALIGN_CENTER);
                Paragraph para16 = new Paragraph(rs.getString("comentario"), fon);
                para16.setAlignment(Element.ALIGN_CENTER);
                doc.add(para);
                doc.add(para1);
                doc.add(para2);
                doc.add(para3);
                doc.add(para4);
                doc.add(para5);
                doc.add(para6);
                doc.add(para7);
                doc.add(para8);
                doc.add(para9);
                doc.add(para10);
                doc.add(para11);
                doc.add(para12);
                doc.add(para13);
                doc.add(para14);
                doc.add(para15);
                doc.add(para16);
                doc.close();
            } catch (Exception exc) {
                System.out.println("ing: " + exc.getMessage());
            }
            try {
                if ((new File("c:\\reportes/reporte.pdf")).exists()) {
                    Process p = Runtime
                            .getRuntime()
                            .exec("rundll32 url.dll,FileProtocolHandler c:\\reportes/reporte.pdf");
                    p.waitFor();
                } else {
                    System.out.println("documento no existe");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ew) {
            System.out.println(ew.toString());
        }
    }

    public void registrar_producto(producto p) {
        try {
            PreparedStatement ps = con.prepareStatement("insert into producto (nombre, tarifa, horas, sobreestadia, tolerancia) values(?,?,?,?,?)");
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getTarifa());
            ps.setDouble(3, p.getHoras());
            ps.setDouble(4, p.getTarsobreestadia());
            ps.setDouble(5, p.getTolerancia());
            ps.executeUpdate();
            System.out.println("Exito en la consulta reg prod!");
        } catch (Exception ex) {
            System.out.println("Error en registrar reg prod: " + ex.getMessage());
        }
    }

    public void modificar_producto(String nombre, producto p) {
        try {
            PreparedStatement ps = con.prepareStatement("update producto set tarifa=?, horas=?, sobreestadia=?, tolerancia=? where nombre='" + nombre + "'");
            ps.setDouble(1, p.getTarifa());
            ps.setDouble(2, p.getHoras());
            ps.setDouble(3, p.getTarsobreestadia());
            ps.setDouble(4, p.getTolerancia());
            ps.executeUpdate();
            System.out.println("Exito en la consulta mod pro!");
        } catch (Exception ex) {
            System.out.println("Error en modificar prod: " + ex);
        }
    }

    public void eliminar_producto(String nombre) {
        try {
            PreparedStatement ps = con.prepareStatement("delete from producto where nombre='" + nombre + "'");
            ps.executeUpdate();
            System.out.println("Exito en la consulta eliminar producto");
        } catch (Exception ex) {
            System.out.println("Error en e prod: " + ex.getMessage());
        }
    }

    public DefaultTableModel listar_vehiculos(String producto, String estado, String fecha, String placa) {
        DefaultTableModel fila = new DefaultTableModel();
        fila.addColumn("Placa");
        fila.addColumn("Producto");
        fila.addColumn("Espacio");
        fila.addColumn("Hora entrada");
        fila.addColumn("Hora salida");
        fila.addColumn("Pago");
        System.out.println(fecha);
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from vehiculo where estado='" + estado + "' AND producto LIKE'%" + producto + "%' AND placa LIKE '%" + placa + "%' AND horaentrada LIKE '%" + fecha + "%'");
           
            rs.first();
            do {
                String horasalida = rs.getString(6);
                String pago = rs.getString(7);
                if (horasalida == null) {
                    horasalida = " ";
                    pago = " ";
                } else {
                    horasalida = rs.getString(6);
                    pago = rs.getString(7);
                }
                String[] vehiculo = {rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), horasalida, "S./ " + pago};
                fila.addRow(vehiculo);
            } while (rs.next());
            System.out.println("Exito en la consulta listado!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "No hay datos");
        }
        return fila;
    }
    
    public DefaultTableModel listar_vehiculos_fecha(String fecha, String fechax) {
        DefaultTableModel fila = new DefaultTableModel();
        fila.addColumn("Placa");
        fila.addColumn("Producto");
        fila.addColumn("Espacio");
        fila.addColumn("Hora entrada");
        fila.addColumn("Hora salida");
        fila.addColumn("Pago");
        System.out.println(fecha);
        System.out.println(fechax);
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from vehiculo where horaentrada BETWEEN '"+fecha+"' AND '"+fechax+"'");
            rs.first();
            do {
                String horasalida = rs.getString(6);
                String pago = rs.getString(7);
                if (horasalida == null) {
                    horasalida = " ";
                    pago = " ";
                } else {
                    horasalida = rs.getString(6);
                    pago = rs.getString(7);
                }
                String[] vehiculo = {rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), horasalida, "S./ " + pago};
                fila.addRow(vehiculo);
            } while (rs.next());
            System.out.println("Exito en la consulta listado!");
        } catch (Exception ex) {
            System.out.println("ret fecha "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "No hay datos");
        }
        return fila;
    }

    public int capacidad() {
        int a = 0;
        try {
            PreparedStatement ps = con.prepareStatement("select capacidad from detalle_estacionamiento where id='1'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                a = rs.getInt(1);
            }
        } catch (Exception ex) {
            System.out.println("Error en cap: " + ex.getMessage());
        }
        return a;
    }

    public void capacidad_actualizada(int capacidad, JButton[] esp) {
        Color o = new Color(252, 159, 159);
        try {
            PreparedStatement ps = con.prepareStatement("select espacio,estado from vehiculo");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int espacio = rs.getInt("espacio");
                String estado = rs.getString("estado");
                for (int i = 0; i < capacidad; i++) {
                    String numeroletra = esp[i].getText();
                    int espac = Integer.parseInt(numeroletra);
                    if (espacio == espac && (estado.equals("en"))) {
                        esp[i].setBackground(o);
                    } else {
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("cap act err " + e);
        }
    }

    public boolean verificar_placa(String placa) {
        boolean en_o_fuera = false;
        String estado = "en";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select estado from vehiculo where placa='" + placa + "'");
            if (rs.last()) {
                String es = rs.getString(1);
                if (es.equals(estado)) {
                    en_o_fuera = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("error verificar_placa: " + ex.getMessage());
        }
        return en_o_fuera;
    }

    public List listar_producto() {
        int id = 0;
        List<producto> pr = new ArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from producto");
            while (rs.next()) {
                producto p = new producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                pr.add(p);
                System.out.println(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return pr;
    }

    public double reportar_ingresos(String estado, String producto, String placa, String fecha) {
        double ingresos = 0.0;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT SUM(valorpagado)FROM vehiculo WHERE estado='" + estado + "' AND producto LIKE'%" + producto + "%' AND placa LIKE '%" + placa + "%' AND horaentrada LIKE '" + fecha + "%'");
            rs.first();
            DecimalFormat df = new DecimalFormat("#.00");
            ingresos = Double.parseDouble(rs.getString(1));
            System.out.println("exito en consulta reportar: " + ingresos);
        } catch (Exception ex) {
            System.out.println("ing "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "No hay datos");
        }
        return ingresos;
    }

    public producto datos_producto(String nombre) {
        producto p = new producto();
        try {
            PreparedStatement ps = con.prepareStatement("select tarifa, horas, sobreestadia, tolerancia from producto where nombre = '" + nombre + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p.setTarifa(rs.getDouble(1));
                p.setHoras(rs.getDouble(2));
                p.setTarsobreestadia(rs.getDouble(3));
                p.setTolerancia(rs.getDouble(4));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    public detalle_estacionamiento datos_estacionamiento() {
        detalle_estacionamiento d = new detalle_estacionamiento();
        try {
            PreparedStatement ps = con.prepareStatement("select * from detalle_estacionamiento");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                d.setRazon(rs.getString("razon"));
                d.setRuc(rs.getString("ruc"));
                d.setDireccion(rs.getString("direccion"));
                d.setCelular(rs.getString("celular"));
                d.setComentario(rs.getString("comentario"));
                d.setCapacidad(rs.getInt("capacidad"));
                d.setIgv(rs.getDouble("igv"));
                d.setAsignarcasilleros(rs.getBoolean("asignarcasillero"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return d;
    }

    public void modificar_datos(detalle_estacionamiento de) {
        try {
            PreparedStatement ps = con.prepareStatement("update detalle_estacionamiento set razon=?, ruc=?, direccion=?, celular=?, igv=?, capacidad=?, comentario=?, asignarcasillero=? where id='1'");
            ps.setString(1, de.getRazon());
            ps.setString(2, de.getRuc());
            ps.setString(3, de.getDireccion());
            ps.setString(4, de.getCelular());
            ps.setDouble(5, de.getIgv());
            ps.setInt(6, de.getCapacidad());
            ps.setString(7, de.getComentario());
            ps.setBoolean(8, de.isAsignarcasilleros());
            ps.executeUpdate();
            System.out.println("Exito en modificar_datos");
        } catch (Exception ex) {
            System.out.println("Error en modificar_datos: " + ex);
        }
    }

    public int datos_capacidad() {
        int d = 0;
        try {
            PreparedStatement ps = con.prepareStatement("select count(*)total from vehiculo where estado= 'en'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                d = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return d;
    }

    public boolean validar(usuario usu, String usuario, String contra) {
        boolean in = false;
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select usu, contra, rol from usuario where usu = '" + usuario + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usu.setUsuario(rs.getString("usu"));
                usu.setContrase(rs.getString("contra"));
                usu.setRol(rs.getString("rol"));
                //System.out.println("login + " + u + " " + c + " " + r + " ");
                if (contra.equals(rs.getString("contra"))) {
                    if (rs.getString("rol").equals("admin")) {
                        in = true;
                    } else if (rs.getString("rol").equals("cajero")) {
                        in = true;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta.");
                    in = false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "El usuario no existe.");
                in = false;
            }
        } catch (Exception ex) {
            System.out.println("xd " + ex.getMessage());
        }
        return in;
    }

    public void exportar_excel(JTable t) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(ruta);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
                archivoXLS.createNewFile();
                Workbook libro = new HSSFWorkbook();
                FileOutputStream archivo = new FileOutputStream(archivoXLS);
                Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
                hoja.setDisplayGridlines(false);
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(f);
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (f == 0) {
                            celda.setCellValue(t.getColumnName(c));
                        }
                    }
                }
                int filaInicio = 1;
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(filaInicio);
                    filaInicio++;
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (t.getValueAt(f, c) instanceof Double) {
                            celda.setCellValue(Double.parseDouble(t.getValueAt(f, c).toString()));
                        } else if (t.getValueAt(f, c) instanceof Float) {
                            celda.setCellValue(Float.parseFloat((String) t.getValueAt(f, c)));
                        } else {
                            celda.setCellValue(String.valueOf(t.getValueAt(f, c)));
                        }
                    }
                }
                libro.write(archivo);
                archivo.close();
                Desktop.getDesktop().open(archivoXLS);
            } catch (IOException | NumberFormatException e) {
                throw e;
            }
        }
    }
}
