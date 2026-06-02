package com.universidad.pedidosu11.repository;

import com.universidad.pedidosu11.model.Pedido;
import com.universidad.pedidosu11.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Método auxiliar para el código original (luego se puede mover a otro repo)
    default Producto findProductoById(Long id) {
        // Simulación - en realidad deberías tener ProductoRepository
        return new Producto(id, "Producto " + id, 100.0);
    }
}