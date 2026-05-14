package com.umg.polleria.controller;

import com.umg.polleria.model.Alimentacion;
import com.umg.polleria.repository.*;
import com.umg.polleria.service.PolleriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/alimentacion")
public class AlimentacionController {
    private final AlimentacionRepository repo;
    private final LoteRepository loteRepo;
    private final PolleriaService service;

    public AlimentacionController(AlimentacionRepository repo, LoteRepository loteRepo, PolleriaService service) {
        this.repo = repo; this.loteRepo = loteRepo; this.service = service;
    }

    @GetMapping
    public String listar(@RequestParam(required=false) Long loteId, Model model) {
        model.addAttribute("alimentacion", new Alimentacion());
        model.addAttribute("lotes", loteRepo.findAll());
        model.addAttribute("lista", loteId == null ? repo.findAll() : repo.findByLoteIdLoteOrderByFechaDesc(loteId));
        model.addAttribute("promedio", loteId == null ? BigDecimal.ZERO : service.consumoPromedioPorPollo(loteId));
        return "alimentacion";
    }

    @PostMapping("/guardar")
    public String guardar(Alimentacion a) {
        if (a.getFecha() == null) a.setFecha(LocalDate.now());
        if (a.getCantidadKg() == null || a.getCantidadKg().compareTo(BigDecimal.ZERO) <= 0) return "redirect:/alimentacion?error=cantidad";
        repo.save(a);
        return "redirect:/alimentacion?ok=1";
    }
}
