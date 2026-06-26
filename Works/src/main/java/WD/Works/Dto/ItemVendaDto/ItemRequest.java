package WD.Works.Dto.ItemVendaDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {
    private Long ProdutoId;
    private Integer quantidade;
}
