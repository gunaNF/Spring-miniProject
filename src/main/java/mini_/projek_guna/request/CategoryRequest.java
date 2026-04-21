package mini_.projek_guna.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "Nama Tidak Boleh Kosong!")
    private String name;
}
