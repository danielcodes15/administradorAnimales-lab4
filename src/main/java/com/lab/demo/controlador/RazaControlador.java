package com.lab.demo.controlador;

import jakarta.validation.Valid;

import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lab.demo.modelo.Raza;
import com.lab.demo.servicio.CategoriaAnimalServicio;
import com.lab.demo.servicio.RazaServicio;

@Controller
public class RazaControlador {

    private final RazaServicio razaServicio;
    private final CategoriaAnimalServicio categoriaServicio;

    public RazaControlador(RazaServicio razaServicio,
            CategoriaAnimalServicio categoriaServicio) {
        this.razaServicio = razaServicio;
        this.categoriaServicio = categoriaServicio;
    }

    @GetMapping("/razas")
    public String listar(@RequestParam(required = false) Long categoria, Model model) {
        model.addAttribute("razas", razaServicio.listar(categoria));
        model.addAttribute("categorias", categoriaServicio.listar());
        model.addAttribute("categoriaSeleccionada", categoria);
        return "razas/lista";
    }

    @GetMapping("/razas/nueva")
    public String nueva(Model model) {
        prepararFormulario(model, new Raza(), "Nueva raza");
        return "razas/formulario";
    }

    @GetMapping("/razas/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        prepararFormulario(model, razaServicio.buscarPorId(id), "Editar raza");
        return "razas/formulario";
    }

    @PostMapping("/razas/guardar")
    public String guardar(@Valid @ModelAttribute Raza raza, BindingResult resultado,
            @RequestParam(required = false) MultipartFile archivoImagen,
            Model model, RedirectAttributes redirectAttributes) {
        if (resultado.hasErrors()) {
            prepararFormulario(model, raza, raza.getId() == null ? "Nueva raza" : "Editar raza");
            return "razas/formulario";
        }

        try {
            razaServicio.guardar(raza, archivoImagen);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            model.addAttribute("errorImagen", ex.getMessage());
            prepararFormulario(model, raza, raza.getId() == null ? "Nueva raza" : "Editar raza");
            return "razas/formulario";
        }
        redirectAttributes.addFlashAttribute("exito", "Raza guardada correctamente");
        return "redirect:/razas";
    }

    @GetMapping("/razas/{id}/imagen")
    public ResponseEntity<byte[]> mostrarImagen(@PathVariable Long id) {
        Raza raza = razaServicio.buscarPorId(id);
        if (!raza.tieneImagen()) {
            return ResponseEntity.notFound().build();
        }

        MediaType tipo;
        try {
            tipo = MediaType.parseMediaType(raza.getImagenTipo());
        } catch (IllegalArgumentException ex) {
            tipo = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache())
                .contentType(tipo)
                .body(raza.getImagen());
    }

    @PostMapping("/razas/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            razaServicio.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "Raza eliminada");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/razas";
    }

    private void prepararFormulario(Model model, Raza raza, String titulo) {
        model.addAttribute("raza", raza);
        model.addAttribute("categorias", categoriaServicio.listar());
        model.addAttribute("titulo", titulo);
    }
}
