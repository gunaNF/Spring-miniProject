package mini_.projek_guna.Repository;

import mini_.projek_guna.model.StockMutation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMutationRepository extends JpaRepository<StockMutation,Long> {
}
