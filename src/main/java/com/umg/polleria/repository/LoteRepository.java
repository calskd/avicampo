package com.umg.polleria.repository;
import com.umg.polleria.model.*;
import org.springframework.data.jpa.repository.*;
import java.math.BigDecimal;
import java.util.List;
public interface LoteRepository extends JpaRepository<Lote, Long> {
    List<Lote> findByEstadoOrderByFechaIngresoAsc(EstadoLote estado);
    @Query("select coalesce(sum(l.cantidadActual),0) from Lote l")
    Integer pollosTotales();
    @Query("select coalesce(sum(l.cantidadActual * l.costoUnitario),0) from Lote l")
    BigDecimal valorTotalInventario();
}
