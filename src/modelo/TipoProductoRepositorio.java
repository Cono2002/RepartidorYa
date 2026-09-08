package modelo;

import java.util.List;
import java.util.Optional;

public interface TipoProductoRepositorio {
    void guardar(TipoProducto tipo);
    Optional<TipoProducto> buscarPorNombre(String nombre);
    List<TipoProducto> listarTodos();
}
