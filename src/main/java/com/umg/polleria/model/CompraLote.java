package com.umg.polleria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "compras_lote")
public class CompraLote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_compra")
    private Long idCompra;

    @ManyToOne
    @JoinColumn(name="id_lote", nullable=false)
    private Lote lote;

    @Column(nullable=false)
    private LocalDate fecha;

    @Column(nullable=false)
    private Integer cantidad;

    @Column(name="costo_unitario", nullable=false, precision=10, scale=2)
    private BigDecimal costoUnitario;

    @Column(nullable=false, precision=10, scale=2)
    private BigDecimal total;

    @Column(length=150)
    private String proveedor;

    public Long getIdCompra() { return idCompra; }
    public void setIdCompra(Long idCompra) { this.idCompra = idCompra; }
    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(BigDecimal costoUnitario) { this.costoUnitario = costoUnitario; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
}
