package com.umg.polleria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cliente")
    private Long idCliente;

    @Column(nullable=false, length=150)
    private String nombre;

    @Column(nullable=false, length=25)
    private String telefono;

    @Column(name="saldo_pendiente", nullable=false, precision=10, scale=2)
    private BigDecimal saldoPendiente = BigDecimal.ZERO;

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public BigDecimal getSaldoPendiente() { return saldoPendiente; }
    public void setSaldoPendiente(BigDecimal saldoPendiente) { this.saldoPendiente = saldoPendiente; }
}
