package mini_.projek_guna.service;

import jakarta.transaction.Transactional;
import mini_.projek_guna.Repository.CategoryRepository;
import mini_.projek_guna.Repository.ProdukRepository;
import mini_.projek_guna.Repository.WarehouseStockRepository;
import mini_.projek_guna.Exception.ValidasiException;
import mini_.projek_guna.model.Category;
import mini_.projek_guna.model.Produk;
import mini_.projek_guna.request.ProdukCreateRequest;
import mini_.projek_guna.request.ProdukUpdateRequest;
import mini_.projek_guna.response.LowStockResponse;
import mini_.projek_guna.response.StockSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdukService {

    private final ProdukRepository produkRepository;
    private final CategoryRepository categoryRepository;
    private final WarehouseStockRepository warehouseStockRepository;

    public ProdukService(ProdukRepository produkRepository, CategoryRepository categoryRepository, WarehouseStockRepository warehouseStockRepository) {
        this.produkRepository = produkRepository;
        this.categoryRepository = categoryRepository;
        this.warehouseStockRepository = warehouseStockRepository;
    }

    // 1. CREATE: Tambah Produk Baru
    @Transactional
    public Produk createProduk(ProdukCreateRequest request) {
        if (produkRepository.findBySkuCode(request.getSku_code()).isPresent()) {
            throw new ValidasiException("SKU produk sudah terdaftar");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ValidasiException("Category tidak ditemukan"));

        Produk produk = new Produk();
        produk.setSkuCode(request.getSku_code());
        produk.setName(request.getName());
        produk.setPrice(request.getPrice());
        produk.setCategory(category);
        produk.setIsActive(request.getIs_active() != null ? request.getIs_active() : true);

        return produkRepository.save(produk);
    }

    // 2. UPDATE: Ubah data produk
    @Transactional
    public Produk updateProduk(Long id, ProdukUpdateRequest request) {
        Produk produk = produkRepository.findById(id)
                .orElseThrow(() -> new ValidasiException("Product tidak ditemukan"));

        produkRepository.findBySkuCode(request.getSku_code())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ValidasiException("SKU produk sudah terdaftar");
                });

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ValidasiException("Category tidak ditemukan"));

        produk.setSkuCode(request.getSku_code());
        produk.setName(request.getName());
        produk.setPrice(request.getPrice());
        produk.setCategory(category);

        if (request.getIs_active() != null) {
            produk.setIsActive(request.getIs_active());
        }

        return produkRepository.save(produk);
    }

    public List<Produk> getAll(){
        return produkRepository.findAllByIsActiveTrue();
    }

    @Transactional
    public void hapusProduk(Long id) {
        Produk produk = produkRepository.findById(id)
                .orElseThrow(() -> new ValidasiException("Product tidak ditemukan"));

        produk.setIsActive(false);
        produkRepository.save(produk);
    }

    @Transactional
    public Produk restoreProduk(Long id) {
        Produk produk = produkRepository.findById(id)
                .orElseThrow(() -> new ValidasiException("Product tidak ditemukan"));

        produk.setIsActive(true);
        return produkRepository.save(produk);
    }

    public Produk getById(Long id) {
        return produkRepository.findById(id)
                .orElseThrow(() -> new ValidasiException("Product tidak ditemukan"));
    }

    public Produk getActiveBySku(String skuCode) {
        return produkRepository.findBySkuCodeAndIsActiveTrue(skuCode)
                .orElseThrow(() -> new ValidasiException("Product tidak ditemukan atau sudah nonaktif"));
    }

    public List<LowStockResponse> getLowStockProducts() {
        return warehouseStockRepository.findLowStockProducts();
    }

    public StockSummaryResponse getStockSummary(String skuCode) {
        Produk produk = getActiveBySku(skuCode);
        Integer totalStock = warehouseStockRepository.hitungTotalStok(skuCode);
        return new StockSummaryResponse(produk.getSkuCode(), totalStock == null ? 0 : totalStock);
    }
}
