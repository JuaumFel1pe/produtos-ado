package br.senac.tads.dsw.produtos_ado.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import tools.jackson.databind.ObjectMapper;
import br.senac.tads.dsw.produtos_ado.dto.CategoriaRequestDTO;
import br.senac.tads.dsw.produtos_ado.model.Categoria;
import br.senac.tads.dsw.produtos_ado.repository.CategoriaRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest 
@AutoConfigureMockMvc 
@Transactional 
public class CategoriaControllerTest {

    @Autowired 
    private MockMvc mockMvc;

    @Autowired 
    private CategoriaRepository repository;

    @Autowired 
    private ObjectMapper objectMapper;

    @Test
    void deveCriarCategoriaComSucesso() throws Exception{
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Eletrônico");

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(
                        post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Eletrônico"));
    }

    @Test 
    void deveListarTodasAsCategorias() throws Exception{
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Eletrônico");

        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());

        repository.save(categoria);

        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test 
    void deveBuscarCategoriaPorId() throws Exception{
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Eletrônico");

        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());

        repository.save(categoria);

        mockMvc.perform(get("/api/categorias/" + categoria.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoria.getId()))
                .andExpect(jsonPath("$.nome").value("Eletrônico"));
    }

    @Test
    void deveAtualizarCategoria() throws Exception{
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Eletrônico");

        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());

        Categoria categoriaSalva = repository.save(categoria);

        categoriaSalva.setNome("Bebida");
        String json = objectMapper.writeValueAsString(categoriaSalva);

        mockMvc.perform(
                        put("/api/categorias/" + categoria.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)    
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Bebida"));
    }

    @Test 
    void deveApagarCategoria() throws Exception{
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Eletrônico");

        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());

        repository.save(categoria);

        mockMvc.perform(delete("/api/categorias/" + categoria.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/categorias/" + categoria.getId()))
                .andExpect(status().isNotFound());
    }
}
