package com.lab.demo.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "razas")
public class Raza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "El nombre no puede superar 80 caracteres")
    @Column(nullable = false, length = 80)
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    @Size(max = 50, message = "La especie no puede superar 50 caracteres")
    @Column(length = 50)
    private String especie;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 500, message = "La descripcion no puede superar 500 caracteres")
    @Column(nullable = false, length = 500)
    private String descripcion;

    @NotBlank(message = "El origen es obligatorio")
    @Size(max = 80, message = "El origen no puede superar 80 caracteres")
    @Column(nullable = false, length = 80)
    private String origen;

    @NotNull(message = "La esperanza de vida es obligatoria")
    @Min(value = 1, message = "La esperanza de vida debe ser mayor que cero")
    @Column(nullable = false)
    private Integer esperanzaVida;

    @NotNull(message = "Debe seleccionar una categoria")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaAnimal categoria;

    @Lob
    @Column(name = "imagen", columnDefinition = "LONGBLOB")
    private byte[] imagen;

    @Column(name = "imagen_tipo", length = 100)
    private String imagenTipo;

    @Column(name = "imagen_nombre", length = 255)
    private String imagenNombre;

    public Raza() {
    }

    public Raza(String nombre, String especie, String descripcion, String origen, Integer esperanzaVida,
            CategoriaAnimal categoria) {
        this.nombre = nombre;
        this.especie = especie;
        this.descripcion = descripcion;
        this.origen = origen;
        this.esperanzaVida = esperanzaVida;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Integer getEsperanzaVida() {
        return esperanzaVida;
    }

    public void setEsperanzaVida(Integer esperanzaVida) {
        this.esperanzaVida = esperanzaVida;
    }

    public CategoriaAnimal getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaAnimal categoria) {
        this.categoria = categoria;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenTipo() {
        return imagenTipo;
    }

    public void setImagenTipo(String imagenTipo) {
        this.imagenTipo = imagenTipo;
    }

    public String getImagenNombre() {
        return imagenNombre;
    }

    public void setImagenNombre(String imagenNombre) {
        this.imagenNombre = imagenNombre;
    }

    public boolean tieneImagen() {
        return imagen != null && imagen.length > 0;
    }
}
