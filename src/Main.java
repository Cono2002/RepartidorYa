import modelo.Cliente;
import modelo.ItemPedido;
import modelo.Pedido;
import modelo.TipoProducto;

public class Main {

    public static void main(String[] args) {
        Cliente cliente = new Cliente("Juan", "Perez", "Calle Falsa 123", "1122334455");

        Pedido pedido = new Pedido(cliente, 1, 2500);
        cliente.agregarPedido(pedido);

        TipoProducto hamburguesa = new TipoProducto("Hamburguesa", 3500.0);
        TipoProducto papas = new TipoProducto("Papas fritas", 1800.0);

        pedido.agregarItem(new ItemPedido(hamburguesa, 2));
        pedido.agregarItem(new ItemPedido(papas, 1));

        System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellido());
        System.out.println("Domicilio: " + cliente.getDomicilio());
        System.out.println("Pedido N°: " + pedido.getNumeroPedido());
        System.out.println("Estado: " + pedido.getEstado());
        System.out.println("Distancia: " + pedido.getDistancia() + " m");
        System.out.println("Costo delivery: $" + pedido.getCostoDelivery());
        System.out.println("Gasto total: $" + pedido.calcularGastoTotal());
        System.out.println("Pedidos del cliente: " + cliente.getPedidos().size());
    }
}
