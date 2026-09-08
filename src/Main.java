import java.util.Arrays;
import java.util.List;

import modelo.Chat;
import modelo.Cliente;
import modelo.ItemPedido;
import modelo.Pedido;
import modelo.PedidoRepositorio;
import modelo.TipoProducto;
import modelo.TipoProductoRepositorio;
import modelo.Vendedor;

public class Main {

    public static void main(String[] args) {
        TipoProductoRepositorio productoRepositorio = new TipoProductoRepositorio();
        productoRepositorio.guardar(new TipoProducto("Hamburguesa", 3500.0));
        productoRepositorio.guardar(new TipoProducto("Papas fritas", 1800.0));

        PedidoRepositorio pedidoRepositorio = new PedidoRepositorio();

        Cliente cliente = new Cliente("Juan", "Perez", "Calle Falsa 123", "1122334455");

        Pedido pedido = new Pedido(cliente, 2500);
        pedidoRepositorio.guardar(pedido);

        productoRepositorio.buscarPorNombre("Hamburguesa")
                .ifPresent(producto -> pedido.agregarItem(new ItemPedido(producto, 2)));

        productoRepositorio.buscarPorNombre("Papas Fritas")
                .ifPresentOrElse(
                        producto -> pedido.agregarItem(new ItemPedido(producto, 1)),
                        () -> System.out.println("No encontramos ese producto en el catálogo"));

        productoRepositorio.buscarPorNombre("Empanadas")
                .ifPresentOrElse(
                        producto -> pedido.agregarItem(new ItemPedido(producto, 1)),
                        () -> System.out.println("No encontramos 'Empanadas' en el catálogo."));

        System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellido());
        System.out.println("Domicilio: " + cliente.getDomicilio());
        System.out.println("Pedido N°: " + pedido.getNumeroPedido());
        System.out.println("Estado: " + pedido.getEstado());
        System.out.println("Distancia: " + pedido.getDistancia() + " m");
        System.out.println("Costo delivery: $" + pedido.getCostoDelivery());
        System.out.println("Gasto total: $" + pedido.calcularGastoTotal());
        System.out.println("Pedidos del cliente: " + cliente.getPedidos().size());
        System.out.println("Pedidos en el repositorio: " + pedidoRepositorio.listarTodos().size());

        System.out.println();
        System.out.println("=== Prueba 1: vendedor preferido disponible ===");
        probarContactoVendedor(
                cliente,
                new Vendedor("Ana", "Gomez", true),
                Arrays.asList(
                        new Vendedor("Luis", "Diaz", false),
                        new Vendedor("Marta", "Ruiz", true)));

        System.out.println();
        System.out.println("=== Prueba 2: vendedor preferido no disponible, pero hay otro disponible ===");
        probarContactoVendedor(
                cliente,
                new Vendedor("Ana", "Gomez", false),
                Arrays.asList(
                        new Vendedor("Luis", "Diaz", false),
                        new Vendedor("Marta", "Ruiz", true)));

        System.out.println();
        System.out.println("=== Prueba 3: ningún vendedor disponible ===");
        probarContactoVendedor(
                cliente,
                new Vendedor("Ana", "Gomez", false),
                Arrays.asList(
                        new Vendedor("Luis", "Diaz", false),
                        new Vendedor("Marta", "Ruiz", false)));
    }

    private static void probarContactoVendedor(Cliente cliente, Vendedor preferido, List<Vendedor> vendedores) {
        Chat chat = new Chat(cliente);
        chat.contactarVendedor(preferido, vendedores);

        Vendedor asignado = chat.getVendedor();
        if (asignado != null) {
            System.out.println("Vendedor asignado: " + asignado.getNombre() + " " + asignado.getApellido()
                    + " (disponible: " + asignado.isDisponibilidad() + ")");
        } else {
            System.out.println("Vendedor asignado: ninguno (no hay vendedores disponibles)");
        }
    }
}
