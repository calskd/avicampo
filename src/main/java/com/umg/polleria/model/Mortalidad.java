package com.umg.polleria.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "mortalidad")
public class Mortalidad {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_mortalidad")
    private Long idMortalidad;

    @ManyToOne
    @JoinColumn(name="id_lote", nullable=false)
    private Lote lote;

    @Column(nullable=false)
    private LocalDate fecha;

    @Column(nullable=false)
    private Integer cantidad;

    @Column(length=250)
    private String motivo;

    public Long getIdMortalidad() { return idMortalidad; }
    public void setIdMortalidad(Long idMortalidad) { this.idMortalidad = idMortalidad; }
    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
