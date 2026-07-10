package ve.com.vettal.trademarketing.features.catalogos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.catalogos.model.MarcaModel;

public interface MarcaRepository extends JpaRepository<MarcaModel, Long> {

	List<MarcaModel> findByActivoTrue();
}
