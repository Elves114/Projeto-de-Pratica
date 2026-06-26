package WD.Works.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/clientes")
    public String clientes() {
        return "clientes";
    }

    @GetMapping("/relatorio")
    public String relatorio() {
        return "relatorio";
    }

    @GetMapping("/venda")
    public String venda() {
        return "venda";
    }
}