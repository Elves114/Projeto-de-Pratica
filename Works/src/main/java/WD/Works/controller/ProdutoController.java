package WD.Works.controller;

import WD.Works.Dto.ProdutoDto.ProdutoRequest;
import WD.Works.Dto.ProdutoDto.ProdutoResponse;
import WD.Works.model.Produto;
import WD.Works.model.Usuario;
import WD.Works.repository.Auth.UsuarioRepository;
import WD.Works.repository.ProdutoRepository;
import WD.Works.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    


    @GetMapping("/produtos")
    public String listarProdutos(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Produto> produtos = produtoRepository.findByUsuarioId(usuario.getId());

        model.addAttribute("produtos", produtos);

        return "produtos";
    }

    @GetMapping("/api/produtos/{id}")
    public ProdutoResponse getProduto(@PathVariable Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!produto.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado");
        }

        return produtoService.buscarPorid(id);
    }

    @PostMapping("/api/produtos/salvar")
    public ProdutoResponse salvar(@Valid @RequestBody ProdutoRequest dto) {
        return produtoService.salvarProduto(dto);
    }

    @PutMapping("/api/produtos/{id}")
    public ProdutoResponse atualizar(@PathVariable Long id, @RequestBody ProdutoRequest dto) {
        return produtoService.atualizarProduto(id, dto);
    }

    @DeleteMapping("/api/produtos/{id}")
    public void excluir(@PathVariable Long id) {
        produtoService.excluirProduto(id);
    }
}
