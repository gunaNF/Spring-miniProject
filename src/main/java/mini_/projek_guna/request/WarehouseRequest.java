package mini_.projek_guna.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WarehouseRequest {
    @NotBlank(message = "Kode gudang tidak boleh kosong")
    private String code;

    @NotBlank(message = "Nama gudang tidak boleh kosong")
    private String name;
}