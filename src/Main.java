import java.util.Arrays;
import java.util.List;

import modelo.Chat;
import modelo.Cliente;
import modelo.ItemPedido;
import modelo.Pedido;
import modelo.TipoProducto;
import modelo.Vendedor;

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
