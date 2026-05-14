package com.umg.polleria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
public class Venta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_venta")
    private Long idVenta;

    @ManyToOne
    @JoinColumn(name="id_cliente", nullable=false)
    private Cliente cliente;

    @Column(nullable=false)
    private LocalDateTime fecha;

    @Column(nullable=false, precision=10, scale=2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_pago", nullable=false, length=20)
    private TipoPago tipoPago;

    public Long getIdVenta() { return idVenta; }
    public void setIdVenta(Long idVenta) { this.idVenta = idVenta; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public TipoPago getTipoPago() { return tipoPago; }
    public void setTipoPago(TipoPago tipoPago) { this.tipoPago = tipoPago; }
}
