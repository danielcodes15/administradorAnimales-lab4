package com.lab.demo.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lab.demo.modelo.Raza;
import com.lab.demo.repositorio.RazaRepositorio;

@Service
@Transactional
public class RazaServicio {

    private final RazaRepositorio razaRepositorio;

    public RazaServicio(RazaRepositorio razaRepositorio) {
        this.razaRepositorio = razaRepositorio;
    }

    @Transactional(readOnly = true)
    public List<Raza> listar(Long categoriaId) {
        if (categoriaId == null) {
            return razaRepositorio.findAllByOrderByNombreAsc();
        }
        return razaRepositorio.findByCategoriaIdOrderByNombreAsc(categoriaId);
    }

    @Transactional(readOnly = true)
    public Raza buscarPorId(Long id) {
        return razaRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Raza no encontrada"));
    }

    public Raza guardar(Raza raza, MultipartFile archivoImagen) {
        if (raza.getId() != null) {
            Raza existente = buscarPorId(raza.getId());
            if (archivoImagen == null || archivoImagen.isEmpty()) {
                raza.setImagen(existente.getImagen());
                raza.setImagenTipo(existente.getImagenTipo());
                raza.setImagenNombre(existente.getImagenNombre());
            }
        }

        if (archivoImagen != null && !archivoImagen.isEmpty()) {
            guardarImagen(raza, archivoImagen);
        }

        raza.setNombre(raza.getNombre().trim());
        raza.setEspecie(raza.getEspecie().trim());
        raza.setDescripcion(raza.getDescripcion().trim());
        raza.setOrigen(raza.getOrigen().trim());
        return razaRepositorio.save(raza);
    }

    public void eliminar(Long id) {
        razaRepositorio.delete(buscarPorId(id));
    }

    private void guardarImagen(Raza raza, MultipartFile archivoImagen) {
        String tipo = archivoImagen.getContentType();
        if (tipo == null || !tipo.startsWith("image/")) {
            throw new IllegalArgumentException("El archivo seleccionado debe ser una imagen");
        }
        if (archivoImagen.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("La imagen no puede superar 5 MB");
        }

        try {
            raza.setImagen(archivoImagen.getBytes());
            raza.setImagenTipo(tipo);
            raza.setImagenNombre(archivoImagen.getOriginalFilename());
        } catch (java.io.IOException ex) {
            throw new IllegalStateException("No se pudo guardar la imagen", ex);
        }
    }
}
