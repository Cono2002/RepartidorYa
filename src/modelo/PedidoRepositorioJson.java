package modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PedidoRepositorioJson implements PedidoRepositorio {

    private final Path archivo;
    private final ClienteRepositorio clienteRepositorio;
    private final TipoProductoRepositorio productoRepositorio;
    private List<Pedido> pedidos;
    private int contadorDelDia = 0;
    private LocalDate fechaContador = null;

    public PedidoRepositorioJson(String rutaArchivo, ClienteRepositorio clienteRepositorio,
            TipoProductoRepositorio productoRepositorio) {
        this.archivo = Path.of(rutaArchivo);
        this.clienteRepositorio = clienteRepositorio;
        this.productoRepositorio = productoRepositorio;
        this.pedidos = cargarDesdeArchivo();

        LocalDate hoy = LocalDate.now();
        for (Pedido p : pedidos) {
            if (p.getFecha().equals(hoy) && p.getNumeroPedido() > contadorDelDia) {
                contadorDelDia = p.getNumeroPedido();
            }
        }
        fechaContador = hoy;
    }

    private List<Pedido> cargarDesdeArchivo() {
        List<Pedido> lista = new ArrayList<>();
        if (!Files.exists(archivo)) {
            return lista;
        }

        try {
            String contenido = Files.readString(archivo);

            Pattern patronPedido = Pattern.compile(
                    "\\{\\s*\"numeroPedido\":\\s*(\\d+),\\s*\"clienteTelefono\":\\s*\"([^\"]*)\",\\s*"
                            + "\"distancia\":\\s*(\\d+),\\s*\"fecha\":\\s*\"([^\"]*)\",\\s*"
                            + "\"estado\":\\s*\"([^\"]*)\",\\s*\"items\":\\s*\\[(.*?)\\]\\s*\\}",
                    Pattern.DOTALL);
            Pattern patronItem = Pattern.compile("\\{\"producto\":\\s*\"([^\"]*)\",\\s*\"cantidad\":\\s*(\\d+)\\}");

            Matcher matcherPedido = patronPedido.matcher(contenido);
            while (matcherPedido.find()) {
                int numeroPedido = Integer.parseInt(matcherPedido.group(1));
                String telefono = matcherPedido.group(2);
                int distancia = Integer.parseInt(matcherPedido.group(3));
                LocalDate fecha = LocalDate.parse(matcherPedido.group(4));
                String estadoTexto = matcherPedido.group(5);
                String itemsTexto = matcherPedido.group(6);

                var clienteOpt = clienteRepositorio.buscarPorTelefono(telefono);
                if (clienteOpt.isEmpty()) {
                    System.out.println("Aviso: no se encontró un cliente con teléfono " + telefono
                            + ", se omite el pedido #" + numeroPedido);
                    continue;
                }

                Pedido pedido = new Pedido(clienteOpt.get(), distancia);
                pedido.setNumeroPedido(numeroPedido);
                pedido.setFecha(fecha);
                pedido.setEstado(Estado.valueOf(estadoTexto));

                Matcher matcherItem = patronItem.matcher(itemsTexto);
                while (matcherItem.find()) {
                    String nombreProducto = matcherItem.group(1);
                    int cantidad = Integer.parseInt(matcherItem.group(2));

                    productoRepositorio.buscarPorNombre(nombreProducto).ifPresentOrElse(
                            producto -> pedido.agregarItem(new ItemPedido(producto, cantidad)),
                            () -> System.out.println("Aviso: no se encontró el producto '" + nombreProducto
                                    + "' al cargar el pedido #" + numeroPedido));
                }

                lista.add(pedido);
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer pedidos.json: " + e.getMessage());
        }
        return lista;
    }

    public void guardar(Pedido pedido) {
        LocalDate hoy = LocalDate.now();
        if (!hoy.equals(fechaContador)) {
            contadorDelDia = 0;
            fechaContador = hoy;
        }
        contadorDelDia++;
        pedido.setNumeroPedido(contadorDelDia);

        pedidos.add(pedido);
        guardarEnArchivo();
    }

    public List<Pedido> listarTodos() {
        return pedidos;
    }

    private void guardarEnArchivo() {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = pedidos.get(i);
            json.append("  {\n");
            json.append("    \"numeroPedido\": ").append(p.getNumeroPedido()).append(",\n");
            json.append("    \"clienteTelefono\": \"").append(p.getCliente().getTelefono()).append("\",\n");
            json.append("    \"distancia\": ").append(p.getDistancia()).append(",\n");
            json.append("    \"fecha\": \"").append(p.getFecha()).append("\",\n");
            json.append("    \"estado\": \"").append(p.getEstado().name()).append("\",\n");
            json.append("    \"items\": [");

            List<ItemPedido> items = p.getItems();
            for (int j = 0; j < items.size(); j++) {
                ItemPedido item = items.get(j);
                json.append("{\"producto\": \"").append(item.getProducto().getNombre())
                        .append("\", \"cantidad\": ").append(item.getCantidad()).append("}");
                if (j < items.size() - 1) {
                    json.append(", ");
                }
            }
            json.append("]\n  }");
            if (i < pedidos.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("]\n");

        try {
            Files.writeString(archivo, json.toString());
        } catch (IOException e) {
            System.out.println("No se pudo guardar pedidos.json: " + e.getMessage());
        }
    }
}
