package WD.Works.controller.page;

import WD.Works.Dto.ProdutoDto.ProdutoRequest;
import WD.Works.model.Produto;
import WD.Works.model.Usuario;
import WD.Works.repository.Auth.UsuarioRepository;
import WD.Works.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ViewController {


    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public String pagina(Model model) {

        model.addAttribute("produto", new ProdutoRequest());

        return "produtos";
    }

    @PostMapping("/salvar")
    public String salvarProduto(@ModelAttribute Produto produto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        produto.setUsuario(usuario);

        produtoRepository.save(produto);

        return "redirect:/produtos";
    }

    @GetMapping("/buscar")
    public String listarProdutos(Model model) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        List<Produto> produtos =
                produtoRepository.findByUsuarioId(usuario.getId());

        model.addAttribute("produtos", produtos);
        model.addAttribute("produto", new Produto());

        return "produtos";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarProduto(@PathVariable Long id,
                                   @ModelAttribute Produto dadosAtualizados) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Produto produto = produtoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));


        if (!produto.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado");
        }

        produto.setNome(dadosAtualizados.getNome());
        produto.setPrecoCompra(dadosAtualizados.getPrecoCompra());
        produto.setPrecoVenda(dadosAtualizados.getPrecoVenda());
        produto.setStock(dadosAtualizados.getStock());

        produtoRepository.save(produto);

        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String abrirEdicao(@PathVariable Long id,
                              Model model) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Produto produto = produtoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (!produto.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado");
        }

        model.addAttribute("produto", produto);

        return "editar-produto";
    }

    @PostMapping("/remover/{id}")
    public String removerProduto(@PathVariable Long id) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseThrow();

        Produto produto = produtoRepository
                .findById(id)
                .orElseThrow();

        if (!produto.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado");
        }

        produtoRepository.delete(produto);

        return "redirect:/produtos";
    }

    @GetMapping("/pesquisar")
    public String pesquisarProduto(@RequestParam String nome,
                                   Model model) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        List<Produto> produtos =
                produtoRepository
                        .findByUsuarioIdAndNomeContainingIgnoreCase(
                                usuario.getId(),
                                nome
                        );

        model.addAttribute("produtos", produtos);
        model.addAttribute("produto", new Produto());

        return "produtos";
    }
    @GetMapping("/perfil")
    public String perfil(Model model) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        model.addAttribute("usuario", usuario);

        return "perfil";
    }

}
