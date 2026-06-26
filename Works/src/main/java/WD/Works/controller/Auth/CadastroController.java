package WD.Works.controller.Auth;

import WD.Works.model.Usuario;
import WD.Works.service.Auth.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cadastro")
public class CadastroController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public String formulario(Model model) {

        model.addAttribute("usuario", new Usuario());

        return "cadastro";
    }

    @PostMapping
    public String salvar(
            @Valid Usuario usuario,
            BindingResult result) {

        if (result.hasErrors()) {
            return "cadastro";
        }
        System.out.println("Entrou no método salvar");
        service.cadastrar(usuario);

        return "redirect:/login";
    }
}