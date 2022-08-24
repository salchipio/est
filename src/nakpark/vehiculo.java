package nakpark;

public class vehiculo {

    private String placa;
    private String estado;

    public vehiculo(String placa, String estado) {
        this.placa = placa;
        this.estado = estado;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
