/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.controller;

/**
 *
 * @author Jose Sequeira
 */
import com.caso2.domain.Teatro;
import com.caso2.service.TeatroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/teatro")
public class TeatroController {
  
    @Autowired
    private TeatroService teatroService;
    
    @GetMapping("/listado")
    private String listado(Model model) {
        var teatros = teatroService.getTeatros(false);
        model.addAttribute("teatros", teatros);
        model.addAttribute("totalTeatros",teatros.size());
        return "/teatro/listado";
    }
    
     @GetMapping("/nuevo")
    public String categoriaNuevo(Teatro teatro) {
        return "/teatro/modifica";
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
        return "/teatro/modifica";
    }   
}
 