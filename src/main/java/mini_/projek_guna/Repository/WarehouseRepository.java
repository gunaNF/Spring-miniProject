package mini_.projek_guna.Repository;

import mini_.projek_guna.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Optional<Warehouse> findByCode(String code);
    boolean existsByCodeAndIdNot(String code,Long id);
}