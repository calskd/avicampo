package com.umg.polleria.repository;
import com.umg.polleria.model.DetalleVenta;
import org.springframework.data.jpa.repository.*;
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    @Query("select coalesce(sum(d.cantidad),0) from DetalleVenta d where d.lote.idLote = ?1")
    Integer vendidosPorLote(Long idLote);
}
