package com.umg.polleria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vacunas")
public class Vacuna {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_vacuna")
    private Long idVacuna;

    @Column(nullable=false, length=100)
    private String nombre;

    @Column(length=250)
    private String descripcion;

    public Long getIdVacuna() { return idVacuna; }
    public void setIdVacuna(Long idVacuna) { this.idVacuna = idVacuna; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
