package mini_.projek_guna.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LowStockResponse {
    private String skuCode;
    private String productName;
    private String warehouseCode;
    private String warehouseName;
    private Integer stock;
}
