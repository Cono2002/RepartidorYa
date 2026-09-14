import modelo.Cliente;
import modelo.ClienteRepositorio;
import modelo.ClienteRepositorioMemoria;
import modelo.ItemPedido;
import modelo.Pedido;
import modelo.PedidoRepositorio;
import modelo.PedidoRepositorioJson;
import modelo.TipoProducto;
import modelo.TipoProductoRepositorio;
import modelo.TipoProductoRepositorioMemoria;

public class ProbarPedidoCompleto {
    public static void main(String[] args) {
        System.out.println("=== 'Arranque 1' del programa: creamos un pedido y lo guardamos ===");
        {
            ClienteRepositorio clienteRepo = new ClienteRepositorioMemoria();
            Cliente juan = new Cliente("Juan", "Perez", "Calle Falsa 123", "1122334455");
            clienteRepo.guardar(juan);

            TipoProductoRepositorio productoRepo = new TipoProductoRepositorioMemoria();
            productoRepo.guardar(new TipoProducto("Hamburguesa", 3500.0));
            productoRepo.guardar(new TipoProducto("Papas fritas", 1800.0));

            PedidoRepositorio pedidoRepo = new PedidoRepositorioJson("pedidos_completo.json", clienteRepo, productoRepo);

            Pedido pedido = new Pedido(juan, 2500);
            pedido.agregarItem(new ItemPedido(productoRepo.buscarPorNombre("Hamburguesa").get(), 2));
            pedido.agregarItem(new ItemPedido(productoRepo.buscarPorNombre("Papas fritas").get(), 1));
            pedidoRepo.guardar(pedido);

            System.out.println("Guardado. Pedido N°: " + pedido.getNumeroPedido());
        }

        System.out.println();
        System.out.println("=== 'Arranque 2' del programa: proceso nuevo, objetos nuevos ===");
        {
            ClienteRepositorio clienteRepo = new ClienteRepositorioMemoria();
            Cliente juan = new Cliente("Juan", "Perez", "Calle Falsa 123", "1122334455");
            clienteRepo.guardar(juan);

            TipoProductoRepositorio productoRepo = new TipoProductoRepositorioMemoria();
            productoRepo.guardar(new TipoProducto("Hamburguesa", 3500.0));
            productoRepo.guardar(new TipoProducto("Papas fritas", 1800.0));

            PedidoRepositorio pedidoRepo = new PedidoRepositorioJson("pedidos_completo.json", clienteRepo, productoRepo);

            System.out.println("Pedidos reconstruidos desde el archivo: " + pedidoRepo.listarTodos().size());
            for (Pedido p : pedidoRepo.listarTodos()) {
                System.out.println("Pedido N°: " + p.getNumeroPedido());
                System.out.println("  Cliente: " + p.getCliente().getNombre() + " " + p.getCliente().getApellido()
                        + " (" + p.getCliente().getTelefono() + ")");
                System.out.println("  Estado: " + p.getEstado());
                System.out.println("  Distancia: " + p.getDistancia() + " m, costo delivery: $" + p.getCostoDelivery());
                for (ItemPedido item : p.getItems()) {
                    System.out.println("  - " + item.getCantidad() + " x " + item.getProducto().getNombre()
                            + " (precio real desde el catálogo: $" + item.getProducto().getPrecio() + ")");
                }
                System.out.println("  Gasto total: $" + p.calcularGastoTotal());
            }
        }
    }
}
