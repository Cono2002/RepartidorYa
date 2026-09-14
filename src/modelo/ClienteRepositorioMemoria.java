package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepositorioMemoria implements ClienteRepositorio {

    private List<Cliente> clientes = new ArrayList<>();

    public void guardar(Cliente cliente) {
        clientes.add(cliente);
    }

    public Optional<Cliente> buscarPorTelefono(String telefono) {
        for (Cliente c : clientes) {
            if (c.getTelefono().equalsIgnoreCase(telefono)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    public List<Cliente> listarTodos() {
        return clientes;
    }
}
