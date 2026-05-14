package com.umg.polleria.repository;
import com.umg.polleria.model.Venta;
import org.springframework.data.jpa.repository.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findTop8ByOrderByFechaDesc();
    List<Venta> findByClienteIdClienteOrderByFechaDesc(Long idCliente);
    @Query("select coalesce(sum(v.total),0) from Venta v where v.fecha between ?1 and ?2")
    BigDecimal totalVendidoDia(LocalDateTime inicio, LocalDateTime fin);
    @Query("select count(v) from Venta v where v.fecha between ?1 and ?2")
    Long ventasDia(LocalDateTime inicio, LocalDateTime fin);
}
