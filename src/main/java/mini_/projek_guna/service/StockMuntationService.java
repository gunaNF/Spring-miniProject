package mini_.projek_guna.service;

import jakarta.transaction.Transactional;
import mini_.projek_guna.Exception.ValidasiException;
import mini_.projek_guna.Repository.ProdukRepository;
import mini_.projek_guna.Repository.StockMutationRepository;
import mini_.projek_guna.Repository.WarehouseRepository;
import mini_.projek_guna.Repository.WarehouseStockRepository;
import mini_.projek_guna.model.Produk;
import mini_.projek_guna.model.StockMutation;
import mini_.projek_guna.model.Warehouse;
import mini_.projek_guna.model.WarehouseStock;
import mini_.projek_guna.request.StockTransferRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockMuntationService {

    private final StockMutationRepository mutationRepository;
    private final WarehouseStockRepository stockRepository;
    private final ProdukRepository produkRepository;
    private final WarehouseRepository warehouseRepository;

    public StockMuntationService(StockMutationRepository mutationRepository,
                                WarehouseStockRepository stockRepository,
                                ProdukRepository produkRepository,
                                WarehouseRepository warehouseRepository) {
        this.mutationRepository = mutationRepository;
        this.stockRepository = stockRepository;
        this.produkRepository = produkRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    public StockMutation transferStock(StockTransferRequest request) {
        Produk produk = produkRepository.findBySkuCodeAndIsActiveTrue(request.getSkuCode())
                .orElseThrow(() -> new ValidasiException("Product tidak ditemukan atau sudah nonaktif"));
        Warehouse from = warehouseRepository.findById(request.getFromWarehouseId())
                .orElseThrow(() -> new ValidasiException("Warehouse asal tidak ditemukan"));
        Warehouse to = warehouseRepository.findById(request.getToWarehouseId())
                .orElseThrow(() -> new ValidasiException("Warehouse tujuan tidak ditemukan"));

        if (from.getId().equals(to.getId())) {
            throw new ValidasiException("Gudang asal dan tujuan tidak boleh sama");
        }

        WarehouseStock sourceStock = stockRepository.findByProdukAndWarehouse(produk, from)
                .orElseThrow(() -> new ValidasiException("Stok produk di gudang asal tidak ditemukan"));

        if (sourceStock.getStock() < request.getQuantity()) {
            throw new ValidasiException("Stok tidak mencukupi untuk dipindahkan");
        }
        sourceStock.setStock(sourceStock.getStock() - request.getQuantity());
        stockRepository.save(sourceStock);

        WarehouseStock destStock = stockRepository.findByProdukAndWarehouse(produk, to)
                .orElse(new WarehouseStock());

        if (destStock.getId() == null) {
            destStock.setProduk(produk);
            destStock.setWarehouse(to);
            destStock.setStock(0);
        }
        destStock.setStock(destStock.getStock() + request.getQuantity());
        stockRepository.save(destStock);

        StockMutation mutation = new StockMutation();
        mutation.setProduk(produk);
        mutation.setFromWarehouse(from);
        mutation.setToWarehouse(to);
        mutation.setQuantity(request.getQuantity());
        mutation.setType("TRANSFER");
        mutation.setTimestamp(LocalDateTime.now());

        return mutationRepository.save(mutation);
    }

    public List<StockMutation> getAll() {
        return mutationRepository.findAll();
    }
}
