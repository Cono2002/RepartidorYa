package modelo;

public class Vendedor extends Persona {

    private boolean disponibilidad;

    public Vendedor(String nombre, String apellido, boolean disponibilidad) {
        super(nombre, apellido);
        this.disponibilidad = disponibilidad;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}
