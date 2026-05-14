package com.umg.polleria.repository;
import com.umg.polleria.model.Mortalidad;
import org.springframework.data.jpa.repository.*;
import java.util.List;
public interface MortalidadRepository extends JpaRepository<Mortalidad, Long> {
    List<Mortalidad> findByLoteIdLoteOrderByFechaDesc(Long idLote);
    @Query("select coalesce(sum(m.cantidad),0) from Mortalidad m")
    Integer totalMuertes();
    @Query("select coalesce(sum(m.cantidad),0) from Mortalidad m where m.lote.idLote = ?1")
    Integer muertesPorLote(Long idLote);
}
