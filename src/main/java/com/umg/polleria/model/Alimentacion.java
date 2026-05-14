package com.umg.polleria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "alimentacion")
public class Alimentacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_alimentacion")
    private Long idAlimentacion;

    @ManyToOne
    @JoinColumn(name="id_lote", nullable=false)
    private Lote lote;

    @Column(nullable=false)
    private LocalDate fecha;

    @Column(name="cantidad_kg", nullable=false, precision=10, scale=2)
    private BigDecimal cantidadKg;

    @Column(precision=10, scale=2)
    private BigDecimal costo;

    public Long getIdAlimentacion() { return idAlimentacion; }
    public void setIdAlimentacion(Long idAlimentacion) { this.idAlimentacion = idAlimentacion; }
    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public BigDecimal getCantidadKg() { return cantidadKg; }
    public void setCantidadKg(BigDecimal cantidadKg) { this.cantidadKg = cantidadKg; }
    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
}
