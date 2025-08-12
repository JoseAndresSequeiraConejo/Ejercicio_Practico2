package com.caso2.controller;

import com.caso2.domain.Carro;
import com.caso2.service.CategoriaService;
import com.caso2.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/carro")
public class CarroController {
    
    @Autowired
    private CarroService carroService;

    @Autowired
    private CategoriaService categoriaService;

    
    @GetMapping("/listado")
    public String listado(Model model) {
        var carros = carroService.getCarros(false); // o getCarros()
        model.addAttribute("carros", carros);
        model.addAttribute("totalCarros", carros.size());

        // 🔴 Necesarios para el modal / fragmento
        model.addAttribute("carro", new Carro());
        model.addAttribute("categorias", categoriaService.getCategorias(true));

        return "carro/listado"; // SIN "/"
    }


    @GetMapping("/nuevo")
    public String carroNuevo(Carro carro, Model model) {
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        return "/carro/modifica";
    }

    @PostMapping("/guardar")
    public String carroGuardar(Carro carro, Model model) {
        if (carro.getCategoria() == null || carro.getCategoria().getIdCategoria() == null) {
            model.addAttribute("carro", carro);
            model.addAttribute("categorias", categoriaService.getCategorias(true));
            model.addAttribute("error", "Debe seleccionar una categoría.");
            return "carro/modifica";
        }

        // Opción rápida: setear solo el id (sin ir a BD)
        var cat = new com.caso2.domain.Categoria();
        cat.setIdCategoria(carro.getCategoria().getIdCategoria());
        carro.setCategoria(cat);

        carroService.save(carro);
        return "redirect:/carro/listado";
    }


    @GetMapping("/eliminar/{idCarro}")
    public String carroEliminar(Carro carro) {
        carroService.delete(carro);
        return "redirect:/carro/listado";
    }

    @GetMapping("/modificar/{idCarro}")
    public String carroModificar(Carro carro, Model model) {
        carro = carroService.getCarro(carro);
        model.addAttribute("carro", carro);

        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);

        return "/carro/modifica";
    }
}
