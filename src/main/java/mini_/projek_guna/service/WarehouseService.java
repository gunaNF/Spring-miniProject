package mini_.projek_guna.service;

import jakarta.transaction.Transactional;
import mini_.projek_guna.Exception.ValidasiException;
import mini_.projek_guna.Repository.WarehouseRepository;
import mini_.projek_guna.model.Warehouse;
import mini_.projek_guna.request.WarehouseRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    // CREATE
    @Transactional
    public Warehouse createWarehouse(WarehouseRequest request) {
        warehouseRepository.findByCode(request.getCode())
                .ifPresent(existing -> {
                    throw new ValidasiException("Kode gudang sudah terdaftar");
                });

        Warehouse warehouse = new Warehouse();
        warehouse.setCode(request.getCode());
        warehouse.setName(request.getName());
        return warehouseRepository.save(warehouse);
    }

    // UPDATE
    @Transactional
    public Warehouse updateWarehouse(Long id, WarehouseRequest request) {
        if (warehouseRepository.existsByCodeAndIdNot(request.getCode(), id)){
            throw new IllegalArgumentException("kode gudang sudah digunakan: " +request.getCode());
        }

        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(()-> new ValidasiException("Warehouse tidak ditemukan dengan id " + id));
        warehouse.setCode(request.getCode());
        warehouse.setName(request.getName());
        return warehouseRepository.save(warehouse);
    }

    // READ ALL
    public List<Warehouse> getAll() {
        return warehouseRepository.findAll();
    }

    //4DELETE
    @Transactional
    public void deleteWarehouse(Long id) {
        if (!warehouseRepository.existsById(id)) {
            throw new ValidasiException("Warehouse tidak ditemukan");
        }
        warehouseRepository.deleteById(id);
    }

    public Warehouse getById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new ValidasiException("Warehouse tidak ditemukan"));
    }
}
