package com.umg.polleria.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vacunacion_lote")
public class VacunacionLote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_lote", nullable=false)
    private Lote lote;

    @ManyToOne
    @JoinColumn(name="id_vacuna", nullable=false)
    private Vacuna vacuna;

    @Column(name="fecha_aplicacion", nullable=false)
    private LocalDate fechaAplicacion;

    @Column(length=250)
    private String observaciones;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }
    public Vacuna getVacuna() { return vacuna; }
    public void setVacuna(Vacuna vacuna) { this.vacuna = vacuna; }
    public LocalDate getFechaAplicacion() { return fechaAplicacion; }
    public void setFechaAplicacion(LocalDate fechaAplicacion) { this.fechaAplicacion = fechaAplicacion; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
