package mini_.projek_guna.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProdukUpdateRequest {


    private Boolean is_active; // Bisa null kalau tidak ingin diubah lewat sini

    @NotBlank(message = "SKU tidak boleh kosong")
    private String sku_code;

    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    @NotNull(message = "Harga tidak boleh kosong")
    @Min(value = 1000, message = "Harga minimal 1.000") // Sesuai spek Bagian 2
    private Double price;

    @NotNull(message = "Category ID tidak boleh kosong")
    @JsonProperty("category_id")
    private Long categoryId;
}