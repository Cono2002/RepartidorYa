package modelo;

import java.util.List;
import java.util.Optional;

public interface ClienteRepositorio {
    void guardar(Cliente cliente);
    Optional<Cliente> buscarPorTelefono(String telefono);
    List<Cliente> listarTodos();
}
