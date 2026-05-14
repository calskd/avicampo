package com.umg.polleria.repository;
import com.umg.polleria.model.Cliente;
import org.springframework.data.jpa.repository.*;
import java.math.BigDecimal;
import java.util.List;
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNombreContainingIgnoreCaseOrTelefonoContainingIgnoreCase(String nombre, String telefono);
    @Query("select coalesce(sum(c.saldoPendiente),0) from Cliente c")
    BigDecimal saldoTotalPendiente();
}
