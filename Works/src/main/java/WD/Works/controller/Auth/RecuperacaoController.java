package WD.Works.controller.Auth;

import WD.Works.service.Auth.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RecuperacaoController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/resetar-senha")
    public String salvarNovaSenha(
            @RequestParam String token,
            @RequestParam String novaSenha) {

        usuarioService.resetarSenha(token, novaSenha);

        return "redirect:/login?reset=sucesso";

    }
}
