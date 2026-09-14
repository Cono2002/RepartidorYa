package modelo;

import java.util.List;

public interface PedidoRepositorio {
    void guardar(Pedido pedido);
    List<Pedido> listarTodos();
}
