package com.lab.demo.configuracion;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lab.demo.modelo.CategoriaAnimal;
import com.lab.demo.modelo.Raza;
import com.lab.demo.repositorio.CategoriaAnimalRepositorio;
import com.lab.demo.repositorio.RazaRepositorio;

@Configuration
@SuppressWarnings("unused")
public class DatosIniciales {

    @Bean
    CommandLineRunner cargarDatos(CategoriaAnimalRepositorio categoriaRepositorio,
            RazaRepositorio razaRepositorio) {
        return args -> {
            CategoriaAnimal domestico = obtenerOCrear(categoriaRepositorio,
                    "Domestico", "Razas adaptadas a convivir con personas en el hogar.");
            CategoriaAnimal carnivoro = obtenerOCrear(categoriaRepositorio,
                    "Carnivoro", "Razas con naturaleza cazadora y alimentacion basada en proteina animal.");
            CategoriaAnimal herbivoro = obtenerOCrear(categoriaRepositorio,
                    "Herbivoro", "Animales que se alimentan principalmente de plantas, hojas, frutos o pasto.");
            CategoriaAnimal omnivoro = obtenerOCrear(categoriaRepositorio,
                    "Omnivoro", "Animales con alimentacion variada de origen vegetal y animal.");
            CategoriaAnimal compania = obtenerOCrear(categoriaRepositorio,
                    "De compania", "Razas buscadas por su cercania, sociabilidad y temperamento familiar.");
            CategoriaAnimal guardian = obtenerOCrear(categoriaRepositorio,
                    "Guardian", "Razas con instinto protector y buena respuesta al entrenamiento.");

            migrarCategoriasAntiguas(categoriaRepositorio, razaRepositorio, domestico, compania);

            if (razaRepositorio.count() > 0) {
                razaRepositorio.findAll().forEach(raza -> {
                    if (raza.getEspecie() == null || raza.getEspecie().isBlank()) {
                        raza.setEspecie("Animal");
                        razaRepositorio.save(raza);
                    }
                });
                cargarEjemplosGenerales(razaRepositorio, herbivoro, omnivoro, compania, carnivoro);
                return;
            }

            razaRepositorio.save(new Raza("Labrador Retriever", "Perro",
                    "Perro amistoso, inteligente y con mucha energía.",
                    "Canada", 12, compania));
            razaRepositorio.save(new Raza("Pastor Aleman", "Perro",
                    "Raza protectora, obediente y versátil.",
                    "Alemania", 11, guardian));
            razaRepositorio.save(new Raza("Golden Retriever", "Perro",
                    "Perro dócil, afectuoso y excelente para familias.",
                    "Escocia", 12, compania));
            razaRepositorio.save(new Raza("Bulldog Frances", "Perro",
                    "Perro pequeno, alegre y muy adaptable a espacios familiares.",
                    "Francia", 11, domestico));
            razaRepositorio.save(new Raza("Husky Siberiano", "Perro",
                    "Raza atletica, resistente y de fuerte instinto de manada.",
                    "Rusia", 13, carnivoro));
            razaRepositorio.save(new Raza("Beagle", "Perro",
                    "Perro curioso, activo y con excelente olfato.",
                    "Reino Unido", 13, domestico));
            razaRepositorio.save(new Raza("Siames", "Gato",
                    "Gato comunicativo, elegante y muy cercano a sus cuidadores.",
                    "Tailandia", 15, compania));
            razaRepositorio.save(new Raza("Maine Coon", "Gato",
                    "Gato grande, tranquilo y de pelaje abundante.",
                    "Estados Unidos", 13, domestico));
            razaRepositorio.save(new Raza("Persa", "Gato",
                    "Gato sereno de rostro corto y pelaje largo.",
                    "Iran", 14, compania));
            razaRepositorio.save(new Raza("Bengali", "Gato",
                    "Gato activo, curioso y con apariencia similar a felinos salvajes.",
                    "Estados Unidos", 14, carnivoro));
            razaRepositorio.save(new Raza("Sphynx", "Gato",
                    "Gato sociable, afectuoso y reconocido por su falta de pelaje.",
                    "Canada", 14, domestico));
            razaRepositorio.save(new Raza("Ragdoll", "Gato",
                    "Gato calmado, grande y muy docil con las familias.",
                    "Estados Unidos", 15, compania));
            razaRepositorio.save(new Raza("Andaluz", "Caballo",
                    "Caballo fuerte, elegante y muy usado para monta y exhibicion.",
                    "Espana", 25, herbivoro));
            razaRepositorio.save(new Raza("Holandes Enano", "Conejo",
                    "Conejo pequeno, tranquilo y comun como animal de compania.",
                    "Paises Bajos", 9, herbivoro));
            razaRepositorio.save(new Raza("Canario", "Ave",
                    "Ave domestica reconocida por su canto y facil cuidado.",
                    "Islas Canarias", 10, compania));
            razaRepositorio.save(new Raza("Iguana Verde", "Reptil",
                    "Reptil herbivoro que requiere clima calido y cuidados especiales.",
                    "America Central", 15, herbivoro));
            razaRepositorio.save(new Raza("Leon Africano", "Leon",
                    "Felino salvaje, social y carnivoro de gran tamano.",
                    "Africa", 14, carnivoro));
            razaRepositorio.save(new Raza("Oso Pardo", "Oso",
                    "Mamifero grande de alimentacion variada y gran fuerza fisica.",
                    "Eurasia y America del Norte", 25, omnivoro));
        };
    }

