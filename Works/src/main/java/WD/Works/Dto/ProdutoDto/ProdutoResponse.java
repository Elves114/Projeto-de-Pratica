package WD.Works.Dto.ProdutoDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponse {
    private Long id;
    @NotBlank
    private String nome;
    private BigDecimal precoCompra;
    private BigDecimal precoVenda;
    private BigDecimal lucro;
    private Integer stock;
}
