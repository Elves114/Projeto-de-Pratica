package WD.Works.Dto.ProdutoDto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class ProdutoRequest {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "Preço de compra é obrigatório")
    @DecimalMin(value = "0.01")
    private BigDecimal precoCompra;

    @NotNull(message = "Preço de venda é obrigatório")
    @DecimalMin(value = "0.01")
    private BigDecimal precoVenda;

    @NotNull(message = "Stock é obrigatório")
    @Min(0)
    private Integer stock;
}