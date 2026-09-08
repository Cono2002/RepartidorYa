package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepositorio {

    private List<Pedido> pedidos = new ArrayList<>();
    private int contadorDelDia = 0;
    private LocalDate fechaContador = null;

    public void guardar(Pedido pedido) {
        LocalDate hoy = LocalDate.now();
        if (!hoy.equals(fechaContador)) {
            contadorDelDia = 0;
            fechaContador = hoy;
        }
        contadorDelDia++;
        pedido.setNumeroPedido(contadorDelDia);

        pedidos.add(pedido);
        pedido.getCliente().agregarPedido(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidos;
    }
}
