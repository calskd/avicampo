package com.umg.polleria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_detalle")
    private Long idDetalle;

    @ManyToOne
    @JoinColumn(name="id_venta", nullable=false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name="id_lote", nullable=false)
    private Lote lote;

    @Column(nullable=false)
    private Integer cantidad;

    @Column(name="precio_unitario", nullable=false, precision=10, scale=2)
    private BigDecimal precioUnitario;

    @Column(nullable=false, precision=10, scale=2)
    private BigDecimal subtotal;

    public Long getIdDetalle() { return idDetalle; }
    public void setIdDetalle(Long idDetalle) { this.idDetalle = idDetalle; }
    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }
    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
