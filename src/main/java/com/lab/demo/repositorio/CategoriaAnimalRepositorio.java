package com.lab.demo.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab.demo.modelo.CategoriaAnimal;

public interface CategoriaAnimalRepositorio extends JpaRepository<CategoriaAnimal, Long> {

    List<CategoriaAnimal> findAllByOrderByNombreAsc();

    Optional<CategoriaAnimal> findByNombreIgnoreCase(String nombre);
}
