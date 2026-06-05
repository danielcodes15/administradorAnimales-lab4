package com.lab.demo.controlador;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lab.demo.modelo.CategoriaAnimal;
import com.lab.demo.servicio.CategoriaAnimalServicio;

@Controller
public class CategoriaAnimalControlador {

    private final CategoriaAnimalServicio categoriaServicio;

    public CategoriaAnimalControlador(CategoriaAnimalServicio categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    @GetMapping("/categorias")
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaServicio.listar());
        return "categorias/lista";
    }

    @GetMapping("/categorias/nueva")
    public String nueva(Model model) {
        model.addAttribute("categoriaAnimal", new CategoriaAnimal());
        model.addAttribute("titulo", "Nueva categoria de animales");
        return "categorias/formulario";
    }

    @GetMapping("/categorias/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("categoriaAnimal", categoriaServicio.buscarPorId(id));
        model.addAttribute("titulo", "Editar categoria de animales");
        return "categorias/formulario";
    }

    @PostMapping("/categorias/guardar")
    public String guardar(@Valid @ModelAttribute CategoriaAnimal categoriaAnimal,
            BindingResult resultado, Model model, RedirectAttributes redirectAttributes) {
        if (!resultado.hasFieldErrors("nombre")
                && categoriaServicio.nombreDuplicado(
                        categoriaAnimal.getNombre(), categoriaAnimal.getId())) {
            resultado.rejectValue("nombre", "duplicado", "Ya existe una categoria con este nombre");
        }

        if (resultado.hasErrors()) {
            model.addAttribute("titulo", categoriaAnimal.getId() == null
                    ? "Nueva categoria de animales"
                    : "Editar categoria de animales");
            return "categorias/formulario";
        }

        categoriaServicio.guardar(categoriaAnimal);
        redirectAttributes.addFlashAttribute("exito", "Categoria guardada correctamente");
        return "redirect:/categorias";
    }

    @PostMapping("/categorias/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoriaServicio.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "Categoria eliminada");
        } catch (IllegalStateException | IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/categorias";
    }
}
