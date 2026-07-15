package ve.com.vettal.trademarketing.features.catalogos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.catalogos.model.RegionModel;

public interface RegionRepository extends JpaRepository<RegionModel, Long> {

	List<RegionModel> findAllByOrderByNombreAsc();

	boolean existsByNombreIgnoreCase(String nombre);
}
