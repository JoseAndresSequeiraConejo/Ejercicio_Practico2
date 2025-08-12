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
        var carros = carroService.getCarros(false); // false = no filtra por activos
        model.addAttribute("carros", carros);
        model.addAttribute("totalCarros", carros.size());

        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);

        return "/carro/listado";
    }

    @GetMapping("/nuevo")
    public String carroNuevo(Carro carro, Model model) {
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        return "/carro/modifica";
    }

    @PostMapping("/guardar")
    public String carroGuardar(Carro carro) {
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
