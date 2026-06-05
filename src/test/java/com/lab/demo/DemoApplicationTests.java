package com.lab.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.lab.demo.modelo.CategoriaAnimal;
import com.lab.demo.modelo.Raza;
import com.lab.demo.servicio.CategoriaAnimalServicio;
import com.lab.demo.servicio.RazaServicio;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CategoriaAnimalServicio categoriaServicio;

	@Autowired
	private RazaServicio razaServicio;

	@Test
	void contextLoads() {
	}

	@Test
	void muestraPaginaDeInicioConRazas() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("inicio"))
				.andExpect(model().attributeExists("razas", "categorias"))
				.andExpect(content().string(
						org.hamcrest.Matchers.containsString("Labrador Retriever")))
				.andExpect(content().string(
						org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("Administrar razas"))));
	}

	@Test
	void usuarioPublicoPuedeVerRazasPeroNoAdministrar() throws Exception {
		mockMvc.perform(get("/razas"))
				.andExpect(status().isOk())
				.andExpect(view().name("razas/lista"))
				.andExpect(model().attributeExists("razas", "categorias"))
				.andExpect(content().string(
						org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("Nueva raza"))))
				.andExpect(content().string(
						org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("Eliminar"))));
	}

	@Test
	void muestraFormularioDeLogin() throws Exception {
		mockMvc.perform(get("/login"))
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Ingreso de administrador")));
	}

	@Test
	void categoriasRequiereLoginDeAdmin() throws Exception {
		mockMvc.perform(get("/categorias"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login"));

		mockMvc.perform(get("/categorias").with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(view().name("categorias/lista"))
				.andExpect(model().attributeExists("categorias"));
	}

	@Test
	void adminPuedeVerAccionesDeRazas() throws Exception {
		mockMvc.perform(get("/razas").with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(view().name("razas/lista"))
				.andExpect(model().attributeExists("razas", "categorias"))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Nueva raza")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Eliminar")));
	}

	@Test
	void rechazaRazaSinDatosObligatorios() throws Exception {
		mockMvc.perform(post("/razas/guardar")
						.with(user("admin").roles("ADMIN"))
						.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("razas/formulario"))
				.andExpect(model().attributeHasFieldErrors(
						"raza", "nombre", "especie", "descripcion", "origen",
						"esperanzaVida", "categoria"));
	}

	@Test
	void guardaYEntregaImagenPersistida() throws Exception {
		CategoriaAnimal categoria = categoriaServicio.listar().getFirst();
		Raza raza = new Raza("Raza con imagen", "Perro", "Descripcion de prueba",
				"El Salvador", 10, categoria);
		MockMultipartFile imagen = new MockMultipartFile(
				"archivoImagen", "prueba.png", "image/png", new byte[] { 1, 2, 3, 4 });

		Raza guardada = razaServicio.guardar(raza, imagen);

		mockMvc.perform(get("/razas/{id}/imagen", guardada.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType("image/png"))
				.andExpect(content().bytes(new byte[] { 1, 2, 3, 4 }));
	}
}
