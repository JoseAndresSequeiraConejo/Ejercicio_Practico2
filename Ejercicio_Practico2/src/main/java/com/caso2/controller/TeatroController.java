package com.caso2.controller;

import com.caso2.domain.Teatro;
import com.caso2.service.TeatroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teatro")
public class TeatroController {

    @Autowired
    private TeatroService teatroService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var teatros = teatroService.getTeatros(false);
        model.addAttribute("teatros", teatros);
        model.addAttribute("totalTeatros", teatros.size());
        // Necesario para el modal agregar (th:object="${teatro}")
        model.addAttribute("teatro", new Teatro());
        return "teatro/listado"; // sin slash inicial
    }

    @GetMapping("/nuevo")
    public String teatroNuevo(Model model) {
        model.addAttribute("teatro", new Teatro());
        return "teatro/modifica";
    }

    @PostMapping("/guardar")
    public String teatroGuardar(Teatro teatro) {
        teatroService.save(teatro);
        return "redirect:/teatro/listado";
    }

    @GetMapping("/eliminar/{idTeatro}")
    public String teatroEliminar(Teatro teatro) {
        teatroService.delete(teatro);
        return "redirect:/teatro/listado";
    }

    @GetMapping("/modificar/{idTeatro}")
    public String teatroModificar(Teatro teatro, Model model) {
        teatro = teatroService.getTeatro(teatro);
        model.addAttribute("teatro", teatro);
        return "teatro/modifica";
    }
}

 