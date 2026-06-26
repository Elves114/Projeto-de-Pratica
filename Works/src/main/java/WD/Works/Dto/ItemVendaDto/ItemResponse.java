package WD.Works.Dto.ItemVendaDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private String nomeDoProduto;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal subtotal;
}
