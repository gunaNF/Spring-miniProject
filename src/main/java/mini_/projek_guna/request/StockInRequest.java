package mini_.projek_guna.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class StockInRequest {

    @NotBlank(message = "SKU tidak boleh kosong")
    @JsonProperty("sku_code")
    private String skuCode;

    @NotNull(message = "Warehouse ID tidak boleh kosong")
    @JsonProperty("warehouse_id")
    private Long warehouseId;

    @NotNull(message = "Quantity tidak boleh kosong")
    @Positive(message = "Quantity harus lebih dari 0")
    private Integer quantity;
}
