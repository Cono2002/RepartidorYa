package modelo;

public class Cliente extends Persona {

    private int numeroPedido;
    private String telefono;
    private String orden;

    public Cliente(String nombre, String apellido, int numeroPedido, String telefono, String orden) {
        super(nombre, apellido);
        this.numeroPedido = numeroPedido;
        this.telefono = telefono;
        this.orden = orden;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }
}
