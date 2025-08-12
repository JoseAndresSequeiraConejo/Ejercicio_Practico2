/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.controller;

/**
 *
 * @author Jose Sequeira
 */


import com.caso2.domain.Carro;
import com.caso2.service.CarroService;
import com.caso2.domain.Categoria;
import com.caso2.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pruebas")
public class PruebasController {

    @Autowired
    private CarroService carroService;
    
    @Autowired
    private CategoriaService categoriaService; // Elimina si no usas categorias

    @GetMapping("/listado")
    public String listado(Model model) {
        var carros = carroService.getCarros(false);
        var categorias = categoriaService.getCategorias(false); // Elimina si no hay categorias
        model.addAttribute("carros", carros);
        model.addAttribute("totalCarros", carros.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }

    @GetMapping("/listado/{idCategoria}")
    public String listado(Model model, Categoria categoria) {
        var carros = categoriaService.getCategoria(categoria).getCarros();
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("carros", carros);
        model.addAttribute("totalCarros", carros.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }
    
    @PostMapping("/query1")
    public String consultaQuery1(@RequestParam(value = "precioInf") double precioInf,
            @RequestParam(value = "precioSup") double precioSup, Model model) {
        var carros = carroService.findByPrecioBetweenOrderByDescripcion(precioInf, precioSup);
        model.addAttribute("carros", carros);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado";
    }
    
    
    @PostMapping("/query2")
    public String consultaQuery2(@RequestParam(value = "precioInf") double precioInf,
                                 @RequestParam(value = "precioSup") double precioSup,
                                 Model model) {
        var carros = carroService.metodoJPQL(precioInf, precioSup);
        model.addAttribute("carros", carros);
        model.addAttribute("totalCarros", carros.size());
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }
    
    

    @PostMapping("/query3")
    public String consultaQuery3(@RequestParam(value = "precioInf") double precioInf,
                                 @RequestParam(value = "precioSup") double precioSup,
                                 Model model) {
        var carros = carroService.metodoNativo(precioInf, precioSup);
        model.addAttribute("carros", carros);
        model.addAttribute("totalCarros", carros.size());
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }
}