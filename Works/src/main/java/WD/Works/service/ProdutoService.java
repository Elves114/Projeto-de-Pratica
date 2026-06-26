package WD.Works.service;

import WD.Works.Dto.ProdutoDto.ProdutoRequest;
import WD.Works.Dto.ProdutoDto.ProdutoResponse;
import WD.Works.model.Produto;
import WD.Works.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepo;

    private ProdutoResponse mapToResponse(Produto produto) {
        ProdutoResponse dto = new ProdutoResponse();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setPrecoCompra(produto.getPrecoCompra());
        dto.setPrecoVenda(produto.getPrecoVenda());
        dto.setStock(produto.getStock());
        dto.setLucro(
                produto.getPrecoVenda()
                        .subtract(produto.getPrecoCompra())
        );

        return dto;
    }

    public List<ProdutoResponse> getProdutos() {
        return produtoRepo.findAll().stream().map(this::mapToResponse).toList();
    }

    public ProdutoResponse salvarProduto(ProdutoRequest dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPrecoCompra(dto.getPrecoCompra());
        produto.setPrecoVenda(dto.getPrecoVenda());
        produto.setStock(dto.getStock());

        Produto Salvo = produtoRepo.save(produto);
        return mapToResponse(Salvo);

    }

    public ProdutoResponse atualizarProduto(Long id, ProdutoRequest dto) {
        Produto produto = produtoRepo.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setNome(dto.getNome());
        produto.setPrecoCompra(dto.getPrecoCompra());
        produto.setPrecoVenda(dto.getPrecoVenda());
        produto.setStock(dto.getStock());
        Produto Salvo = produtoRepo.save(produto);
        return mapToResponse(Salvo);
    }

    public void excluirProduto(Long id) {
        Produto produto = produtoRepo.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produtoRepo.delete(produto);
    }

    public ProdutoResponse buscarPorid(Long id) {
        Produto produto = produtoRepo.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return mapToResponse(produto);
    }

    public List<ProdutoResponse> buscarPorNome(String nome) {

        return produtoRepo
                .findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

}
