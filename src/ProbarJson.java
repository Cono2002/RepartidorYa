import modelo.TipoProducto;
import modelo.TipoProductoRepositorio;
import modelo.TipoProductoRepositorioJson;

public class ProbarJson {
    public static void main(String[] args) {
        TipoProductoRepositorio repo = new TipoProductoRepositorioJson("productos.json");

        System.out.println("Productos ya guardados al arrancar: " + repo.listarTodos().size());
        for (TipoProducto tp : repo.listarTodos()) {
            System.out.println(" - " + tp.getNombre() + " ($" + tp.getPrecio() + ")");
        }

        if (repo.listarTodos().isEmpty()) {
            System.out.println("Catálogo vacío, cargando productos por primera vez...");
            repo.guardar(new TipoProducto("Hamburguesa", 3500.0));
            repo.guardar(new TipoProducto("Papas fritas", 1800.0));
        }
    }
}
