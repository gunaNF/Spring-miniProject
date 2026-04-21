package mini_.projek_guna.controller;

import jakarta.validation.Valid;
import mini_.projek_guna.model.WarehouseStock;
import mini_.projek_guna.request.StockInRequest;
import mini_.projek_guna.response.WebResponse;
import mini_.projek_guna.service.WarehouseStockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class WarehouseStockController {

    private final WarehouseStockService stockService;

    public WarehouseStockController(WarehouseStockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/in")
    public WebResponse<WarehouseStock> create(@Valid @RequestBody StockInRequest request) {
        WarehouseStock result = stockService.stockIn(request);
        return WebResponse.<WarehouseStock>builder()
                .status("Success")
                .message("Stok awal berhasil ditambahkan")
                .data(result)
                .build();
    }

    @GetMapping
    public WebResponse<List<WarehouseStock>> getAll() {
        List<WarehouseStock> list = stockService.getAll();
        return WebResponse.<List<WarehouseStock>>builder()
                .status("Success")
                .message("Semua data stok berhasil diambil")
                .data(list)
                .build();
    }

    @GetMapping("/{id}")
    public WebResponse<WarehouseStock> getById(@PathVariable Long id) {
        WarehouseStock result = stockService.getById(id);
        return WebResponse.<WarehouseStock>builder()
                .status("Success")
                .message("Detail stok ditemukan")
                .data(result)
                .build();
    }
}
