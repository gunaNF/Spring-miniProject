package mini_.projek_guna.controller;

import jakarta.validation.Valid;
import mini_.projek_guna.model.StockMutation;
import mini_.projek_guna.request.StockTransferRequest;
import mini_.projek_guna.response.WebResponse;
import mini_.projek_guna.service.StockMuntationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockMuntationController {

    private final StockMuntationService mutationService;

    public StockMuntationController(StockMuntationService mutationService) {
        this.mutationService = mutationService;
    }

    @PostMapping("/transfer")
    public WebResponse<StockMutation> transfer(@Valid @RequestBody StockTransferRequest request) {
        StockMutation result = mutationService.transferStock(request);

        return WebResponse.<StockMutation>builder()
                .status("Success")
                .message("Mutasi stok berhasil dicatat")
                .data(result)
                .build();
    }

    @GetMapping("/mutations")
    public WebResponse<List<StockMutation>> getAll() {
        List<StockMutation> result = mutationService.getAll();
        return WebResponse.<List<StockMutation>>builder()
                .status("Success")
                .message("Riwayat mutasi berhasil diambil")
                .data(result)
                .build();
    }
}
