package modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TipoProductoRepositorioJson implements TipoProductoRepositorio {

    private final Path archivo;
    private List<TipoProducto> tiposProducto;

    public TipoProductoRepositorioJson(String rutaArchivo) {
        this.archivo = Path.of(rutaArchivo);
        this.tiposProducto = cargarDesdeArchivo();
    }

    private List<TipoProducto> cargarDesdeArchivo() {
        List<TipoProducto> lista = new ArrayList<>();
        if (!Files.exists(archivo)) {
            return lista;
        }
        try {
            String contenido = Files.readString(archivo);
            Pattern patron = Pattern.compile("\"nombre\"\\s*:\\s*\"([^\"]*)\"\\s*,\\s*\"precio\"\\s*:\\s*([0-9.]+)");
            Matcher matcher = patron.matcher(contenido);
            while (matcher.find()) {
                String nombre = matcher.group(1);
                double precio = Double.parseDouble(matcher.group(2));
                lista.add(new TipoProducto(nombre, precio));
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de productos: " + e.getMessage());
        }
        return lista;
    }

    private void guardarEnArchivo() {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < tiposProducto.size(); i++) {
            TipoProducto tp = tiposProducto.get(i);
            json.append("  {\"nombre\": \"").append(tp.getNombre())
                    .append("\", \"precio\": ").append(tp.getPrecio()).append("}");
            if (i < tiposProducto.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("]\n");

        try {
            Files.writeString(archivo, json.toString());
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo de productos: " + e.getMessage());
        }
    }

    public void guardar(TipoProducto tipo) {
        tiposProducto.add(tipo);
        guardarEnArchivo();
    }

    public Optional<TipoProducto> buscarPorNombre(String nombre) {
        for (TipoProducto tp : tiposProducto) {
            if (tp.getNombre().equalsIgnoreCase(nombre)) {
                return Optional.of(tp);
            }
        }
        return Optional.empty();
    }

    public List<TipoProducto> listarTodos() {
        return tiposProducto;
    }
}
