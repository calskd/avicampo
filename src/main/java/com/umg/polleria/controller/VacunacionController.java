package com.umg.polleria.controller;

import com.umg.polleria.model.*;
import com.umg.polleria.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/vacunacion")
public class VacunacionController {
    private final VacunaRepository vacunaRepo;
    private final VacunacionLoteRepository vacunacionRepo;
    private final LoteRepository loteRepo;

    public VacunacionController(VacunaRepository vacunaRepo, VacunacionLoteRepository vacunacionRepo, LoteRepository loteRepo) {
        this.vacunaRepo = vacunaRepo; this.vacunacionRepo = vacunacionRepo; this.loteRepo = loteRepo;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("vacuna", new Vacuna());
        model.addAttribute("vacunacion", new VacunacionLote());
        model.addAttribute("vacunas", vacunaRepo.findAll());
        model.addAttribute("lotes", loteRepo.findAll());
        model.addAttribute("historial", vacunacionRepo.findAll());
        return "vacunacion";
    }

    @PostMapping("/vacuna")
    public String guardarVacuna(Vacuna vacuna) {
        vacunaRepo.save(vacuna);
        return "redirect:/vacunacion?ok=vacuna";
    }

    @PostMapping("/aplicar")
    public String aplicar(VacunacionLote vacunacion) {
        if (vacunacion.getFechaAplicacion() == null) vacunacion.setFechaAplicacion(LocalDate.now());
        vacunacionRepo.save(vacunacion);
        return "redirect:/vacunacion?ok=aplicada";
    }
}
