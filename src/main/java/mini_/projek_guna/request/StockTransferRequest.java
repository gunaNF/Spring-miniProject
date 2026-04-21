package mini_.projek_guna.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class StockTransferRequest {

    @NotBlank(message = "SKU tidak boleh kosong")
    @JsonProperty("sku_code")
    private String skuCode;

    @NotNull(message = "Gudang asal tidak boleh kosong")
    @JsonProperty("from_warehouse_id")
    private Long fromWarehouseId;

    @NotNull(message = "Gudang tujuan tidak boleh kosong")
    @JsonProperty("to_warehouse_id")
    private Long toWarehouseId;

    @NotNull(message = "Quantity tidak boleh kosong")
    @Positive(message = "Quantity harus lebih dari 0")
    private Integer quantity;
}
