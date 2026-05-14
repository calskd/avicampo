package com.umg.polleria.controller;

import com.umg.polleria.model.Mortalidad;
import com.umg.polleria.repository.*;
import com.umg.polleria.service.PolleriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mortalidad")
public class MortalidadController {
    private final MortalidadRepository repo;
    private final LoteRepository loteRepo;
    private final PolleriaService service;

    public MortalidadController(MortalidadRepository repo, LoteRepository loteRepo, PolleriaService service) {
        this.repo = repo; this.loteRepo = loteRepo; this.service = service;
    }

    @GetMapping
    public String listar(@RequestParam(required=false) Long loteId, Model model) {
        model.addAttribute("mortalidad", new Mortalidad());
        model.addAttribute("lotes", loteRepo.findAll());
        model.addAttribute("lista", loteId == null ? repo.findAll() : repo.findByLoteIdLoteOrderByFechaDesc(loteId));
        model.addAttribute("tasa", loteId == null ? null : service.tasaMortalidad(loteId));
        return "mortalidad";
    }

    @PostMapping("/guardar")
    public String guardar(Mortalidad mortalidad) {
        try { service.registrarMortalidad(mortalidad); return "redirect:/mortalidad?ok=1"; }
        catch(RuntimeException e) { return "redirect:/mortalidad?error=" + e.getMessage().replace(" ","_"); }
    }
}
