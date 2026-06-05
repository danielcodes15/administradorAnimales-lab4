package com.lab.demo.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lab.demo.servicio.CategoriaAnimalServicio;
import com.lab.demo.servicio.RazaServicio;

@Controller
public class InicioControlador {

    private final RazaServicio razaServicio;
    private final CategoriaAnimalServicio categoriaServicio;

    public InicioControlador(RazaServicio razaServicio,
            CategoriaAnimalServicio categoriaServicio) {
        this.razaServicio = razaServicio;
        this.categoriaServicio = categoriaServicio;
    }

    @GetMapping("/")
    public String inicio(@RequestParam(required = false) Long categoria, Model model) {
        model.addAttribute("razas", razaServicio.listar(categoria));
        model.addAttribute("categorias", categoriaServicio.listar());
        model.addAttribute("categoriaSeleccionada", categoria);
        return "inicio";
    }
}
