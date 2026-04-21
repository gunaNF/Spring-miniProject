package mini_.projek_guna.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockSummaryResponse {
    private String skuCode;
    private Integer totalStock;
}
