package mini_.projek_guna.service;

import jakarta.transaction.Transactional;
import mini_.projek_guna.Exception.ValidasiException;
import mini_.projek_guna.Repository.StockMutationRepository;
import mini_.projek_guna.Repository.ProdukRepository;
import mini_.projek_guna.Repository.WarehouseRepository;
import mini_.projek_guna.Repository.WarehouseStockRepository;
import mini_.projek_guna.model.Produk;
import mini_.projek_guna.model.StockMutation;
import mini_.projek_guna.model.Warehouse;
import mini_.projek_guna.model.WarehouseStock;
import mini_.projek_guna.request.StockInRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WarehouseStockService {

    private final WarehouseStockRepository stockRepository;
    private final ProdukRepository produkRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockMutationRepository stockMutationRepository;

    public WarehouseStockService(WarehouseStockRepository stockRepository, ProdukRepository produkRepository, WarehouseRepository warehouseRepository, StockMutationRepository stockMutationRepository) {
        this.stockRepository = stockRepository;
        this.produkRepository = produkRepository;
        this.warehouseRepository = warehouseRepository;
        this.stockMutationRepository = stockMutationRepository;
    }

    @Transactional
    public WarehouseStock stockIn(StockInRequest request) {
        Produk produk = produkRepository.findBySkuCodeAndIsActiveTrue(request.getSkuCode())
                .orElseThrow(() -> new ValidasiException("Product tidak ditemukan atau sudah nonaktif"));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ValidasiException("Warehouse tidak ditemukan"));

        WarehouseStock warehouseStock = stockRepository.findByProdukAndWarehouse(produk, warehouse)
                .orElseGet(() -> {
                    WarehouseStock newStock = new WarehouseStock();
                    newStock.setProduk(produk);
                    newStock.setWarehouse(warehouse);
                    newStock.setStock(0);
                    return newStock;
                });

        warehouseStock.setStock(warehouseStock.getStock() + request.getQuantity());
        WarehouseStock savedStock = stockRepository.save(warehouseStock);

        StockMutation mutation = new StockMutation();
        mutation.setProduk(produk);
        mutation.setFromWarehouse(null);
        mutation.setToWarehouse(warehouse);
        mutation.setQuantity(request.getQuantity());
        mutation.setType("IN");
        mutation.setTimestamp(LocalDateTime.now());
        stockMutationRepository.save(mutation);

        return savedStock;
    }

    public List<WarehouseStock> getAll() {
        return stockRepository.findAll();
    }

    public WarehouseStock getById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new ValidasiException("Stok tidak ditemukan"));
    }
}
