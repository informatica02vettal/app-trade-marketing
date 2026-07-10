package ve.com.vettal.trademarketing.features.catalogos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.catalogos.model.CategoriaMaterialModel;
import ve.com.vettal.trademarketing.features.catalogos.model.FamiliaMaterial;

public interface CategoriaMaterialRepository extends JpaRepository<CategoriaMaterialModel, Long> {

	List<CategoriaMaterialModel> findByActivoTrue();

	List<CategoriaMaterialModel> findByFamiliaAndActivoTrue(FamiliaMaterial familia);
}
