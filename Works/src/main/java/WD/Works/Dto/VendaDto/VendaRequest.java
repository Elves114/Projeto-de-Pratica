package WD.Works.Dto.VendaDto;

import WD.Works.Dto.ItemVendaDto.ItemRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaRequest {
    private List<ItemRequest> items = new ArrayList<>();
}
