package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private Cliente cliente;
    private int numeroPedido;
    private List<ItemPedido> items;
    private double costoDelivery;
    private int distancia;
    private Estado estado;
    private LocalDate fecha;

    public Pedido(Cliente cliente, int distancia) {
        this.cliente = cliente;
        this.distancia = distancia;
        this.items = new ArrayList<>();
        this.costoDelivery = calcularEnvio(distancia);
        this.estado = Estado.EN_PROCESO;
        this.fecha = LocalDate.now();
    }

    public double calcularEnvio(int distancia) {
        int tramosDeUnKm = distancia / 1000;
        return tramosDeUnKm * 500;
    }

    public double calcularGastoTotal() {
        double totalProductos = 0;
        for (ItemPedido item : items) {
            totalProductos += item.getCantidad() * item.getProducto().getPrecio();
        }
        return totalProductos + costoDelivery;
    }

    public void agregarItem(ItemPedido item) {
        items.add(item);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public double getCostoDelivery() {
        return costoDelivery;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
        this.costoDelivery = calcularEnvio(distancia);
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
