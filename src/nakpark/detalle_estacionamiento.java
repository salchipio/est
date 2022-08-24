
package nakpark;

public class detalle_estacionamiento {
    
    private String razon;
    private String ruc;
    private String direccion;
    private String celular;
    private String comentario;
    private double igv;
    private int capacidad;
    private boolean asignarcasilleros;
    private _db d;

    public detalle_estacionamiento() {
    }

    public void modificar_detalle(String razon, String ruc, String direccion, String celular, String comentario, double igv, int capacidad, boolean asignarcasilleros) {
        d = new _db();
        this.razon = razon;
        this.ruc = ruc;
        this.direccion = direccion;
        this.celular = celular;
        this.comentario = comentario;
        this.igv = igv;
        this.capacidad = capacidad;
        this.asignarcasilleros = asignarcasilleros;
        d.modificar_datos(this);
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    public boolean isAsignarcasilleros() {
        return asignarcasilleros;
    }

    public void setAsignarcasilleros(boolean asignarcasilleros) {
        this.asignarcasilleros = asignarcasilleros;
    }
}
