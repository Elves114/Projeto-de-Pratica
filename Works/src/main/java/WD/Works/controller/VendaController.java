package WD.Works.controller;

import WD.Works.Dto.VendaDto.VendaRequest;
import WD.Works.Dto.VendaDto.VendaResponse;
import WD.Works.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venda")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @GetMapping
    public List<VendaResponse> listar() {
        return vendaService.listarVendas();
    }

    @GetMapping("/{id}")
    public VendaResponse buscar(@PathVariable Long id) {
        return vendaService.buscarPorId(id);
    }

    @PostMapping
    public VendaResponse salvar(@RequestBody VendaRequest venda) {
        return vendaService.registarVenda(venda);
    }
}