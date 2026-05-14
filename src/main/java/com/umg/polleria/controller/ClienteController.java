package com.umg.polleria.controller;

import com.umg.polleria.model.Cliente;
import com.umg.polleria.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteRepository clienteRepo;
    private final VentaRepository ventaRepo;

    public ClienteController(ClienteRepository clienteRepo, VentaRepository ventaRepo) {
        this.clienteRepo = clienteRepo; this.ventaRepo = ventaRepo;
    }

    @GetMapping
    public String listar(@RequestParam(defaultValue="") String buscar, Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("buscar", buscar);
        model.addAttribute("clientes", buscar.isBlank() ? clienteRepo.findAll() : clienteRepo.findByNombreContainingIgnoreCaseOrTelefonoContainingIgnoreCase(buscar, buscar));
        return "clientes";
    }

    @PostMapping("/guardar")
    public String guardar(Cliente cliente) {
        if (cliente.getSaldoPendiente() == null) cliente.setSaldoPendiente(BigDecimal.ZERO);
        if (cliente.getSaldoPendiente().compareTo(BigDecimal.ZERO) < 0) return "redirect:/clientes?error=saldo_negativo";
        clienteRepo.save(cliente);
        return "redirect:/clientes?ok=1";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", clienteRepo.findById(id).orElse(new Cliente()));
        model.addAttribute("clientes", clienteRepo.findAll());
        model.addAttribute("buscar", "");
        return "clientes";
    }

    @GetMapping("/historial/{id}")
    public String historial(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", clienteRepo.findById(id).orElseThrow());
        model.addAttribute("ventas", ventaRepo.findByClienteIdClienteOrderByFechaDesc(id));
        return "historial_cliente";
    }
}
