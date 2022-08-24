package nakpark;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class estacionamiento {

    private vehiculo v;
    private espacio e;
    private producto p;

    _db d;

    public estacionamiento() {

    }

    public void ingreso(String placa, String producto, int espacio) {
        d = new _db();
        String estado = "en";
        e = new espacio();
        v = new vehiculo(placa, estado);
        p = new producto(producto);
        e.setVehiculo(v);
        e.setProducto(p);
        e.setEspacio(espacio);
        e.setHoraingreso(hora());
        d.ingreso(e);
    }
    
    public void ingreso_se(String placa, String producto){
        d = new _db();
        String estado = "en";
        e = new espacio();
        v = new vehiculo(placa, estado);
        p = new producto(producto);
        e.setVehiculo(v);
        e.setProducto(p);
        e.setHoraingreso(hora());
        d.ingreso_se(e);
    }

    public void salida(String placa) {
        d = new _db();
        d.salida(placa);
    }
    
    public void salida_normal(String placa){
        d = new _db();
        d.salidar_normal(placa);
    }

    public String hora() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String he = dateFormat.format(date);
        return he;
    }
}
