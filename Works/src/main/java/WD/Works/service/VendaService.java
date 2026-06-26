package WD.Works.service;

import WD.Works.Dto.ItemVendaDto.ItemRequest;
import WD.Works.Dto.VendaDto.VendaRequest;
import WD.Works.Dto.VendaDto.VendaResponse;
import WD.Works.model.Produto;
import WD.Works.model.Venda;
import WD.Works.repository.ProdutoRepository;
import WD.Works.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepo;

    private ProdutoRepository produtoRepo;

    private VendaResponse mapToResponse(Venda venda) {
        VendaResponse dto = new VendaResponse();
        dto.setId(venda.getId());
        dto.setDataVenda(venda.getData());
        dto.setTotal(venda.getTotal());
        dto.setLucroTotal(venda.getLucroTotal());
        return dto;
    }


    public VendaResponse registarVenda(VendaRequest request) {
        BigDecimal totalVenda = BigDecimal.ZERO;
        BigDecimal lucroTotal = BigDecimal.ZERO;

        for (ItemRequest item : request.getItems()) {
            Produto produto = produtoRepo.findById(item.getProdutoId()).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            if (item.getQuantidade() > produto.getStock()) {
                throw new RuntimeException("Quantidade de produtos insuficiente");
            }
            BigDecimal subtotal = produto.getPrecoVenda().multiply(BigDecimal.valueOf(item.getQuantidade()));

            totalVenda = totalVenda.add(subtotal);

            BigDecimal lucroUnitario = produto.getPrecoVenda().subtract(produto.getPrecoCompra());

            BigDecimal lucroItem = lucroUnitario.multiply(BigDecimal.valueOf(item.getQuantidade()));

            lucroTotal = lucroTotal.add(lucroItem);

            produto.setStock(produto.getStock() - item.getQuantidade());

            produtoRepo.save(produto);
        }
        Venda venda = new Venda();

        venda.setData(LocalDateTime.now());
        venda.setTotal(totalVenda);
        venda.setLucroTotal(lucroTotal);

        Venda vendaSalva = vendaRepo.save(venda);

        VendaResponse response = new VendaResponse();

        response.setId(vendaSalva.getId());
        response.setDataVenda(vendaSalva.getData());
        response.setTotal(vendaSalva.getTotal());
        response.setLucroTotal(vendaSalva.getLucroTotal());

        return response;

    }

    public List<VendaResponse> listarVendas() {
        return vendaRepo.findAll().stream().map(this::mapToResponse).toList();
    }

    public VendaResponse buscarPorId(Long id) {

        Venda venda = vendaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        VendaResponse response = new VendaResponse();

        response.setId(venda.getId());
        response.setDataVenda(venda.getData());
        response.setTotal(venda.getTotal());
        response.setLucroTotal(venda.getLucroTotal());

        return response;
    }


}