    private void cargarEjemplosGenerales(RazaRepositorio razaRepositorio, CategoriaAnimal herbivoro,
            CategoriaAnimal omnivoro, CategoriaAnimal compania, CategoriaAnimal carnivoro) {
        guardarSiNoExiste(razaRepositorio, new Raza("Andaluz", "Caballo",
                "Caballo fuerte, elegante y muy usado para monta y exhibicion.",
                "Espana", 25, herbivoro));
        guardarSiNoExiste(razaRepositorio, new Raza("Canario", "Ave",
                "Ave domestica reconocida por su canto y facil cuidado.",
                "Islas Canarias", 10, compania));
        guardarSiNoExiste(razaRepositorio, new Raza("Iguana Verde", "Reptil",
                "Reptil herbivoro que requiere clima calido y cuidados especiales.",
                "America Central", 15, herbivoro));
        guardarSiNoExiste(razaRepositorio, new Raza("Leon Africano", "Leon",
                "Felino salvaje, social y carnivoro de gran tamano.",
                "Africa", 14, carnivoro));
        guardarSiNoExiste(razaRepositorio, new Raza("Oso Pardo", "Oso",
                "Mamifero grande de alimentacion variada y gran fuerza fisica.",
                "Eurasia y America del Norte", 25, omnivoro));
    }

    private void guardarSiNoExiste(RazaRepositorio razaRepositorio, Raza nuevaRaza) {
        boolean existe = razaRepositorio.findAll().stream()
                .anyMatch(raza -> raza.getNombre().equalsIgnoreCase(nuevaRaza.getNombre())
                        && raza.getEspecie() != null
                        && raza.getEspecie().equalsIgnoreCase(nuevaRaza.getEspecie()));
        if (!existe) {
            razaRepositorio.save(nuevaRaza);
        }
    }

    private CategoriaAnimal obtenerOCrear(CategoriaAnimalRepositorio categoriaRepositorio,
            String nombre, String descripcion) {
        return categoriaRepositorio.findByNombreIgnoreCase(nombre)
                .orElseGet(() -> categoriaRepositorio.save(new CategoriaAnimal(nombre, descripcion)));
    }

    private void migrarCategoriasAntiguas(CategoriaAnimalRepositorio categoriaRepositorio,
            RazaRepositorio razaRepositorio, CategoriaAnimal domestico, CategoriaAnimal compania) {
        categoriaRepositorio.findByNombreIgnoreCase("Perro").ifPresent(categoria -> {
            moverRazasDeCategoria(razaRepositorio, categoria, domestico, "Perro");
            eliminarSiQuedaVacia(categoriaRepositorio, razaRepositorio, categoria);
        });
        categoriaRepositorio.findByNombreIgnoreCase("Gato").ifPresent(categoria -> {
            moverRazasDeCategoria(razaRepositorio, categoria, compania, "Gato");
            eliminarSiQuedaVacia(categoriaRepositorio, razaRepositorio, categoria);
        });
    }

    private void moverRazasDeCategoria(RazaRepositorio razaRepositorio, CategoriaAnimal origen,
            CategoriaAnimal destino, String especiePorDefecto) {
        razaRepositorio.findByCategoriaIdOrderByNombreAsc(origen.getId()).forEach(raza -> {
            if (raza.getEspecie() == null || raza.getEspecie().isBlank()) {
                raza.setEspecie(especiePorDefecto);
            }
            raza.setCategoria(destino);
            razaRepositorio.save(raza);
        });
    }

    private void eliminarSiQuedaVacia(CategoriaAnimalRepositorio categoriaRepositorio,
            RazaRepositorio razaRepositorio, CategoriaAnimal categoria) {
        if (!razaRepositorio.existsByCategoriaId(categoria.getId())) {
            categoriaRepositorio.delete(categoria);
        }
    }
}
