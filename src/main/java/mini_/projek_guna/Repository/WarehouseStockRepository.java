package mini_.projek_guna.Repository;

import mini_.projek_guna.model.Produk;
import mini_.projek_guna.model.Warehouse;
import mini_.projek_guna.model.WarehouseStock;
import mini_.projek_guna.response.LowStockResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseStockRepository extends JpaRepository<WarehouseStock, Long> {
    Optional<WarehouseStock> findByProdukAndWarehouse(Produk produk, Warehouse warehouse);

    @Query("SELECT SUM(ws.stock) FROM WarehouseStock ws WHERE ws.produk.skuCode = :sku AND ws.produk.isActive = true")
    Integer hitungTotalStok(@Param("sku") String sku);

    @Query("SELECT new LowStockResponse(p.skuCode, p.name, w.code, w.name, ws.stock) FROM WarehouseStock ws JOIN ws.produk p JOIN ws.warehouse w WHERE p.isActive = true AND ws.stock < 10 ORDER BY ws.stock ASC")
    List<LowStockResponse> findLowStockProducts();
}
