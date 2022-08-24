package nakpark;

public class espacio {

    private int id;
    private int espacio;
    private vehiculo vehiculo;
    private producto producto;
    private String horaingreso;
    private String horasalida;
    private double cargo;

    public espacio() {
    }

    public espacio(int id) {
        this.id = id;
    }

    public espacio(int espacio, vehiculo vehiculo, producto producto, String horaingreso) {
        this.espacio = espacio;
        this.vehiculo = vehiculo;
        this.producto = producto;
        this.horaingreso = horaingreso;
    }

    public espacio(int espacio, vehiculo vehiculo, producto producto, String horaingreso, String horasalida, double cargo) {
        this.espacio = espacio;
        this.vehiculo = vehiculo;
        this.producto = producto;
        this.horaingreso = horaingreso;
        this.horasalida = horasalida;
        this.cargo = cargo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEspacio() {
        return espacio;
    }

    public void setEspacio(int espacio) {
        this.espacio = espacio;
    }

    public vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public producto getProducto() {
        return producto;
    }

    public void setProducto(producto producto) {
        this.producto = producto;
    }

    public String getHoraingreso() {
        return horaingreso;
    }

    public void setHoraingreso(String horaingreso) {
        this.horaingreso = horaingreso;
    }

    public String getHorasalida() {
        return horasalida;
    }

    public void setHorasalida(String horasalida) {
        this.horasalida = horasalida;
    }

    public double getCargo() {
        return cargo;
    }

    public void setCargo(double cargo) {
        this.cargo = cargo;
    }
}