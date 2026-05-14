package com.umg.polleria.controller;

import com.umg.polleria.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.*;

@Controller
public class DashboardController {
    private final LoteRepository loteRepo;
    private final VentaRepository ventaRepo;
    private final ClienteRepository clienteRepo;
    private final AlimentacionRepository alimentacionRepo;
    private final VacunacionLoteRepository vacunacionRepo;
    private final MortalidadRepository mortalidadRepo;
    private final CompraLoteRepository compraRepo;

    public DashboardController(LoteRepository loteRepo, VentaRepository ventaRepo, ClienteRepository clienteRepo,
                               AlimentacionRepository alimentacionRepo, VacunacionLoteRepository vacunacionRepo,
                               MortalidadRepository mortalidadRepo, CompraLoteRepository compraRepo) {
        this.loteRepo = loteRepo; this.ventaRepo = ventaRepo; this.clienteRepo = clienteRepo;
        this.alimentacionRepo = alimentacionRepo; this.vacunacionRepo = vacunacionRepo;
        this.mortalidadRepo = mortalidadRepo; this.compraRepo = compraRepo;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicio = hoy.atStartOfDay();
        LocalDateTime fin = hoy.plusDays(1).atStartOfDay().minusNanos(1);
        model.addAttribute("pollosInventario", loteRepo.pollosTotales());
        model.addAttribute("valorInventario", loteRepo.valorTotalInventario());
        model.addAttribute("ventasDia", ventaRepo.ventasDia(inicio, fin));
        model.addAttribute("totalVendidoDia", ventaRepo.totalVendidoDia(inicio, fin));
        model.addAttribute("clientesRegistrados", clienteRepo.count());
        model.addAttribute("saldoPendiente", clienteRepo.saldoTotalPendiente());
        model.addAttribute("consumoTotalKg", alimentacionRepo.consumoTotalKg());
        model.addAttribute("ultimaVacuna", vacunacionRepo.findTop1ByOrderByFechaAplicacionDescIdDesc().stream().findFirst().orElse(null));
        model.addAttribute("totalMortalidad", mortalidadRepo.totalMuertes());
        model.addAttribute("lotes", loteRepo.findAll());
        model.addAttribute("ultimasCompras", compraRepo.findTop8ByOrderByFechaDescIdCompraDesc());
        return "dashboard";
    }
}
