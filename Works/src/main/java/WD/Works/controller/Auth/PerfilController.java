package WD.Works.controller.Auth;

import WD.Works.model.Usuario;
import WD.Works.repository.Auth.UsuarioRepository;
import WD.Works.repository.ProdutoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerfilController {

    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;

    public PerfilController(UsuarioRepository usuarioRepository,
                            ProdutoRepository produtoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/perfil")
    public String perfil(Authentication authentication,
                         Model model) {

        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow();

        long totalProdutos = produtoRepository.count();

        model.addAttribute("usuario", usuario);
        model.addAttribute("totalProdutos", totalProdutos);

        return "perfil";
    }
}


