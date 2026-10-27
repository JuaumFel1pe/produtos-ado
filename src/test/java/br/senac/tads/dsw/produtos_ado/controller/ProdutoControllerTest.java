package br.senac.tads.dsw.produtos_ado.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import br.senac.tads.dsw.produtos_ado.dto.CategoriaRequestDTO;
import br.senac.tads.dsw.produtos_ado.dto.ProdutoRequestDTO;
import br.senac.tads.dsw.produtos_ado.model.Categoria;
import br.senac.tads.dsw.produtos_ado.model.Produto;
import br.senac.tads.dsw.produtos_ado.repository.CategoriaRepository;
import br.senac.tads.dsw.produtos_ado.repository.ProdutoRepository;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

@SpringBootTest 
@AutoConfigureMockMvc 
@Transactional 
public class ProdutoControllerTest {

    @Autowired 
    private MockMvc mockMvc;

    @Autowired 
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired 
    private ObjectMapper objectMapper;

    private Categoria categoriaPadrao;

    @BeforeEach 
    void setUp(){
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Eletrônico");

        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        categoriaPadrao = categoriaRepository.save(categoria);
    }

    @Test 
    void deveCriarProdutoComSucesso() throws Exception{
        ProdutoRequestDTO dto = new ProdutoRequestDTO("RTX 5090", new BigDecimal(4800.0), categoriaPadrao.getId());

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(
                        post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("RTX 5090"))
                .andExpect(jsonPath("$.preco").value(4800.0))
                .andExpect(jsonPath("$.nomeCategoria").value("Eletrônico"));
    }

    @Test 
    void deveListarTodosOsProdutos() throws Exception{
        ProdutoRequestDTO dto = new ProdutoRequestDTO("RTX 5090", new BigDecimal(4800.0), categoriaPadrao.getId());
        
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());
        produto.setCategoria(categoriaPadrao);
        produtoRepository.save(produto);

        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test 
    void deveBuscarProdutoPorId() throws Exception{
        ProdutoRequestDTO dto = new ProdutoRequestDTO("RTX 5090", new BigDecimal(4800.0), categoriaPadrao.getId());
        
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());
        produto.setCategoria(categoriaPadrao);
        produto = produtoRepository.save(produto);

        mockMvc.perform(get("/api/produtos/" + produto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(produto.getId()))
                .andExpect(jsonPath("$.nome").value("RTX 5090"))
                .andExpect(jsonPath("$.preco").value(4800.0))
                .andExpect(jsonPath("$.nomeCategoria").value("Eletrônico"));
    }

    @Test 
    void deveAtualizarProduto() throws Exception{
        ProdutoRequestDTO dto = new ProdutoRequestDTO("RTX 5090", new BigDecimal(4800.0), categoriaPadrao.getId());
        
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());
        produto.setCategoria(categoriaPadrao);
        Produto produtoSalvo = produtoRepository.save(produto);

        ProdutoRequestDTO dtoSalvo = new ProdutoRequestDTO("RTX 3060", new BigDecimal(2000.0), produtoSalvo.getCategoria().getId());

        String json = objectMapper.writeValueAsString(dtoSalvo);

        mockMvc.perform(
                        put("/api/produtos/" + produtoSalvo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("RTX 3060"))
                .andExpect(jsonPath("$.preco").value(2000.0));
    }

    @Test 
    void deveApagarProduto() throws Exception{
        ProdutoRequestDTO dto = new ProdutoRequestDTO("RTX 5090", new BigDecimal(4800.0), categoriaPadrao.getId());
        
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());
        produto.setCategoria(categoriaPadrao);

        Produto produtoSalvo = produtoRepository.save(produto);

        mockMvc.perform(delete("/api/produtos/" + produtoSalvo.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/produtos/" + produtoSalvo.getId()))
                .andExpect(status().isNotFound());
    }
}
