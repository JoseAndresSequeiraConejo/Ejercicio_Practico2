package com.caso2.controller;

import com.caso2.domain.Funcion;
import com.caso2.domain.Teatro;
import com.caso2.service.FuncionService;
import com.caso2.service.TeatroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/funcion")
public class FuncionController {

    @Autowired
    private FuncionService funcionService;

    @Autowired
    private TeatroService teatroService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var funciones = funcionService.getFunciones(false);
        model.addAttribute("funciones", funciones);
        model.addAttribute("totalFunciones", funciones.size());

        // Para el modal/alta rápida
        model.addAttribute("funcion", new Funcion());
        model.addAttribute("teatros", teatroService.getTeatros(true));

        return "funcion/listado"; // estaba "carro/listado"
    }

    @GetMapping("/nuevo")
    public String funcionNuevo(Funcion funcion, Model model) {
        model.addAttribute("funcion", funcion);
        var teatros = teatroService.getTeatros(false); // antes getTeatro(false)
        model.addAttribute("teatros", teatros);
        return "funcion/modifica";
    }

    @PostMapping("/guardar")
    public String funcionGuardar(Funcion funcion, Model model) {
        // Validación simple: debe seleccionar un teatro
        if (funcion.getTeatro() == null || funcion.getTeatro().getIdTeatro() == null) {
            model.addAttribute("funcion", funcion);
            model.addAttribute("teatros", teatroService.getTeatros(true));
            model.addAttribute("error", "Debe seleccionar un teatro.");
            return "funcion/modifica";
        }

        // Seteo rápido solo con el ID (sin ir a BD)
        Teatro t = new Teatro();
        t.setIdTeatro(funcion.getTeatro().getIdTeatro());
        funcion.setTeatro(t);

        funcionService.save(funcion); // antes llamaba a carroService.save(carro)
        return "redirect:/funcion/listado";
    }

    @GetMapping("/eliminar/{idFuncion}")
    public String funcionEliminar(Funcion funcion) { // binding por idFuncion
        funcionService.delete(funcion);
        return "redirect:/funcion/listado";
    }

    @GetMapping("/modificar/{idFuncion}")
    public String funcionModificar(Funcion funcion, Model model) {
        funcion = funcionService.getFuncion(funcion);
        model.addAttribute("funcion", funcion);

        var teatros = teatroService.getTeatros(false); // variable correcta
        model.addAttribute("teatros", teatros);

        return "funcion/modifica";
    }
}
