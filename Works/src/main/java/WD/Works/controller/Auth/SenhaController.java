package WD.Works.controller.Auth;

import WD.Works.service.Auth.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.security.Principal;
import java.util.UUID;

@Controller
public class SenhaController {
    @Autowired
    private UsuarioService service;


    @GetMapping("/alterar-senha")
    public String formularioAlterarSenha() {
        return "alterar-senha";
    }

    @PostMapping("/alterar-senha")
    public String alterarSenha(
            Principal principal,
            String senhaAtual,
            String novaSenha,
            String confirmarSenha) {

        service.alterarSenha(
                principal.getName(),
                senhaAtual,
                novaSenha,
                confirmarSenha
        );

        return "redirect:/home";
    }


    @PostMapping("/recuperar-senha")
    public String recuperarSenha(String email) {
        String token = UUID.randomUUID().toString();
        return token;

    }

    @GetMapping("/resetar-senha")
    public String telaNovaSenha(
            @RequestParam String token){

        return "nova-senha";
    }


}
