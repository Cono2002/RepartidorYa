package modelo;

public class ItemPedido {

    private TipoProducto producto;
    private int cantidad;

    public ItemPedido(TipoProducto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public TipoProducto getProducto() {
        return producto;
    }

    public void setProducto(TipoProducto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
