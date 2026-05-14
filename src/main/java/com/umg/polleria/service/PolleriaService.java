package com.umg.polleria.service;

import com.umg.polleria.model.*;
import com.umg.polleria.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PolleriaService {
    private final LoteRepository loteRepo;
    private final CompraLoteRepository compraRepo;
    private final ClienteRepository clienteRepo;
    private final VentaRepository ventaRepo;
    private final DetalleVentaRepository detalleRepo;
    private final AlimentacionRepository alimentacionRepo;
    private final VacunacionLoteRepository vacunacionRepo;
    private final MortalidadRepository mortalidadRepo;

    public PolleriaService(LoteRepository loteRepo, CompraLoteRepository compraRepo,
                           ClienteRepository clienteRepo, VentaRepository ventaRepo,
                           DetalleVentaRepository detalleRepo, AlimentacionRepository alimentacionRepo,
                           VacunacionLoteRepository vacunacionRepo, MortalidadRepository mortalidadRepo) {
        this.loteRepo = loteRepo;
        this.compraRepo = compraRepo;
        this.clienteRepo = clienteRepo;
        this.ventaRepo = ventaRepo;
        this.detalleRepo = detalleRepo;
        this.alimentacionRepo = alimentacionRepo;
        this.vacunacionRepo = vacunacionRepo;
        this.mortalidadRepo = mortalidadRepo;
    }

    @Transactional
    public void registrarCompra(CompraLote compra) {
        validarPositivo(compra.getCantidad(), "La cantidad debe ser mayor a cero.");
        validarDinero(compra.getCostoUnitario(), "El costo unitario debe ser mayor a cero.");

        Lote lote = new Lote();
        lote.setFechaIngreso(compra.getFecha() == null ? LocalDate.now() : compra.getFecha());
        lote.setCantidadInicial(compra.getCantidad());
        lote.setCantidadActual(compra.getCantidad());
        lote.setCostoUnitario(compra.getCostoUnitario());
        lote.setEstado(EstadoLote.ACTIVO);
        lote = loteRepo.save(lote);

        compra.setLote(lote);
        compra.setFecha(lote.getFechaIngreso());
        compra.setTotal(compra.getCostoUnitario().multiply(BigDecimal.valueOf(compra.getCantidad())));
        compraRepo.save(compra);
    }

    @Transactional
    public void registrarVenta(Long idCliente, Long idLote, Integer cantidad, BigDecimal precio, TipoPago tipoPago) {
        validarPositivo(cantidad, "La cantidad debe ser mayor a cero.");
        validarDinero(precio, "El precio debe ser mayor a cero.");

        Cliente cliente = clienteRepo.findById(idCliente).orElseThrow(() -> new RuntimeException("Cliente no encontrado."));
        Lote lote = loteRepo.findById(idLote).orElseThrow(() -> new RuntimeException("Lote no encontrado."));

        if (lote.getCantidadActual() < cantidad) throw new RuntimeException("No hay suficientes pollos en ese lote.");

        BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(cantidad));

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(LocalDateTime.now());
        venta.setTipoPago(tipoPago);
        venta.setTotal(subtotal);
        venta = ventaRepo.save(venta);

        DetalleVenta detalle = new DetalleVenta();
        detalle.setVenta(venta);
        detalle.setLote(lote);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precio);
        detalle.setSubtotal(subtotal);
        detalleRepo.save(detalle);

        lote.setCantidadActual(lote.getCantidadActual() - cantidad);
        if (lote.getCantidadActual() == 0) lote.setEstado(EstadoLote.CERRADO);
        loteRepo.save(lote);

        if (tipoPago == TipoPago.CREDITO) {
            cliente.setSaldoPendiente(cliente.getSaldoPendiente().add(subtotal));
            clienteRepo.save(cliente);
        }
    }

    @Transactional
    public void registrarMortalidad(Mortalidad mortalidad) {
        validarPositivo(mortalidad.getCantidad(), "La mortalidad debe ser mayor a cero.");
        Lote lote = loteRepo.findById(mortalidad.getLote().getIdLote()).orElseThrow(() -> new RuntimeException("Lote no encontrado."));
        if (mortalidad.getCantidad() > lote.getCantidadActual()) throw new RuntimeException("La mortalidad no puede ser mayor que la cantidad actual.");
        mortalidad.setLote(lote);
        mortalidad.setFecha(mortalidad.getFecha() == null ? LocalDate.now() : mortalidad.getFecha());
        mortalidadRepo.save(mortalidad);
        lote.setCantidadActual(lote.getCantidadActual() - mortalidad.getCantidad());
        if (lote.getCantidadActual() == 0) lote.setEstado(EstadoLote.CERRADO);
        loteRepo.save(lote);
    }

    public BigDecimal consumoPromedioPorPollo(Long idLote) {
        Lote lote = loteRepo.findById(idLote).orElse(null);
        if (lote == null || lote.getCantidadActual() == null || lote.getCantidadActual() <= 0) return BigDecimal.ZERO;
        return alimentacionRepo.consumoPorLote(idLote).divide(BigDecimal.valueOf(lote.getCantidadActual()), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal tasaMortalidad(Long idLote) {
        Lote lote = loteRepo.findById(idLote).orElse(null);
        if (lote == null || lote.getCantidadInicial() == null || lote.getCantidadInicial() <= 0) return BigDecimal.ZERO;
        return BigDecimal.valueOf(mortalidadRepo.muertesPorLote(idLote))
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(lote.getCantidadInicial()), 2, RoundingMode.HALF_UP);
    }

    private void validarPositivo(Integer valor, String mensaje) {
        if (valor == null || valor <= 0) throw new RuntimeException(mensaje);
    }
    private void validarDinero(BigDecimal valor, String mensaje) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException(mensaje);
    }
}
