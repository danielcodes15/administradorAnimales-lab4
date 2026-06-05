package com.lab.demo.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab.demo.modelo.CategoriaAnimal;
import com.lab.demo.repositorio.CategoriaAnimalRepositorio;
import com.lab.demo.repositorio.RazaRepositorio;

@Service
@Transactional
public class CategoriaAnimalServicio {

    private final CategoriaAnimalRepositorio categoriaRepositorio;
    private final RazaRepositorio razaRepositorio;

    public CategoriaAnimalServicio(CategoriaAnimalRepositorio categoriaRepositorio,
            RazaRepositorio razaRepositorio) {
        this.categoriaRepositorio = categoriaRepositorio;
        this.razaRepositorio = razaRepositorio;
    }

    @Transactional(readOnly = true)
    public List<CategoriaAnimal> listar() {
        return categoriaRepositorio.findAllByOrderByNombreAsc();
    }

    @Transactional(readOnly = true)
    public CategoriaAnimal buscarPorId(Long id) {
        return categoriaRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria no encontrada"));
    }

    public CategoriaAnimal guardar(CategoriaAnimal categoria) {
        categoria.setNombre(categoria.getNombre().trim());
        categoria.setDescripcion(categoria.getDescripcion().trim());
        return categoriaRepositorio.save(categoria);
    }

    @Transactional(readOnly = true)
    public boolean nombreDuplicado(String nombre, Long idActual) {
        return categoriaRepositorio.findByNombreIgnoreCase(nombre.trim())
                .filter(categoria -> !categoria.getId().equals(idActual))
                .isPresent();
    }

    public void eliminar(Long id) {
        if (razaRepositorio.existsByCategoriaId(id)) {
            throw new IllegalStateException(
                    "No se puede eliminar la categoria porque tiene razas asociadas");
        }
        categoriaRepositorio.delete(buscarPorId(id));
    }
}
