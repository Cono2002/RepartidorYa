package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TipoProductoRepositorioMemoria implements TipoProductoRepositorio {

    private List<TipoProducto> tiposProducto = new ArrayList<>();

    public void guardar(TipoProducto tipo) {
        tiposProducto.add(tipo);
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
