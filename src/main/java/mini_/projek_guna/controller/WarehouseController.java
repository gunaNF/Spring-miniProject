package mini_.projek_guna.controller;

import jakarta.validation.Valid;
import mini_.projek_guna.model.Warehouse;
import mini_.projek_guna.request.WarehouseRequest;
import mini_.projek_guna.response.WebResponse;
import mini_.projek_guna.service.WarehouseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public WebResponse<Warehouse> create(@Valid @RequestBody WarehouseRequest request) {
        Warehouse result = warehouseService.createWarehouse(request);
        return WebResponse.<Warehouse>builder()
                .status("Success")
                .message("Warehouse berhasil didaftarkan")
                .data(result)
                .build();
    }

    @PutMapping("/{id}")
    public WebResponse<Warehouse> update(@PathVariable Long id, @Valid @RequestBody WarehouseRequest request) {
        Warehouse result = warehouseService.updateWarehouse(id, request);
        return WebResponse.<Warehouse>builder()
                .status("Success")
                .message("Data warehouse berhasil diperbarui")
                .data(result)
                .build();
    }

    @GetMapping
    public WebResponse<List<Warehouse>> getAll() {
        List<Warehouse> result = warehouseService.getAll();
        return WebResponse.<List<Warehouse>>builder()
                .status("Success")
                .message("Semua data warehouse berhasil diambil")
                .data(result)
                .build();
    }

    @DeleteMapping("/{id}")
    public WebResponse<String> delete(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
        return WebResponse.<String>builder()
                .status("Success")
                .message("Warehouse berhasil dihapus")
                .data("ID: " + id)
                .build();
    }
}
