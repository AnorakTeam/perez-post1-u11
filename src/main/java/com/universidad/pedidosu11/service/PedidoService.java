package com.universidad.pedidosu11.service;

import com.universidad.pedidosu11.model.LineaPedido;
import com.universidad.pedidosu11.model.Pedido;
import com.universidad.pedidosu11.repository.PedidoRepository;
import com.universidad.pedidosu11.valueobject.CodigoDescuento;
import com.universidad.pedidosu11.valueobject.DatosCliente;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class PedidoService {
    
    private final PedidoRepository repo;
    private final NotificationService notification;
    
    // Inyección por constructor (elimina @Autowired en campo)
    public PedidoService(PedidoRepository repo, NotificationService notification) {
        this.repo = repo;
        this.notification = notification;
    }
    
    // Método principal refactorizado (máximo 8 líneas)
    public String procesarPedido(DatosCliente cliente, 
                                 LineaPedido[] lineas,
                                 boolean esUrgente,
                                 CodigoDescuento descuento) {
        double total = calcularTotal(lineas);
        double totalConDescuento = aplicarDescuento(total, descuento);
        notification.notificarPedido(cliente, esUrgente);
        return persistirPedido(cliente, totalConDescuento);
    }
    
    private double calcularTotal(LineaPedido[] lineas) {
        return Arrays.stream(lineas)
                .mapToDouble(l -> l.getPrecioUnitario() * l.getCantidad())
                .sum();
    }
    
    private double aplicarDescuento(double total, CodigoDescuento descuento) {
        if (descuento != null && descuento.esValido()) {
            return total * (1 - descuento.getPorcentaje());
        }
        return total;
    }
    
    private String persistirPedido(DatosCliente cliente, double total) {
        Pedido pedido = new Pedido(cliente, total);
        return "OK_" + repo.save(pedido).getId();
    }
}