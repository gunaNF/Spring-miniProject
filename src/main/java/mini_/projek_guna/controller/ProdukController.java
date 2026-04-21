package mini_.projek_guna.controller;

import jakarta.validation.Valid;
import mini_.projek_guna.model.Produk;
import mini_.projek_guna.request.ProdukCreateRequest;
import mini_.projek_guna.request.ProdukUpdateRequest;
import mini_.projek_guna.response.LowStockResponse;
import mini_.projek_guna.response.StockSummaryResponse;
import mini_.projek_guna.response.WebResponse;
import mini_.projek_guna.service.ProdukService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProdukController {

    private final ProdukService produkService;

    public ProdukController(ProdukService produkService) {
        this.produkService = produkService;
    }

    // 1. CREATE
    @PostMapping
    public WebResponse<Produk> createProduk(@Valid @RequestBody ProdukCreateRequest request) {
        Produk produk = produkService.createProduk(request);

        return WebResponse.<Produk>builder()
                .status("Success")
                .message("Product berhasil disimpan")
                .data(produk)
                .build();
    }

    // 2. UPDATE
    @PutMapping("/{id}")
    public WebResponse<Produk> updateProduk(@PathVariable Long id, @Valid @RequestBody ProdukUpdateRequest request) {
        Produk produk = produkService.updateProduk(id, request);

        return WebResponse.<Produk>builder()
                .status("Success")
                .message("Product berhasil diupdate")
                .data(produk)
                .build();
    }

    // 3. GET ALL
    @GetMapping
    public WebResponse<List<Produk>> getAll() {
        List<Produk> list = produkService.getAll();

        return WebResponse.<List<Produk>>builder()
                .status("Success")
                .message("Semua data product aktif berhasil diambil")
                .data(list)
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public WebResponse<String> delete(@PathVariable Long id) {
        produkService.hapusProduk(id);

        return WebResponse.<String>builder()
                .status("Success")
                .message("Product berhasil dinonaktifkan")
                .data("ID: " + id)
                .build();
    }

    @PutMapping("/restore/{id}")
    public WebResponse<Produk> restore(@PathVariable Long id) {
        return WebResponse.<Produk>builder()
                .status("Success")
                .message("Product berhasil diaktifkan kembali")
                .data(produkService.restoreProduk(id))
                .build();
    }

    @GetMapping("/low-stock")
    public WebResponse<List<LowStockResponse>> lowStock() {
        return WebResponse.<List<LowStockResponse>>builder()
                .status("Success")
                .message("Daftar product dengan stok rendah berhasil diambil")
                .data(produkService.getLowStockProducts())
                .build();
    }

    @GetMapping("/{sku}/stock-summary")
    public WebResponse<StockSummaryResponse> stockSummary(@PathVariable String sku) {
        return WebResponse.<StockSummaryResponse>builder()
                .status("Success")
                .message("Ringkasan stok product berhasil diambil")
                .data(produkService.getStockSummary(sku))
                .build();
    }
}
