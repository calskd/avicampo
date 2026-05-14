package com.umg.polleria.controller;

import com.umg.polleria.model.CompraLote;
import com.umg.polleria.repository.*;
import com.umg.polleria.service.PolleriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/compras")
public class CompraController {
    private final CompraLoteRepository compraRepo;
    private final LoteRepository loteRepo;
    private final PolleriaService service;

    public CompraController(CompraLoteRepository compraRepo, LoteRepository loteRepo, PolleriaService service) {
        this.compraRepo = compraRepo; this.loteRepo = loteRepo; this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("compra", new CompraLote());
        model.addAttribute("compras", compraRepo.findAll());
        model.addAttribute("lotes", loteRepo.findAll());
        return "compras";
    }

    @PostMapping("/guardar")
    public String guardar(CompraLote compra) {
        try { service.registrarCompra(compra); return "redirect:/compras?ok=1"; }
        catch(RuntimeException e) { return "redirect:/compras?error=" + e.getMessage().replace(" ","_"); }
    }
}
