package WD.Works.Dto.VendaDto;

import WD.Works.Dto.ItemVendaDto.ItemResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaResponse {
    private Long id;
    private LocalDateTime dataVenda;
    private BigDecimal total;
    private BigDecimal lucroTotal;
    private List<ItemResponse> items = new ArrayList<>();
}
