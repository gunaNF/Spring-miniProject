package mini_.projek_guna.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min; // Tambahkan ini
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isActive = true;

    @Column(unique = true)
    private String skuCode;

    private String name;

    private Double price;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}