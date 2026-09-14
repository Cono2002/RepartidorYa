import modelo.Cliente;
import modelo.ItemPedido;
import modelo.Pedido;
import modelo.PedidoRepositorio;
import modelo.PedidoRepositorioJson;
import modelo.TipoProducto;

public class ProbarPedidoJson {
    public static void main(String[] args) {
        Cliente juan = new Cliente("Juan", "Perez", "Calle Falsa 123", "1122334455");

        Pedido pedido = new Pedido(juan, 2500);
        pedido.agregarItem(new ItemPedido(new TipoProducto("Hamburguesa", 3500.0), 2));
        pedido.agregarItem(new ItemPedido(new TipoProducto("Papas fritas", 1800.0), 1));
        pedido.setNumeroPedido(1);

        // Ojo: acá NO usamos PedidoRepositorioMemoria (que llama a
        // cliente.agregarPedido(pedido), generando la relación circular real).
        // Guardamos directo con el Json para probar que la serialización
        // no la sigue, incluso si el ciclo existiera en memoria.
        juan.agregarPedido(pedido);

        PedidoRepositorio repo = new PedidoRepositorioJson("pedidos.json");
        repo.guardar(pedido);

        System.out.println("Guardado sin errores. Pedidos en el repositorio: " + repo.listarTodos().size());
    }
}
