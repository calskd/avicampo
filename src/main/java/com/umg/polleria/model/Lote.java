package com.umg.polleria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lotes")
public class Lote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_lote")
    private Long idLote;

    @Column(name="fecha_ingreso", nullable=false)
    private LocalDate fechaIngreso;

    @Column(name="cantidad_inicial", nullable=false)
    private Integer cantidadInicial;

    @Column(name="cantidad_actual", nullable=false)
    private Integer cantidadActual;

    @Column(name="costo_unitario", nullable=false, precision=10, scale=2)
    private BigDecimal costoUnitario;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private EstadoLote estado = EstadoLote.ACTIVO;

    public Long getIdLote() { return idLote; }
    public void setIdLote(Long idLote) { this.idLote = idLote; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public Integer getCantidadInicial() { return cantidadInicial; }
    public void setCantidadInicial(Integer cantidadInicial) { this.cantidadInicial = cantidadInicial; }
    public Integer getCantidadActual() { return cantidadActual; }
    public void setCantidadActual(Integer cantidadActual) { this.cantidadActual = cantidadActual; }
    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(BigDecimal costoUnitario) { this.costoUnitario = costoUnitario; }
    public EstadoLote getEstado() { return estado; }
    public void setEstado(EstadoLote estado) { this.estado = estado; }
}
