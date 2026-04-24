package mini_.projek_guna.Repository;

import jakarta.transaction.Transactional;
import mini_.projek_guna.model.StockMutation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMutationRepository extends JpaRepository<StockMutation,Long> {
    @Modifying
    @Transactional
    @Query("UPDATE StockMutation sm SET sm.produk = null WHERE sm.produk.id = :produkId")
     void setProdukNullByProdukId(@Param("produkId") Long produkId);

}