package com.umg.polleria.controller;

import com.umg.polleria.model.*;
import com.umg.polleria.repository.*;
import com.umg.polleria.service.PolleriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@Controller
@RequestMapping("/ventas")
public class VentaController {
    private final VentaRepository ventaRepo;
    private final ClienteRepository clienteRepo;
    private final LoteRepository loteRepo;
    private final PolleriaService service;

    public VentaController(VentaRepository ventaRepo, ClienteRepository clienteRepo, LoteRepository loteRepo, PolleriaService service) {
        this.ventaRepo = ventaRepo; this.clienteRepo = clienteRepo; this.loteRepo = loteRepo; this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaRepo.findAll());
        model.addAttribute("clientes", clienteRepo.findAll());
        model.addAttribute("lotes", loteRepo.findByEstadoOrderByFechaIngresoAsc(EstadoLote.ACTIVO));
        model.addAttribute("tiposPago", TipoPago.values());
        return "ventas";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam Long idCliente, @RequestParam Long idLote, @RequestParam Integer cantidad, @RequestParam BigDecimal precioUnitario, @RequestParam TipoPago tipoPago) {
        try { service.registrarVenta(idCliente, idLote, cantidad, precioUnitario, tipoPago); return "redirect:/ventas?ok=1"; }
        catch(RuntimeException e) { return "redirect:/ventas?error=" + e.getMessage().replace(" ","_"); }
    }
}
