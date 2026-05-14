package com.umg.polleria.repository;
import com.umg.polleria.model.CompraLote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CompraLoteRepository extends JpaRepository<CompraLote, Long> {
    List<CompraLote> findTop8ByOrderByFechaDescIdCompraDesc();
}
