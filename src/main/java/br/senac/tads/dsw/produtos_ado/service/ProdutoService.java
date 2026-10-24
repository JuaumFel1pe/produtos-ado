package br.senac.tads.dsw.produtos_ado.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.senac.tads.dsw.produtos_ado.dto.ProdutoRequestDTO;
import br.senac.tads.dsw.produtos_ado.dto.ProdutoResponseDTO;
import br.senac.tads.dsw.produtos_ado.model.Categoria;
import br.senac.tads.dsw.produtos_ado.model.Produto;
import br.senac.tads.dsw.produtos_ado.repository.CategoriaRepository;
import br.senac.tads.dsw.produtos_ado.repository.ProdutoRepository;

@Service 
public class ProdutoService {
    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    
    public ProdutoService(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoResponseDTO> listarTodos(){
        return produtoRepository.findAll()
                                .stream()
                                .map(p -> new ProdutoResponseDTO(p.getId(), p.getNome(), p.getPreco(), p.getCategoria().getNome()))
                                .toList();
    }

    public ProdutoResponseDTO buscarPorId(Long id){
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getPreco(), produto.getCategoria().getNome());
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO dto){
        Categoria categoria = categoriaRepository.findById(dto.categoriaId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrado"));

        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());
        produto.setCategoria(categoria);

        produto = produtoRepository.save(produto);
        return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getPreco(), produto.getCategoria().getNome());
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto){
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        Categoria categoria = categoriaRepository.findById(dto.categoriaId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrado"));

        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());
        produto.setCategoria(categoria);
        produto = produtoRepository.save(produto);

        return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getPreco(), produto.getCategoria().getNome());
    }

    public void apagar(Long id){
        if(!produtoRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        produtoRepository.deleteById(id);
    }
}
