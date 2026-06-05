package com.lab.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab.demo.modelo.Raza;

public interface RazaRepositorio extends JpaRepository<Raza, Long> {

    List<Raza> findAllByOrderByNombreAsc();

    List<Raza> findByCategoriaIdOrderByNombreAsc(Long categoriaId);

    boolean existsByCategoriaId(Long categoriaId);
}
