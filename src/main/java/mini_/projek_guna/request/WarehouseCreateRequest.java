package mini_.projek_guna.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseCreateRequest {

    @NotBlank(message = "Kode gudang tidak boleh kosong")
    private String code;

    @NotBlank(message = "Nama gudang tidak boleh kosong")
    private String name;
}