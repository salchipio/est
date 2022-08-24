package nakpark;

public class producto {
    
    private int id;
    private String nombre;
    private double tarifa;
    private double horas;
    private double tarsobreestadia;
    private double tolerancia;
    
    _db d = new _db();
    
    public producto() {
    }
    
    public producto(String nombre) {
        this.nombre = nombre;
    }
    
    public void registrar_producto(String nombre, double tarifa, double horas, double tarsobreestadia, double tolerancia) {
        this.nombre = nombre;
        this.tarifa = tarifa;
        this.horas = horas;
        this.tarsobreestadia = tarsobreestadia;
        this.tolerancia = tolerancia;
        d.registrar_producto(this);
    }
    
    public void modificar_producto(String nombre, double tarifa, double horas, double tarsobreestadia, double tolerancia) {
        this.tarifa = tarifa;
        this.horas = horas;
        this.tarsobreestadia = tarsobreestadia;
        this.tolerancia = tolerancia;
        d.modificar_producto(nombre, this);
    }
    
    public void eliminar_producto(String placa) {
        d.eliminar_producto(placa);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public double getTarifa() {
        return tarifa;
    }
    
    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }
    
    public double getHoras() {
        return horas;
    }
    
    public void setHoras(double horas) {
        this.horas = horas;
    }
    
    public double getTarsobreestadia() {
        return tarsobreestadia;
    }
    
    public void setTarsobreestadia(double tarsobreestadia) {
        this.tarsobreestadia = tarsobreestadia;
    }
    
    public double getTolerancia() {
        return tolerancia;
    }
    
    public void setTolerancia(double tolerancia) {
        this.tolerancia = tolerancia;
    }
}