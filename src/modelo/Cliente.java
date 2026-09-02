package modelo;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {

    private String domicilio;
    private String telefono;
    private List<Pedido> pedidos;

    public Cliente(String nombre, String apellido, String domicilio, String telefono) {
        super(nombre, apellido);
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.pedidos = new ArrayList<>();
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }
}
