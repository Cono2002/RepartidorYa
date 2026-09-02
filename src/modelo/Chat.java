package modelo;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    private Cliente cliente;
    private Vendedor vendedor;
    private List<Mensaje> mensajes;

    public Chat(Cliente cliente) {
        this.cliente = cliente;
        this.mensajes = new ArrayList<>();
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public void contactarVendedor(Vendedor vendedorPreferido, List<Vendedor> vendedores) {
        if (vendedorPreferido.isDisponibilidad()) {
            setVendedor(vendedorPreferido);
            return;
        }

        for (Vendedor candidato : vendedores) {
            if (candidato.isDisponibilidad()) {
                setVendedor(candidato);
                return;
            }
        }

        setVendedor(null);
    }

    public void agregarMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }
}
