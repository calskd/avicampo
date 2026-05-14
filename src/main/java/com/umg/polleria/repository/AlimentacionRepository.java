package com.umg.polleria.repository;
import com.umg.polleria.model.Alimentacion;
import org.springframework.data.jpa.repository.*;
import java.math.BigDecimal;
import java.util.List;
public interface AlimentacionRepository extends JpaRepository<Alimentacion, Long> {
    List<Alimentacion> findByLoteIdLoteOrderByFechaDesc(Long idLote);
    @Query("select coalesce(sum(a.cantidadKg),0) from Alimentacion a")
    BigDecimal consumoTotalKg();
    @Query("select coalesce(sum(a.cantidadKg),0) from Alimentacion a where a.lote.idLote = ?1")
    BigDecimal consumoPorLote(Long idLote);
}
