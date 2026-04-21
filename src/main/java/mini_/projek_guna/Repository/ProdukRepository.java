package mini_.projek_guna.Repository;

import mini_.projek_guna.model.Produk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdukRepository extends JpaRepository<Produk,Long> {
    Optional<Produk> findBySkuCode(String skuCode);

    Optional<Produk> findBySkuCodeAndIsActiveTrue(String skuCode);
    java.util.List<Produk> findAllByIsActiveTrue();
}
