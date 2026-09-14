package modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepositorioJson implements PedidoRepositorio {

    private final Path archivo;
    private List<Pedido> pedidos = new ArrayList<>();

    public PedidoRepositorioJson(String rutaArchivo) {
        this.archivo = Path.of(rutaArchivo);
        // Ojo: todavía no reconstruimos los Pedidos completos al arrancar el programa.
        // Para eso necesitaríamos poder buscar el Cliente real a partir del teléfono
        // guardado, y eso requiere un ClienteRepositorio que todavía no armamos.
    }

    public void guardar(Pedido pedido) {
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
