package com.umg.polleria.repository;
import com.umg.polleria.model.VacunacionLote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface VacunacionLoteRepository extends JpaRepository<VacunacionLote, Long> {
    List<VacunacionLote> findTop1ByOrderByFechaAplicacionDescIdDesc();
    List<VacunacionLote> findByLoteIdLoteOrderByFechaAplicacionDesc(Long idLote);
}
